package uet.oop.bomberman.controlSystem;

import uet.oop.bomberman.Map;
import uet.oop.bomberman.entities.*;

import java.util.ArrayList;
import java.util.List;

public class EnemyControl {
    private final List<Enemy> enemyList;

    public EnemyControl() {
        enemyList = new ArrayList<>();
    }

    public List<Enemy> getEnemyList() {
        return enemyList;
    }

    /**
     * add Enemies to the enemyList and entities.
     */
    public void addEnemy(Enemy enemy) {
        enemyList.add(enemy);
        Map.entities.add(enemy);
    }

    /**
     * Update Enemy, check Death.
     */
    public void updateEnemyList() {
        int i = 0;

        while (i < enemyList.size()) {
            boolean check = false;
            boolean dead = false;


            if (enemyList.get(i).checkDeath()) {
                dead = true;
            }


            if (dead) {
                enemyList.get(i).setDead(true);
                enemyList.remove(i);
                if (enemyList.size() == 0) break;
                check = true;
            }

            if (!check) i++;
        }
    }

    public void clear() {
        enemyList.clear();
    }

}
