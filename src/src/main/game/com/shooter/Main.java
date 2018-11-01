package main.game.com.shooter;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Random;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    //TODO add obstacles that you cant move through
    private double runduration = 0;

    private Group characters = new Group();
    private Group defences = new Group();
    private Group powerups = new Group();
    private Player player = new Player(0,0);

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

        Pane level = new Pane();
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

                // removes dead characters
                powerups.getChildren().removeIf(o -> ((PowerUps) o).used);

            }
        };

        levelscene = new Scene(level,1000,1000);

        startButton.setOnAction(e -> {
                initializeLevel1();
                levelscene.setOnKeyTyped(event -> {
                    switch (event.getCharacter()) {
                        case "\u001B": timer.stop();primaryStage.setScene(scene); break;
                    }
                });
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

    private void initializeLevel1(){
        Pane level = new Pane();
        player = new Player(450,0);

        characters = new Group();
        defences = new Group();
        powerups = new Group();
        characters.getChildren().add(player);
        characters.getChildren().add(new MeleeEnemy(450,900));
        level.getChildren().addAll(characters,defences,powerups);
        level.setStyle("-fx-background-color : #63ff69;");

        //newPowerup();
        runduration = 0;
        levelscene = new Scene(level,1000,1000);
        levelscene.onMouseMovedProperty().bind(player.onMouseMovedProperty());
        levelscene.onKeyPressedProperty().bind(player.onKeyPressedProperty());
        levelscene.onKeyReleasedProperty().bind(player.onKeyReleasedProperty());
        levelscene.onMousePressedProperty().bind(player.onMousePressedProperty());
        levelscene.onMouseReleasedProperty().bind(player.onMouseReleasedProperty());
        levelscene.onMouseDraggedProperty().bind(player.onMouseDraggedProperty());
    }
}