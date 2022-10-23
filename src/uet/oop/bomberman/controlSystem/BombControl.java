package uet.oop.bomberman.controlSystem;

import com.sun.prism.shader.DrawEllipse_ImagePattern_Loader;
import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;

public class BombControl {
    private List<Bomb> bombList = new ArrayList<>();
    private Map map;
    private int power = 1;
    private int numberOfBomb = 1;
    private List<Flame> flameList = new ArrayList<>();
    boolean hasJustSetBomb = false;

    /**
     *Remove bomb and Flame.
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

//    public void getBombInfo() {
//        System.out.println("Bomb: " + this.);
//    }

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
    public boolean canSetBomb(int x, int y, Direction direction) {
        if (bombList.size() == numberOfBomb) {
            return false;
        }
        if (map.getEntity(x * SCALED_SIZE, y * SCALED_SIZE) instanceof Grass) {
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

        flameList.add(new Flame(x, y, Direction.CENTER, Flame.TYPE.BODY, map));

        for (int i = 1; i <= power; i++) {
            for (int j = 0; j < 4; j++) {
                int posX = x + i * valX[j];
                int posY = y + i * valY[j];

                Flame.TYPE type = Flame.TYPE.BODY;
                if (i == power) type = Flame.TYPE.LAST;

                if (valCheck[j]) {
                    if (!(map.getMap().get(posY).get(posX) instanceof Obstacle)) {
                        flameList.add(new Flame(posX, posY, valD[j], type, map));
                    } else {
                        valCheck[j] = false;
                        if ((map.getMap().get(posY).get(posX) instanceof Brick)) {
                            if (map.getCode(posX, posY) == Portal.code) {
                                map.replace(posX, posY, new Portal(posX, posY, Sprite.portal.getFxImage()));
                                break;
                            }
                            int random = (int) (Math.random() * 20);
                            switch (random) {
                                case 0:
                                    map.replace(posX, posY, new FlameItem(posX, posY, Sprite.powerup_flames.getFxImage()));
                                    break;
                                case 1:
                                    map.replace(posX, posY, new BombItem(posX, posY, Sprite.powerup_bombs.getFxImage()));
                                    break;
                                case 2:
                                    map.replace(posX, posY, new SpeedItem(posX, posY, Sprite.powerup_speed.getFxImage()));
                                    break;
                                case 3:
                                    map.replace(posX, posY, new BombPassItem(posX, posY, Sprite.powerup_bombpass.getFxImage()));
                                    break;
                                default:
                                    map.replace(posX, posY, new Grass(posX, posY, Sprite.grass.getFxImage()));
                                    break;
                            }
                            flameList.add(new Flame(posX, posY, valD[j], Flame.TYPE.BRICK, map));
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

    public BombControl(Map map) {
        this.map = map;
    }

    public void setHasJustSetBomb(boolean hasJustSetBomb) {
        this.hasJustSetBomb = hasJustSetBomb;
    }

    public boolean HasJustSetBomb() {
        return hasJustSetBomb;
    }

    public Map getMap() {
        return map;
    }

    public List<Bomb> getBombList() {
        return bombList;
    }

    public List<Flame> getFlameList() {
        return flameList;
    }

}
