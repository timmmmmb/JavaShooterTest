package main.game.com.shooter;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class MeleeEnemy extends Enemy {
    private int attackcooldown = 100;
    private int currentattackcooldown = 0;
    private int meleedamage = 3;
    private double movementx = 0;
    private double movementy = 0;
    MeleeEnemy(Character player){
        speed = 1;
        characterModel.setImage(new Image("MeleeEnemy.png", 100,100,true,true));
        Random rand = new Random();
        int xpos = 0;
        int ypos = 0;
        do{
            xpos = rand.nextInt(1000) + 1;
            ypos = rand.nextInt(1000) + 1;
        }while(player.characterModel.intersects(xpos-10,ypos-10,120,120));
        characterModel.setY(ypos);
        characterModel.setX(xpos);
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
            movementx = target.characterModel.getX();
            movementy = target.characterModel.getY();
            Pane level = (Pane)this.getScene().getRoot();
            Group obstacles = (Group)level.getChildren().get(1);
            ArrayList<Obstacle> obstacleList = new ArrayList<>();
            for(Node obstaclenode:obstacles.getChildren()){
                Obstacle obstacle = (Obstacle) obstaclenode;
                obstacleList.add(obstacle);
            }
            Collections.sort(obstacleList, new Sortbydistance());
            //create a line between enemy and target
            Line line = new Line(characterModel.getX(),characterModel.getY(),target.characterModel.getX(),target.characterModel.getY());
            line.setStrokeWidth(characterModel.getImage().getWidth());
            // the line has the width of the meleeenemy
            checkObstacleCollision(obstacleList,line);

            double distance = Math.sqrt(Math.pow(movementx-characterModel.getX(),2)+Math.pow(movementy-characterModel.getY(),2));
            double steps = distance/speed;

            double xspeed = (movementx-characterModel.getX())/steps;
            double yspeed = (movementy-characterModel.getY())/steps;

            //System.out.println("Targetpos: "+movementx+" / "+movementy+" currentpos: "+characterModel.getX()+" / "+characterModel.getY());

            characterModel.setX(characterModel.getX()+xspeed);
            characterModel.setY(characterModel.getY()+yspeed);

        }
    }

    private boolean checkObstacleCollision(ArrayList<Obstacle> obstacleList, Line line){
        boolean collision = false;
        //get direction
        Direction direction = Direction.NONE;
        if(Math.abs(line.getEndX()-line.getStartX())<Math.abs(line.getEndY()-line.getStartY())&&line.getEndY()-line.getStartY()<0){
            direction = Direction.NORTH;
        }else if(Math.abs(line.getEndX()-line.getStartX())<Math.abs(line.getEndY()-line.getStartY())&&line.getEndY()-line.getStartY()>=0){
            direction = Direction.SOUTH;
        }else if(Math.abs(line.getEndX()-line.getStartX())>=Math.abs(line.getEndY()-line.getStartY())&&line.getEndX()-line.getStartX()<0){
            direction = Direction.WEST;
        }else if(Math.abs(line.getEndX()-line.getStartX())>=Math.abs(line.getEndY()-line.getStartY())&&line.getEndX()-line.getStartX()>=0){
            direction = Direction.EAST;
        }
        for(Obstacle obstacle:obstacleList) {
            //check if the line collides with a obstacle
            if(obstacle.intersects(line.getLayoutBounds())){
                collision = true;
                //if it collides create a new line to the edges of the obstacle
                if(direction == Direction.NORTH){
                    Line edge1 = new Line(line.getStartX(), line.getStartY(), obstacle.getX()-characterModel.getImage().getWidth(),obstacle.getY()+obstacle.getHeight());
                    Line edge2 = new Line(line.getStartX(), line.getStartY(), obstacle.getX()+obstacle.getWidth(),obstacle.getY()+obstacle.getHeight());
                    if(edge1.getEndX() == line.getEndX()&& edge1.getEndY()== line.getEndY()){
                        continue;
                    }

                    //falls die momentane position der rand von dem Objekt ist
                    if(obstacle.getX()-characterModel.getImage().getWidth()+0.5>=characterModel.getX()&&
                            obstacle.getX()-characterModel.getImage().getWidth()-0.5<=characterModel.getX()&&
                            characterModel.getY()<=obstacle.getY()+obstacle.getHeight()&&
                            characterModel.getY()+characterModel.getImage().getHeight()>=obstacle.getY()){

                        movementx = obstacle.getX()-characterModel.getImage().getWidth();
                        movementy = obstacle.getY()-characterModel.getImage().getHeight();
                    }else if(obstacle.getX()+obstacle.getWidth()+0.5>=characterModel.getX()&&
                            obstacle.getX()+obstacle.getWidth()-0.5<=characterModel.getX()&&
                            characterModel.getY()<=obstacle.getY()+obstacle.getHeight()&&
                            characterModel.getY()+characterModel.getImage().getHeight()>=obstacle.getY()){

                        movementx = obstacle.getX()+obstacle.getWidth();
                        movementy = obstacle.getY()-characterModel.getImage().getHeight();
                    }else{
                        gotoCloserEdge(edge1,edge2);
                    }

                }else if(direction == Direction.SOUTH){
                    Line edge1 = new Line(line.getStartX(), line.getStartY(), obstacle.getX()-characterModel.getImage().getWidth(),obstacle.getY()-characterModel.getImage().getHeight()+obstacle.getHeight());
                    Line edge2 = new Line(line.getStartX(), line.getStartY(), obstacle.getX()+obstacle.getWidth(),obstacle.getY()-characterModel.getImage().getHeight()+obstacle.getHeight());
                    if(edge1.getEndX() == line.getEndX()&& edge1.getEndY()== line.getEndY()){
                        continue;
                    }

                    //falls die momentane position der rand von dem Objekt ist
                    if(obstacle.getX()-characterModel.getImage().getWidth()+0.5>=characterModel.getX()&&
                            obstacle.getX()-characterModel.getImage().getWidth()-0.5<=characterModel.getX()&&
                            characterModel.getY()<obstacle.getY()+obstacle.getHeight()+characterModel.getImage().getHeight()&&
                            characterModel.getY()+obstacle.getHeight()+characterModel.getImage().getHeight()>obstacle.getY()){

                        movementx = obstacle.getX()-characterModel.getImage().getWidth();
                        movementy = obstacle.getY()+obstacle.getHeight()+characterModel.getImage().getHeight();
                    }else if(obstacle.getX()+obstacle.getWidth()+0.5>=characterModel.getX()&&
                            obstacle.getX()+obstacle.getWidth()-0.5<=characterModel.getX()&&
                            characterModel.getY()<obstacle.getY()+obstacle.getHeight()+characterModel.getImage().getHeight()&&
                            characterModel.getY()+obstacle.getHeight()+characterModel.getImage().getHeight()>obstacle.getY()){

                        movementx = obstacle.getX()+obstacle.getWidth();
                        movementy = obstacle.getY()+obstacle.getHeight()+characterModel.getImage().getHeight();
                    }else{
                        gotoCloserEdge(edge1,edge2);
                    }

                }else if(direction == Direction.EAST){
                    Line edge1 = new Line(line.getStartX(), line.getStartY(), obstacle.getX()-characterModel.getImage().getWidth(),obstacle.getY()+obstacle.getHeight());
                    Line edge2 = new Line(line.getStartX(), line.getStartY(), obstacle.getX()-characterModel.getImage().getWidth(),obstacle.getY()-characterModel.getImage().getHeight());
                    if((edge1.getEndX() == line.getEndX()&& edge1.getEndY()== line.getEndY())||(edge2.getEndX() == line.getEndX()&& edge2.getEndY()== line.getEndY())){
                        continue;
                    }

                    //falls die momentane position der rand von dem Objekt ist
                    if(obstacle.getX()+obstacle.getWidth()+characterModel.getImage().getWidth()>=characterModel.getX()-0.5&&
                            obstacle.getX()-characterModel.getImage().getWidth()<=characterModel.getX()+5&&
                            characterModel.getY()>=obstacle.getY()+obstacle.getHeight()-0.5&&
                            characterModel.getY()<=obstacle.getY()+obstacle.getHeight()+0.5){

                        movementx = obstacle.getX()+obstacle.getWidth()+characterModel.getImage().getWidth();
                        movementy = obstacle.getY()+obstacle.getHeight();
                    }else if(obstacle.getX()+obstacle.getWidth()+characterModel.getImage().getWidth()>=characterModel.getX()-0.5&&
                            obstacle.getX()-characterModel.getImage().getWidth()<=characterModel.getX()+0.5&&
                            characterModel.getY()>=obstacle.getY()-characterModel.getImage().getHeight()-0.5&&
                            characterModel.getY()<=obstacle.getY()-characterModel.getImage().getHeight()+0.5){
                        movementx = obstacle.getX()+obstacle.getWidth()+characterModel.getImage().getWidth();
                        movementy = obstacle.getY()-characterModel.getImage().getHeight();
                    }else{
                        gotoCloserEdge(edge1,edge2);
                    }
                }else if(direction == Direction.WEST){
                    Line edge1 = new Line(line.getStartX(), line.getStartY(), obstacle.getX()+obstacle.getWidth(),obstacle.getY()+obstacle.getHeight());
                    Line edge2 = new Line(line.getStartX(), line.getStartY(), obstacle.getX()+obstacle.getWidth(),obstacle.getY()-characterModel.getImage().getHeight());
                    if((edge1.getEndX() == line.getEndX()&& edge1.getEndY()== line.getEndY())||(edge2.getEndX() == line.getEndX()&& edge2.getEndY()== line.getEndY())){
                        continue;
                    }

                    //falls die momentane position der rand von dem Objekt ist
                    if(obstacle.getX()+obstacle.getWidth()>=characterModel.getX()-0.5&&
                            obstacle.getX()-characterModel.getImage().getWidth()<=characterModel.getX()+0.5&&
                            characterModel.getY()>=obstacle.getY()+obstacle.getHeight()-0.5&&
                            characterModel.getY()<=obstacle.getY()+obstacle.getHeight()+0.5){

                        movementx = obstacle.getX()-characterModel.getImage().getWidth();
                        movementy = obstacle.getY()+obstacle.getHeight();
                    }else if(obstacle.getX()+obstacle.getWidth()>=characterModel.getX()-0.5&&
                            obstacle.getX()-characterModel.getImage().getWidth()<=characterModel.getX()+0.5&&
                            characterModel.getY()>=obstacle.getY()-characterModel.getImage().getHeight()-0.5&&
                            characterModel.getY()<=obstacle.getY()-characterModel.getImage().getHeight()+0.5){
                        movementx = obstacle.getX()-characterModel.getImage().getWidth()+1;
                        movementy = obstacle.getY()-characterModel.getImage().getHeight();
                    }else{
                        gotoCloserEdge(edge1,edge2);
                    }
                }
            }
        }
        return collision;
    }

    private void gotoCloserEdge(Line edge1, Line edge2){
        Helper helper = new Helper();
        //use the edge closer to you
        if(helper.calculateDistanceBetweenPoints(edge1.getEndX(),edge1.getEndY(),edge1.getStartX(),edge1.getStartY())<helper.calculateDistanceBetweenPoints(edge2.getEndX(),edge2.getEndY(),edge2.getStartX(),edge2.getStartY())){
            movementx = edge1.getEndX();
            movementy = edge1.getEndY();
        }else{
            movementx = edge2.getEndX();
            movementy = edge2.getEndY();
        }
    }

    class Sortbydistance implements Comparator<Obstacle>
    {
        public int compare(Obstacle a, Obstacle b)
        {
            Helper helper = new Helper();
            return (int) (helper.calculateDistanceBetweenPoints(a.getObstacleCenterX(), a.getObstacleCenterY(), getCharacterCenterX(), getCharacterCenterY())-helper.calculateDistanceBetweenPoints(b.getObstacleCenterX(), b.getObstacleCenterY(), getCharacterCenterX(), getCharacterCenterY()))*1000;
        }
    }



}
