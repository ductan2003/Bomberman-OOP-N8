package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

public class Bomb extends MoveableEntity{
    private long timeRemain;
    private boolean isExploded;
    private int flame;

    public Bomb(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        //timeRemain = Timer.now();
        isExploded = false;
    }

}
