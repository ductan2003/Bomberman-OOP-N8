package uet.oop.bomberman.controlSystem;

import uet.oop.bomberman.Map;
import uet.oop.bomberman.entities.*;

import java.util.ArrayList;
import java.util.List;

public class EnemyControl {
    private List<Enemy> enemyList = new ArrayList<>();
    private BombControl bombControl;
    private Map map;

    public EnemyControl() {

    }

    public EnemyControl(BombControl bombControl, Map map) {
        this.map = map;
        this.bombControl = bombControl;
    }

    public List<Enemy> getEnemyList() {
        return enemyList;
    }

    /**
     * add Enemies to the enemyList and entities.
     */
    public void addEnemy(Enemy enemy, List<Entity> entities) {
        enemyList.add(enemy);
        entities.add(enemy);
    }

    /**
     * Update Enemy, check Death.
     */
    public void updateEnemyList() {
        int i = 0;

        while (i < enemyList.size()) {
            boolean check = false;
            boolean dead = false;

            if (enemyList.get(i) instanceof Balloom) {
                if (((Balloom) enemyList.get(i)).checkDeath()) {
                    dead = true;
                }
            }

            if (enemyList.get(i) instanceof Oneal) {
                if (((Oneal) enemyList.get(i)).checkDeath()) {
                    dead = true;
                }
            }

            if (enemyList.get(i) instanceof Doll) {
                if (((Doll) enemyList.get(i)).checkDeath()) {
                    dead = true;
                }
            }

            if (dead) {
                enemyList.get(i).setDead(true);
                enemyList.remove(enemyList.get(i));
                if (enemyList.size() == 0) break;
                check = true;
            }

            if (!check) i++;
        }
    }

}
