package main.game.com.shooter;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;

public class PowerUps extends Group {
    int value = 0;
    boolean used = false;
    ImageView powerupimage = new ImageView(new Image("PowerUp.png",50,50,true,true));
    public PowerUps(int value){
        this.value = value;
        this.getChildren().add(powerupimage);
        Random rand = new Random();
        powerupimage.setY(rand.nextInt(1000) + 1);
        powerupimage.setX(rand.nextInt(1000) + 1);
    }

    public void activate(Player player){
        player.score += value;
        used = true;
    }

    public void update(Player player){
        if(powerupimage.intersects(player.characterModel.getX(),player.characterModel.getY(),player.characterModel.getImage().getWidth(),player.characterModel.getImage().getHeight())){
            activate(player);
        }
    }
}
