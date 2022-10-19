package uet.oop.bomberman.controlSystem;

import javafx.animation.AnimationTimer;
import uet.oop.bomberman.BombermanGame;

public class Timer {
    private static final int FPS = 30;
    private static final long TIME_PER_FRAME = 1000000000 / FPS;

    public static final long TIME_FOR_DELAY_BOMB_SET = 200000000L;
    public static final long TIME_FOR_SINGLE_INPUT = TIME_PER_FRAME * 5;
    public static final long TIME_FOR_BOMB_EXPLODE = 2000000000;
    private AnimationTimer timer;
    private BombermanGame game;

    public Timer(BombermanGame bombermanGame) {
        this.game = bombermanGame;
        this.timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                game.loop();
            }
        };
        timer.start();
    }

    public static long now() {
        return System.nanoTime()/10;
    }
}
