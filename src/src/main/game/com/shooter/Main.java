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

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

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
        Group characters = new Group();
        Group defences = new Group();
        Group powerups = new Group();

        Player player = new Player();
        player.characterModel.setX(400);
        player.characterModel.setY(400);
        characters.getChildren().add(player);

        Enemy enemy1 = new Enemy();
        enemy1.characterModel.setX(100);
        enemy1.characterModel.setY(400);
        characters.getChildren().add(enemy1);

        level.getChildren().addAll(characters,defences,powerups);
        level.setStyle("-fx-background-color : #63ff69;");

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                for (Object o : characters.getChildren()) {
                    Character character = (Character) o;
                    character.update();
                }
                // removes dead characters
                characters.getChildren().removeIf(o -> ((Character) o).dead);
            }
        };

        //define second scene and styles
        Scene secondScene = new Scene(level,1000,1000);
        startButton.setOnAction(e -> {
                    timer.start();
                    primaryStage.setScene(secondScene);
            }
        );

        secondScene.onMouseMovedProperty().bind(player.onMouseMovedProperty());
        secondScene.onKeyPressedProperty().bind(player.onKeyPressedProperty());
        secondScene.onMouseClickedProperty().bind(player.onMouseClickedProperty());
        exitButton.setOnAction(e -> System.exit(0));

        // define styles of primarystage
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        player.requestFocus();
        primaryStage.maximizedProperty();
        primaryStage.show();
    }
}