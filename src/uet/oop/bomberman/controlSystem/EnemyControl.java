package uet.oop.bomberman.controlSystem;

import javafx.util.Pair;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.entities.Balloom;
import uet.oop.bomberman.entities.Enemy;
import uet.oop.bomberman.entities.Entity;

import java.util.ArrayList;
import java.util.List;

public class EnemyControl {
    private List<Enemy> enemyList = new ArrayList<>();
    private Collision collision;
    private BombControl bombControl;
    private Map map;

    public EnemyControl(Collision collision, BombControl bombControl) {
        this.collision = collision;
        this.map = collision.getMap();
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

    public boolean collideOtherEnemy() {
        for (int i = 0; i < getEnemyList().size(); i++) {
            for (int j = i + 1; j < enemyList.size(); j++) {
                if (collision.collide(enemyList.get(i), enemyList.get(j)))
                    return true;
            }
        }
        return false;
    }

    public void updateEnemyList() {
        int i = 0;

        while (i < enemyList.size()) {
            boolean check = false;
            for (int j = 0; j < bombControl.getFlameList().size(); j++) {
                if (collision.collide(enemyList.get(i), bombControl.getFlameList().get(j))) {
                    System.out.println("Enemy meet bomb");
                    enemyList.get(i).setDead(true);
                    enemyList.remove(enemyList.get(i));
                    if (enemyList.size() == 0) break;
                    check = true;
                }
            }
            if (!check) i++;

        }
    }

    public boolean checkBombCollide() {
        return true;
    }
}
