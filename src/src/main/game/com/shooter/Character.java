package main.game.com.shooter;

import javafx.scene.Group;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class Character extends ImageView {
    int maxlife = 10;
    int currentlife = maxlife;
    int speed = 2;
    Group componenets = new Group ();
    boolean running, goNorth, goSouth, goEast, goWest;
    ArrayList<Gun> guns = new ArrayList<>();
    Gun selectedWeapon;
    String name = "Character";


    public Character(){
        componenets.getChildren().add(this);
    }

    public Character(String name){
        this.name = name;
        componenets.getChildren().add(this);
    }

    public Character(int maxlife, String name){
        this.maxlife = maxlife;
        this.currentlife = this.maxlife;
        this.name = name;
        componenets.getChildren().add(this);
    }

    public void update(){

    }

}
