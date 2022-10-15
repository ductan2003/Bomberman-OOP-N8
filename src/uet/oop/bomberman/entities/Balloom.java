package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.controlSystem.*;

import static uet.oop.bomberman.controlSystem.Direction.*;
import static uet.oop.bomberman.graphics.Sprite.*;

public class Balloom extends Enemy{
    private Collision collision;
    private BombControl bombControl;
    private int countTimeDeath = 0;

    public Balloom(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        speed = 1;
    }

    public Balloom(int xUnit, int yUnit, Image img, Collision collision, BombControl bombControl) {
        super(xUnit, yUnit, img);
        speed = 1;
        this.collision = collision;
        direction = RIGHT;
        this.bombControl = bombControl;
        this.status = Status.ALIVE;
        countTimeDeath = 0;
    }

    @Override
    public void setDead(boolean dead) {
        super.setDead(dead);
    }

    public void go() {
        //slow the enemy
        if (count % 2 == 1) return;
        //go
        if (getDirection() == RIGHT ) {
            if (!collision.isNextPosBomb(this, RIGHT, speed)) {
                if (goRight(collision)) {
                    return;
                }
                else {
                    goRand(collision);
                }
            } else {
                 goLeft(collision);
            }
        }

        if (getDirection() == LEFT) {
            if (!collision.isNextPosBomb(this, LEFT, speed)) {
                if (goLeft(collision)) {
                    return;
                }
                else {
                    goRand(collision);
                }
            } else {
                goRight(collision);
            }

        }

        if (getDirection() == DOWN) {
            if (!collision.isNextPosBomb(this, DOWN, speed)) {
                if (goDown(collision)) {
                    return;
                }
                else {
                    goRand(collision);
                }
            } else {
                 goUp(collision);
            }

        }

        if (getDirection() == UP) {
            if (!collision.isNextPosBomb(this, UP, speed)) {
                if (goUp(collision)) {
                    return;
                }
                else {
                    goRand(collision);
                }
            } else {
                goDown(collision);
            }

        }
    }

    public void update() {
        if (!isDead) {
            count++;
            go();
            img = getImg();
        }
        if (status == Status.DEAD) {
            img = getImg();
            countTimeDeath++;
        }
    }

    public boolean checkDeath() {
        for (int j = 0; j < bombControl.getFlameList().size(); j++) {
            if (collision.collide(this, bombControl.getFlameList().get(j))) {
                status = Status.DEAD;
                return true;
            }
        }
        return false;
    }

    public Image getImg() {
        switch (status) {
            case ALIVE:
                if (super.getDirection() == LEFT || super.getDirection() == UP)
                    return movingSprite(balloom_left1, balloom_left2, balloom_left3, count, 9).getFxImage();
                if (super.getDirection() == RIGHT || super.getDirection() == DOWN) {
                    return movingSprite(balloom_right1, balloom_right2, balloom_right3, count, 9).getFxImage();
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
//            System.out.println("Render Death Enemy");
        }
    }
}
