package uet.oop.bomberman.controlSystem;

import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.*;

public class Camera {
    private int x;
    private int y;
    private int width;
    private int height;

    public Camera() {

    }

    public Camera(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }

    /**
     * Camera follow the bomber.
     */
    public void update(Entity bomber) {
        //to make bomber at the center of screen
        x = bomber.getX() - Screen.WIDTH * Sprite.DEFAULT_SIZE;
        if (x < 0) {
            x = 0;
        }
        if (x + Screen.WIDTH * Sprite.SCALED_SIZE > width * Sprite.SCALED_SIZE) {
            x = width * Sprite.SCALED_SIZE - Screen.WIDTH * Sprite.SCALED_SIZE;
        }

        y = bomber.getY() - Screen.HEIGHT * Sprite.DEFAULT_SIZE;
        if (y < 0) {
            y = 0;
        }
        if (y + Screen.HEIGHT * Sprite.SCALED_SIZE > height * Sprite.SCALED_SIZE) {
            y = height * Sprite.SCALED_SIZE - Screen.HEIGHT * Sprite.SCALED_SIZE;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
