package main.game.com.shooter;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Obstacle extends Rectangle {
    private boolean movethrough = false, shootthrough = false;

    public Obstacle(double x, double y, double width, double height, double rotation){
        super(x,y,width,height);
        this.setRotate(rotation);
        this.setStroke(Color.BLACK);
        this.setFill(Color.BROWN);
    }

    public boolean isShootthrough() {
        return shootthrough;
    }

    public boolean isMovethrough() {
        return movethrough;
    }

    double getObstacleCenterX(){
        return getX()+getWidth()/2;
    }

    double getObstacleCenterY(){
        return getY()+(getHeight()-(getHeight()/5))/2;
    }
}
