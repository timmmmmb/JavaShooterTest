package main.game.com.shooter;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

class Bullet extends ImageView {
    private double xspeed;
    private double yspeed;

    Bullet(double targetx, double targety, double startposx, double startposy, int speed) {
        double hypothenus = Math.sqrt((targetx-startposx)*(targetx-startposx) + (targety-startposy) * (targety-startposy));
        double steps = hypothenus/speed;
        xspeed = (targetx-startposx)/steps;
        yspeed = (targety-startposy)/steps;

        this.setImage(new Image("Bullet.png", 25,25,true,true));
        setX(startposx-getImage().getHeight()/2);
        setY(startposy);

        setX(getX()+xspeed*62.5/speed);
        setY(getY()+yspeed*62.5/speed);
    }

    void update(){
        move();
    }

    private void move() {
        setX(getX()+xspeed);
        setY(getY()+yspeed);
    }
}
