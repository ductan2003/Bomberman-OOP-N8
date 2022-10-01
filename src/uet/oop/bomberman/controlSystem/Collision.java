package uet.oop.bomberman.controlSystem;

import javafx.util.Pair;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Grass;


import java.util.ArrayList;

import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;


public class Collision {
    private Map map;
    private final int FIX = 4;

    public Collision(Map map) {
        this.map = map;
    }

    public Entity getEntity(int xPos, int yPos) {
        return map.getEntity(xPos, yPos);
    }

    // add direction to the entity to compare to entity1
    public boolean collide(Entity entity, Entity entity1) {
        System.out.println(entity.getX() + " " + entity.getY());
        System.out.println(entity1.getX() + " " + entity1.getY());
        ArrayList<Pair<Integer, Integer>> coordinates = new ArrayList<>();
        coordinates.add(new Pair<Integer, Integer>(entity1.getX(), entity1.getY()));
        coordinates.add(new Pair<Integer, Integer>(entity1.getX(), entity1.getY() + SCALED_SIZE));
        coordinates.add(new Pair<Integer, Integer>(entity1.getX() + SCALED_SIZE, entity1.getY()));
        coordinates.add(new Pair<Integer, Integer>(entity1.getX() + SCALED_SIZE, entity1.getY() + SCALED_SIZE));
        return contain(entity, coordinates.get(0))
                || contain(entity, coordinates.get(1))
                || contain(entity, coordinates.get(2))
                || contain(entity, coordinates.get(3));
    }

    public boolean contain(Entity entity, Pair<Integer, Integer> point) {
        return (entity.getX() <= point.getKey() &&
                point.getKey() <= entity.getX() + SCALED_SIZE &&
                point.getValue() >= entity.getY() &&
                entity.getY() + SCALED_SIZE >= point.getValue());
    }

    public boolean canMove(int x, int y, int speed, Direction direction) {
        Entity object1;
        Entity object2;
        switch (direction) {
            case UP:
                object1 = map.getEntity(x + FIX, y + speed + FIX);
                object2 = map.getEntity(x + SCALED_SIZE - 2 * FIX, y + speed - FIX);
//                entity.setY(y + speed);
                break;
            case DOWN:
                object1 = map.getEntity(x + FIX, y + SCALED_SIZE + FIX - speed);
                object2 = map.getEntity(x + SCALED_SIZE - 2 * FIX, y + SCALED_SIZE + FIX - speed);
//                entity.setY(y - speed);
                break;
            case RIGHT:
                object1 = map.getEntity(x + speed + SCALED_SIZE - 2 * FIX, y + 2 * FIX);
                object2 = map.getEntity(x + speed + SCALED_SIZE - 2 * FIX, y + SCALED_SIZE - FIX);
//                entity.setX(x + speed);
                break;
            case LEFT:
                object1 = map.getEntity(x - speed, y + 2 * FIX);
                object2 = map.getEntity(x - speed, y + SCALED_SIZE - FIX);
//                entity.setX(x - speed);
                break;
            default:
                object1 = map.getEntity(x, y);
                object2 = map.getEntity(x, y);
                break;
        }
        if (object2 instanceof Grass && object1 instanceof Grass) {
            return true;
        }
        return false;
    }

    public Map getMap() {
        return map;
    }
}
