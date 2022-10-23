package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
//import jdk.internal.net.http.common.Pair;
import javafx.util.Pair;
import uet.oop.bomberman.controlSystem.BombControl;
import uet.oop.bomberman.controlSystem.Collision;
import uet.oop.bomberman.controlSystem.Direction;
import uet.oop.bomberman.controlSystem.Sound;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static uet.oop.bomberman.controlSystem.Direction.*;
import static uet.oop.bomberman.graphics.Sprite.DEFAULT_SIZE;
import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;

public class Enemy extends DestroyableEntity {
    protected enum Status {
        ALIVE, DEAD,
    }

    Direction direction;
    protected Status status;
    public int count = 0;
    protected int dist;
    protected static int[] FIX_LENGTH = {0, -4, 4};

    public Enemy(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
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
        Sound.enemyDie.play();
    }

    public void updateDist() {
        return;
    }

    public void render() {

    }

    public void go(Collision collision) {
        //slow the enemy
        if (count % 2 == 0) return;

        //go
        if (getDirection() == RIGHT) {
            if (!collision.isNextPosBomb(this, RIGHT, speed)) {
                if (goRight(collision)) {
                    return;
                } else {
                    goRand(collision);
                }
            } else {
                goLeft(collision);
            }
        }

        if (getDirection() == LEFT) {
            if (!collision.isNextPosBomb(this, LEFT, speed)) {
                if (goLeft(collision)) {
                    return;
                } else {
                    goRand(collision);
                }
            } else {
                goRight(collision);
            }
        }

        if (getDirection() == DOWN) {
            if (!collision.isNextPosBomb(this, DOWN, speed)) {
                if (goDown(collision)) {
                    return;
                } else {
                    goRand(collision);
                }
            } else {
                goUp(collision);
            }

        }

        if (getDirection() == UP) {
            if (!collision.isNextPosBomb(this, UP, speed)) {
                if (goUp(collision)) {
                    return;
                } else {
                    goRand(collision);
                }
            } else {
                goDown(collision);
            }

        }

    }

    public boolean goLeft(Collision collision) {
        if (canGoByDirection(collision, LEFT)) {
            x -= speed;
            setDirection(LEFT);
            return true;
        }
        return false;
    }

    public boolean goRight(Collision collision) {
        if (canGoByDirection(collision, RIGHT)) {
            x += speed;
            setDirection(RIGHT);
            return true;
        }
        return false;
    }

    public boolean goUp(Collision collision) {
        if (canGoByDirection(collision, UP)) {
            y -= speed;
            setDirection(UP);
            return true;
        }
        return false;
    }

    public boolean goDown(Collision collision) {
        if (canGoByDirection(collision, DOWN)) {
            y += speed;
            setDirection(DOWN);
            return true;
        }
        return false;
    }

    public void goRand(Collision collision) {
        int rand = (int) (Math.random() * 4);
        switch (rand) {
            case 0:
                if (goDown(collision)) return;
                if (goLeft(collision)) return;
                if (goUp(collision)) return;
                if (goRight(collision)) return;
            case 1:
                if (goLeft(collision)) return;
                if (goUp(collision)) return;
                if (goRight(collision)) return;
                if (goDown(collision)) return;
            case 2:
                if (goUp(collision)) return;
                if (goRight(collision)) return;
                if (goDown(collision)) return;
                if (goLeft(collision)) return;
            case 3:
                if (goRight(collision)) return;
                if (goDown(collision)) return;
                if (goLeft(collision)) return;
                if (goUp(collision)) return;
        }
    }

    public List<Pair<Integer, Integer>> getCoordinateDirection(Collision collision, int endX, int endY) {
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

                if (collision.isCoordinateValid(newX, newY) && formatMap.get(newX).get(newY) == 0) {
                    if (!visited[newX][newY]) {
                        q.add(new Pair<>(newX, newY));
                        distance[newX][newY] = distance[tmp.getKey()][tmp.getValue()] + 1;
                        last[newX][newY] = new Pair<>(tmp.getKey(), tmp.getValue());
                        visited[newX][newY] = true;
                    } else {
                        if (distance[newX][newY] > distance[tmp.getKey()][tmp.getValue()] + 1) {
                            distance[newX][newY] = distance[tmp.getKey()][tmp.getValue()] + 1;
                            last[newX][newY] = new Pair(tmp.getKey(), tmp.getValue());
//                            q.add(new Pair<>(newX, newY));
                        }
                    }

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

    public boolean canGoByDirection(Collision collision, Direction direction) {
        return (collision.canMove(x, y, speed, direction) && !collision.isNextPosEnemy(this, direction, speed));
//                && !collision.isNextPosBomb(this, direction, speed));
    }

    public boolean checkCanMove(int x, int y, int speed, Direction direction, Collision collision) {
        return (collision.canMove(x, y, speed, direction) && !collision.isNextPosEnemy(this, direction, speed)
                && !collision.isNextPosBomb(this, direction, speed));
    }


    public void goByDirection(Collision collision, Direction direction) {
        switch (direction) {
            case DOWN:
                for (int i = 0; i < FIX_LENGTH.length; i++) {
                    if (checkCanMove(x + FIX_LENGTH[i], y, speed, DOWN, collision)) {
                        y += speed;
                        x = x + FIX_LENGTH[i];
                        setDirection(DOWN);
                        break;
                    }
                }
                break;
            case UP:
                for (int i = 0; i < FIX_LENGTH.length; i++) {
                    if (checkCanMove(x + FIX_LENGTH[i], y, speed, UP, collision)) {
                        y -= speed;
                        x = x + FIX_LENGTH[i];
                        setDirection(UP);
                        break;
                    }
                }
                break;
            case RIGHT:
                for (int i = 0; i < FIX_LENGTH.length; i++) {
                    if (checkCanMove(x, y + FIX_LENGTH[i], speed, RIGHT, collision)) {
                        x += speed;
                        y = y + FIX_LENGTH[i];
                        setDirection(RIGHT);
                        break;
                    }
                }
                break;
            case LEFT:
                for (int i = 0; i < FIX_LENGTH.length; i++) {
                    if (checkCanMove(x, y + FIX_LENGTH[i], speed, LEFT, collision)) {
                        x -= speed;
                        y = y + FIX_LENGTH[i];
                        setDirection(LEFT);
                        break;
                    }
                }
                break;
        }
    }
}
