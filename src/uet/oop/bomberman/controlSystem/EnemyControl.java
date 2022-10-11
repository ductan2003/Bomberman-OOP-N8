package uet.oop.bomberman.controlSystem;

import uet.oop.bomberman.Map;
import uet.oop.bomberman.entities.Enemy;
import uet.oop.bomberman.entities.Entity;

import java.util.ArrayList;
import java.util.List;

public class EnemyControl {
    private List<Enemy> enemyList = new ArrayList<>();
    private Collision collision;
    private Map map;

    public EnemyControl(Collision collision) {
        this.collision = collision;
        this.map = collision.getMap();
    }

    public List<Enemy> getEnemyList() {
        return enemyList;
    }

    public void addBalloomEnemy(Enemy enemy, List<Entity> entities) {
        enemyList.add(enemy);
        entities.add(enemy);
        System.out.println("Add Balloom Enemy " + enemy.getX() + " " + enemy.getY());
    }

//    public boolean collideOtherEnemy() {
//        for (int i = 0; i < getEnemyList().size(); i++) {
//            for (int j = i + 1; j < enemyList.size(); j++) {
//                if (collision.collide(enemyList.get(i), enemyList.get(j)))
//                    return true;
//            }
//        }
//        return false;
//    }
}
