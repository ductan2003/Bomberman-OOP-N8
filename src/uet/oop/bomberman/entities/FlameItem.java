package uet.oop.bomberman.entities;


import javafx.scene.image.Image;
import uet.oop.bomberman.controlSystem.BombControl;

public class FlameItem extends Item {
    public FlameItem(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
    }

    @Override
    public void powerUp(Bomber bomber) {
        bomber.getBombControl().setPower(bomber.getBombControl().getPower() + 1);
    }

}