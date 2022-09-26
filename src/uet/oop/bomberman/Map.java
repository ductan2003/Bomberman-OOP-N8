package uet.oop.bomberman;

import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Grass;
import uet.oop.bomberman.entities.Wall;
import uet.oop.bomberman.graphics.Sprite;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Map {
    protected List<List<Entity>> map;
    protected int height;
    protected int width;

    protected int level;

    protected int numberBomberDie = 0;
    protected int numberBomberLife = 3;

    protected boolean isWin = false;

    public void createMap(int level) {
        map = new ArrayList<>();
        this.level = level;

        numberBomberDie = 0;
        numberBomberLife = 3;

        // Read a map file

        Path path = Paths.get("").toAbsolutePath();
        File file = new File(path.normalize().toString() + "/res/levels/Level" + level + ".txt");

        try {
            Scanner scanner = new Scanner(file);
            level = scanner.nextInt();
            height = scanner.nextInt();
            width = scanner.nextInt();
            scanner.nextLine();
            for(int i = 0; i < height; i++) {
                String tempStr = scanner.nextLine();
                List<Entity> tempList = new ArrayList<>();
                for (int j = 0; j < width; j++) {
                    switch (tempStr.charAt(j)) {
                        case '#':
                            tempList.add(new Wall(j, i, Sprite.wall.getFxImage()));
                            break;
                        case '*':
                            tempList.add(new Brick(j, i,Sprite.brick.getFxImage()));
                            break;
                        default:
                            tempList.add(new Grass(j, i,Sprite.grass.getFxImage()));
                            break;
                    }
                }
                map.add(tempList);
            }
            scanner.close();
        } catch (FileNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public List<List<Entity>> getMap() {
        return map;
    }
}
