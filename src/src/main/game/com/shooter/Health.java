package main.game.com.shooter;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Health extends Group {
    private int maxlife;
    private int currentlife;
    private ImageView healthBar = new ImageView(new Image("health.png"));
    private ImageView healthBarOverlay = new ImageView(new Image("bar.png"));
    private boolean dead = false;

    Health(int maxlife){
        getChildren().addAll(healthBar,healthBarOverlay);
        this.maxlife = maxlife;
        this.currentlife = this.maxlife;
    }

    boolean takeDamage(int amount) {
        if (this.currentlife - amount <= 0){
            dead = true;
            return true;
        }
        this.currentlife = this.currentlife- amount;
        updateHealthDisplay();
        return false;
    }

    public void setCurrentlife(int currentlife) {
        if(this.currentlife+currentlife>=this.maxlife){
            this.currentlife = this.maxlife;
        }else{
            this.currentlife += currentlife;
        }
        updateHealthDisplay();
    }

    void updateHealthDisplay(){
        healthBar.setViewport(new Rectangle2D(0,0,healthBarOverlay.getImage().getWidth()*currentlife/maxlife, healthBarOverlay.getImage().getHeight()));
    }

    public int getMaxlife() {
        return maxlife;
    }

    public int getCurrentlife() {
        return currentlife;
    }

    public boolean isDead() {
        return dead;
    }
}
