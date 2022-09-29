package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import uet.oop.bomberman.controlSystem.BombControl;
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
    private BombControl bombControl;
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
    public Bomber(int x, int y, Image img, KeyListener keyEvent, Collision collisionManage, BombControl bombControl) {
        super( x, y, img);
        this.keyEvent = keyEvent;
        speed = 3;
        this.collisionManage = collisionManage;
        this.bombControl = bombControl;
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
        } else if (keyEvent.pressed(KeyCode.SPACE)) {
            if (collisionManage.canMove(x,y,0, UP)) {
                // to do
                System.out.println("Add Bomb");
                int xPos = Math.round(x / SCALED_SIZE);
                int yPos = Math.round(y / SCALED_SIZE);
                Bomb newBomb = new Bomb(xPos, yPos, bomb.getFxImage());
                bombControl.addBomb(newBomb);
            }
        } else return;
    }

}

