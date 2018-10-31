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
    //TODO change spawn position so they can no longer spawn near you
    //TODO make it impossible to move out of screen
    //TODO display the current weapon and its amuntion
    //TODO add a rifle that shoots 3 shots at once all with a 0.5 sec delay
    //TODO make it possible to restart the game
    //TODO stop the player from running indefinetly
    //TODO spawn more enemys after 30s and after 60s
    //TODO add powerups that heal
    //TODO add powerups that restore ammunition
    private double runduration = 0;

    Group characters = new Group();
    Group defences = new Group();
    Group powerups = new Group();
    Player player = new Player(0,0);

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
                //it triggers all 15 sec
                if(runduration%(30*20)==0){
                    Random rand = new Random();
                    characters.getChildren().add(new MeleeEnemy(rand.nextInt(1000) + 1,rand.nextInt(1000) + 1));
                }
            }
        };

        levelscene = new Scene(level,1000,1000);

        startButton.setOnAction(e -> {
                initializeLevel1();
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

    private void initializeLevel1(){
        Pane level = new Pane();
        player = new Player(400,400);

        characters = new Group();
        defences = new Group();
        powerups = new Group();
        characters.getChildren().add(player);
        characters.getChildren().add(new MeleeEnemy(100,400));


        level.getChildren().addAll(characters,defences,powerups);
        level.setStyle("-fx-background-color : #63ff69;");

        runduration = 0;
        levelscene = new Scene(level,1000,1000);
        levelscene.onMouseMovedProperty().bind(player.onMouseMovedProperty());
        levelscene.onKeyPressedProperty().bind(player.onKeyPressedProperty());
        levelscene.onKeyReleasedProperty().bind(player.onKeyReleasedProperty());
        levelscene.onMousePressedProperty().bind(player.onMousePressedProperty());
    }
}