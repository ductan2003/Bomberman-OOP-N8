package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

public class Brick extends DestroyableEntity{
    public Brick(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public void die() {
        isDead = true;
    }
}
