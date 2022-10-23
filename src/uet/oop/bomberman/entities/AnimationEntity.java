package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class AnimationEntity extends Entity {
    public AnimationEntity(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
    }
}
