package main.game.com.shooter;

import javafx.geometry.Rectangle2D;
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
        this.currentlife = this.currentlife- amount;
        healthBar.setViewport(new Rectangle2D(0,0,healthBarOverlay.getImage().getWidth()*currentlife/maxlife, healthBarOverlay.getImage().getHeight()));
        return false;
    }

    public void setCurrentlife(int currentlife) {
        if(this.currentlife+currentlife>=this.maxlife){
            this.currentlife = this.maxlife;
        }else{
            this.currentlife = currentlife;
        }
    }

    public int getMaxlife() {
        return maxlife;
    }

    public int getCurrentlife() {
        return currentlife;
    }
}
