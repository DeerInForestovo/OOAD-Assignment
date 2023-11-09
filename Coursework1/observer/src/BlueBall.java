import java.awt.*;
import java.util.Random;

public class BlueBall extends Ball {
    public BlueBall(int xSpeed, int ySpeed, int ballSize) {
        super(Color.BLUE, xSpeed, ySpeed, ballSize);
        this.random = new Random();
    }

    Random random;

    @Override
    public void update(char key, WhiteBall whiteBall) {
        if (key == 0) {  // notified by a WhiteBall
            if (whiteBall.isIntersect(this)) {
                this.setXSpeed(-1 * this.getXSpeed());
                this.setYSpeed(-1 * this.getYSpeed());
            }
        } else {
            this.setXSpeed(-1 * this.getXSpeed());
            this.setYSpeed(-1 * this.getYSpeed());
        }
    }
}
