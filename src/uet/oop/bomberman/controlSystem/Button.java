package uet.oop.bomberman.controlSystem;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import uet.oop.bomberman.graphics.Screen;

public class Button {

    private Text buttonName;
    private int x;
    private int y;

    /**
     * Constructor 1.
     */
    public Button(int x, int y, Text text) {
        this.x = x;
        this.y = y;
        this.buttonName = text;
    }

    public void render(GraphicsContext gc) {
        gc.setFont(buttonName.getFont());
        gc.setFill(buttonName.getFill());
        gc.fillText(buttonName.getText(), x, y);
    }

    public void renderChoosen(GraphicsContext gc) {
        gc.strokeText(buttonName.getText(), x, y);
        gc.setFont(Screen.FUTURE_FONT);
        gc.setFill(Color.CHOCOLATE);
        gc.fillText(buttonName.getText(), x, y);
    }

    /**
     * getFont().
     */
    public Font getFont() {
        return buttonName.getFont();
    }

    public Text getText() {
        return buttonName;
    }

    public void setText(Text text) {
        this.buttonName = text;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}