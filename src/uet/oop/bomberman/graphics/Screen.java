package uet.oop.bomberman.graphics;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.controlSystem.GameMenu;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static uet.oop.bomberman.Map.collision;
import static uet.oop.bomberman.entities.Bomber.lives;
import static uet.oop.bomberman.entities.Bomber.timeRemain;
import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;
import static uet.oop.bomberman.graphics.Sprite.heart;


public class Screen {
    public static int WIDTH = 22;
    public static int HEIGHT = 13;
    public static Font FUTURE_FONT;
    public static Image backGroundMenu;
    public static Image loser;
    public static Image winner;
    public static Image saveData;

    private final GraphicsContext gc;

    public Screen(Canvas canvas) {
        gc = canvas.getGraphicsContext2D();
        try {
            FUTURE_FONT = Font.loadFont(Files.newInputStream(Paths.get("res/font/Future Techno Italic 400.ttf")), 40);
            winner = new Image(Files.newInputStream(Paths.get("res/Menu/winner.png")));
            loser = new Image(Files.newInputStream(Paths.get("res/Menu/loser.png")));
            backGroundMenu = new Image(Files.newInputStream(Paths.get("res/Menu/BG.png")));
            saveData = new Image(Files.newInputStream(Paths.get("res/Menu/saveData.png")));
        } catch (IOException e) {
            System.out.println("[IOException] Wrong filepath.");
        }
    }


    public void renderMenu(GameMenu menu) {
        gc.drawImage(backGroundMenu, 0, 0, WIDTH * Sprite.SCALED_SIZE, HEIGHT * Sprite.SCALED_SIZE + 20);
        menu.render(gc);
    }

    public void renderMap(Map map) {
        for (int i = 0; i < map.getMap().size(); i++) {
            map.getMap().get(i).forEach(g -> g.render(gc, map.getCamera()));
        }
        map.getEntities().forEach(g -> g.render(gc, map.getCamera()));
        for (int i = 0; i < lives; i++) {
            gc.drawImage(heart.getFxImage(), (Screen.WIDTH - 5) * SCALED_SIZE + i * 20, Screen.HEIGHT * SCALED_SIZE);
        }
        try {
            gc.setFont(Font.loadFont(Files.newInputStream(Paths.get("res/font/Future Techno Italic 400.ttf")), 20));
            gc.fillText("Time: " + timeRemain, 3 * SCALED_SIZE, Screen.HEIGHT * SCALED_SIZE + 16);
            gc.fillText("Level: " + map.getLevel() + "/4", 10 * SCALED_SIZE, Screen.HEIGHT * SCALED_SIZE + 16);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void clearScreen(Canvas canvas) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

}
