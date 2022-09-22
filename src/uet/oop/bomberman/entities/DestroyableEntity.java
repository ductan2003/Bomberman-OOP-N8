package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

public class DestroyableEntity extends MoveableEntity{
    protected boolean isDead = false;

    public DestroyableEntity(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public boolean isDead() {
        return isDead;
    }

}
