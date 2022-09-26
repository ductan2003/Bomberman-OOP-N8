package uet.oop.bomberman.controlSystem;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.HashSet;
import java.util.Set;

public class KeyListener {
    private Set<KeyCode> keyList = new HashSet<>();

    public KeyListener(Scene scene) {
        KeyEvent event;
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (KeyEvent.KEY_PRESSED.equals(event.getEventType())) {
                    keyList.add(event.getCode());
                }
            }
        });
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (KeyEvent.KEY_RELEASED.equals(event.getEventType())) {
                    keyList.remove(event.getCode());
                }
            }
        });
    }

    public boolean pressed(KeyCode keycode) {
        return (keyList.contains(keycode));
    }
}
