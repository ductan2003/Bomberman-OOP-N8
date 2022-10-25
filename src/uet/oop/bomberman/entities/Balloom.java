package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

import static uet.oop.bomberman.Map.bombControl;
import static uet.oop.bomberman.Map.collision;
import static uet.oop.bomberman.controlSystem.Direction.*;
import static uet.oop.bomberman.graphics.Sprite.*;

public class Balloom extends Enemy {
    private final boolean bornByDoll;
    private int skipBombNewBorn;

    public Balloom(int xUnit, int yUnit, Image img, boolean bornByDoll) {
        super(xUnit, yUnit, img);
        speed = 1;
        direction = RIGHT;
        this.status = Status.ALIVE;
        countTimeDeath = 0;
        this.bornByDoll = bornByDoll;
        if (bornByDoll) {
            skipBombNewBorn = 0;
            speed = 2;
        }
    }

    /**
     * update Balloom.
     */
    public void update() {
        if (countTimeDeath > 35) {
            return;
        }

        if (bornByDoll) {
            skipBombNewBorn++;
        }

        if (!isDead) {
            count++;
            super.go();
            img = getImg();
        }

        if (status == Status.DEAD) {
            img = getImg();
            countTimeDeath++;
        }

    }

    /**
     * Check Death.
     */
    @Override
    public boolean checkDeath() {
        if (!bornByDoll || skipBombNewBorn > 30) {
            for (int j = 0; j < bombControl.getFlameList().size(); j++) {
                if (collision.checkCollide(this, bombControl.getFlameList().get(j))) {
                    status = Status.DEAD;
                    return true;
                }
            }
        }
        return false;
    }

    public Image getImg() {
        switch (status) {
            case ALIVE:
                if (super.getDirection() == LEFT || super.getDirection() == UP)
                    return movingSprite(balloom_left1, balloom_left2, balloom_left3, count, 20).getFxImage();
                if (super.getDirection() == RIGHT || super.getDirection() == DOWN) {
                    return movingSprite(balloom_right1, balloom_right2, balloom_right3, count, 20).getFxImage();
                }
                break;
            case DEAD:
                return balloom_dead.getFxImage();
        }
        return img;
    }
}
