package uet.oop.bomberman.graphics;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Enemy;
import uet.oop.bomberman.entities.Entity;

import java.util.List;

public class Screen {
    public static int WIDTH = 22;
    public static int HEIGHT = 13;

    private GraphicsContext gc;

    public Screen(Canvas canvas) {
        gc = canvas.getGraphicsContext2D();
    }

    public void renderMap(Map map) {

        for (int i = 0; i < map.getMap().size(); i++) {
            map.getMap().get(i).forEach(g -> g.render(gc, map.getCamera()));
        }

        for (Entity entity: map.getEntities()) {
            if (entity instanceof Bomber) {
                ((Bomber) entity).render(gc, map.getCamera());
            }
            else entity.render(gc, map.getCamera());
        }

//        for (Enemy enemy)
    }

    public void clearScreen(Canvas canvas) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

}
