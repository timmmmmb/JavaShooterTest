package main.game.com.shooter;

import javafx.scene.control.Label;
import javafx.scene.image.Image;


public class Player extends Character {
    private double mouseposx;
    private double mouseposy;
    private Label scorelabel = new Label("Score: "+score);
    Player(int x, int y){
        characterModel.setImage(new Image("Player.png", 100,125,true,true));
        characterModel.setX(x);
        characterModel.setY(y);
        this.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP: case W:    goNorth = true; break;
                case DOWN: case S: goSouth = true; break;
                case LEFT: case A: goWest  = true; break;
                case RIGHT: case D: goEast  = true; break;
                case DIGIT1: break;
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
            if(characterModel.getX()<=event.getX() && event.getX()<=characterModel.getX()+characterModel.getImage().getWidth()&&characterModel.getY()<=event.getY() && event.getY()<=characterModel.getY()+characterModel.getImage().getHeight()){
                return;
            }
            mouseposx = event.getX();
            mouseposy = event.getY();
            rotateTowardsMouse();
        });

        this.setOnMouseDragged(event -> {
            if(characterModel.getX()<=event.getX() && event.getX()<=characterModel.getX()+characterModel.getImage().getWidth()&&characterModel.getY()<=event.getY() && event.getY()<=characterModel.getY()+characterModel.getImage().getHeight()){
                return;
            }
            mouseposx = event.getX();
            mouseposy = event.getY();
            rotateTowardsMouse();
        });

        guns.getChildren().add(new Pistol(1000));
        guns.getChildren().add(new AssaultRifle(1000));
        this.getChildren().add(guns);
        selectedWeapon = (Gun)guns.getChildren().get(1);

        this.setOnMousePressed(event -> {
            shooting = true;
        });

        this.setOnMouseReleased(event -> {
            shooting = false;
        });
        this.getChildren().add(scorelabel);
        this.name = "Player";
    }

    @Override
    public void update() {
        move();
        moveBullets();
        moveHealth();
        updateScore();
        shoot();
    }

    private void shoot(){
        if(shooting){
            selectedWeapon.shoot(this.getCharacterCenterX(), this.getCharacterCenterY(), mouseposx, mouseposy);
        }
    }

    private void rotateTowardsMouse(){
        double dx = mouseposx - getCharacterCenterX();
        double dy = mouseposy - getCharacterCenterY();
        Double angle = Math.atan2(dy, dx);
        double rotate = angle * ( 180 / Math.PI )-90;
        characterModel.setRotate(rotate);
    }

    private void updateScore() {
        scorelabel.setText("Score: "+score);
    }

    private void move(){
        int dx = 0, dy = 0;

        if (goNorth) dy -= speed;
        if (goSouth) dy += speed;
        if (goEast)  dx += speed;
        if (goWest)  dx -= speed;
        if (running) { dx *= 3; dy *= 3; }
        if(getScene().getWidth()>=characterModel.getX()+dx+characterModel.getImage().getWidth()&&0<=characterModel.getX()+dx){
            characterModel.setX(characterModel.getX()+dx);
        }
        if(getScene().getHeight()>=characterModel.getY()+dy+characterModel.getImage().getHeight()&&0<=characterModel.getY()+dy){
            characterModel.setY(characterModel.getY()+dy);
        }
        if(dx!=0||dy!=0){
            rotateTowardsMouse();
        }
    }
}
