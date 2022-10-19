package uet.oop.bomberman.controlSystem;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Map;
//import uet.oop.bomberman.MultiPlayerMap;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

public class GameMenu {
    public static enum GAME_STATE {
        IN_MENU, IN_PLAY, IN_PAUSE,
        END, IN_END_STATE;
    }

    private final int PLAY = 0;
    private final int EXIT = 1;
    private long delayInput = 0;
    public static GAME_STATE preGameState;

    List<Button> button = new ArrayList<>();
    Button pauseButton;

    private int choosenButton;
    private int numberReady = 0;

    private KeyListener keyListener;
    public static GAME_STATE gameState;

    public GameMenu(KeyListener keyListener) {
        this.gameState = GAME_STATE.IN_MENU;
        this.keyListener = keyListener;

        Text text = new Text("PLAY");
        text.setFont(Screen.FUTUREFONT);
        text.setFill(Color.BLACK);
        button.add(new Button(Screen.WIDTH / 2 * Sprite.SCALED_SIZE - (int) text.getLayoutBounds().getWidth() / 2,
                Screen.HEIGHT / 2 * Sprite.SCALED_SIZE + (int) text.getLayoutBounds().getHeight() / 2, text));

        text = new Text("EXIT");
        text.setFont(Screen.FUTUREFONT);
        text.setFill(Color.BLACK);
        button.add(new Button(Screen.WIDTH / 2 * Sprite.SCALED_SIZE - (int) text.getLayoutBounds().getWidth() / 2,
                Screen.HEIGHT / 2 * Sprite.SCALED_SIZE + 3 * (int) text.getLayoutBounds().getHeight() / 2, text));
        choosenButton = PLAY;
    }

    public void update() {
        switch (gameState) {
            case IN_MENU:
                long now = Timer.now();
//                if (now - delayInput > Timer.TIME_FOR_SINGLE_INPUT) {
//                    delayInput = now;
                    if (keyListener.pressed(KeyCode.ENTER)) {
                        //Sound.menuSelect.play();
                        switch (choosenButton) {
                            case PLAY:
                                //Sound.backgroundGame.stop();
                               // Sound.menu.loop();
                                System.out.println("[ENTER PLAY]");
                                gameState = GAME_STATE.IN_PLAY;
                                BombermanGame.map = new Map(1, keyListener);
                                break;
                            case EXIT:
                                System.out.println("[ENTER END STATE]");
                                gameState = GAME_STATE.END;
                                break;
                        }
                    } else if (keyListener.pressed(KeyCode.UP)) {
                        choosenButton = PLAY;
                    } else if (keyListener.pressed(KeyCode.DOWN)) {
                         choosenButton = EXIT;
                   }
//                }
               break;
                case IN_PAUSE:
                now = Timer.now();
//                if (now - delayInput > Timer.TIME_FOR_SINGLE_INPUT) {
//                    delayInput = now;
                    if (keyListener.pressed(KeyCode.ENTER))
                        gameState = GAME_STATE.IN_PLAY;
//                }
                break;

            case IN_END_STATE:
                now = Timer.now();
//                if (now - delayInput > Timer.TIME_FOR_SINGLE_INPUT) {
//                    delayInput = now;
                    if (keyListener.pressed(KeyCode.ENTER)) {
                        gameState = GAME_STATE.IN_MENU;
                    }
//                }
                break;

            case END:
                break;
        }
    }

    /**
     * Getter for gameState.
     */
    public GAME_STATE getGameState() {
        return gameState;
    }

    public void setGameState(GAME_STATE state) {
        gameState = state;
    }

    /**
     * Render menu.
     */
    public void render(GraphicsContext gc) {
        switch (gameState) {
            case IN_MENU:
                for (int i = 0; i < button.size(); i++) {
                    if (choosenButton == i) {
                        button.get(i).renderChoosen(gc);
                    } else {
                        button.get(i).render(gc);
                    }
                }
                break;

            case IN_PAUSE:
                pauseButton.render(gc);
            case IN_END_STATE:
        }
    }
}