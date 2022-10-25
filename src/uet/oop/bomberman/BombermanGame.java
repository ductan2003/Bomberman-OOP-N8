package uet.oop.bomberman;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import uet.oop.bomberman.controlSystem.*;

import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;


public class BombermanGame extends Application {
    public static GameMenu menu;

    private Canvas canvas;
    public static Map map;
    private KeyListener keyEvent;
    public Screen screen;
    public Timer timer;

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * Screen.WIDTH, Sprite.SCALED_SIZE * Screen.HEIGHT + 20);
        screen = new Screen(canvas);
        Sound.backgroundGame.loop();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);
        stage.setTitle("BOMBERMAN");
        keyEvent = new KeyListener(scene);
        menu = new GameMenu(keyEvent);
        timer = new Timer(this);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();


        // map.createMap(1,keyEvent);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                Platform.exit();
                System.exit(0);
            }
        });

    }

    public void loop() {
        update();
        render();
    }

    public void update() {
        switch (menu.getGameState()) {
            case IN_MENU:
            case IN_PAUSE:
                menu.update();
                break;
            case IN_END_STATE:
                menu.update();
                map.clear();
                break;

            case IN_PLAY:
                map.update();
                break;

            case END:
                System.exit(0);
                break;
            default:
                throw new IllegalArgumentException("Invalid game state");
        }
    }

    public void render() {
        screen.clearScreen(canvas);
        switch (menu.getGameState()) {

            case IN_MENU:
            case IN_PAUSE:
            case IN_END_STATE:
                screen.renderMenu(menu);
                break;

            case IN_PLAY:
                screen.renderMap(map);
                break;

            case END:
                break;
            default:
                throw new IllegalArgumentException("Invalid game state");
        }
    }
}
