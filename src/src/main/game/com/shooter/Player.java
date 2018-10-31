package main.game.com.shooter;

import javafx.scene.control.Label;
import javafx.scene.image.Image;


public class Player extends Character {
    double mouseposx;
    double mouseposy;
    Label scorelabel = new Label("Score: "+score);
    Player(){
        characterModel.setImage(new Image("Player.png", 100,125,true,true));

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
            if(characterModel.getX()<=event.getX() && event.getX()<=characterModel.getX()+characterModel.getImage().getWidth()&&characterModel.getY()<=event.getY() && event.getY()<=characterModel.getY()+characterModel.getImage().getHeight()){
                return;
            }
            mouseposx = event.getX();
            mouseposy = event.getY();
            double dx = event.getX() - getCharacterCenterX();
            double dy = event.getY() - getCharacterCenterY();
            Double angle = Math.atan2(dy, dx);
            double rotate = angle * ( 180 / Math.PI )-90;
            characterModel.setRotate(rotate);

        });

        guns.getChildren().add(new Pistol());
        this.getChildren().add(guns);
        selectedWeapon = (Gun)guns.getChildren().get(0);

        this.setOnMouseClicked(event -> {
            selectedWeapon.shoot(this.getCharacterCenterX(), this.getCharacterCenterY(), mouseposx, mouseposy);
            toFront();
        });
        this.getChildren().add(scorelabel);
    }

    @Override
    public void update() {
        move();
        moveBullets();
        moveHealth();
        updateScore();

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

        characterModel.setX(characterModel.getX()+dx);
        characterModel.setY(characterModel.getY()+dy);
    }
}
