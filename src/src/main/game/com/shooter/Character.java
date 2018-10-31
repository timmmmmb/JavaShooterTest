package main.game.com.shooter;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Character extends Group {
    public ImageView characterModel = new ImageView(new Image("Enemy.png"));
    public int scorevalue = 1;
    Health health = new Health(10);
    int speed = 2;
    boolean running, goNorth, goSouth, goEast, goWest;
    Group guns = new Group();
    Gun selectedWeapon;
    boolean dead = false;
    int score = 0;
    String name = "Character";


    public Character(){
        this.getChildren().add(characterModel);
        this.getChildren().add(health);
    }

    public Character(String name){
        this.name = name;
        this.getChildren().add(characterModel);
        this.getChildren().add(health);
    }

    public Character(int maxlife, String name){
        health = new Health(maxlife);
        this.name = name;
        this.getChildren().add(characterModel);
        this.getChildren().add(health);
    }

    public void update(){
        moveHealth();
        moveBullets();
    }

    public void moveHealth(){
        ((ImageView)health.getChildren().get(0)).setX(characterModel.getX());
        ((ImageView)health.getChildren().get(1)).setX(characterModel.getX());
        ((ImageView)health.getChildren().get(0)).setY(characterModel.getY()-((ImageView)health.getChildren().get(0)).getImage().getHeight());
        ((ImageView)health.getChildren().get(1)).setY(characterModel.getY()-((ImageView)health.getChildren().get(1)).getImage().getHeight());
    }

    public void moveBullets(){
        for(Node gun: guns.getChildren()){
            ((Gun)gun).update();
        }
    }

    public double getCharacterCenterX(){
        return characterModel.getX()+characterModel.getImage().getWidth()/2;
    }

    public double getCharacterCenterY(){
        return characterModel.getY()+(characterModel.getImage().getHeight()-(characterModel.getImage().getHeight()/5))/2;
    }

}
