package main.game.com.shooter;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Enemy extends Character{
    public Enemy(){
        characterModel.setImage(new Image("Enemy.png", 100,125,true,true));
    }
}
