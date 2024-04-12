package net.bilaljh.fubar;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static Raycaster engine;
    public static Player player;
    public static Display display;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        player = new Player(300, 100);
        engine = new Raycaster();
        display = new Display();


    }
}