package main.game.com.shooter;

import javafx.scene.Group;
import java.util.Iterator;

public class Gun extends Group{
    private String name = "gun";
    private int damage = 2;
    private int ammunition;
    public Gun(){
        ammunition = 10;
    }

    public Gun(int ammunition){
        this.ammunition = ammunition;
    }

    public Gun(int ammunition, String name) {
        this.name = name;
        this.ammunition = ammunition;
    }

    public Gun(int ammunition, int damage, String name) {
        this.damage = damage;
        this.name = name;
        this.ammunition = ammunition;
    }

    public void shoot(double startposx, double startposy, double mouseposx, double mouseposy){
        if(ammunition==0){
            return;
        }
        Bullet bullet = new Bullet(mouseposx, mouseposy,startposx,startposy);
        this.getChildren().add(bullet);
        ammunition--;
    }

    public void update(){
        Iterator iter= this.getChildren().iterator();
        while(iter.hasNext()){
            Bullet bullet = (Bullet)iter.next();
            if(bullet!=null&&bullet.getImage()!=null&&bullet.getScene()!=null&&(bullet.getX()+bullet.getImage().getWidth()>bullet.getScene().getWidth()||bullet.getX()<0-bullet.getImage().getWidth()||bullet.getY()>bullet.getScene().getHeight()+bullet.getImage().getHeight()||bullet.getY()<0-bullet.getImage().getHeight())){
                iter.remove();
                continue;
            }
            //test if bullet collides with something
            bullet.update();
        }
    }

    public String getName() {
        return name;
    }

}
