package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

public class MoveableEntity extends Entity{

    public MoveableEntity(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        //abstract
    }
}
