package uet.oop.bomberman.controlSystem;

import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.*;

public class Camera {
    private int x;
    private int y;
    private int height;
    private int width;

    public Camera() {

    }

    public Camera(int x, int y, int height, int width) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }

    public void update(Entity bomber) {

    }
}
