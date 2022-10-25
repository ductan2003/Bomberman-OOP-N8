package uet.oop.bomberman.controlSystem;

import javafx.util.Pair;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.entities.*;


import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import static uet.oop.bomberman.Map.bombControl;
import static uet.oop.bomberman.Map.enemyControl;
import static uet.oop.bomberman.graphics.Sprite.*;


public class Collision {
    public static final int FIX = 4;

    public Collision() {
    }

    /**
     * Check if 2 entities which are not in map collide.
     */
    public boolean collide(Entity entity, Entity entity1) {
        ArrayList<Pair<Integer, Integer>> coordinates = new ArrayList<>();
        coordinates.add(new Pair<>(entity1.getX(), entity1.getY()));
        coordinates.add(new Pair<>(entity1.getX(), entity1.getY() + SCALED_SIZE));
        coordinates.add(new Pair<>(entity1.getX() + SCALED_SIZE, entity1.getY()));
        coordinates.add(new Pair<>(entity1.getX() + SCALED_SIZE, entity1.getY() + SCALED_SIZE));
        return contain(entity, coordinates.get(0))
                || contain(entity, coordinates.get(1))
                || contain(entity, coordinates.get(2))
                || contain(entity, coordinates.get(3));
    }

    /**
     * Check if 2 entities which are not in map collide.
     */
    public boolean checkCollide(Entity entity, Entity entity1) {
        ArrayList<Pair<Integer, Integer>> coordinates = new ArrayList<>();
        coordinates.add(new Pair<>(entity1.getX() + FIX, entity1.getY() + 2 * FIX));
        coordinates.add(new Pair<>(entity1.getX() + FIX,
                entity1.getY() + SCALED_SIZE - FIX));
        coordinates.add(new Pair<>(entity1.getX() + SCALED_SIZE - FIX,
                entity1.getY() + 2 * FIX));
        coordinates.add(new Pair<>(entity1.getX() + SCALED_SIZE - FIX,
                entity1.getY() + SCALED_SIZE - FIX));
        return contain(entity, coordinates.get(0))
                || contain(entity, coordinates.get(1))
                || contain(entity, coordinates.get(2))
                || contain(entity, coordinates.get(3));
    }

    /**
     * Check if 2 entities collide with the coordinate.
     */
    public boolean collide(Entity entity, int x, int y) {
        ArrayList<Pair<Integer, Integer>> coordinates = new ArrayList<>();
        coordinates.add(new Pair<>(x, y));
        coordinates.add(new Pair<>(x, y + SCALED_SIZE));
        coordinates.add(new Pair<>(x + SCALED_SIZE, y));
        coordinates.add(new Pair<>(x + SCALED_SIZE, y + SCALED_SIZE));
        return contain(entity, coordinates.get(0))
                || contain(entity, coordinates.get(1))
                || contain(entity, coordinates.get(2))
                || contain(entity, coordinates.get(3));
    }

    /**
     * Check if 2 entities share a position.
     */
    public boolean contain(Entity entity, Pair<Integer, Integer> point) {
        return (entity.getX() <= point.getKey() &&
                point.getKey() <= entity.getX() + SCALED_SIZE &&
                point.getValue() >= entity.getY() &&
                entity.getY() + SCALED_SIZE >= point.getValue());
    }

    /**
     * Check Bomber can move.
     */
    public boolean canMove(int x, int y, int speed, Direction direction) {
        Entity object1;
        Entity object2;
        switch (direction) {
            case UP:
                object1 = BombermanGame.map.getEntity(x + FIX, y + speed);
                object2 = BombermanGame.map.getEntity(x + SCALED_SIZE - FIX, y + speed);
//                entity.setY(y + speed);
                break;
            case DOWN:
                object1 = BombermanGame.map.getEntity(x + FIX, y + SCALED_SIZE + FIX - speed);
                object2 = BombermanGame.map.getEntity(x + SCALED_SIZE - FIX, y + SCALED_SIZE + FIX - speed);
//                entity.setY(y - speed);
                break;
            case RIGHT:
                object1 = BombermanGame.map.getEntity(x + speed + SCALED_SIZE - FIX, y + FIX);
                object2 = BombermanGame.map.getEntity(x + speed + SCALED_SIZE - FIX, y + SCALED_SIZE);
//                entity.setX(x + speed);
                break;
            case LEFT:
                object1 = BombermanGame.map.getEntity(x - speed, y + FIX);
                object2 = BombermanGame.map.getEntity(x - speed, y + SCALED_SIZE);
//                entity.setX(x - speed);
                break;
            default:
                object1 = BombermanGame.map.getEntity(x, y);
                object2 = BombermanGame.map.getEntity(x, y);
                break;
        }
        if (!(object2 instanceof Obstacle) && !(object1 instanceof Obstacle)) {
            return true;
        }
        return false;
    }

    /**
     * Check to find if the next position has a Bomb.
     */
    public boolean isNextPosBomb(Entity entity, Direction direction, int speed) {
        if (bombControl.getBombList().size() == 0) return false;
        int a = getNextXPos(entity, direction, speed);
        int b = getNextYPos(entity, direction, speed);
        for (int i = 0; i < bombControl.getBombList().size(); i++) {
            if (collide(bombControl.getBombList().get(i), a, b)) return true;
        }
        return false;
    }

    /**
     * Check to find if the next position has an Enemy.
     */
    public boolean isNextPosEnemy(Entity entity, Direction direction, int speed) {
        int countDuplicate = 0;
        if (enemyControl.getEnemyList().size() == 0) return false;
        int a = getNextXPos(entity, direction, speed);
        int b = getNextYPos(entity, direction, speed);
        for (int i = 0; i < enemyControl.getEnemyList().size(); i++) {
            if (collide(enemyControl.getEnemyList().get(i), a, b)) countDuplicate++;
            if (countDuplicate > 1) return true;
        }
        return false;
    }

    /**
     * Get next xPos.
     */
    public int getNextXPos(Entity entity, Direction direction, int speed) {
        int a = entity.getX();
        switch (direction) {
            case LEFT:
                a -= speed;
                break;
            case RIGHT:
                a += speed;
                break;
            default:
                break;
        }
        return a;
    }

    /**
     * Get next YPos.
     */
    public int getNextYPos(Entity entity, Direction direction, int speed) {
        int b = entity.getY();
        switch (direction) {
            case DOWN:
                b += speed;
                break;
            case UP:
                b -= speed;
                break;
            default:
                break;
        }
        return b;
    }

    /**
     * To get a map with 1 is obstacle, 0 is grass.
     */
    public List<List<Integer>> formatMapData() {
        List<List<Integer>> formatMap = new ArrayList<>();

        int height = BombermanGame.map.getHeight();
        int width = BombermanGame.map.getWidth();

        //Format the entities in map
        for (int i = 0; i < height; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                if (BombermanGame.map.getEntityWithMapPos(i, j) instanceof Grass) {
                    row.add(0);
                } else row.add(1);
            }
            formatMap.add(row);
        }
        //Todo: Format Enemy map && BomberPos
        for (Entity entity : BombermanGame.map.getEntities()) {
            formatMap.get(entity.getYMapCoordinate(entity.getY())).set(entity.getXMapCoordinate(entity.getX()), 1);
        }

        //Bomb.
        for (Bomb bomb : bombControl.getBombList()) {
            formatMap.get(bomb.getYMapCoordinate(bomb.getY())).set(bomb.getXMapCoordinate(bomb.getX()), 1);
        }

        return formatMap;
    }

    public List<List<Character>> formatMapDetailData() {
        List<List<Character>> formatMap = new ArrayList<>();

        int height = BombermanGame.map.getHeight();
        int width = BombermanGame.map.getWidth();

        //Format the entities in map
        for (int i = 0; i < height; i++) {
            List<Character> row = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                if (BombermanGame.map.getEntityWithMapPos(i, j) instanceof Grass) {
                    row.add(' ');
                } else if (BombermanGame.map.getEntityWithMapPos(i, j) instanceof Wall) {
                    row.add('#');
                } else if (BombermanGame.map.getEntityWithMapPos(i, j) instanceof FlameItem) {
                    row.add('f');
                } else if (BombermanGame.map.getEntityWithMapPos(i, j) instanceof BombItem) {
                    row.add('b');
                } else if (BombermanGame.map.getEntityWithMapPos(i, j) instanceof SpeedItem) {
                    row.add('s');
                } else if (BombermanGame.map.getEntityWithMapPos(i, j) instanceof BombPassItem) {
                    row.add('m');
                } else if (BombermanGame.map.getEntityWithMapPos(i, j) instanceof Brick) {
                    row.add('*');
                } else row.add(' ');
            }
            formatMap.add(row);
        }
        //Bomber, enemy
        for (Entity entity : BombermanGame.map.getEntities()) {
            int x = Math.round((entity.getX() + DEFAULT_SIZE) / SCALED_SIZE);
            int y = Math.round((entity.getY() + DEFAULT_SIZE) / SCALED_SIZE);
            if (entity instanceof Bomber) {
                formatMap.get(y).set(x, 'p');
            } else if (entity instanceof Balloom) {
                formatMap.get(y).set(x, '1');
            } else if (entity instanceof Oneal) {
                formatMap.get(y).set(x, '2');
            } else if (entity instanceof Doll) {
                formatMap.get(y).set(x, '3');
            }
        }

        //Bomb.
        for (Bomb bomb : bombControl.getBombList()) {
            formatMap.get(bomb.getYMapCoordinate(bomb.getY())).set(bomb.getXMapCoordinate(bomb.getX()), '!');
        }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (BombermanGame.map.getCode(j, i) == Portal.code && BombermanGame.map.getEntityWithMapPos(i, j) instanceof Brick) {
                    formatMap.get(i).set(j, 'x');
                } else if (BombermanGame.map.getCode(j, i) == Portal.code) {
                    formatMap.get(i).set(j, 'y');
                }
            }
            System.out.println();
        }

        return formatMap;
    }

    public void saveData() {
        List<List<Character>> formatMap = formatMapDetailData();
        StringBuilder res = new StringBuilder();
        res.append(BombermanGame.map.getLevel());
        res.append(' ');
        res.append(BombermanGame.map.getHeight());
        res.append(' ');
        res.append(BombermanGame.map.getWidth());
        res.append('\n');

        for (int i = 0; i < BombermanGame.map.getHeight(); i++) {
            for (int j = 0; j < BombermanGame.map.getWidth(); j++) {
                res.append(formatMap.get(i).get(j));
            }
            res.append('\n');
        }

        if (BombermanGame.map.getEntities().get(0) instanceof Bomber) {
            Bomber bomber = (Bomber) BombermanGame.map.getEntities().get(0);
            res.append(bomber.getSpeed());
            res.append('\n');
            res.append(bomber.getBombControl().getNumberOfBomb());
            res.append('\n');
            if (bomber.getBombControl().hasJustSetBomb) {
                res.append(1);
            } else res.append(0);
            res.append('\n');
            res.append(bomber.getBombControl().getPower());
            res.append('\n');
            res.append(bomber.getLives());
            res.append('\n');
            res.append(bomber.getTimeRemain());
        }

        try {
            String text = "SaveData" + ".txt";
            Formatter f = new Formatter(text);
            f.format(res.toString());
            f.close();
        } catch (Exception e) {
            System.out.println("Error");
        }
        formatMap.clear();
    }

    /**
     * Check if the coordinate valid.
     */
    public boolean isCoordinateValid(int x, int y) {
        return x >= 0 && x < BombermanGame.map.getHeight() && y >= 0 && y < BombermanGame.map.getWidth();
    }

    public Map getMap() {
        return BombermanGame.map;
    }
}
