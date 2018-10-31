package main.game.com.shooter;

import javafx.scene.Group;

import java.util.ArrayList;

public class Gun {
    private String name = "gun";
    private int ammunition;
    private ArrayList<Bullet> bullets = new ArrayList<>();
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

    public void shoot(Group components, double startposx, double startposy, double mouseposx, double mouseposy){
        if(ammunition==0){
            return;
        }
        Bullet bullet = new Bullet(mouseposx, mouseposy,startposx,startposy);
        bullets.add(bullet);
        components.getChildren().add(bullet);
        ammunition--;
    }

    public void update(){
        ArrayList<Integer> toremove = new ArrayList();
        int i = 0;
        for(Bullet bullet: bullets){

            //remove if out of game
            if(bullet.getX()+bullet.getImage().getWidth()>bullet.getScene().getWidth()||bullet.getX()<0-bullet.getImage().getWidth()||bullet.getY()>bullet.getScene().getHeight()+bullet.getImage().getHeight()||bullet.getY()<0-bullet.getImage().getHeight()){
                toremove.add(i);
            }
            bullet.update();
            i++;
        }
        for(int remove: toremove){
            ((Group)bullets.get(remove).getParent()).getChildren().remove(remove);
            bullets.remove(remove);
        }
    }
}
