import java.awt.*;
import java.util.Random;

public class RedBall extends Ball {
    public RedBall(int xSpeed, int ySpeed, int ballSize) {
        super(Color.RED, xSpeed, ySpeed, ballSize);
        this.locked = false;
        this.random = new Random();
    }

    private boolean locked;

    Random random;

    @Override
    public void update(char key, WhiteBall whiteBall) {
        System.out.println("Red Ball update");
        if (key == 0) {  // notified by a WhiteBall
            if (whiteBall.isIntersect(this)) {
                System.out.println("locked");
                this.locked = true;
                this.setXSpeed(whiteBall.getXSpeed());
                this.setYSpeed(whiteBall.getYSpeed());
            }
        } else {
            if (!this.locked) {
                switch (key) {
                    case 'a':
                        this.setXSpeed(-random.nextInt(3) - 1);
                        break;
                    case 'd':
                        this.setXSpeed(random.nextInt(3) + 1);
                        break;
                    case 'w':
                        this.setYSpeed(-random.nextInt(3) - 1);
                        break;
                    case 's':
                        this.setYSpeed(random.nextInt(3) + 1);
                        break;
                }
            } else {
                this.setXSpeed(whiteBall.getXSpeed());
                this.setYSpeed(whiteBall.getYSpeed());
            }
        }
    }
}
