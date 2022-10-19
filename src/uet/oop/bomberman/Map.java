package uet.oop.bomberman;

import javafx.util.Pair;
import uet.oop.bomberman.controlSystem.*;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Map {
    protected List<List<Entity>> map;
    protected int[][] codeList;
    protected int height;
    protected int width;
    protected int level;

    protected int numberBomberDie = 0;
    protected int numberBomberLife = 3;

    protected int startxPos = 1;
    protected int startyPos = 1;

    protected boolean isWin = false;
    protected Camera camera;
    private List<Entity> entities;
    private List<Pair<Integer, Integer>> balloomPos;
    private List<Pair<Integer, Integer>> onealPos;
    private Collision collision;
    private BombControl bombControl;
    private EnemyControl enemyControl;

    public void createMap(int level, KeyListener keyListener) {
        map = new ArrayList<>();
        entities = new ArrayList<>();
        this.level = level;

        numberBomberDie = 0;
        numberBomberLife = 3;

        balloomPos = new ArrayList<>();
        onealPos = new ArrayList<>();
        // Read a map file

        Path path = Paths.get("").toAbsolutePath();
        File file = new File(path.normalize().toString() + "/res/levels/Level" + level + ".txt");

        try {
            Scanner scanner = new Scanner(file);
            level = scanner.nextInt();
            height = scanner.nextInt();
            width = scanner.nextInt();
            codeList = new int[height][width];
            scanner.nextLine();
            for (int i = 0; i < height; i++) {
                String tempStr = scanner.nextLine();
                List<Entity> tempList = new ArrayList<>();
                for (int j = 0; j < width; j++) {
                    switch (tempStr.charAt(j)) {
                        case 'p':
                            startxPos = j;
                            startyPos = i;
                            tempList.add(new Grass(j, i, Sprite.grass.getFxImage()));
                            break;
                        case '#':
                            tempList.add(new Wall(j, i, Sprite.wall.getFxImage()));
                            break;
                        case '*':
                            tempList.add(new Brick(j, i, Sprite.brick.getFxImage()));
                            break;
                        case 'x':
                            tempList.add(new Brick(j, i, Sprite.brick.getFxImage()));
                            codeList[i][j] = Portal.code;
                            break;
                        case '1':
                            tempList.add(new Grass(j, i, Sprite.grass.getFxImage()));
                            balloomPos.add(new Pair<>(j, i));
                            break;
                        case '2':
                            tempList.add(new Grass(j, i, Sprite.grass.getFxImage()));
                            onealPos.add(new Pair<>(j, i));
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
        bombControl = new BombControl(this);
        enemyControl = new EnemyControl(bombControl, this);
        collision = new Collision(this, bombControl, enemyControl);

        Entity bomberman = new Bomber(startxPos, startyPos, Sprite.player_right.getFxImage(), keyListener, collision);
        entities.add(bomberman);

        for (Pair<Integer, Integer> pos: balloomPos) {
            Enemy ballom = new Balloom(pos.getKey(), pos.getValue(), Sprite.balloom_right1.getFxImage(), collision);
            enemyControl.addEnemy(ballom, entities);
        }

        for (Pair<Integer, Integer> pos: onealPos) {
            Enemy oneal = new Oneal(pos.getKey(), pos.getValue(), Sprite.oneal_right1.getFxImage(), collision);
            enemyControl.addEnemy(oneal, entities);
        }
    }

    public List<List<Entity>> getMap() {
        return map;
    }

    public Camera getCamera() {
        return camera;
    }

    public void update() {
        Bomber bomber1 = (Bomber) entities.get(0);
        if (bomber1.isDead()) {
            bomber1.update();
            return;
        }
        entities.forEach(Entity::update);
        int index = 0;
        for (; index < entities.size(); index++) {
            if (entities.get(index) instanceof Bomber) {
                Bomber bomber = (Bomber) entities.get(index);
                camera.update(bomber);
                bomber.getBombControl().updateBomb();
            }
        }

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

    public Entity getEntityWithMapPos(int x, int y) {
        return map.get(x).get(y);
    }

    public void replace(int x, int y, Entity entity) {
        map.get(y).set(x, entity);
    }

    public int getLevel() {
        return level;
    }

    public int getCode(int x, int y) {
        return codeList[y][x];
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
