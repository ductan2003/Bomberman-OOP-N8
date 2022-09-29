package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.controlSystem.BombControl;
import uet.oop.bomberman.controlSystem.Collision;
import uet.oop.bomberman.controlSystem.Direction;
import uet.oop.bomberman.controlSystem.KeyListener;
import uet.oop.bomberman.graphics.Sprite;

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

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
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
        direction = RIGHT;
    }

    public void getBomberInfo() {
        System.out.println("Bomber: " + this.getCoordinateInfo() + ". Going " + this.getDirection());
    }

    @Override
    public void update() {

        if (keyEvent.pressed(KeyCode.UP)) {
            if (collisionManage.canMove(x,y,speed,UP)) {
                y -= speed;
                setDirection(UP);
                getBomberInfo();
            }
        }
        else if (keyEvent.pressed(KeyCode.DOWN)) {
            if (collisionManage.canMove(x,y,speed,DOWN)) {
                y += speed;
                setDirection(DOWN);
                getBomberInfo();
            }
        }
        else if (keyEvent.pressed(KeyCode.LEFT)) {
            if (collisionManage.canMove(x,y,speed,LEFT)) {
                x -= speed;
                setDirection(LEFT);
                getBomberInfo();
            }
        }
        else if (keyEvent.pressed(KeyCode.RIGHT)) {
            if (collisionManage.canMove(x,y,speed,RIGHT)) {
                x += speed;
                setDirection(RIGHT);
                getBomberInfo();
            }
        } else if (keyEvent.pressed(KeyCode.SPACE)) {
            getBomberInfo();
            int xPos = getXMapCoordinate(x);
            int yPos = getYMapCoordinate(y);
            if (bombControl.canSetBomb(xPos, yPos, getDirection())) {
                // to do
                System.out.println("Add Bomb");
                switch (getDirection()) {
                    case UP:
                        yPos -= 1;
                        break;
                    case DOWN:
                        yPos += 1;
                        break;
                    case LEFT:
                        xPos -= 1;
                        break;
                    case RIGHT:
                        xPos += 1;
                        break;
                    default: break;
                }
                Bomb newBomb = new Bomb(xPos, yPos, bomb.getFxImage());
                bombControl.addBomb(newBomb);
            }
        }
        img = getImg(getDirection());
    }

    public Image getImg(Direction direction) {
        switch (direction) {
            case UP:
                return Sprite.player_up.getFxImage();
            case DOWN:
                return Sprite.player_down.getFxImage();
            case RIGHT:
                return Sprite.player_right.getFxImage();
            case LEFT:
                return Sprite.player_left.getFxImage();
        }
        return img;
    }

}

