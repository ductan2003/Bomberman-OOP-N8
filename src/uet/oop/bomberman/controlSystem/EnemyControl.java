package uet.oop.bomberman.controlSystem;

import javafx.util.Pair;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.entities.Balloom;
import uet.oop.bomberman.entities.Bomb;
import uet.oop.bomberman.entities.Enemy;
import uet.oop.bomberman.entities.Entity;

import java.util.ArrayList;
import java.util.List;

public class EnemyControl {
    private List<Enemy> enemyList = new ArrayList<>();
    private BombControl bombControl;
//    private Collision collision;
    private Map map;

    public EnemyControl(BombControl bombControl, Map map) {
//        this.collision = collision;
        this.map = map;
        this.bombControl = bombControl;
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

    public void updateEnemyList() {
        int i = 0;

        while (i < enemyList.size()) {
            boolean check = false;
            boolean dead = false;
            if (enemyList.get(i) instanceof Balloom) {
                if (((Balloom) enemyList.get(i)).checkBalloomDeath()) {
                    dead = true;
                }
            }
            if (dead) {
                enemyList.get(i).setDead(true);
                enemyList.remove(enemyList.get(i));
                System.out.println("Delete Enemy");
                if (enemyList.size() == 0) break;
                check = true;
            }
            if (!check) i++;
        }
    }


}
