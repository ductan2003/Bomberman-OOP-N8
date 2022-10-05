package uet.oop.bomberman.controlSystem;

import javafx.animation.AnimationTimer;
import uet.oop.bomberman.BombermanGame;

public class Timer {
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
        return System.nanoTime();
    }
}
