package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.controlSystem.*;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.controlSystem.Direction.*;
import static uet.oop.bomberman.graphics.Sprite.*;

public class Bomber extends DestroyableEntity {
    private boolean isGoToNextLevel = false;
    private KeyListener keyEvent;
    private Collision collisionManage;
    private BombControl bombControl;
    private Direction direction;
    private static int FIX_LENGTH = 3;
    int count = 0;

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
        super(x, y, img);
        speed = 1;
    }

    // new Constructor with keyEvent
    public Bomber(int x, int y, Image img, KeyListener keyEvent, Collision collisionManage, BombControl bombControl) {
        super(x, y, img);
        this.keyEvent = keyEvent;
        speed = 3;
        this.collisionManage = collisionManage;
        this.bombControl = bombControl;
        direction = RIGHT;
        count = 0;
    }

    public void getBomberInfo() {
        System.out.println("Bomber: " + this.getCoordinateInfo() + ". Going " + this.getDirection());
    }

    @Override
    public void update() {
        bombControl.updateBombList();
        if (bombControl.HasJustSetBomb()) {
            for (int i=0; i<bombControl.getBombList().size();i++) {
                if (!collisionManage.collide(bombControl.getBombList().get(i), this)) {
                    bombControl.setHasJustSetBomb(false);
                }
            }
        }
        if (keyEvent.pressed(KeyCode.UP)) {
            if (collisionManage.canMove(x, y, speed, UP) || bombControl.HasJustSetBomb()) {
                y -= speed;
                setDirection(UP);
                getBomberInfo();
                count++;
            }
        } else if (keyEvent.pressed(KeyCode.DOWN)) {
            if (collisionManage.canMove(x, y, speed, DOWN) || bombControl.HasJustSetBomb()) {
                y += speed;
                setDirection(DOWN);
                getBomberInfo();
                count++;
            }
        } else if (keyEvent.pressed(KeyCode.LEFT)) {
            if (collisionManage.canMove(x, y, speed, LEFT) || bombControl.HasJustSetBomb()) {
                x -= speed;
                setDirection(LEFT);
                getBomberInfo();
                count++;
            }
        } else if (keyEvent.pressed(KeyCode.RIGHT)) {
            if (collisionManage.canMove(x, y, speed, RIGHT) || bombControl.HasJustSetBomb()) {
                x += speed;
                setDirection(RIGHT);
                getBomberInfo();
                count++;
            }
        } else if (keyEvent.pressed(KeyCode.SPACE)) {
            getBomberInfo();
            int xPos = Math.round(getXMapCoordinate(x + SCALED_SIZE / 2));
            int yPos = Math.round(getYMapCoordinate(y + SCALED_SIZE / 2));
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
                    default:
                        break;
                }
                Bomb newBomb = new Bomb(xPos, yPos, bomb.getFxImage());
                bombControl.addBomb(newBomb);
                bombControl.setHasJustSetBomb(true);
            }
        } else count = 0;
        img = getImg(getDirection());
        bombControl.updateBombList();
    }

    public Image getImg(Direction direction) {
        switch (direction) {
            case UP:
                return movingSprite(player_up, player_up_1, player_up_2, count, 9).getFxImage();
            case DOWN:
                return movingSprite(player_down, player_down_1, player_down_2,count,9).getFxImage();
            case RIGHT:
                return movingSprite(player_right, player_right_1, player_right_2, count, 9).getFxImage();
            case LEFT:
                return movingSprite(player_left, player_left_1, player_left_2, count, 9).getFxImage();
        }
        return img;
    }

    @Override
    public void render(GraphicsContext gc, Camera camera) {
        super.render(gc, camera);
        bombControl.renderBombs(gc, camera);
    }

}

