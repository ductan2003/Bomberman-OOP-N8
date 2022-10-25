package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

import static uet.oop.bomberman.graphics.Sprite.*;

public class Bomb extends Entity {
    private int count = 0;
    //    private long timeSet;
    private boolean isExploded;
    private int flame;
    private final long EXPLODE = 300;

    public boolean isExploded() {
        return isExploded;
    }

    public void setExploded(boolean exploded) {
        isExploded = exploded;
    }

    public Bomb(int xUnit, int yUnit, Image img, int count) {
        super(xUnit, yUnit, img);
        this.count = count;
//        timeSet = Timer.now();
        isExploded = false;
    }

    @Override
    public void update() {
        count++;
        if (!isExploded) {
            if ((count) > EXPLODE) isExploded = true;
        }
        img = getImg();
    }

    public Image getImg() {
        return movingSprite(bomb, bomb_1, bomb_2, count, 100).getFxImage();
    }

    public long getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
