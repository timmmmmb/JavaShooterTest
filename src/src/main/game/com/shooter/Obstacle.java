package main.game.com.shooter;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Obstacle extends Rectangle {
    public Obstacle(double x, double y, double width, double height, double rotation){
        super(x,y,width,height);
        this.setRotate(rotation);
        this.setStroke(Color.BLACK);
        this.setFill(Color.BROWN);
    }
}
