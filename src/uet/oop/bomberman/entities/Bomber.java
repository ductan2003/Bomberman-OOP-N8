package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import uet.oop.bomberman.controlSystem.Collision;
import uet.oop.bomberman.controlSystem.Direction;
import uet.oop.bomberman.controlSystem.KeyListener;

import static uet.oop.bomberman.controlSystem.Direction.*;
import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;
import static uet.oop.bomberman.graphics.Sprite.bomb;

public class Bomber extends DestroyableEntity {
    private boolean isGoToNextLevel = false;
    private KeyListener keyEvent;
    private Collision collisionManage;
    private Direction direction;
    private static int FIX_LENGTH = 3;

    public void setGoToNextLevel(boolean goToNextLevel) {
        isGoToNextLevel = goToNextLevel;
    }

    public boolean isGoToNextLevel() {
        return isGoToNextLevel;
    }

    public Bomber(int x, int y, Image img) {
        super( x, y, img);
        speed = 1;
    }
// new Constructor with keyEvent
    public Bomber(int x, int y, Image img, KeyListener keyEvent, Collision collisionManage) {
        super( x, y, img);
        this.keyEvent = keyEvent;
        speed = 3;
        this.collisionManage = collisionManage;
    }

    @Override
    public void update() {

        if (keyEvent.pressed(KeyCode.UP)) {
            if (collisionManage.canMove(x,y,speed,UP)) {
                y -= speed;
                System.out.println("Up" + " " + x + " " + y);
            }
        }
        else if (keyEvent.pressed(KeyCode.DOWN)) {
            if (collisionManage.canMove(x,y,speed,DOWN)) {
                y += speed;
                System.out.println("DOWN" + " " + x + " " + y);
            }
        }
        else if (keyEvent.pressed(KeyCode.LEFT)) {
            if (collisionManage.canMove(x,y,speed,LEFT)) {
                x -= speed;
                System.out.println("LEFT" + " " + x + " " + y);
            }
        }
        else if (keyEvent.pressed(KeyCode.RIGHT)) {
            if (collisionManage.canMove(x,y,speed,RIGHT)) {
                x += speed;
                System.out.println("RIGHT" + " " + x + " " + y);
            }
        } else return;
    }

//    public boolean isMetBarrier(Direction direction) {
//        Entity nextPos = new Wall(x,y, img);
//        nextPos.setX(x);
//        nextPos.setY(y);
//        Entity nextEntity = new Brick(x,y,img);
//        switch (direction) {
//            case UP:
//                nextEntity = collisionManage.getEntity(x, y-speed);
//                nextPos.setY(y-speed);
//                break;
//            case DOWN:
//                nextEntity = collisionManage.getEntity(x, y+speed+SCALED_SIZE);
//                nextPos.setY(y+speed-FIX_LENGTH);
//                break;
//            case LEFT:
//                nextEntity = collisionManage.getEntity(x+speed, y);
//                nextPos.setX(x+speed-FIX_LENGTH);
//                break;
//            case RIGHT:
//                nextEntity = collisionManage.getEntity(x-speed+SCALED_SIZE, y);
//                nextPos.setX(x-speed+FIX_LENGTH);
//                break;
//        }
//        if (nextEntity instanceof Grass && collisionManage.collide(nextEntity, nextPos)) {
//            return true;
//        }
//        return false;
//    }
}

