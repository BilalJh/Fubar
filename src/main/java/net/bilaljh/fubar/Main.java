package net.bilaljh.fubar;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Stage primaryStage = new Stage();
        Group root = new Group();
        Scene primaryScene = new Scene(root, Color.BLACK);

        Image icon = new Image("LS00.png");

        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("Fubar! - PreAlpha v0.1");
        primaryStage.setWidth(960);
        primaryStage.setHeight(640);
        primaryStage.setResizable(false);

        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }
}