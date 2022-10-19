package uet.oop.bomberman;

import uet.oop.bomberman.controlSystem.BombControl;
import uet.oop.bomberman.controlSystem.Camera;
import uet.oop.bomberman.controlSystem.Collision;
import uet.oop.bomberman.controlSystem.KeyListener;
import uet.oop.bomberman.entities.*;
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
    protected int currentBomber = 0;
    protected int numberBomber = 1;
    protected int numberBomberDie = 0;
    protected int numberBomberLife = 3;
    public static final int MAX_NUMBER_BOMBERS = 4;

    public int getNumberBomber() {
        return numberBomber;
    }

    public void setNumberBomber(int temp) {
        numberBomber = temp;
    }

    public void setCurrentBomber(int temp) {
        currentBomber = temp;
    }

    public int getCurrentBomber() {
        return currentBomber;
    }

    public void setIsWin(boolean is){
        isWin = is;
    }
    public boolean getIsWin(){
        return isWin;
    }

    protected int startxPos = 6;
    protected int startyPos = 4;

    protected boolean isWin = false;
    protected Camera camera;
    private List<Entity> entities = new ArrayList<>();
    private Collision collision;
    private BombControl bombControl;

    public Map(int level, KeyListener keyListener) {
        createMap(level, keyListener);
    }

    public void createMap(int level, KeyListener keyListener) {
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
            for (int i = 0; i < height; i++) {
                String tempStr = scanner.nextLine();
                List<Entity> tempList = new ArrayList<>();
                for (int j = 0; j < width; j++) {
                    switch (tempStr.charAt(j)) {
                        case '#':
                            tempList.add(new Wall(j, i, Sprite.wall.getFxImage()));
                            break;
                        case '*':
                            tempList.add(new Brick(j, i, Sprite.brick.getFxImage()));
                            break;
                        default:
                            tempList.add(new Grass(j, i, Sprite.grass.getFxImage()));
                            break;
                    }
                }
                map.add(tempList);
            }
            scanner.close();
        } catch (FileNotFoundException exception) {
            System.out.println(exception.getMessage());
        }

        camera = new Camera(0, 0, width, height);
        collision = new Collision(this);
        bombControl = new BombControl(collision, startxPos, startyPos);
        Entity bomberman = new Bomber(startxPos, startyPos, Sprite.player_right.getFxImage(), keyListener, collision, bombControl);
        entities.add(bomberman);
    }

    public List<List<Entity>> getMap() {
        return map;
    }

    public Camera getCamera() {
        return camera;
    }

    public void update() {
        entities.forEach(Entity::update);
        int index=0;
        for(;index<entities.size();index++) {
            if(entities.get(index) instanceof Bomber) break;
        }
        camera.update(entities.get(index));
    }

    public void addEntity(Entity entity) {
        int xPos = Math.round(entity.getX() / Sprite.SCALED_SIZE);
        int yPos = Math.round(entity.getY() / Sprite.SCALED_SIZE);
        map.get(yPos).set(xPos, entity);
    }

    public List<Entity> getEntities() {
        return entities;
    }

    /**
     * Return entity in [xPos][yPos].
     */
    public Entity getEntity(int x, int y) {
        int xPos = Math.round(x / Sprite.SCALED_SIZE);
        int yPos = Math.round(y / Sprite.SCALED_SIZE);
        return map.get(yPos).get(xPos);
    }

}
