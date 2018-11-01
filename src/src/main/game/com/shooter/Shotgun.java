package main.game.com.shooter;

public class Shotgun extends Gun {

    Shotgun(int ammuntion) {
        super(ammuntion, 2, "Shotgun", 4, 150);
        maxrange = 400;
    }
    @Override
    void shoot(double startposx, double startposy, double mouseposx, double mouseposy){
        if(ammunition==0 || cooldown>0){
            return;
        }
        this.getChildren().add(new Bullet(mouseposx, mouseposy,startposx,startposy,speed));
        this.getChildren().add(new Bullet(mouseposx+50, mouseposy,startposx,startposy,speed));
        this.getChildren().add(new Bullet(mouseposx+100, mouseposy,startposx,startposy,speed));
        this.getChildren().add(new Bullet(mouseposx-50, mouseposy,startposx,startposy,speed));
        this.getChildren().add(new Bullet(mouseposx-100, mouseposy,startposx,startposy,speed));
        cooldown = cooldownbetweenshots;
        ammunition--;
    }

}
