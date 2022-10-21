package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.controlSystem.*;
import uet.oop.bomberman.graphics.Sprite;

import java.util.List;

import static uet.oop.bomberman.controlSystem.Direction.*;
import static uet.oop.bomberman.graphics.Sprite.*;

public class Bomber extends DestroyableEntity {
    private boolean isGoToNextLevel = false;
    private KeyListener keyEvent;
    private Collision collisionManage;
    private BombControl bombControl;
    private Direction direction;
    private EnemyControl enemyControl;
    private int timeDead = 0;
    private boolean respawn;
    private int timeRespawn;

    private int lives = 3;
    private static int[] FIX_LENGTH = {0, -4, 4};
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
        speed = 3;
    }

    // new Constructor with keyEvent
    public Bomber(int x, int y, Image img, KeyListener keyEvent,
                  Collision collisionManage) {
        super(x, y, img);
        this.keyEvent = keyEvent;
        speed = 2;
        this.collisionManage = collisionManage;
        this.bombControl = collisionManage.getBombControl();
        this.enemyControl = collisionManage.getEnemyControl();
        direction = RIGHT;
        count = 0;
        respawn = false;
        timeRespawn = 0;
    }

    public void getBomberInfo() {
        System.out.println("Bomber: " + this.getCoordinateInfo() + ". Going " + this.getDirection());
    }

    @Override
    public void update() {
//        bombControl.updateBombList();
        enemyControl.updateEnemyList();
//        System.out.println(enemyControl.getEnemyList().size());
        if (isCollideEnemy() && !respawn) {
            lives--;
            if (lives == 0) {
                setDead(true);
            } else {
                respawn = true;
            }
        }
        if (bombControl.HasJustSetBomb()) {
            boolean check = false;
            for (int i = 0; i < bombControl.getBombList().size(); i++) {
                if (collisionManage.collide(this, bombControl.getBombList().get(i))) {
                    bombControl.setHasJustSetBomb(true);
                    check = true;
//                    System.out.println("Through Bomb false");
                }
            }
            if (!check) bombControl.setHasJustSetBomb(false);
//            System.out.println("Bombs num: " + bombControl.getBombList().size());
        }

        if (keyEvent.pressed(KeyCode.UP)) {
            for (int i = 0; i < FIX_LENGTH.length; i++) {
//            if ((collisionManage.canMove(x, y, speed, UP) && !bombControl.isNextPosBomb(this, UP, speed)) || bombControl.HasJustSetBomb()) {
                if (checkCanMove(x + FIX_LENGTH[i], y, speed, UP)) {
                    y -= speed;
                    x = x + FIX_LENGTH[i];
                    setDirection(UP);
//                getBomberInfo();
                    count++;
                    break;
                }
            }
        } else if (keyEvent.pressed(KeyCode.DOWN)) {
//            if ((collisionManage.canMove(x, y, speed, DOWN) && !bombControl.isNextPosBomb(this, DOWN, speed))|| bombControl.HasJustSetBomb()) {
            for (int i = 0; i < FIX_LENGTH.length; i++) {
                if (checkCanMove(x + FIX_LENGTH[i], y, speed, DOWN)) {
                    y += speed;
                    x = x + FIX_LENGTH[i];
                    setDirection(DOWN);
//                getBomberInfo();
                    count++;
                    break;
                }
            }

        } else if (keyEvent.pressed(KeyCode.LEFT)) {
//            if ((collisionManage.canMove(x, y, speed, LEFT) && !bombControl.isNextPosBomb(this, LEFT, speed)) || bombControl.HasJustSetBomb()) {
            for (int i = 0; i < FIX_LENGTH.length; i++) {
                if (checkCanMove(x, y + FIX_LENGTH[i], speed, LEFT)) {
                    x -= speed;
                    y = y + FIX_LENGTH[i];
                    setDirection(LEFT);
//                getBomberInfo();
                    count++;
                    break;
                }
            }
        } else if (keyEvent.pressed(KeyCode.RIGHT)) {
//            if ((collisionManage.canMove(x, y, speed, RIGHT) && !bombControl.isNextPosBomb(this, RIGHT, speed)) || bombControl.HasJustSetBomb()) {

            for (int i = 0; i < FIX_LENGTH.length; i++) {
                if (checkCanMove(x, y + FIX_LENGTH[i], speed, RIGHT)) {
                    x += speed;
                    y = y + FIX_LENGTH[i];
                    setDirection(RIGHT);
//                getBomberInfo();
                    count++;
                    break;
                }
            }
        } else if (keyEvent.pressed(KeyCode.SPACE)) {
//            getBomberInfo();
            int xPos = Math.round(getXMapCoordinate(x + SCALED_SIZE / 2));
            int yPos = Math.round(getYMapCoordinate(y + SCALED_SIZE / 2));
            if (bombControl.canSetBomb(xPos, yPos, getDirection()) && !isDead) {
                System.out.println("Add Bomb");
                Bomb newBomb = new Bomb(xPos, yPos, bomb.getFxImage());
                bombControl.addBomb(newBomb);
                bombControl.setHasJustSetBomb(true);
            }
        } else if (keyEvent.pressed(KeyCode.Z)) {
            List<List<Integer>> formatMap = collisionManage.formatMapData();
        } else count = 0;
        img = getImg(getDirection());
        updateItems();
        bombControl.updateBomb();
    }

    public void updateItems() {
        Entity entity = collisionManage.getMap().getEntity(x + 16, y + 16);
        if (entity instanceof Portal) {
            int nextLevel = collisionManage.getMap().getLevel() + 1;
            if (nextLevel <= 4) {
                BombermanGame.map.createMap(nextLevel, keyEvent);
            }
        }
        if (entity instanceof Item) {
            Item item = (Item) entity;
            item.powerUp(this);
            collisionManage.getMap().replace((x + 16) / SCALED_SIZE, (y + 16) / SCALED_SIZE,
                    new Grass((x + 16) / SCALED_SIZE, (y + 16) / SCALED_SIZE, grass.getFxImage()));
        }
    }

    public Image getImg(Direction direction) {
        if (respawn && Timer.now() % 3 == 1) {
            if (timeRespawn++ < 30) {
                return null;
            } else {
                respawn = false;
                timeRespawn = 0;
            }

        }
        if (isDead()) {
            if (timeDead++ < 100)
                return movingSprite(player_dead1, player_dead2, player_dead3, timeDead, 100).getFxImage();
            return null;
        }
        switch (direction) {
            case UP:
                return movingSprite(player_up, player_up_1, player_up_2, count, 15).getFxImage();
            case DOWN:
                return movingSprite(player_down, player_down_1, player_down_2, count, 15).getFxImage();
            case RIGHT:
                return movingSprite(player_right, player_right_1, player_right_2, count, 15).getFxImage();
            case LEFT:
                return movingSprite(player_left, player_left_1, player_left_2, count, 15).getFxImage();
        }
        return img;
    }

    public BombControl getBombControl() {
        return bombControl;
    }

    @Override
    public void render(GraphicsContext gc, Camera camera) {
        bombControl.renderBombs(gc, camera);
        super.render(gc, camera);
    }

    public boolean checkCanMove(int x, int y, int speed, Direction direction) {
        if (isDead) return false;
        return (collisionManage.canMove(x, y, speed, direction)
                && !collisionManage.isNextPosBomb(this, direction, speed) && !isCollideEnemy())
                || (bombControl.HasJustSetBomb() && collisionManage.canMove(x, y, speed, direction));
    }

    public boolean isCollideEnemy() {
        if (respawn) return false;
        int a = x;
        int b = y;
        switch (direction) {
            case DOWN:
                b = y + speed;
                break;
            case RIGHT:
                a = x + speed;
                break;
            case LEFT:
                a = x - speed;
                break;
            case UP:
                b = y - speed;
                break;
            default:
                break;
        }
        for (int i = 0; i < enemyControl.getEnemyList().size(); i++) {
            if (collisionManage.collide(enemyControl.getEnemyList().get(i), a, b))
                return true;
        }
        for (int i = 0; i < bombControl.getFlameList().size(); i++) {
            if (collisionManage.collide(bombControl.getFlameList().get(i), a, b))
                return true;
        }
        return false;
    }
}

