package uet.oop.bomberman.graphics;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.controlSystem.GameMenu;
import uet.oop.bomberman.entities.Entity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static java.awt.SystemColor.text;
import static uet.oop.bomberman.BombermanGame.map;
import static uet.oop.bomberman.BombermanGame.menu;

public class Screen {
    public static int WIDTH = 22;
    public static int HEIGHT = 13;
    public static Font DEFAULTFONT;
    public static Font CHOOSENFONT;
    public static Font FUTUREFONT;
    public static Font FUTUREFONTTHIN;
    public static Image backGroundMenu;

    private GraphicsContext gc;

    public Screen(Canvas canvas) {
        gc = canvas.getGraphicsContext2D();
        try {
            DEFAULTFONT = Font.loadFont(Files.newInputStream(Paths.get("res/font/default.ttf")), 30);
            CHOOSENFONT = Font.loadFont(Files.newInputStream(Paths.get("res/font/title.ttf")), 25);
            FUTUREFONT = Font.loadFont(Files.newInputStream(Paths.get("res/font/Future Techno Italic 400.ttf")), 40);
            FUTUREFONTTHIN = Font.loadFont(Files.newInputStream(Paths.get("res/font/Future Techno Italic 400.ttf")), 15);
            backGroundMenu = new Image(Files.newInputStream(Paths.get("res/Menu/BG.gif")));
        } catch (IOException e) {
            System.out.println("[IOException] Wrong filepaths.");
        }
    }

//        public void renderText(Font font, Text text, int x, int y) {
//            gc.setFont(font);
//            gc.setFill(text.getFill());
//            gc.fillText(text.getText(), x, y);
//        }

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
