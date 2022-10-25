package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.util.Pair;
import uet.oop.bomberman.controlSystem.Camera;
import uet.oop.bomberman.controlSystem.Direction;
import uet.oop.bomberman.controlSystem.Sound;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static uet.oop.bomberman.Map.bombControl;
import static uet.oop.bomberman.Map.collision;
import static uet.oop.bomberman.controlSystem.Direction.*;
import static uet.oop.bomberman.graphics.Sprite.*;

public class Enemy extends DestroyableEntity {
    protected enum Status {
        ALIVE, DEAD,
    }

    protected Status status;
    protected int dist;
    protected int countTimeDeath;

    public Enemy(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        direction = RIGHT;
        countTimeDeath = 0;
    }

    public int getCountTimeDeath() {
        return countTimeDeath;
    }

    @Override
    public void setDead(boolean dead) {
        super.setDead(dead);
        Sound.enemyDie.play();
    }

    public void updateDist() {
    }

    public boolean checkDeath() {
        for (int j = 0; j < bombControl.getFlameList().size(); j++) {
            if (collision.checkCollide(bombControl.getFlameList().get(j), this)) {
                status = Status.DEAD;
                return true;
            }
        }
        return false;
    }

    public void go() {
        //slow the enemy
        if (count % 2 == 0) return;

        //go
        if (getDirection() == RIGHT) {
            if (!collision.isNextPosBomb(this, RIGHT, speed)) {
                if (goRight()) {
                    return;
                } else {
                    goRand();
                }
            } else {
                goLeft();
            }
        }

        if (getDirection() == LEFT) {
            if (!collision.isNextPosBomb(this, LEFT, speed)) {
                if (goLeft()) {
                    return;
                } else {
                    goRand();
                }
            } else {
                goRight();
            }
        }

        if (getDirection() == DOWN) {
            if (!collision.isNextPosBomb(this, DOWN, speed)) {
                if (goDown()) {
                    return;
                } else {
                    goRand();
                }
            } else {
                goUp();
            }

        }

        if (getDirection() == UP) {
            if (!collision.isNextPosBomb(this, UP, speed)) {
                if (goUp()) {
                    return;
                } else {
                    goRand();
                }
            } else {
                goDown();
            }
        }

    }

    public boolean goLeft() {
        if (canGoByDirection(LEFT)) {
            x -= speed;
            setDirection(LEFT);
            return true;
        }
        return false;
    }

    public boolean goRight() {
        if (canGoByDirection(RIGHT)) {
            x += speed;
            setDirection(RIGHT);
            return true;
        }
        return false;
    }

    public boolean goUp() {
        if (canGoByDirection(UP)) {
            y -= speed;
            setDirection(UP);
            return true;
        }
        return false;
    }

    public boolean goDown() {
        if (canGoByDirection(DOWN)) {
            y += speed;
            setDirection(DOWN);
            return true;
        }
        return false;
    }

    public void goRand() {
        int rand = (int) (Math.random() * 4);
        switch (rand) {
            case 0:
                if (goDown()) return;
                if (goLeft()) return;
                if (goUp()) return;
                if (goRight()) return;
            case 1:
                if (goLeft()) return;
                if (goUp()) return;
                if (goRight()) return;
                if (goDown()) return;
            case 2:
                if (goUp()) return;
                if (goRight()) return;
                if (goDown()) return;
                if (goLeft()) return;
            case 3:
                if (goRight()) return;
                if (goDown()) return;
                if (goLeft()) return;
                if (goUp()) break;
        }
    }

    /**
     * Find path.
     */
    public List<Pair<Integer, Integer>> getCoordinateDirection(int endX, int endY) {
        List<List<Integer>> formatMap = collision.formatMapData();
        int height = collision.getMap().getHeight();
        int width = collision.getMap().getWidth();

        int startX = Math.round((y + DEFAULT_SIZE) / SCALED_SIZE);
        int startY = Math.round((x + DEFAULT_SIZE) / SCALED_SIZE);

        if (startX == endX && startY == endY) return null;

        formatMap.get(endX).set(endY, 0);
        formatMap.get(startX).set(startY, 0);

        Queue<Pair<Integer, Integer>> q = new LinkedList<>();
        q.add(new Pair<>(startX, startY));

        int[][] distance = new int[height][width];

        boolean[][] visited = new boolean[height][width];
        visited[startX][startY] = true;

        Pair<Integer, Integer>[][] last = new Pair[height][width];
        last[startX][startY] = new Pair<>(-1, -1);

        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};

        while (!q.isEmpty()) {
            Pair<Integer, Integer> tmp = q.poll();

            for (int i = 0; i < 4; i++) {
                int newX = tmp.getKey() + dx[i];
                int newY = tmp.getValue() + dy[i];

                if (collision.isCoordinateValid(newX, newY) && formatMap.get(newX).get(newY) == 0 && !visited[newX][newY]) {
                    q.add(new Pair<>(newX, newY));
                    distance[newX][newY] = distance[tmp.getKey()][tmp.getValue()] + 1;
                    last[newX][newY] = new Pair<>(tmp.getKey(), tmp.getValue());
                    visited[newX][newY] = true;
                }
            }
        }

        if (distance[endX][endY] == 0) return null;

        List<Pair<Integer, Integer>> pathCoordinate = new ArrayList<>();
        int X = last[endX][endY].getKey();
        int Y = last[endX][endY].getValue();
        pathCoordinate.add(0, new Pair<>(endX, endY));

        while (true) {
            if (last[X][Y].getKey() == -1 && last[X][Y].getValue() == -1) {
                pathCoordinate.add(0, new Pair<>(X, Y));
                break;
            }

            pathCoordinate.add(0, new Pair<>(X, Y));
            int tmpX = X;
            int tmpY = Y;
            X = last[tmpX][tmpY].getKey();
            Y = last[tmpX][tmpY].getValue();
        }
        return pathCoordinate;
    }

    /**
     * Direction Path.
     */
    public List<Direction> getDirection(List<Pair<Integer, Integer>> list) {
        List<Direction> path = new ArrayList<>();
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i).getKey() - list.get(i - 1).getKey() == 0 && list.get(i).getValue() - list.get(i - 1).getValue() == 1) {
                path.add(RIGHT);
            }
            if (list.get(i).getKey() - list.get(i - 1).getKey() == 0 && list.get(i).getValue() - list.get(i - 1).getValue() == -1) {
                path.add(LEFT);
            }
            if (list.get(i).getKey() - list.get(i - 1).getKey() == 1 && list.get(i).getValue() - list.get(i - 1).getValue() == 0) {
                path.add(DOWN);
            }
            if (list.get(i).getKey() - list.get(i - 1).getKey() == -1 && list.get(i).getValue() - list.get(i - 1).getValue() == 0) {
                path.add(UP);
            }
        }
        return path;
    }

    public boolean canGoByDirection(Direction direction) {
        return (collision.canMove(x, y, speed, direction) && !collision.isNextPosEnemy(this, direction, speed));
    }

    @Override
    public boolean checkCanMove(int x, int y, int speed, Direction direction) {
        return (collision.canMove(x, y, speed, direction) && !collision.isNextPosEnemy(this, direction, speed)
                && !collision.isNextPosBomb(this, direction, speed));
    }

    public void render(GraphicsContext gc, Camera camera) {
        if (!isDead) gc.drawImage(img, x - camera.getX(), y - camera.getY());
        if (status == Status.DEAD && countTimeDeath < 35) {
            gc.drawImage(img, x - camera.getX(), y - camera.getY());
        }
    }
}
