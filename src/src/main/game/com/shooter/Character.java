package main.game.com.shooter;

import javafx.scene.image.ImageView;

public class Character extends ImageView {
    int maxlife = 10;
    int currentlife = maxlife;
    String name = "Character";

    public Character(){

    }

    public Character(String name){
        this.name = name;
    }

    public Character(int maxlife, String name){
        this.maxlife = maxlife;
        this.currentlife = this.maxlife;
        this.name = name;
    }

}
