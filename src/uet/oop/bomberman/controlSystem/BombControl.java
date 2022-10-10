package uet.oop.bomberman.controlSystem;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;

public class BombControl {
    // Todo: setup bomb
    private List<Bomb> bombList = new ArrayList<>();
    private Collision collisionManage;
    private Map map;
    private int power=1;
    private List<Flame> flameList = new ArrayList<>();
    boolean hasJustSetBomb = false;

    public BombControl(Collision collisionManage, int x, int y) {
        this.collisionManage = collisionManage;
        this.map = collisionManage.getMap();
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

    public void updateBombList() {
        for (int i=0; i<bombList.size();i++) {
            bombList.get(i).update();
            if (bombList.get(i).isExploded()) {
                this.bombExplode(i);
            }
        }
        for (int i=0;i<flameList.size();i++) {
            flameList.get(i).update();
            if(flameList.get(i).isExploded()) {
                flameList.remove(i);
                i--;
            }
        }
    }

//    public void getBombInfo() {
//        System.out.println("Bomb: " + this.);
//    }
    public void addBomb(Bomb bomb) {
        bombList.add(bomb);
//        map.addEntity(bomb);
        System.out.println("Bomb: " + bomb.getCoordinateInfo());
    }

    public boolean canSetBomb(int x, int y, Direction direction) {
//        if (collisionManage.canMove(x * SCALED_SIZE,y * SCALED_SIZE,0, direction)) {
//            System.out.println("Can Set Bomb" + map.getEntity(x * SCALED_SIZE, y * SCALED_SIZE));
//            return true;
//        }
        if (map.getEntity(x * SCALED_SIZE,y * SCALED_SIZE) instanceof Grass) {
            System.out.println("Can Set Bomb" + map.getEntity(x * SCALED_SIZE, y * SCALED_SIZE));
            return true;
        }

        return false;
    }

    public boolean isNextPosBomb(Entity bomber) {
        if (bombList.size() == 0) return false;
        for (int i = 0; i < bombList.size(); i++) {
            if (collisionManage.collide(bomber, bombList.get(i))) return true;
        }
        return false;
    }

    public void bombExplode(int index) {
        Bomb bomb = bombList.get(index);
        int[] valX = {-1,1,0,0};
        int[] valY = {0,0,-1,1};
        Direction[] valD = {Direction.LEFT,Direction.RIGHT,Direction.UP,Direction.DOWN};
        boolean[] valCheck = {true,true,true,true};
        int x = bomb.getX()/ SCALED_SIZE;
        int y = bomb.getY()/ SCALED_SIZE;

        flameList.add(new Flame(x,y,Direction.CENTER,collisionManage));
        for (int i=1; i<=power; i++) {
            for (int j=0; j<4;j++) {
                int posX = x+i*valX[j];
                int posY = y+i*valY[j];
                if (valCheck[j] && !(map.getMap().get(posY).get(posX) instanceof Obstacle)) {
                    flameList.add(new Flame(posX, posY, valD[j], collisionManage));
                } else {
                    if (valCheck[j] && (map.getMap().get(posY).get(posX) instanceof Brick)) {
                        map.replace(posX,posY,new Grass(posX,posY,Sprite.grass.getFxImage()));
                    }
                }
            }
        }
        bombList.remove(index);
    }

    public void renderBombs(GraphicsContext gc, Camera camera) {
        for (Bomb bomb:bombList) {
            bomb.render(gc, camera);
        }
        for (Flame flame:flameList) {
            flame.render(gc, camera);
        }
    }
}
