package uet.oop.bomberman.controlSystem;

import javafx.animation.AnimationTimer;
import uet.oop.bomberman.BombermanGame;

public class Timer {
    private final AnimationTimer timer;
    private final BombermanGame game;
    private static long tick;

    public Timer(BombermanGame bombermanGame) {
        this.game = bombermanGame;
        this.timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                game.loop();
            }
        };
        timer.start();
        tick = 0;
    }

    public static long now() {
        return System.nanoTime() / 10;
    }

    public static long getTime() {
        return System.nanoTime() / 1000000000;
    }

    public static void pause() {
        tick = now();
    }

    public static long unpause() {
        return now() - tick;
    }
}
