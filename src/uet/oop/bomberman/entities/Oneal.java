package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.controlSystem.BombControl;
import uet.oop.bomberman.controlSystem.Camera;
import uet.oop.bomberman.controlSystem.Collision;

import static uet.oop.bomberman.controlSystem.Direction.*;
import static uet.oop.bomberman.controlSystem.Direction.DOWN;
import static uet.oop.bomberman.graphics.Sprite.*;

public class Oneal extends Enemy{
    private Collision collision;
    private BombControl bombControl;
    private int countTimeDeath = 0;
    public Oneal(int xUnit, int yUnit, Image img, Collision collision, BombControl bombControl) {
        super(xUnit, yUnit, img);
        this.bombControl = bombControl;
        this.collision = collision;
        speed = 1;
        this.direction = RIGHT;
        this.status = Status.ALIVE;
        countTimeDeath = 0;
    }

    @Override
    public void setDead(boolean dead) {
        super.setDead(dead);
    }

    public void go() {
        //slow the enemy
        if (count % 2 == 0) return;

        //go

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

    public Image getImg() {
        if (status == Status.ALIVE) {
            if (super.getDirection() == LEFT || super.getDirection() == UP)
                return movingSprite(oneal_left1, oneal_left2, oneal_left3, count, 9).getFxImage();
            if (super.getDirection() == RIGHT || super.getDirection() == DOWN) {
                return movingSprite(oneal_right1, oneal_right2, oneal_right3, count, 9).getFxImage();
            }
        } else if (status == Status.DEAD) {
            return oneal_dead.getFxImage();
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

    public boolean checkDeath() {
        for (int j = 0; j < bombControl.getFlameList().size(); j++) {
            if (collision.collide(this, bombControl.getFlameList().get(j))) {
                status = Status.DEAD;
                return true;
            }
        }
        return false;
    }
}
