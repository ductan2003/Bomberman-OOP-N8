package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

public class BombPassItem extends Item {
    public BombPassItem(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void powerUp(Bomber bomber) {
        bomber.getBombControl().setHasJustSetBomb(true);
    }
}
