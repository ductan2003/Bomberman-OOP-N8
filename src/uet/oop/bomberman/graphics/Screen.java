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


public class Screen {
    public static int WIDTH = 22;
    public static int HEIGHT = 13;
    public static Font FUTUREFONT;
    public static Image backGroundMenu;
    public static Image loser;
    public static Image winner;

    private GraphicsContext gc;

    public Screen(Canvas canvas) {
        gc = canvas.getGraphicsContext2D();
        try {
            FUTUREFONT = Font.loadFont(Files.newInputStream(Paths.get("res/font/Future Techno Italic 400.ttf")), 40);
            winner = new Image(Files.newInputStream(Paths.get("res/Menu/winner.png")));
            loser = new Image(Files.newInputStream(Paths.get("res/Menu/loser.png")));
            backGroundMenu = new Image(Files.newInputStream(Paths.get("res/Menu/BG.png")));
        } catch (IOException e) {
            System.out.println("[IOException] Wrong filepaths.");
        }
    }


    public void renderMenu(GameMenu menu) {
            gc.drawImage(backGroundMenu, 0, 0, WIDTH * Sprite.SCALED_SIZE, HEIGHT * Sprite.SCALED_SIZE);
            menu.render(gc);
        }
    public void renderMap(Map map) {
        for (int i = 0; i < map.getMap().size(); i++) {
            map.getMap().get(i).forEach(g -> g.render(gc, map.getCamera()));
        }
        map.getEntities().forEach(g -> g.render(gc,map.getCamera()));

    }

    public void clearScreen(Canvas canvas) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

}
