package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

public abstract class Item extends Entity {
    public Item(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
    }

    public abstract void powerUp(Bomber bomber);

}
