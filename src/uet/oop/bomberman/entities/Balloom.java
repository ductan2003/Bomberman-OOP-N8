package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.controlSystem.*;

import static uet.oop.bomberman.controlSystem.Direction.*;
import static uet.oop.bomberman.graphics.Sprite.*;

public class Balloom extends Enemy{
    private enum Status {
        ALIVE, DEAD,
    }
    private Collision collision;
    private BombControl bombControl;
    private Status status;
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
                if (goRight()) {
                    return;
                }
                else {
                    goRand();
                }
            } else {
                 goLeft();
            }
        }

        if (getDirection() == LEFT) {
            if (!collision.isNextPosBomb(this, LEFT, speed)) {
                if (goLeft()) {
                    return;
                }
                else {
                    goRand();
                }
            } else {
                goRight();
            }

        }

        if (getDirection() == DOWN) {
            if (!collision.isNextPosBomb(this, DOWN, speed)) {
                if (goDown()) {
                    return;
                }
                else {
                    goRand();
                }
            } else {
                 goUp();
            }

        }

        if (getDirection() == UP) {
            if (!collision.isNextPosBomb(this, UP, speed)) {
                if (goUp()) {
                    return;
                }
                else {
                    goRand();
                }
            } else {
                goDown();
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
//        if (status == Status.DEAD) {
//            status = Status.AFTERDEAD;
//        }
    }

    public boolean checkBalloomDeath() {
        for (int j = 0; j < bombControl.getFlameList().size(); j++) {
            if (collision.collide(this, bombControl.getFlameList().get(j))) {
                status = Status.DEAD;
                return true;
//                break;
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
            System.out.println("Render Death Enemy");
        }
    }

    public boolean goLeft() {
        if (collision.canMove(x, y, speed, LEFT) && !collision.isNextPosEnemy(this, LEFT, speed)) {
            x -= speed;
            setDirection(LEFT);
//            System.out.println("Ballom " + getDirection());
            return true;
        }
        return false;
    }

    public boolean goRight() {
        if (collision.canMove(x, y, speed, RIGHT) && !collision.isNextPosEnemy(this, RIGHT, speed)) {
            x += speed;
            setDirection(RIGHT);
//            System.out.println("Ballom " + getDirection());
            return true;
        }
        return false;
    }

    public boolean goUp() {
        if (collision.canMove(x, y, speed, UP) && !collision.isNextPosEnemy(this, UP, speed)) {
            y -= speed;
            setDirection(UP);
//            System.out.println("Ballom " + getDirection());
            return true;
        }
        return false;
    }

    public boolean goDown() {
        if (collision.canMove(x, y, speed, DOWN) && !collision.isNextPosEnemy(this, DOWN, speed)) {
            y += speed;
            setDirection(DOWN);
//            System.out.println("Ballom " + getDirection());
            return true;
        }
        return false;
    }

    public void goRand() {
        int rand = (int)(Math.random() * 4);
        switch (rand) {
            case 0:
                if(goDown()) return;
                if(goLeft()) return;
                if(goUp()) return;
                if(goRight()) return;
            case 1:
                if(goLeft()) return;
                if(goUp()) return;
                if(goRight()) return;
                if(goDown()) return;
            case 2:
                if(goUp()) return;
                if(goRight()) return;
                if(goDown()) return;
                if(goLeft()) return;
            case 3:
                if(goRight()) return;
                if(goDown()) return;
                if(goLeft()) return;
                if(goUp()) return;
        }
    }

//    public boolean isMetBomb(Direction direction) {
//        for (Bomb bomb: bombControl.getBombList()) {
//            switch (direction) {
//                case RIGHT:
//                    if (collision.collide(bomb, x + speed, y)) {
//                        System.out.println("Right Meet Bomb");
//                        return true;
//                    }
//                case LEFT:
//                    if (collision.collide(bomb, x - speed, y)) {
//                        System.out.println("Left Meet Bomb");
//                        return true;
//                    }
//                case UP:
//                    if (collision.collide(bomb, x, y - speed)) {
//                        System.out.println("Up Meet Bomb");
//                        return true;
//                    }
//                case DOWN:
//                    if (collision.collide(bomb, x, y + speed)) {
//                        System.out.println("Down Meet Bomb");
//                        return true;
//                    }
//            }
//        }
//        return false;
//    }
}
