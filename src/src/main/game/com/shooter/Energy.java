package main.game.com.shooter;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Energy extends Group {
    private int maxenergy;
    private int currentenergy;
    private ImageView energyBar = new ImageView(new Image("energy.png"));
    private ImageView energyBarOverlay = new ImageView(new Image("bar.png"));
    private boolean exhausted = false;

    Energy(int maxenergy){
        getChildren().addAll(energyBar,energyBarOverlay);
        this.maxenergy = maxenergy;
        this.currentenergy = this.maxenergy;
    }

    boolean useEnergy(int amount) {
        if (this.currentenergy - amount <= 0){
            exhausted = true;
            this.currentenergy = 0;
            return true;
        }
        this.currentenergy = this.currentenergy- amount;
        updateEnergyDisplay();
        return false;
    }

    void updateEnergyDisplay(){
        energyBar.setViewport(new Rectangle2D(0,0,energyBarOverlay.getImage().getWidth()*currentenergy/maxenergy, energyBarOverlay.getImage().getHeight()));
    }

    public void setCurrentenergy(int currentenergy) {
        if(this.currentenergy+currentenergy>=this.maxenergy){
            this.currentenergy = this.maxenergy;
            exhausted = false;
        }else{
            this.currentenergy += currentenergy;
        }
        updateEnergyDisplay();
    }

    public int getMaxenergy() {
        return maxenergy;
    }

    public int getCurrentenergy() {
        return currentenergy;
    }

    public boolean isExhausted() {
        return exhausted;
    }
}
