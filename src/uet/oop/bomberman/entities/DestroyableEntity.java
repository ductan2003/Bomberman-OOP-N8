package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.controlSystem.Sound;

public class DestroyableEntity extends MoveableEntity implements Obstacle{
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
