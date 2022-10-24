package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

public class MoveableEntity extends Entity {
    protected int speed;

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public MoveableEntity(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);

    }

    @Override
    public void update() {
        //abstract
    }
}
