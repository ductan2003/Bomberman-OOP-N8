package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Bomber extends DestroyableEntity {
    private boolean isGoToNextLevel = false;

    public void setGoToNextLevel(boolean goToNextLevel) {
        isGoToNextLevel = goToNextLevel;
    }

    public boolean isGoToNextLevel() {
        return isGoToNextLevel;
    }

    public Bomber(int x, int y, Image img) {
        super( x, y, img);
        speed = 2;
    }

    @Override
    public void update() {

    }
}
