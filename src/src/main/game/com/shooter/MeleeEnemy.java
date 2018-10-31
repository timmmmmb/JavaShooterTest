package main.game.com.shooter;

import javafx.scene.image.Image;

public class MeleeEnemy extends Enemy {
    private int attackcooldown = 100;
    private int currentattackcooldown = 0;
    private int meleedamage = 5;
    MeleeEnemy(){
        speed = 1;
        characterModel.setImage(new Image("MeleeEnemy.png", 100,100,true,true));
    }

    public MeleeEnemy(int x, int y) {
        speed = 1;
        characterModel.setImage(new Image("MeleeEnemy.png", 100,100,true,true));
        characterModel.setX(x);
        characterModel.setY(y);
    }

    @Override
    public void update(){
        moveHealth();
        moveBullets();
        moveTowardsPlayer();
        attackPlayer();
    }

    private void attackPlayer() {
        Character target = (Character)getParent().getChildrenUnmodifiable().get(0);
        if(target.getName().equals("Player")) {
            if (currentattackcooldown == 0) {
                if (characterModel.intersects(target.characterModel.getX(), target.characterModel.getY(), target.characterModel.getImage().getWidth(), target.characterModel.getImage().getHeight())) {
                    currentattackcooldown = attackcooldown;
                    target.health.takeDamage(meleedamage);
                }
            } else {
                currentattackcooldown--;
            }
        }
    }

    private void moveTowardsPlayer() {
        Character target = (Character)getParent().getChildrenUnmodifiable().get(0);
        if(target.getName().equals("Player")&&(target.characterModel.getX()!=characterModel.getX()||target.characterModel.getY()!=characterModel.getY())){
            double distance = Math.sqrt(Math.pow(target.characterModel.getX()-characterModel.getX(),2)+Math.pow(target.characterModel.getY()-characterModel.getY(),2));
            double steps = distance/speed;
            characterModel.setX(characterModel.getX()+(target.characterModel.getX()-characterModel.getX())/steps);
            characterModel.setY(characterModel.getY()+(target.characterModel.getY()-characterModel.getY())/steps);
        }
    }


}
