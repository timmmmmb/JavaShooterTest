package main.game.com.shooter;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.Iterator;

class Gun extends Group{
    private String name;
    GunType type = GunType.GUN;
    private int damage;
    int ammunition;
    int speed;
    int cooldownbetweenshots;
    int cooldown;
    int maxrange = 1000;

    Gun(int ammunition, int damage, String name, int speed, int cooldownbetweenshots, GunType type) {
        this.damage = damage;
        this.name = name;
        this.ammunition = ammunition;
        this.speed = speed;
        this.cooldownbetweenshots = cooldownbetweenshots;
        this.type = type;
    }

    void shoot(double startposx, double startposy, double mouseposx, double mouseposy){
        if(ammunition==0 || cooldown>0){
            return;
        }
        Bullet bullet = new Bullet(mouseposx, mouseposy,startposx,startposy,speed);
        this.getChildren().add(bullet);
        cooldown = cooldownbetweenshots;
        ammunition--;
    }

    void update(){
        updateCooldown();
        moveBullets();
    }

    public void updateCooldown(){
        if(cooldown>0){
            cooldown--;
        }
    }

    public void moveBullets(){
        Iterator iter= this.getChildren().iterator();
        Character gunuser = (Character)getParent().getParent();
        Pane level = (Pane)this.getScene().getRoot();
        Group characters = (Group)level.getChildren().get(0);
        Group obstacles = (Group)level.getChildren().get(1);
        bulletloop: while(iter.hasNext()){
            Bullet bullet = (Bullet)iter.next();
            if(bullet!=null&&bullet.getImage()!=null&&bullet.getScene()!=null&&(bullet.getX()+bullet.getImage().getWidth()>bullet.getScene().getWidth()||bullet.getX()<0-bullet.getImage().getWidth()||bullet.getY()>bullet.getScene().getHeight()+bullet.getImage().getHeight()||bullet.getY()<0-bullet.getImage().getHeight())){
                iter.remove();
                continue bulletloop;
            }
            if(bullet!=null&&bullet.getImage()!=null&&bullet.getScene()!=null&&(bullet.distance > maxrange)){
                iter.remove();
                continue bulletloop;
            }
            //test if bullet collides with a character
            for(Node characternode:characters.getChildren()){
                Character character = (Character)characternode;
                if(character!=gunuser){
                    if (bullet != null && character.characterModel.intersects(bullet.getX(), bullet.getY(), bullet.getImage().getWidth(), bullet.getImage().getHeight())) {
                        if (character.health.takeDamage(damage)) {
                            gunuser.score += character.scorevalue;
                            iter.remove();
                            continue bulletloop;
                        }
                        iter.remove();
                        continue bulletloop;
                    }
                }
            }

            //test if bullet collides with a obstacle
            for(Node obstaclenode:obstacles.getChildren()){
                Obstacle obstacle = (Obstacle)obstaclenode;
                if (bullet != null && obstacle.intersects(bullet.getX(), bullet.getY(), bullet.getImage().getWidth(), bullet.getImage().getHeight())) {
                    iter.remove();
                    continue bulletloop;
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

    public void addAmmunition(int amount){
        ammunition += amount;
    }
}
