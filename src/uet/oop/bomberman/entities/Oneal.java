package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.util.Pair;
import uet.oop.bomberman.controlSystem.BombControl;
import uet.oop.bomberman.controlSystem.Camera;
import uet.oop.bomberman.controlSystem.Collision;
import uet.oop.bomberman.controlSystem.Direction;

import java.util.List;
import java.util.Objects;

import static uet.oop.bomberman.controlSystem.Direction.*;
import static uet.oop.bomberman.controlSystem.Direction.DOWN;
import static uet.oop.bomberman.graphics.Sprite.*;

public class Oneal extends Enemy{
    private Collision collision;

    private List<Pair<Integer, Integer>> path;
    private List<Direction> pathDirection;

    private int countTimeDeath = 0;
    public Oneal(int xUnit, int yUnit, Image img, Collision collision) {
        super(xUnit, yUnit, img);
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
        //Todo: Find the way to the Enemy
        if (count % 2 == 0) return;

        if (pathDirection != null && pathDirection.size() < 8) {
//            for (Pair<Integer, Integer> pair:path){
//                System.out.println(pair.getKey() + " " + pair.getValue());
//            }
//            System.out.println();
//            if (pathDirection.size() < 4) {
//                speed = 2;
//            }
            System.out.println("Found");
            if (pathDirection.size() == 1) {
                Direction tmp = pathDirection.get(0);
                pathDirection.remove(0);
                goByDirection(collision, tmp);
            } else {
                Direction tmp = pathDirection.get(0);
                Direction next = pathDirection.get(1);
                if (canGoByDirection(collision, next)) {
                    goByDirection(collision, next);
                } else if (canGoByDirection(collision, tmp)){
                    goByDirection(collision, tmp);
                } else super.go(collision);
            }
        } else {
            System.out.println("Go rand");
            speed = 1;
            super.go(collision);
        }
    }

    public void update() {
        if (count % 5 == 0 && !isDead) {
            Entity bomber = collision.getMap().getEntities().get(0);
            int endX = Math.round((bomber.getX() + DEFAULT_SIZE) / SCALED_SIZE);
            int endY = Math.round((bomber.getY() + DEFAULT_SIZE) / SCALED_SIZE);
            path = getCoordinateDirection(collision, endY, endX);
            if (path != null) {
                pathDirection = getDirection(path);
            } else {
                pathDirection = null;
            }
//            if (path != null) {
//                for (Pair<Integer, Integer> pair:path){
//                    System.out.println(pair.getKey() + " " + pair.getValue());
//                }
//                System.out.println();
//            }
//            if (pathDirection != null) {
//                System.out.println("Change into Direction");
//                System.out.println("Direction size " + pathDirection.size());
//                for (Direction direction1 : pathDirection)
//                    System.out.println(direction1);
//            }
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

    public void render(GraphicsContext gc, Camera camera) {
        if (!isDead)
            gc.drawImage(img, x - camera.getX(), y - camera.getY());
        if (status == Status.DEAD && countTimeDeath < 35) {
            gc.drawImage(img, x - camera.getX(), y - camera.getY());
//            System.out.println("Render Death Enemy");
        }
    }

    public boolean checkDeath() {
        for (int j = 0; j < collision.getBombControl().getFlameList().size(); j++) {
            if (collision.checkCollide(this, collision.getBombControl().getFlameList().get(j))) {
                status = Status.DEAD;
                return true;
            }
        }
        return false;
    }

}
