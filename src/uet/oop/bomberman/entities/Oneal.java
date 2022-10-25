package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import javafx.util.Pair;
import uet.oop.bomberman.controlSystem.*;

import java.util.List;

import static uet.oop.bomberman.Map.collision;
import static uet.oop.bomberman.controlSystem.Direction.*;
import static uet.oop.bomberman.graphics.Sprite.*;

public class Oneal extends Enemy {
    private List<Pair<Integer, Integer>> path;
    private List<Direction> pathDirection;

    public Oneal(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        speed = 1;
        this.direction = RIGHT;
        this.status = Status.ALIVE;
    }

    @Override
    public void updateDist() {
        Entity bomber = collision.getMap().getEntities().get(0);
        int endY = Math.round((bomber.getX() + DEFAULT_SIZE) / SCALED_SIZE);
        int endX = Math.round((bomber.getY() + DEFAULT_SIZE) / SCALED_SIZE);

        int startX = Math.round((y + DEFAULT_SIZE) / SCALED_SIZE);
        int startY = Math.round((x + DEFAULT_SIZE) / SCALED_SIZE);

        dist = (int) Math.round(Math.sqrt((startX - endX) * (startX - endX) + (startY - endY) * (startY - endY)));
    }

    public void go() {
        //slow the Enemy
        if (count % 2 == 0) return;

        //Change Oneal's speed if Bomber is in the Oneal's area
        if (dist < 7) speed = 2;
        else speed = 1;

        if (pathDirection != null && pathDirection.size() < 8) {
            if (pathDirection.size() == 1) {
                Direction tmp = pathDirection.get(0);
                goByDirection(tmp);
            } else {
                Direction tmp = pathDirection.get(0);
                Direction next = pathDirection.get(1);
                if (canGoByDirection(next) && !collision.isNextPosBomb(this, next, speed)) {
                    goByDirection(next);
                } else if (canGoByDirection(tmp) && !collision.isNextPosBomb(this, tmp, speed)) {
                    goByDirection(tmp);
                } else super.go();
            }
        } else {
            super.go();
        }
    }

    public void update() {
        if (countTimeDeath > 35) {
            return;
        }

        updateDist();
        if (dist < 8) {
            Sound.attackingWarning.play();
        }

        //find path.
        if (!isDead && dist < 8 && count % 15 == 0) {

            //get Bomber position, change map coordinate into array coordinate
            Entity bomber = collision.getMap().getEntities().get(0);
            int endX = Math.round((bomber.getX() + DEFAULT_SIZE) / SCALED_SIZE);
            int endY = Math.round((bomber.getY() + DEFAULT_SIZE) / SCALED_SIZE);
            path = getCoordinateDirection(endY, endX);

            if (path != null) {
                pathDirection = getDirection(path);
            } else {
                pathDirection = null;
            }
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
        if (status == Status.ALIVE) {
            if (super.getDirection() == LEFT || super.getDirection() == UP)
                return movingSprite(oneal_left1, oneal_left2, oneal_left3, count, 30).getFxImage();
            if (super.getDirection() == RIGHT || super.getDirection() == DOWN) {
                return movingSprite(oneal_right1, oneal_right2, oneal_right3, count, 30).getFxImage();
            }
        } else if (status == Status.DEAD) {
            return oneal_dead.getFxImage();
        }
        return img;
    }
}
