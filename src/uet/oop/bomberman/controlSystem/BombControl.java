package uet.oop.bomberman.controlSystem;

import uet.oop.bomberman.Map;
import uet.oop.bomberman.entities.Bomb;

import java.util.ArrayList;
import java.util.List;

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

//    public List<Bomb> updateBombList() {
//
//    }

    public void addBomb(Bomb bomb) {
        bombList.add(bomb);
        // add cả vô Map nữa
    }

    public boolean canSetBomb(int x, int y) {
        // to do
        if (collisionManage.canMove(x,y,0, Direction.UP))
            return true;
        return false;
    }

}
