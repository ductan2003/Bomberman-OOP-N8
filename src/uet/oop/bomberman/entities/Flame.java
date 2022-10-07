package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Flame extends AnimationEntity{
    public Flame(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
    }

    @Override
    public void update() {

    }
}
