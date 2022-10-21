package uet.oop.bomberman.controlSystem;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;


public class GameMenu {
    public static enum GAME_STATE {
        IN_MENU, IN_PLAY, IN_PAUSE,
        END, IN_END_STATE;
    }

    private final int PLAY = 0;
    private final int EXIT = 1;
    private final int CONTINUE_PLAY = 0;
    private final int GO_TO_MENU = 1;
    private long delayInput = 0;

    List<Button> buttonMenu = new ArrayList<>();
    List<Button> buttonPause = new ArrayList<>();

    private int choosenButton;
    private int choosenButton1;
    private int numberReady = 0;

    private KeyListener keyListener;
    public static GAME_STATE gameState;

    public GameMenu(KeyListener keyListener) {
        this.gameState = GAME_STATE.IN_MENU;
        this.keyListener = keyListener;

        Text text = new Text("PLAY");
        text.setFont(Screen.FUTUREFONT);
        text.setFill(Color.BLACK);
        buttonMenu.add(new Button(Screen.WIDTH / 2 * Sprite.SCALED_SIZE - (int) text.getLayoutBounds().getWidth() / 2,
                Screen.HEIGHT / 2 * Sprite.SCALED_SIZE + (int) text.getLayoutBounds().getHeight() / 2, text));

        text = new Text("EXIT");
        text.setFont(Screen.FUTUREFONT);
        text.setFill(Color.BLACK);
        buttonMenu.add(new Button(Screen.WIDTH / 2 * Sprite.SCALED_SIZE - (int) text.getLayoutBounds().getWidth() / 2,
                Screen.HEIGHT / 2 * Sprite.SCALED_SIZE + 3 * (int) text.getLayoutBounds().getHeight() / 2, text));

        text = new Text("CONTINUE GAME");
        text.setFont(Screen.FUTUREFONT);
        text.setFill(Color.BLACK);
        buttonPause.add(new Button(Screen.WIDTH / 2 * Sprite.SCALED_SIZE - (int) text.getLayoutBounds().getWidth() / 2,
                Screen.HEIGHT / 2 * Sprite.SCALED_SIZE + (int) text.getLayoutBounds().getHeight() / 2, text));

        text = new Text("GO TO MENU");
        text.setFont(Screen.FUTUREFONT);
        text.setFill(Color.BLACK);
        buttonPause.add(new Button(Screen.WIDTH / 2 * Sprite.SCALED_SIZE - (int) text.getLayoutBounds().getWidth() / 2,
                Screen.HEIGHT / 2 * Sprite.SCALED_SIZE + 3 * (int) text.getLayoutBounds().getHeight() / 2, text));

        choosenButton = PLAY;
        choosenButton1 = CONTINUE_PLAY;
    }

    public void update() {
        switch (gameState) {
            case IN_MENU:
                long now = Timer.now();
                if (now - delayInput > 10000000) {
                    delayInput = now;
                    if (keyListener.pressed(KeyCode.ENTER)) {
                        Sound.menuSelect.play();
                        switch (choosenButton) {
                            case PLAY:
                                Sound.backgroundGame.stop();
                                Sound.menu.loop();
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
                        Sound.menuMove.play();
                        choosenButton = PLAY;
                    } else if (keyListener.pressed(KeyCode.DOWN)) {
                        Sound.menuMove.play();
                        choosenButton = EXIT;
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
               }
                    break;


            case IN_END_STATE:
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
                break;
            }

            case IN_END_STATE:
                        if (BombermanGame.map.getIsWin()) {
                            Sound.menu.stop();
                            Sound.win.loop();
                            gc.drawImage(Screen.winner, 0, 0,
                                    Screen.WIDTH * Sprite.SCALED_SIZE, Screen.HEIGHT * Sprite.SCALED_SIZE);

                        } else {
                            Sound.menu.stop();
                            Sound.lose.loop();
                            gc.drawImage(Screen.loser, 0, 0,
                                    Screen.WIDTH * Sprite.SCALED_SIZE, Screen.HEIGHT * Sprite.SCALED_SIZE);
                        }
                        break;
        }
    }
}