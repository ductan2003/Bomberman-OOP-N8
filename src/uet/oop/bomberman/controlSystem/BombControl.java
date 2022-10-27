package uet.oop.bomberman.controlSystem;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

import static uet.oop.bomberman.graphics.Sprite.DEFAULT_SIZE;
import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;

public class BombControl {
    // Todo: setup bomb
    private final List<Bomb> bombList;
    private int power;
    private int numberOfBomb;
    private final List<Flame> flameList;
    boolean hasJustSetBomb = false;

    public BombControl() {
        power = 1;
        numberOfBomb = 1;
        bombList = new ArrayList<>();
        flameList = new ArrayList<>();
    }

    /**
     * Remove bomb and Flame.
     */
    public void updateBomb() {
        for (int i = 0; i < flameList.size(); i++) {
            flameList.get(i).update();
            if (flameList.get(i).isExploded()) {
                flameList.remove(i);
                i--;
            }
        }
        for (int i = 0; i < bombList.size(); i++) {
            bombList.get(i).update();
            if (bombList.get(i).isExploded()) {
                this.bombExplode(i);
            }
        }
    }

    /**
     * Add Bomb to the bombList.
     */
    public void addBomb(Bomb bomb) {
        for (Bomb bomb1 : bombList) {
            if (bomb.getX() == bomb1.getX() && bomb.getY() == bomb1.getY()) return;
        }
        bombList.add(bomb);
        Sound.putBomb.play();
//        System.out.println("Bomb: " + bomb.getCoordinateInfo());
    }

    /**
     * Check to see if a position can set bomb.
     */
    public boolean canSetBomb(int x, int y) {
        if (bombList.size() == numberOfBomb) {
            return false;
        }
        if (BombermanGame.map.getEntity(x * SCALED_SIZE, y * SCALED_SIZE) instanceof Grass) {
            return true;
        }

        return false;
    }

    /**
     * Bomb explode.
     */
    public void bombExplode(int index) {
        Bomb bomb = bombList.get(index);

        int[] valX = {-1, 1, 0, 0};
        int[] valY = {0, 0, -1, 1};
        Direction[] valD = {Direction.LEFT, Direction.RIGHT, Direction.UP, Direction.DOWN};
        boolean[] valCheck = {true, true, true, true};

        int x = bomb.getX() / SCALED_SIZE;
        int y = bomb.getY() / SCALED_SIZE;

        flameList.add(new Flame(x, y, Direction.CENTER, Flame.TYPE.BODY));

        for (int i = 1; i <= power; i++) {
            for (int j = 0; j < 4; j++) {
                int posX = x + i * valX[j];
                int posY = y + i * valY[j];

                Flame.TYPE type = Flame.TYPE.BODY;
                if (i == power) type = Flame.TYPE.LAST;

                if (valCheck[j]) {
                    for (Bomb bomb1 : bombList) {
                        if (bomb1.getX() / SCALED_SIZE == posX && bomb1.getY() / SCALED_SIZE == posY) {
                            bomb1.setExploded(true);
                            valCheck[j] = false;
                            break;
                        }
                    }

                    if (!(BombermanGame.map.getMap().get(posY).get(posX) instanceof Obstacle)) {
                        flameList.add(new Flame(posX, posY, valD[j], type));
                    } else {
                        valCheck[j] = false;
                        if ((BombermanGame.map.getMap().get(posY).get(posX) instanceof Brick)) {
                            if (BombermanGame.map.getCode(posX, posY) == Portal.code) {
                                BombermanGame.map.replace(posX, posY, new Portal(posX, posY, Sprite.portal.getFxImage()));
                                break;
                            }
                            int random = (int) (Math.random() * 20);
                            switch (random) {
                                case 0:
                                    BombermanGame.map.replace(posX, posY, new FlameItem(posX, posY, Sprite.powerup_flames.getFxImage()));
                                    break;
                                case 1:
                                    BombermanGame.map.replace(posX, posY, new BombItem(posX, posY, Sprite.powerup_bombs.getFxImage()));
                                    break;
                                case 2:
                                    BombermanGame.map.replace(posX, posY, new SpeedItem(posX, posY, Sprite.powerup_speed.getFxImage()));
                                    break;
                                case 3:
                                    BombermanGame.map.replace(posX, posY, new BombPassItem(posX, posY, Sprite.powerup_bombpass.getFxImage()));
                                    break;
                                default:
                                    BombermanGame.map.replace(posX, posY, new Grass(posX, posY, Sprite.grass.getFxImage()));
                                    break;
                            }
                            flameList.add(new Flame(posX, posY, valD[j], Flame.TYPE.BRICK));
                        }
                    }
                }
            }
        }
        bombList.remove(bomb);
        Sound.bombExplose.play();
    }

    /**
     * Render Bomb and Flame.
     */
    public void renderBombs(GraphicsContext gc, Camera camera) {
        for (Bomb bomb : bombList) {
            bomb.render(gc, camera);
        }
        for (Flame flame : flameList) {
            flame.render(gc, camera);
        }
    }

    public Bomb getBomb(int x, int y) {
        for (Bomb bomb : bombList) {
            int a = Math.round((bomb.getX() + DEFAULT_SIZE) / SCALED_SIZE);
            int b = Math.round((bomb.getY() + DEFAULT_SIZE) / SCALED_SIZE);
            if (a == x && b == y) return bomb;
        }
        return null;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void setNumberOfBomb(int numberOfBomb) {
        this.numberOfBomb = numberOfBomb;
    }

    public int getNumberOfBomb() {
        return numberOfBomb;
    }

    public void setHasJustSetBomb(boolean hasJustSetBomb) {
        this.hasJustSetBomb = hasJustSetBomb;
    }

    public boolean HasJustSetBomb() {
        return hasJustSetBomb;
    }

    public Map getMap() {
        return BombermanGame.map;
    }

    public List<Bomb> getBombList() {
        return bombList;
    }

    public List<Flame> getFlameList() {
        return flameList;
    }

    public void clear() {
        bombList.clear();
        flameList.clear();
    }
}
