import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainPanel extends JPanel implements KeyListener, Subject<Ball> {
    private List<Ball> paintingBallList = new ArrayList<>();
    enum GameStatus {PREPARING, START, STOP}
    private GameStatus gameStatus;
    private int score;
    private final WhiteBall whiteBall;
    Random random = new Random();
    Timer t;

    public MainPanel(WhiteBall whiteBall) {
        super();

        this.whiteBall = whiteBall;
        this.whiteBall.setVisible(false);
        add(whiteBall);
        this.registerObserver(this.whiteBall);

        setLayout(null);
        setSize(590, 590);
        setFocusable(true);
        this.addKeyListener(this);
        t = new Timer(50, e -> moveBalls());
        restartGame();
    }

    public void startGame() {
        this.gameStatus = GameStatus.START;
        this.whiteBall.setVisible(true);
        this.paintingBallList.forEach(b -> b.setVisible(false));
    }

    public void stopGame() {
        this.gameStatus = GameStatus.STOP;
        this.t.stop();
        paintingBallList.forEach(b -> {
            if (b.isVisible()) {
                if (b.getColor() == Color.RED) {
                    scoreIncrement(80);
                } else if (b.getColor() == Color.BLUE) {
                    scoreIncrement(-80);
                }
            }
        });
        repaint();
    }

    public void restartGame() {
        this.clearObservers();
        this.whiteBall.clearObservers();

        this.gameStatus = GameStatus.PREPARING;
        if (!paintingBallList.isEmpty()) {
            paintingBallList.forEach(this::remove);
        }
        this.paintingBallList = new ArrayList<>();
        Ball.setCount(0);
        this.score = 100;

        // deleted: if (this.whiteBall != null)
        this.whiteBall.setVisible(false);
        this.observers.add(this.whiteBall);

        this.t.start();
        repaint();
    }

    public void moveBalls() {
        paintingBallList.forEach(Ball::move);
        if (this.gameStatus == GameStatus.START) {
            score--;
            whiteBall.move();
            paintingBallList.forEach(b -> {
                b.setVisible(b.isIntersect(whiteBall));
            });
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        g.setColor(Color.BLACK);
        g.drawString("Score: " + score, 20, 40);

        if (gameStatus == GameStatus.START) {
            this.setBackground(Color.WHITE);
        }

        if (gameStatus == GameStatus.STOP) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 45));
            g.drawString("Game Over!", 200, 200);
            g.setFont(new Font("", Font.BOLD, 40));
            g.drawString("Your score is " + score, 190, 280);
        }
    }

    public void scoreIncrement(int increment) {
        this.score += increment;
    }

    public void addBallToPanel(Ball ball) {
        paintingBallList.add(ball);
        this.add(ball);

        this.registerObserver(ball);
        this.whiteBall.registerObserver(ball);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        char keyChar = e.getKeyChar();
        System.out.println("Press: " + keyChar);
        if (gameStatus == GameStatus.START)
            this.notifyObservers(keyChar);
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    final private List<Ball> observers = new ArrayList<>();

    @Override
    public void registerObserver(Ball ball) {
        this.observers.add(ball);
    }

    @Override
    public void removeObserver(Ball ball) {
        this.observers.remove(ball);
    }

    @Override
    public void clearObservers() {
        this.observers.clear();
    }

    @Override
    public void notifyObservers(char key) {
        for (Ball observer: this.observers)
            observer.update(key, this.whiteBall);
    }
}
