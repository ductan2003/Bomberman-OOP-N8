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

public class Bomber extends DestroyableEntity {
    private boolean isGoToNextLevel = false;
    private KeyListener keyEvent;
    private Collision collisionManage;
    private Direction direction;

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
            if (isMetBarrier(UP)) {
                y -= speed;
                System.out.println("Up" + " " + x + " " + y);
            } else y -= 1;
        }
        else if (keyEvent.pressed(KeyCode.DOWN)) {
            if (isMetBarrier(DOWN)) {
                y += speed;
                System.out.println("DOWN" + " " + x + " " + y);
            } else y += 1;
        }
        else if (keyEvent.pressed(KeyCode.LEFT)) {
            if (isMetBarrier(LEFT)) {
                x -= speed;
                System.out.println("LEFT" + " " + x + " " + y);
            } else x -= 1;
        }
        else if (keyEvent.pressed(KeyCode.RIGHT)) {
            if (isMetBarrier(RIGHT)) {
                x += speed;
                System.out.println("RIGHT" + " " + x + " " + y);
            } else x += 1;
        } else return;
    }

    public boolean isMetBarrier(Direction direction) {
        Entity nextPos;
        switch (direction) {
            case UP:
                nextPos = new Wall(x, y - speed, img);
                if (collisionManage.collide(this, nextPos)) {
                    return true;
                }
                break;
            case DOWN:
                nextPos = new Wall(x, y + speed, img);
                if (collisionManage.collide(this, nextPos)) {
                    return true;
                }
                break;
            case LEFT:
                nextPos = new Wall(x + speed, y , img);
                if (collisionManage.collide(this, nextPos)) {
                    return true;
                }
                break;
            case RIGHT:
                nextPos = new Wall(x - speed, y , img);
                if (collisionManage.collide(this, nextPos)) {
                    return true;
                }
                break;
        }
        return false;
    }
}

