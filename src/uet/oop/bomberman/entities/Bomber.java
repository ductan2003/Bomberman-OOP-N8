package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import uet.oop.bomberman.controlSystem.KeyListener;

public class Bomber extends DestroyableEntity {
    private boolean isGoToNextLevel = false;
    private KeyListener keyEvent;

    public void setGoToNextLevel(boolean goToNextLevel) {
        isGoToNextLevel = goToNextLevel;
    }

    public boolean isGoToNextLevel() {
        return isGoToNextLevel;
    }

    public Bomber(int x, int y, Image img) {
        super( x, y, img);
        speed = 1;
    }
// new Constructor with keyEvent
    public Bomber(int x, int y, Image img, KeyListener keyEvent) {
        super( x, y, img);
        this.keyEvent = keyEvent;
        speed = 1;
    }

    @Override
    public void update() {
        if (keyEvent.pressed(KeyCode.UP)) {
            y -= speed;
            System.out.println("Up" + " " + x + " " + y);
        }
        else if (keyEvent.pressed(KeyCode.DOWN)) {
            y += speed;
            System.out.println("DOWN" + " " + x + " " + y);
        }
        else if (keyEvent.pressed(KeyCode.LEFT)) {
            x -= speed;
            System.out.println("LEFT" + " " + x + " " + y);
        }
        else if (keyEvent.pressed(KeyCode.RIGHT)) {
            x += speed;
            System.out.println("RIGHT" + " " + x + " " + y);
        } else return;
    }
}
