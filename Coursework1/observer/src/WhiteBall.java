import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class WhiteBall extends Ball implements Subject<Ball> {
    public WhiteBall(int xSpeed, int ySpeed, int ballSize) {
        super(Color.WHITE, xSpeed, ySpeed, ballSize);
    }

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
            observer.update(key, this);
    }

    @Override
    public void update(char key, WhiteBall whiteBall) {
        switch (key) {
            case 'a':
                this.setXSpeed(-8);
                break;
            case 'd':
                this.setXSpeed(8);
                break;
            case 'w':
                this.setYSpeed(-8);
                break;
            case 's':
                this.setYSpeed(8);
                break;
        }
        this.notifyObservers((char)0);
    }

    @Override
    public void move() {
        super.move();
        this.notifyObservers((char)0);
    }
}
