package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.controlSystem.Camera;
import uet.oop.bomberman.controlSystem.Collision;

import static uet.oop.bomberman.controlSystem.Direction.*;
import static uet.oop.bomberman.graphics.Sprite.*;

public class Balloom extends Enemy{
    public Collision collision;
    public Balloom(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        speed = 1;
    }

    public Balloom(int xUnit, int yUnit, Image img, Collision collision) {
        super(xUnit, yUnit, img);
        speed = 1;
        this.collision = collision;
        direction = RIGHT;
    }

    public void go() {
        //slow the enemy
//        if (count % 6 == 1 || count % 6 == 3 || count % 6 == 5) return;
        //go
        if (getDirection() == RIGHT) {
            if (goRight()) return;
            else goRand();
        }

        if (getDirection() == LEFT) {
            if (goLeft()) return;
            else goRand();
        }

        if (getDirection() == DOWN) {
            if (goDown()) return;
            else goRand();
        }

        if (getDirection() == UP) {
            if (goUp()) return;
            else goRand();
        }
    }

    public void update() {
        count++;
        go();
        img = getImg();
    }

    public Image getImg() {
        if (super.getDirection() == LEFT || super.getDirection() == UP)
            return movingSprite(balloom_left1, balloom_left2, balloom_left3, count, 9).getFxImage();
        if (super.getDirection() == RIGHT || super.getDirection() == DOWN) {
            return movingSprite(balloom_right1, balloom_right2, balloom_right3, count, 9).getFxImage();
        }
        return img;
    }

    public void render(GraphicsContext gc, Camera camera) {
        gc.drawImage(img, x, y);
    }

    public boolean goLeft() {
        if (collision.canMove(x, y, speed, LEFT)) {
            x -= speed;
            setDirection(LEFT);
            return true;
        }
        return false;
    }

    public boolean goRight() {
        if (collision.canMove(x, y, speed, RIGHT)) {
            x += speed;
            setDirection(RIGHT);
            return true;
        }
        return false;
    }

    public boolean goUp() {
        if (collision.canMove(x, y, speed, UP)) {
            y -= speed;
            setDirection(UP);
            return true;
        }
        return false;
    }

    public boolean goDown() {
        if (collision.canMove(x, y, speed, DOWN)) {
            y += speed;
            setDirection(DOWN);
            return true;
        }
        return false;
    }

    public void goRand() {
        int rand = (int)(Math.random() * 4);
        switch (rand) {
            case 0:
                if(goDown()) return;
                if(goLeft()) return;
                if(goUp()) return;
                if(goRight()) return;
            case 1:
                if(goLeft()) return;
                if(goUp()) return;
                if(goRight()) return;
                if(goDown()) return;
            case 2:
                if(goUp()) return;
                if(goRight()) return;
                if(goDown()) return;
                if(goLeft()) return;
            case 3:
                if(goRight()) return;
                if(goDown()) return;
                if(goLeft()) return;
                if(goUp()) return;
        }
    }
}
