package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.controlSystem.Direction;

import static uet.oop.bomberman.controlSystem.Direction.*;
import static uet.oop.bomberman.controlSystem.Direction.LEFT;

public class MoveableEntity extends Entity {
    protected int speed;
    Direction direction;
    public int count = 0;
    protected static int[] FIX_LENGTH = {0, -4, 4};

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public MoveableEntity(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);

    }

    @Override
    public void update() {
        //abstract
    }

    public boolean checkCanMove(int x, int y, int speed, Direction direction) {
        return true;
    }

    public void goByDirection(Direction direction) {
        switch (direction) {
            case DOWN:
                for (int num : FIX_LENGTH) {
                    if (checkCanMove(x + num, y, speed, DOWN)) {
                        y += speed;
                        x = x + num;
                        setDirection(DOWN);
                        count++;
                        break;
                    }
                }
                break;
            case UP:
                for (int num : FIX_LENGTH) {
                    if (checkCanMove(x + num, y, speed, UP)) {
                        y -= speed;
                        x = x + num;
                        setDirection(UP);
                        count++;
                        break;
                    }
                }
                break;
            case RIGHT:
                for (int num : FIX_LENGTH) {
                    if (checkCanMove(x, y + num, speed, RIGHT)) {
                        x += speed;
                        y = y + num;
                        setDirection(RIGHT);
                        count++;
                        break;
                    }
                }
                break;
            case LEFT:
                for (int num : FIX_LENGTH) {
                    if (checkCanMove(x, y + num, speed, LEFT)) {
                        x -= speed;
                        y = y + num;
                        setDirection(LEFT);
                        count++;
                        break;
                    }
                }
                break;
        }
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }
}
