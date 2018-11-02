package main.game.com.shooter;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.Random;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    //TODO make it that the ammunition powerup gives different amounts of ammo per weapon
    //TODO add obstacles that you cant move through
    //TODO make a boss meleeenemy that has a lot of health
    //TODO make multiple level layouts that you can choose
    //TODO make different bulletdesignes and shoot different bullets per gun
    //TODO create a simple ai for enemys that can shoot
    private double runduration = 0;

    private Group characters = new Group();
    private Group deathscreen = new Group();
    private Group defences = new Group();
    private Group powerups = new Group();
    private Player player = new Player(0,0);
    private Pane level = new Pane();

    private Scene levelscene;
    @Override
    public void start(Stage primaryStage) {
        // define primary stage properties
        primaryStage.setTitle("Game");

        // define elements for intro screen
        Button startButton = new Button("Start");
        Button settingsButton = new Button("Settings");
        Button exitButton = new Button("Exit");
        Text introText = new Text("Start the game");
        introText.setStyle("-fx-font: 24 arial;");

        //define hbox for buttons
        HBox hbox = new HBox();
        hbox.getChildren().addAll(startButton,settingsButton,exitButton);
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(20);

        //vBox for scene one
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.getChildren().addAll(introText, hbox);
        vBox.setAlignment(Pos.CENTER);

        // define first scene and styles
        Scene scene = new Scene(vBox,1000,1000);


        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                runduration++;
                for (Object o : characters.getChildren()) {
                    Character character = (Character) o;
                    character.update();
                }
                // removes dead characters
                characters.getChildren().removeIf(o -> ((Character) o).health.isDead());

                //adds new enemys at random coordinates
                //it triggers all 30 sec
                if(runduration%(30*15)==0){
                    //adds a powerup
                    newPowerup();
                    characters.getChildren().add(new MeleeEnemy(player));
                    if(runduration>30*30){
                        characters.getChildren().add(new MeleeEnemy(player));
                    }
                    if(runduration>30*60){
                        characters.getChildren().add(new MeleeEnemy(player));
                    }
                }

                for (Object o : powerups.getChildren()) {
                    PowerUps powerup = (PowerUps) o;
                    powerup.update(player);
                }

                if(player.health.isDead()){
                    deathscreen = new Group();
                    Label label = new Label("You died \nyour score: "+player.score+"\npress Escape to return to menu or R to restart the level");
                    label.setTextAlignment(TextAlignment.CENTER);
                    label.setFont(Font.font("Cambria", 32));
                    label.setLayoutX(100);
                    label.setLayoutY(400);
                    deathscreen.getChildren().add(label);
                    level.getChildren().add(deathscreen);
                    this.stop();
                }

                // removes dead characters
                powerups.getChildren().removeIf(o -> ((PowerUps) o).used);

            }
        };

        levelscene = new Scene(level,1000,1000);

        startButton.setOnAction(e -> {
                initializeLevel1(timer,primaryStage,scene);
                timer.start();
                primaryStage.setScene(levelscene);
            }
        );
        exitButton.setOnAction(e -> System.exit(0));

        // define styles of primarystage
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.maximizedProperty();
        primaryStage.show();
    }

    private void newPowerup(){
        Random rand = new Random();
        if(rand.nextInt(2) + 1==1){
            powerups.getChildren().add(new PowerUps(5));
        }else if(rand.nextInt(2) + 1==2){
            powerups.getChildren().add(new AmmunitionPowerUp(5));
        }else{
            powerups.getChildren().add(new HealPowerUp(5));
        }
    }

    private void initializeLevel1(AnimationTimer timer, Stage primaryStage, Scene scene){
        level = new Pane();
        player = new Player(450,0);

        characters = new Group();
        defences = new Group();
        powerups = new Group();
        characters.getChildren().add(player);
        characters.getChildren().add(new MeleeEnemy(450,900));
        level.getChildren().addAll(characters,defences,powerups);
        level.setStyle("-fx-background-color : #63ff69;");
        defences.getChildren().add(new Obstacle(400,495,200,10,0));


        //newPowerup();
        runduration = 0;
        levelscene = new Scene(level,1000,1000);
        levelscene.setOnKeyTyped(event -> {
            switch (event.getCharacter()) {
                case "\u001B":
                    timer.stop();
                    primaryStage.setScene(scene);
                    break;
                case "R": case "r":
                    timer.stop();
                    initializeLevel1(timer,primaryStage,scene);
                    primaryStage.setScene(levelscene);
                    timer.start();
                    break;
            }
        });
        levelscene.onMouseMovedProperty().bind(player.onMouseMovedProperty());
        levelscene.onKeyPressedProperty().bind(player.onKeyPressedProperty());
        levelscene.onKeyReleasedProperty().bind(player.onKeyReleasedProperty());
        levelscene.onMousePressedProperty().bind(player.onMousePressedProperty());
        levelscene.onMouseReleasedProperty().bind(player.onMouseReleasedProperty());
        levelscene.onMouseDraggedProperty().bind(player.onMouseDraggedProperty());
    }
}