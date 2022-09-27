package uet.oop.bomberman.controlSystem;

import javafx.util.Pair;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;

import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;

public class Collision {
    private Map map;

    public Collision(Map map) {
        this.map = map;
    }

    public Entity getEntity(int xPos, int yPos) {
        return map.getMap().get(yPos).get(xPos);
    }
// add direction to the entity to compare to entity1
    public boolean collide(Entity entity, Entity entity1) {
        ArrayList<Pair<Integer, Integer>> coordinates = new ArrayList<>();
        coordinates.add(new Pair<Integer, Integer> (entity1.getX(), entity1.getY()));
        coordinates.add(new Pair<Integer, Integer> (entity1.getX(), entity1.getX() + SCALED_SIZE));
        coordinates.add(new Pair<Integer, Integer> (entity1.getX() + SCALED_SIZE, entity1.getX()));
        coordinates.add(new Pair<Integer, Integer> (entity1.getX() + SCALED_SIZE, entity1.getX() + SCALED_SIZE));
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

//    public Entity getNextPosition(Entity entity, Direction direction) {
//
//    }

}
