package main.game.com.shooter;

import javafx.scene.Node;
import javafx.scene.image.Image;

public class AmmunitionPowerUp extends PowerUps {
        public AmmunitionPowerUp(int value) {
            super(value);
            powerupimage.setImage(new Image("PowerUpAmmunition.png",50,50,true,true));
        }
        @Override
        public void activate(Player player){
            for(Node gun:player.guns.getChildren()){
                ((Gun)gun).addAmmunition(value);
            }
            player.updateUI();
            used = true;
        }
    }

