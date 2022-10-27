package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.controlSystem.*;

import static uet.oop.bomberman.Map.*;
import static uet.oop.bomberman.controlSystem.Direction.*;
import static uet.oop.bomberman.graphics.Sprite.*;

public class Bomber extends DestroyableEntity {
    private boolean isGoToNextLevel = false;
    private KeyListener keyEvent;
    private Direction direction;
    private int timeDead = 0;
    private long timeSet;
    public static long timeRemain;
    private boolean respawn;
    private int timeRespawn;
    public static long pauseTime;
    public static int lives = 3;

    public void setGoToNextLevel(boolean goToNextLevel) {
        isGoToNextLevel = goToNextLevel;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }


    public boolean isGoToNextLevel() {
        return isGoToNextLevel;
    }

    public int getLives() {
        return lives;
    }

    public long getTimeRemain() {
        return timeRemain;
    }

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
        speed = 2;
    }

    // new Constructor with keyEvent
    public Bomber(int x, int y, Image img, KeyListener keyEvent,
                  int speed, int lives, long timeSet) {
        super(x, y, img);
        this.keyEvent = keyEvent;
        this.speed = speed;
        direction = RIGHT;
        respawn = false;
        timeRespawn = 0;
        timeRemain = 0;
        this.timeSet = timeSet;
        this.lives = lives;
        pauseTime = 0;
    }

    public void getBomberInfo() {
        System.out.println("Bomber: " + this.getCoordinateInfo() + ". Going " + this.getDirection());
    }

    @Override
    public void update() {
        enemyControl.updateEnemyList();

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
                if (collision.collide(this, bombControl.getBombList().get(i))) {
                    bombControl.setHasJustSetBomb(true);
                    check = true;
                }
            }
            if (!check) bombControl.setHasJustSetBomb(false);
        }

        if (keyEvent.pressed(KeyCode.UP)) {
            goByDirection(UP);
        } else if (keyEvent.pressed(KeyCode.DOWN)) {
            goByDirection(DOWN);
        } else if (keyEvent.pressed(KeyCode.LEFT)) {
            goByDirection(LEFT);
        } else if (keyEvent.pressed(KeyCode.RIGHT)) {
            goByDirection(RIGHT);
        } else if (keyEvent.pressed(KeyCode.SPACE)) {
            int xPos = Math.round(getXMapCoordinate(x + SCALED_SIZE / 2));
            int yPos = Math.round(getYMapCoordinate(y + SCALED_SIZE / 2));
            if (bombControl.canSetBomb(xPos, yPos) && !isDead) {
                Bomb newBomb = new Bomb(xPos, yPos, bomb.getFxImage(), 0);
                bombControl.addBomb(newBomb);
                bombControl.setHasJustSetBomb(true);
            }
        } else if (keyEvent.pressed(KeyCode.P)) {
            if (GameMenu.gameState == GameMenu.GAME_STATE.IN_PLAY) {
                GameMenu.gameState = GameMenu.GAME_STATE.IN_PAUSE;
                collision.saveData();
                Timer.pause();
            }
        } else if (keyEvent.pressed(KeyCode.E)) {
            if (GameMenu.gameState != GameMenu.GAME_STATE.END) GameMenu.gameState = GameMenu.GAME_STATE.IN_END_STATE;
            collision.saveData();
        } else count = 0;
        img = getImg(getDirection());
        updateItems();
        bombControl.updateBomb();
        timeRemain = Math.max(timeSet - (Timer.now() - collision.getMap().getTime_begin() - pauseTime) / 100000000, 0);
        if (timeRemain == 0) {
            setDead(true);
        }
    }

    public void updateItems() {
        Entity entity = collision.getMap().getEntity(x + 16, y + 16);
        if (entity instanceof Portal && enemyControl.getEnemyList().size() == 0) {
            int nextLevel = collision.getMap().getLevel() + 1;
            if (nextLevel <= 4) {
                BombermanGame.map.clear();
                BombermanGame.map.createMap(nextLevel, keyEvent, false);
            } else {
                BombermanGame.map.setIsWin(true);
                GameMenu.gameState = GameMenu.GAME_STATE.IN_END_STATE;
            }
        }
        if (entity instanceof Item) {
            Item item = (Item) entity;
            item.powerUp(this);
            collision.getMap().replace((x + 16) / SCALED_SIZE, (y + 16) / SCALED_SIZE,
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
            Sound.bomberDie.play();
            BombermanGame.map.setIsWin(false);
            GameMenu.gameState = GameMenu.GAME_STATE.IN_END_STATE;
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

    @Override
    public boolean checkCanMove(int x, int y, int speed, Direction direction) {
        if (isDead) return false;
        return (collision.canMove(x, y, speed, direction)
                && !collision.isNextPosBomb(this, direction, speed) && !isCollideEnemy())
                || (bombControl.HasJustSetBomb() && collision.canMove(x, y, speed, direction));
    }

    public boolean isCollideEnemy() {
        if (respawn) return false;
        for (int i = 0; i < enemyControl.getEnemyList().size(); i++) {
            if (collision.checkCollide(enemyControl.getEnemyList().get(i), this))
                return true;
        }
        for (int i = 0; i < bombControl.getFlameList().size(); i++) {
            if (collision.checkCollide(bombControl.getFlameList().get(i), this))
                return true;
        }
        return false;
    }
}

