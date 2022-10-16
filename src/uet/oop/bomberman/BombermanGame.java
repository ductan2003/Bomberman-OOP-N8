package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.controlSystem.BombControl;
import uet.oop.bomberman.controlSystem.Collision;
import uet.oop.bomberman.controlSystem.KeyListener;
import uet.oop.bomberman.controlSystem.Timer;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Grass;
import uet.oop.bomberman.entities.Wall;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {

    
    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> stillObjects = new ArrayList<>();
    private KeyListener keyEvent;
    public static Map map = new Map();

    public Screen screen;
    public Timer timer;


    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * Screen.WIDTH, Sprite.SCALED_SIZE * Screen.HEIGHT);
        screen = new Screen(canvas);

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);
        keyEvent = new KeyListener(scene);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        timer = new Timer(this);

        map.createMap(1,keyEvent);

    }

    public void loop() {
        update();
        render();
    }

    public void update() {
        map.update();
    }

    public void render() {
        screen.clearScreen(canvas);
        screen.renderMap(map);
    }

    //adasdaxeae
}
