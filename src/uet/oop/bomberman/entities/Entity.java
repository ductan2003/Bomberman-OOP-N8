package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.controlSystem.Camera;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;

public abstract class Entity {
    //Tọa độ X tính từ góc trái trên trong Canvas
    protected int x;

    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

//    public void setXUnit(int x) {
//        this.x = x * Sprite.SCALED_SIZE;
//    }
//
//    public void setYUnit(int y) {
//        this.y = y * Sprite.SCALED_SIZE;
//    }

    protected Image img;

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity( int xUnit, int yUnit, Image img) {
        this.x = xUnit * SCALED_SIZE;
        this.y = yUnit * SCALED_SIZE;
        this.img = img;
    }

    public Entity(int xUnit, int yUnit) {
        this.x = xUnit * SCALED_SIZE;
        this.y = yUnit * SCALED_SIZE;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public int getXMapCoordinate(int x) {
        return Math.round(x / SCALED_SIZE);
    }

    public int getYMapCoordinate(int y) {
        return Math.round(y / SCALED_SIZE);
    }

    public String getCoordinateInfo() {
        return "(x,y) = (" + Math.round(x / SCALED_SIZE) + ", " + Math.round(y / SCALED_SIZE) + ")";
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }
    public void render(GraphicsContext gc, Camera camera) {gc.drawImage(img,x- camera.getX(),y-camera.getY());}
    public abstract void update();
}
