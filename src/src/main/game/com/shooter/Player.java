package main.game.com.shooter;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;


public class Player extends Character {
    private double mouseposx;
    private double mouseposy;
    private Energy energy = new Energy(300);
    private HBox ui = new HBox();
    private Label scorelabel = new Label("Score: "+score);
    private Label currentweapon = new Label("Currentweapon: "+selectedWeapon.getName()+" Ammunition: "+selectedWeapon.ammunition);
    Player(int x, int y){
        ui.getChildren().addAll(scorelabel,currentweapon);
        characterModel.setImage(new Image("Player.png", 100,125,true,true));
        characterModel.setX(x);
        characterModel.setY(y);
        this.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP: case W:    goNorth = true; break;
                case DOWN: case S: goSouth = true; break;
                case LEFT: case A: goWest  = true; break;
                case RIGHT: case D: goEast  = true; break;
                case DIGIT1:
                    if(guns.getChildren().size()>=1){
                        selectedWeapon = (Gun)guns.getChildren().get(0);
                        updateUI();
                    }
                    break;
                case DIGIT2:
                    if(guns.getChildren().size()>=2){
                        selectedWeapon = (Gun)guns.getChildren().get(1);
                        updateUI();
                    }
                    break;
                case DIGIT3:
                    if(guns.getChildren().size()>=3){
                        selectedWeapon = (Gun)guns.getChildren().get(2);
                        updateUI();
                    }
                    break;
                case DIGIT4:
                    if(guns.getChildren().size()>=4){
                        selectedWeapon = (Gun)guns.getChildren().get(3);
                        updateUI();
                    }
                    break;
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
        guns.getChildren().add(new AssaultRifle(100));
        guns.getChildren().add(new Shotgun(10));
        guns.getChildren().add(new Machinegun(100));
        this.getChildren().add(guns);
        selectedWeapon = (Gun)guns.getChildren().get(0);

        this.setOnMousePressed(event -> {
            shooting = true;
        });

        this.setOnMouseReleased(event -> {
            shooting = false;
        });
        this.getChildren().add(ui);
        this.name = "Player";
        this.getChildren().add(energy);
        updateUI();
        moveEnergy();
    }

    @Override
    public void update() {
        move();
        moveBullets();
        moveHealth();
        moveEnergy();
        updateScore();
        shoot();
    }

    private void shoot(){
        if(shooting){
            selectedWeapon.shoot(this.getCharacterCenterX(), this.getCharacterCenterY(), mouseposx, mouseposy);
            updateUI();
        }
    }

    public void updateUI(){
        currentweapon.setText("  Currentweapon: "+selectedWeapon.getName()+" Ammunition: "+selectedWeapon.ammunition);
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

    void moveEnergy(){
        ((ImageView)energy.getChildren().get(0)).setX(characterModel.getX());
        ((ImageView)energy.getChildren().get(1)).setX(characterModel.getX());
        ((ImageView)energy.getChildren().get(0)).setY(characterModel.getY()-((ImageView)health.getChildren().get(0)).getImage().getHeight());
        ((ImageView)energy.getChildren().get(1)).setY(characterModel.getY()-((ImageView)health.getChildren().get(1)).getImage().getHeight());
    }

    @Override
    void moveHealth(){
        ((ImageView)health.getChildren().get(0)).setX(characterModel.getX());
        ((ImageView)health.getChildren().get(1)).setX(characterModel.getX());
        ((ImageView)health.getChildren().get(0)).setY(characterModel.getY()-((ImageView)health.getChildren().get(0)).getImage().getHeight()*2);
        ((ImageView)health.getChildren().get(1)).setY(characterModel.getY()-((ImageView)health.getChildren().get(1)).getImage().getHeight()*2);
    }

    private void move(){
        int dx = 0, dy = 0;

        if (goNorth) dy -= speed;
        if (goSouth) dy += speed;
        if (goEast)  dx += speed;
        if (goWest)  dx -= speed;
        if (running&&!energy.isExhausted()) { dx *= 3; dy *= 3;energy.useEnergy(2); }
        else{
            energy.setCurrentenergy(1);
        }
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
