package main.game.com.shooter;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HealPowerUp extends PowerUps {
    public HealPowerUp(int value) {
        super(value);
        powerupimage.setImage(new Image("PowerUpHealth.png",50,50,true,true));
    }
    @Override
    public void activate(Player player){
        player.health.setCurrentlife(value);
        used = true;
    }
}
