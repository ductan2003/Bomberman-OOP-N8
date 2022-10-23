package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.controlSystem.*;

import static uet.oop.bomberman.controlSystem.Direction.*;
import static uet.oop.bomberman.graphics.Sprite.*;

public class Balloom extends Enemy{
    private Collision collision;
    private int countTimeDeath = 0;
    private boolean bornByDoll;
    private int skipBombNewBorn;

//    public Balloom(int xUnit, int yUnit, Image img) {
//        super(xUnit, yUnit, img);
//        speed = 1;
//        bornByDoll = false;
//    }

    public Balloom(int xUnit, int yUnit, Image img, Collision collision, boolean bornByDoll) {
        super(xUnit, yUnit, img);
        speed = 1;
        this.collision = collision;
        direction = RIGHT;
        this.status = Status.ALIVE;
        countTimeDeath = 0;
        this.bornByDoll = bornByDoll;
        if (bornByDoll) {
            skipBombNewBorn = 0;
            speed = 2;
        }
    }

    @Override
    public void setDead(boolean dead) {
        super.setDead(dead);
    }

    public int getCountTimeDeath() {
        return countTimeDeath;
    }

    public void update() {
        if (countTimeDeath > 35) {
            return;
        }

        if (bornByDoll) {
            skipBombNewBorn++;
        }
        if (!isDead) {
            count++;
            super.go(collision);
            img = getImg();
        }

        if (status == Status.DEAD) {
            img = getImg();
            countTimeDeath++;
        }

    }

    public boolean checkDeath() {
        if (!bornByDoll || skipBombNewBorn > 30) {
            for (int j = 0; j < collision.getBombControl().getFlameList().size(); j++) {
                if (collision.checkCollide(this, collision.getBombControl().getFlameList().get(j))) {
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

    public void render(GraphicsContext gc, Camera camera) {
        if (!isDead)
            gc.drawImage(img, x - camera.getX(), y - camera.getY());
        if (status == Status.DEAD && countTimeDeath < 35) {
            gc.drawImage(img, x - camera.getX(), y - camera.getY());
        }
    }
}
