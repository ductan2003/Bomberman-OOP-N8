package uet.oop.bomberman.controlSystem;

import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Screen;
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
        //to make bomber at the center of screen
        x = bomber.getX() - Screen.WIDTH * Sprite.DEFAULT_SIZE;
        if (x<0) x=0;

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
