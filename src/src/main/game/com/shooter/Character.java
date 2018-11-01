package main.game.com.shooter;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Character extends Group {
    ImageView characterModel = new ImageView(new Image("Enemy.png"));
    int scorevalue = 1;
    Health health = new Health(10);
    int speed = 2;
    boolean running, goNorth, goSouth, goEast, goWest,shooting;
    Group guns = new Group();
    Gun selectedWeapon;
    int score = 0;
    String name;


    Character(){
        this.getChildren().add(characterModel);
        this.getChildren().add(health);
        name = "Character";
        toFront();
    }

    public void update(){
        moveHealth();
        moveBullets();
    }

    void moveHealth(){
        ((ImageView)health.getChildren().get(0)).setX(characterModel.getX());
        ((ImageView)health.getChildren().get(1)).setX(characterModel.getX());
        ((ImageView)health.getChildren().get(0)).setY(characterModel.getY()-((ImageView)health.getChildren().get(0)).getImage().getHeight());
        ((ImageView)health.getChildren().get(1)).setY(characterModel.getY()-((ImageView)health.getChildren().get(1)).getImage().getHeight());
    }

    void moveBullets(){
        for(Node gun: guns.getChildren()){
            ((Gun)gun).update();
        }
    }

    double getCharacterCenterX(){
        return characterModel.getX()+characterModel.getImage().getWidth()/2;
    }

    double getCharacterCenterY(){
        return characterModel.getY()+(characterModel.getImage().getHeight()-(characterModel.getImage().getHeight()/5))/2;
    }

    public String getName() {
        return name;
    }
}
