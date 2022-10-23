package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.controlSystem.Camera;
import uet.oop.bomberman.controlSystem.Collision;
import uet.oop.bomberman.controlSystem.Direction;

import static uet.oop.bomberman.controlSystem.Direction.*;
import static uet.oop.bomberman.controlSystem.Direction.DOWN;
import static uet.oop.bomberman.graphics.Sprite.*;

public class Doll extends Enemy{
    private Collision collision;
    private int countTimeDeath = 0;

    public Doll(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public Doll(int xUnit, int yUnit, Image img, Collision collision) {
        super(xUnit, yUnit, img);
        speed = 1;
        this.collision = collision;
        direction = LEFT;
        this.status = Status.ALIVE;
        countTimeDeath = 0;
    }

    @Override
    public void setDead(boolean dead) {
        super.setDead(dead);
    }

    public int getCountTimeDeath() {
        return countTimeDeath;
    }

    @Override
    public void go(Collision collision) {
        if (count % 4 == 1) {
            super.go(collision);
        }
    }

    public void update() {
        if (countTimeDeath > 70) {
            return;
//            collision.getMap().getEntities().remove(this);
        }
        if (!isDead) {
            count++;
            go(collision);
            img = getImg();
        }
        if (status == Status.DEAD) {
            img = getImg();
            countTimeDeath++;
        }

        if (countTimeDeath == 35) {
            Balloom b1 = new Balloom(Math.round((x + DEFAULT_SIZE) / SCALED_SIZE), Math.round((y + DEFAULT_SIZE) / SCALED_SIZE),
                    balloom_right1.getFxImage(), collision, true);
            collision.getEnemyControl().addEnemy(b1, collision.getMap().getEntities());
        }
    }

    public boolean checkDeath() {
        for (int j = 0; j < collision.getBombControl().getFlameList().size(); j++) {
            if (collision.checkCollide(this, collision.getBombControl().getFlameList().get(j))) {
                status = Status.DEAD;
                return true;
            }
        }
        return false;
    }

    public Image getImg() {
        switch (status) {
            case ALIVE:
                if (super.getDirection() == LEFT || super.getDirection() == UP)
                    return movingSprite(doll_left1, doll_left2, doll_left3, count, 20).getFxImage();
                if (super.getDirection() == RIGHT || super.getDirection() == DOWN) {
                    return movingSprite(doll_right1, doll_right2, doll_right3, count, 20).getFxImage();
                }
                break;
            case DEAD:
                return doll_dead.getFxImage();
        }
        return img;
    }

    public void render(GraphicsContext gc, Camera camera) {
        if (!isDead)
            gc.drawImage(img, x - camera.getX(), y - camera.getY());
        if (status == Status.DEAD && countTimeDeath < 35) {
            gc.drawImage(img, x - camera.getX(), y - camera.getY());
        }
    }
}
