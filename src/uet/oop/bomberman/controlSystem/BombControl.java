package uet.oop.bomberman.controlSystem;

import uet.oop.bomberman.Map;
import uet.oop.bomberman.entities.Bomb;
import uet.oop.bomberman.entities.Grass;

import java.util.ArrayList;
import java.util.List;

import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;

public class BombControl {
    // Todo: setup bomb
    private List<Bomb> bombList = new ArrayList<>();
    Collision collisionManage;
    Map map;

    public BombControl(Collision collisionManage) {
        this.collisionManage = collisionManage;
        this.map = collisionManage.getMap();
    }

    public Map getMap() {
        return map;
    }

    public List<Bomb> getBombList() {
        return bombList;
    }

    public void updateBombList() {

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
        // to do
        switch (direction) {
            case UP:
                y -= 1;
                break;
            case DOWN:
                y += 1;
                break;
            case LEFT:
                x -= 1;
                break;
            case RIGHT:
                x += 1;
                break;
            default: break;
        }
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
}
