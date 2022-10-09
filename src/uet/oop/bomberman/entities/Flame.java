package uet.oop.bomberman.entities;

import javafx.scene.Camera;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.controlSystem.Collision;
import uet.oop.bomberman.controlSystem.Direction;
import uet.oop.bomberman.controlSystem.Timer;
import uet.oop.bomberman.graphics.Sprite;

public class Flame extends Entity implements Obstacle{
    private Collision collision;
    private Direction direction;
    private boolean exploded;
    private long timeSet;
    private long limit;

    public Flame(int xUnit, int yUnit, Direction direction, Collision collisionManager) {
        super(xUnit, yUnit);
        this.direction = direction;
        this.collision = collisionManager;
        if (direction.equals(Direction.LEFT) || direction.equals(Direction.RIGHT)) {
            this.setImg(Sprite.explosion_horizontal2.getFxImage());
        } else if (direction.equals(Direction.CENTER)) {
            this.setImg(Sprite.bomb_exploded2.getFxImage());
        } else {
            this.setImg(Sprite.explosion_vertical2.getFxImage());
        }
        exploded = false;
        timeSet = Timer.now();
        limit = 100000000;
    }

    @Override
    public void update() {
        Map map = collision.getMap();
        for (int i = 0; i < map.getEntities().size(); i++) {
            if (map.getEntities().get(i) instanceof DestroyableEntity) {

            }
        }
        if (Timer.now()-timeSet>limit) exploded = true;
    }
    public boolean isExploded() {return exploded;}
}
