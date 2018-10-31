package main.game.com.shooter;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Health extends Group {
    int maxlife = 10;
    int currentlife = maxlife;
    ImageView healthBar = new ImageView(new Image("health.png"));
    ImageView healthBarOverlay = new ImageView(new Image("bar.png"));

    public Health(){
        getChildren().addAll(healthBar,healthBarOverlay);
        this.maxlife = 10;
        this.currentlife = this.maxlife;
    }

    public Health(int maxlife){
        getChildren().addAll(healthBar,healthBarOverlay);
        this.maxlife = maxlife;
        this.currentlife = this.maxlife;
    }

    public boolean takeDamage(int amount){
        if(this.currentlife-amount<=0){
            return true;
        }
        this.currentlife -= amount;
        return false;
    }
}
