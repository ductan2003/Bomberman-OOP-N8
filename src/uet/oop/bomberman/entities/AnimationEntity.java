package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class AnimationEntity extends Entity {
//    public static enum DIRECTION {
//        UP, DOWN, LEFT, RIGHT, CENTER, NOTGO;
//    }
//
//    protected DIRECTION direction = DIRECTION.RIGHT;
//
//    public DIRECTION getDirection() {
//        return direction;
//    }
//
//    public void setDirection(DIRECTION direction) {
//        this.direction = direction;
//    }


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
