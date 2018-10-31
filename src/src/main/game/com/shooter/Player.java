package main.game.com.shooter;

import javafx.scene.image.Image;


public class Player extends Character {
    double mouseposx;
    double mouseposy;
    Player(){
        this.setImage(new Image("Player.png", 100,125,true,true));
        this.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP: case W:    goNorth = true; break;
                case DOWN: case S: goSouth = true; break;
                case LEFT: case A: goWest  = true; break;
                case RIGHT: case D: goEast  = true; break;
                case SHIFT: running = true; break;
            }
        });

        this.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case UP: case W:   goNorth = false; break;
                case DOWN: case S:  goSouth = false; break;
                case LEFT: case A:  goWest  = false; break;
                case RIGHT: case D: goEast  = false; break;
                case SHIFT: running = false; break;
            }
        });

        this.setOnMouseMoved(event -> {
            if(getX()<=event.getX() && event.getX()<=getX()+getImage().getWidth()&&getY()<=event.getY() && event.getY()<=getY()+getImage().getHeight()){
                return;
            }
            mouseposx = event.getX();
            mouseposy = event.getY();
            double dx = event.getX() - playerCenterX();
            double dy = event.getY() - playerCenterY();
            Double angle = Math.atan2(dy, dx);
            double rotate = angle * ( 180 / Math.PI )-90;
            setRotate(rotate);

        });
        guns.add(new Pistol());
        selectedWeapon = guns.get(0);
        this.setOnMouseClicked(event -> {
            selectedWeapon.shoot(componenets, this.playerCenterX(), this.playerCenterY(), mouseposx, mouseposy);
            toFront();
        });
    }

    private double playerCenterX(){
        return getX()+getImage().getWidth()/2;
    }

    private double playerCenterY(){
        return getY()+(getImage().getHeight()-(getImage().getHeight()/5))/2;
    }

    @Override
    public void update() {
        move();
        moveBullets();
    }

    private void move(){
        int dx = 0, dy = 0;

        if (goNorth) dy -= speed;
        if (goSouth) dy += speed;
        if (goEast)  dx += speed;
        if (goWest)  dx -= speed;
        if (running) { dx *= 3; dy *= 3; }

        this.setX(getX()+dx);
        this.setY(getY()+dy);
    }

    private void moveBullets(){
        for(Gun gun: guns){
            gun.update();
        }
    }
}
