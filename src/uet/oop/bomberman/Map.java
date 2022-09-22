package uet.oop.bomberman;

import uet.oop.bomberman.entities.Entity;

import java.util.ArrayList;
import java.util.List;

public class Map {
    protected List<List<Entity>> map;
    protected int height;
    protected int width;

    protected int level;

    protected int numberBomberDie = 0;
    protected int numberBomberLife = 3;

    protected boolean isWin = false;

    public void CreateMap(int level) {
        map = new ArrayList<>();
        this.level = level;

        numberBomberDie = 0;
        numberBomberLife = 3;

        // Read a map file
    }
}
