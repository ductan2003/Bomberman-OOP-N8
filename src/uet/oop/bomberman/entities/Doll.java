package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

import static uet.oop.bomberman.controlSystem.Direction.*;
import static uet.oop.bomberman.graphics.Sprite.*;

public class Doll extends Enemy {

    public Doll(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        speed = 1;
        direction = LEFT;
        this.status = Status.ALIVE;
    }

    @Override
    public void go() {
        if (count % 4 == 1) {
            super.go();
        }
    }

    public void update() {
        if (countTimeDeath > 70) {
            return;
        }

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
        switch (status) {
            case ALIVE:
                if (super.getDirection() == LEFT || super.getDirection() == UP)
                    return movingSprite(doll_left1, doll_left2, doll_left3, count, 20).getFxImage();
                if (super.getDirection() == RIGHT || super.getDirection() == DOWN) {
                    return movingSprite(doll_right1, doll_right2, doll_right3, count, 20).getFxImage();
                }
                break;
            case DEAD:
                return doll_dead.getFxImage();
        }
        return img;
    }
}
