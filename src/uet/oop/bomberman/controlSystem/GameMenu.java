package uet.oop.bomberman.controlSystem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.Map.collision;
import static uet.oop.bomberman.entities.Bomber.timeRemain;
import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;


public class GameMenu {
    public static enum GAME_STATE {
        IN_MENU, IN_PLAY, IN_PAUSE,
        END, IN_END_STATE;
    }

    private final int PLAY = 0;
    private final int EXIT = 1;
    private final int CONTINUE_GAME = 2;
    private final int REPLAY = 2;
    private final int CONTINUE_PLAY = 0;
    private final int GO_TO_MENU = 1;
    private long delayInput = 0;

    List<Button> buttonMenu = new ArrayList<>();
    List<Button> buttonPause = new ArrayList<>();
    Button replayButton;
    Button exitButton;

    private int choosenButton;
    private int choosenButton1;
    private int choosenButton2;
    private int numberReady = 0;

    private KeyListener keyListener;
    public static GAME_STATE gameState;

    public GameMenu(KeyListener keyListener) {
        this.gameState = GAME_STATE.IN_MENU;
        this.keyListener = keyListener;

        Text text = new Text("NEW GAME");
        text.setFont(Screen.FUTURE_FONT);
        text.setFill(Color.BLACK);
        buttonMenu.add(new Button(Screen.WIDTH / 2 * Sprite.SCALED_SIZE - (int) text.getLayoutBounds().getWidth() / 2,
                Screen.HEIGHT / 2 * Sprite.SCALED_SIZE + (int) text.getLayoutBounds().getHeight() / 2, text));

        text = new Text("EXIT");
        text.setFont(Screen.FUTURE_FONT);
        text.setFill(Color.BLACK);
        exitButton = new Button(Screen.WIDTH / 2 * Sprite.SCALED_SIZE - (int) text.getLayoutBounds().getWidth() / 2,
                Screen.HEIGHT / 2 * Sprite.SCALED_SIZE + 3 * (int) text.getLayoutBounds().getHeight() / 2, text);
        buttonMenu.add(new Button(Screen.WIDTH / 2 * Sprite.SCALED_SIZE - (int) text.getLayoutBounds().getWidth() / 2,
                Screen.HEIGHT / 2 * Sprite.SCALED_SIZE + 3 * (int) text.getLayoutBounds().getHeight() / 2, text));
        text = new Text("CONTINUE GAME");
        text.setFont(Screen.FUTURE_FONT);
        text.setFill(Color.BLACK);
        buttonMenu.add(new Button(Screen.WIDTH / 2 * Sprite.SCALED_SIZE - (int) text.getLayoutBounds().getWidth()
                / 2,
                Screen.HEIGHT / 2 * Sprite.SCALED_SIZE + 5 * (int) text.getLayoutBounds().getHeight() / 2, text));

        text = new Text("CONTINUE PLAY");
        text.setFont(Screen.FUTURE_FONT);
        text.setFill(Color.BLACK);
        buttonPause.add(new Button(Screen.WIDTH / 2 * Sprite.SCALED_SIZE - (int) text.getLayoutBounds().getWidth() / 2,
                Screen.HEIGHT / 2 * Sprite.SCALED_SIZE + (int) text.getLayoutBounds().getHeight() / 2, text));

        text = new Text("GO TO MENU");
        text.setFont(Screen.FUTURE_FONT);
        text.setFill(Color.BLACK);
        buttonPause.add(new Button(Screen.WIDTH / 2 * Sprite.SCALED_SIZE - (int) text.getLayoutBounds().getWidth() / 2,
                Screen.HEIGHT / 2 * Sprite.SCALED_SIZE + 3 * (int) text.getLayoutBounds().getHeight() / 2, text));

        text = new Text("REPLAY");
        text.setFont(Screen.FUTURE_FONT);
        text.setFill(Color.BLACK);
        replayButton = new Button(Screen.WIDTH / 2 * Sprite.SCALED_SIZE - (int) text.getLayoutBounds().getWidth() / 2,
                Screen.HEIGHT / 2 * Sprite.SCALED_SIZE + (int) text.getLayoutBounds().getHeight() / 2, text);


        choosenButton = PLAY;
        choosenButton1 = CONTINUE_PLAY;
        choosenButton2 = REPLAY;
    }

    public void update() {
        switch (gameState) {
            case IN_MENU:
                long now = Timer.now();
                if (now - delayInput > 16000000) {
                    delayInput = now;
                    if (keyListener.pressed(KeyCode.ENTER)) {
                        Sound.menuSelect.play();
                        switch (choosenButton) {
                            case PLAY:
                                Sound.backgroundGame.stop();
                                Sound.menu.loop();
                                System.out.println("[ENTER PLAY]");
                                gameState = GAME_STATE.IN_PLAY;
                                BombermanGame.map = new Map(1, keyListener, false);
                                break;
                            case EXIT:
                                System.out.println("[ENTER END STATE]");
                                gameState = GAME_STATE.END;
                                break;

                            case CONTINUE_GAME:
                                Sound.backgroundGame.stop();
                                Sound.menu.loop();
                                System.out.println("[ENTER PLAY]");
                                gameState = GAME_STATE.IN_PLAY;
                                BombermanGame.map = new Map(1, keyListener, true);

                        }
                    } else if (keyListener.pressed(KeyCode.UP)) {
                        Sound.menuMove.play();
                        choosenButton--;
                        if (choosenButton < 0) {
                            choosenButton = 2;
                        }
                        System.out.println(choosenButton);
                        System.out.println("DOWN");
                    } else if (keyListener.pressed(KeyCode.DOWN)) {
                        Sound.menuMove.play();
                        choosenButton++;
                        if (choosenButton > 2) {
                            choosenButton = 0;
                        }
                        System.out.println(choosenButton);
                        System.out.println("DOWN");
                    }
                }
                break;

            case IN_PAUSE:
                //Sound.menu.stop();
                now = Timer.now();
                if (now - delayInput > 10000000) {
                    delayInput = now;
                    if (keyListener.pressed(KeyCode.ENTER)) {
                        Sound.menuSelect.play();
                        switch (choosenButton1) {
                            case CONTINUE_PLAY:
                                Sound.backgroundGame.stop();
                                Sound.menu.loop();
                                System.out.println("[ENTER CONTINUE_PLAY]");
                                gameState = GAME_STATE.IN_PLAY;
                                break;
                            case GO_TO_MENU:
                                Sound.menu.stop();
                                Sound.backgroundGame.loop();
                                System.out.println("[ENTER GO_TO_MENU]");
                                gameState = GAME_STATE.IN_MENU;
                                break;
                        }
                    } else if (keyListener.pressed(KeyCode.UP)) {
                        Sound.menuMove.play();
                        choosenButton1 = CONTINUE_PLAY;
                    } else if (keyListener.pressed(KeyCode.DOWN)) {
                        Sound.menuMove.play();
                        choosenButton1 = GO_TO_MENU;
                    }
                    if (keyListener.pressed(KeyCode.W)) {
                        //Sound.backgroundGame.higher();
                        Sound.menu.higher();
                    } else if (keyListener.pressed(KeyCode.S)) {
                        //Sound.backgroundGame.lower();
                        Sound.menu.lower();
                    }
                }
                break;


            case IN_END_STATE:
                now = Timer.now();
                if (now - delayInput > 10000000) {
                    delayInput = now;
                    if (keyListener.pressed(KeyCode.ENTER)) {
                        Sound.win.stop();
                        Sound.lose.stop();
                        Sound.backgroundGame.loop();
                        switch (choosenButton2) {
                            case REPLAY:
                                System.out.println("[ENTER GO_TO_MENU]");
                                gameState = GAME_STATE.IN_MENU;
                                break;
                            case EXIT:
                                System.out.println("[ENTER END STATE]");
                                gameState = GAME_STATE.END;
                                break;
                        }
                    } else if (keyListener.pressed(KeyCode.UP)) {
                        Sound.menuMove.play();
                        choosenButton2 = REPLAY;
                    } else if (keyListener.pressed(KeyCode.DOWN)) {
                        Sound.menuMove.play();
                        choosenButton2 = EXIT;
                    }
                }
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
                for (int i = 0; i < buttonMenu.size(); i++) {
                    if (choosenButton == i) {
                        buttonMenu.get(i).renderChoosen(gc);
                    } else {
                        buttonMenu.get(i).render(gc);
                    }
                }
                break;
            case IN_PAUSE: {
                for (int i = 0; i < buttonPause.size(); i++) {
                    if (choosenButton1 == i) {
                        buttonPause.get(i).renderChoosen(gc);
                    } else {
                        buttonPause.get(i).render(gc);
                    }
                }
                gc.fillText("Volume: " + Sound.menu.getVol() + "%", 7 * SCALED_SIZE, Screen.HEIGHT * SCALED_SIZE + 16);
                break;
            }

            case IN_END_STATE:
                if (BombermanGame.map.getIsWin()) {
                    Sound.menu.stop();
                    Sound.win.loop();
                    gc.drawImage(Screen.winner, 0, 0,
                            Screen.WIDTH * Sprite.SCALED_SIZE, Screen.HEIGHT * Sprite.SCALED_SIZE + 20);
                    if (choosenButton2 == 2) {
                        replayButton.renderChoosen(gc);
                        exitButton.render(gc);
                    } else {
                        exitButton.renderChoosen(gc);
                        replayButton.render(gc);
                    }

                } else {
                    Sound.menu.stop();
                    Sound.lose.loop();
                    gc.drawImage(Screen.loser, 0, 0,
                            Screen.WIDTH * Sprite.SCALED_SIZE, Screen.HEIGHT * Sprite.SCALED_SIZE + 20);
                    if (choosenButton2 == 2) {
                        replayButton.renderChoosen(gc);
                        exitButton.render(gc);
                    } else {
                        exitButton.renderChoosen(gc);
                        replayButton.render(gc);
                    }
                }
                break;
        }
    }
}