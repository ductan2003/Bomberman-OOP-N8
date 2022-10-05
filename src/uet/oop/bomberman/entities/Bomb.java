package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.controlSystem.Timer;

public class Bomb extends AnimationEntity{
    private long timeRemain;
    private long countTime;
    private long timeSet;
    private boolean isExploded;
    private int flame;
    private final long EXPLODE = 2000000000;

    public boolean isExploded() {
        return isExploded;
    }

    public void setExploded(boolean exploded) {
        isExploded = exploded;
    }

    public Bomb(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        countTime = 0;
        timeSet = Timer.now();
        isExploded = false;
    }

    @Override
    public void update() {
        countTime++;
        if(!isExploded) {
            isExploded = (Timer.now() - timeSet) > EXPLODE;
        }
    }

}
