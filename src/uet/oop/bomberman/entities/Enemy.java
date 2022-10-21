package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
//import jdk.internal.net.http.common.Pair;
import javafx.util.Pair;
import uet.oop.bomberman.controlSystem.BombControl;
import uet.oop.bomberman.controlSystem.Collision;
import uet.oop.bomberman.controlSystem.Direction;
import uet.oop.bomberman.controlSystem.Sound;

import java.util.ArrayList;
import java.util.List;

import static uet.oop.bomberman.controlSystem.Direction.*;

public class Enemy extends DestroyableEntity{
    protected enum Status {
        ALIVE, DEAD,
    }
    Direction direction;
    protected Status status;

    public int count = 0;

//    public Enemy(int xUnit, int yUnit, Image img) {
//        super(xUnit, yUnit, img);
//    }

    public Enemy(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        direction = RIGHT;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public void setDead(boolean dead) {
        super.setDead(dead);
        Sound.enemyDie.play();
    }

    public void render() {

    }

    public void go(Collision collision) {
        //slow the enemy
        if (count % 2 == 0) return;

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
    public boolean goLeft(Collision collision) {
        if (collision.canMove(x, y, speed, LEFT) && !collision.isNextPosEnemy(this, LEFT, speed)) {
            x -= speed;
            setDirection(LEFT);
//            System.out.println("Ballom " + getDirection());
            return true;
        }
        return false;
    }

    public boolean goRight(Collision collision) {
        if (collision.canMove(x, y, speed, RIGHT) && !collision.isNextPosEnemy(this, RIGHT, speed)) {
            x += speed;
            setDirection(RIGHT);
//            System.out.println("Ballom " + getDirection());
            return true;
        }
        return false;
    }

    public boolean goUp(Collision collision) {
        if (collision.canMove(x, y, speed, UP) && !collision.isNextPosEnemy(this, UP, speed)) {
            y -= speed;
            setDirection(UP);
//            System.out.println("Ballom " + getDirection());
            return true;
        }
        return false;
    }

    public boolean goDown(Collision collision) {
        if (collision.canMove(x, y, speed, DOWN) && !collision.isNextPosEnemy(this, DOWN, speed)) {
            y += speed;
            setDirection(DOWN);
//            System.out.println("Ballom " + getDirection());
            return true;
        }
        return false;
    }

    public void goRand(Collision collision) {
        int rand = (int)(Math.random() * 4);
        switch (rand) {
            case 0:
                if(goDown(collision)) return;
                if(goLeft(collision)) return;
                if(goUp(collision)) return;
                if(goRight(collision)) return;
            case 1:
                if(goLeft(collision)) return;
                if(goUp(collision)) return;
                if(goRight(collision)) return;
                if(goDown(collision)) return;
            case 2:
                if(goUp(collision)) return;
                if(goRight(collision)) return;
                if(goDown(collision)) return;
                if(goLeft(collision)) return;
            case 3:
                if(goRight(collision)) return;
                if(goDown(collision)) return;
                if(goLeft(collision)) return;
                if(goUp(collision)) return;
        }
    }

    public Direction getDirectionToAnEntity(Collision collision, int x, int y) {
        List<List<Integer>> formatMap = collision.formatMapData();
        return UP;
    }

}
