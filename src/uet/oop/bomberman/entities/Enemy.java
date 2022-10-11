package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.controlSystem.Collision;
import uet.oop.bomberman.controlSystem.Direction;

import java.util.ArrayList;
import java.util.List;

import static uet.oop.bomberman.controlSystem.Direction.*;

public class Enemy extends DestroyableEntity{
    Collision collision;
    Direction direction;
    public int count = 0;

    public Enemy(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public Enemy(int xUnit, int yUnit, Image img, Collision collision) {
        super(xUnit, yUnit, img);
        this.collision = collision;
        direction = RIGHT;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public void render() {

    }
}
