package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

public class Bomb extends AnimationEntity{
    private long timeRemain;
    private long countTime;
    private boolean isExploded;
    private int flame;

    public boolean isExploded() {
        return isExploded;
    }

    public void setExploded(boolean exploded) {
        isExploded = exploded;
    }

    public Bomb(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        countTime = 0;
        isExploded = false;
    }

    @Override
    public void update() {
        countTime++;
    }

}
