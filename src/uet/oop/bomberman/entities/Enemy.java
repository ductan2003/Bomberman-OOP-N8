package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
//import jdk.internal.net.http.common.Pair;
import javafx.util.Pair;
import uet.oop.bomberman.controlSystem.BombControl;
import uet.oop.bomberman.controlSystem.Collision;
import uet.oop.bomberman.controlSystem.Direction;

import java.util.ArrayList;
import java.util.List;

import static uet.oop.bomberman.controlSystem.Direction.*;

public class Enemy extends DestroyableEntity{
    Collision collision;
    BombControl bombControl;
    Direction direction;
    public int count = 0;

    public Enemy(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public Enemy(int xUnit, int yUnit, Image img, Collision collision, BombControl bombControl) {
        super(xUnit, yUnit, img);
        this.collision = collision;
        this.bombControl = bombControl;
        direction = RIGHT;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public void setDead(boolean dead) {
        super.setDead(dead);
    }

    public void render() {

    }

    public Pair<Integer, Integer> getNextPos(Direction direction, int speed) {
        int a = x;
        int b = y;
        switch (direction) {
            case UP:
                b -= speed;
                break;
            case DOWN:
                b += speed;
                break;
            case LEFT:
                x -= speed;
                break;
            case RIGHT:
                x += speed;
                break;
            default: break;
        }
        return new Pair<>(a, b);
    }

}
