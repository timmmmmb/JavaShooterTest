package main.game.com.shooter;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.Iterator;

class Gun extends Group{
    private String name;
    private int damage;
    private int ammunition;
    private int speed;

    Gun(int ammunition, int damage, String name, int speed) {
        this.damage = damage;
        this.name = name;
        this.ammunition = ammunition;
        this.speed = speed;
    }

    void shoot(double startposx, double startposy, double mouseposx, double mouseposy){
        if(ammunition==0){
            return;
        }
        Bullet bullet = new Bullet(mouseposx, mouseposy,startposx,startposy,speed);
        this.getChildren().add(bullet);
        ammunition--;
    }

    void update(){
        Iterator iter= this.getChildren().iterator();
        Character gunuser = (Character)getParent().getParent();
        Pane level = (Pane)this.getScene().getRoot();
        Group characters = (Group)level.getChildren().get(0);
        while(iter.hasNext()){
            Bullet bullet = (Bullet)iter.next();
            if(bullet!=null&&bullet.getImage()!=null&&bullet.getScene()!=null&&(bullet.getX()+bullet.getImage().getWidth()>bullet.getScene().getWidth()||bullet.getX()<0-bullet.getImage().getWidth()||bullet.getY()>bullet.getScene().getHeight()+bullet.getImage().getHeight()||bullet.getY()<0-bullet.getImage().getHeight())){
                iter.remove();
                continue;
            }
            //test if bullet collides with something
            for(Node characternode:characters.getChildren()){
                Character character = (Character)characternode;
                if(character!=gunuser){
                    if (bullet != null && character.characterModel.intersects(bullet.getX(), bullet.getY(), bullet.getImage().getWidth(), bullet.getImage().getHeight())) {
                        if (character.health.takeDamage(damage)) {
                            character.dead = true;
                            gunuser.score = character.scorevalue;
                            iter.remove();
                            break;
                        }
                        iter.remove();
                        break;
                    }
                }
            }

            if (bullet != null) {
                bullet.update();
            }
        }
    }

    public String getName() {
        return name;
    }
}
