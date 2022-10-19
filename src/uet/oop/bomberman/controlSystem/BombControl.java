package uet.oop.bomberman.controlSystem;

import uet.oop.bomberman.Map;
import uet.oop.bomberman.entities.Bomb;
import uet.oop.bomberman.entities.Grass;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;

public class BombControl {
    // Todo: setup bomb
    private List<Bomb> bombList = new ArrayList<>();
    Collision collisionManage;
    boolean hasJustSetBomb = false;
    Map map;

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
                bombExplode();
                Grass g = new Grass(bombList.get(i).getXMapCoordinate(bombList.get(i).getX()),
                        bombList.get(i).getYMapCoordinate(bombList.get(i).getY()), Sprite.grass.getFxImage());
                map.addEntity(g);
                bombList.remove(i);
            }
        }
//        if (hasJustSetBomb) {
//            for (int i=0; i<bombList.size();i++) {
//                if (collisionManage.collide(bombList.get(i), ))
//            }
//        }
    }

//    public void getBombInfo() {
//        System.out.println("Bomb: " + this.);
//    }
    public void addBomb(Bomb bomb) {
        bombList.add(bomb);
        map.addEntity(bomb);
        System.out.println("Bomb: " + bomb.getCoordinateInfo());
    }

    public boolean canSetBomb(int x, int y, Direction direction) {
        if (map.getEntity(x * SCALED_SIZE,y * SCALED_SIZE) instanceof Grass) {
//            System.out.println("Can Set Bomb" + map.getEntity(x * SCALED_SIZE, y * SCALED_SIZE));
            return true;
        }
        return false;
    }

    public void bombExplode() {
        System.out.println("Bomb Explode");

    }
}
