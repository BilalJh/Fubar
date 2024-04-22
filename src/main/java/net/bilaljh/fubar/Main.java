package net.bilaljh.fubar;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class Main extends Application {

    public static Raycaster engine;
    public static Player player;
    public static Display display;
    public static Map map;

    public static Ray[] rays = new Ray[90];

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        map = new Map();
        player = new Player(100, 100, 1);
        engine = new Raycaster();
        display = new Display();


        controlls();
        Platform.runLater(() -> Main.display.draw());
    }

    public void controlls() {
        Main.display.primaryScene.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            switch (keyCode) {
                case W:
                    player.moveForward();
                    System.out.println(player.getPosX());
                    break;
                case A:
                    player.setAngle(player.getAngle()-5);
                    if(player.getAngle() <= 0) {
                        player.setAngle(360);
                    }
                    System.out.println(player.getAngle());
                    break;
                case S:
                    player.moveBackward();
                    System.out.println(player.getPosX());
                    break;
                case D:
                    player.setAngle(player.getAngle()+5);
                    if(player.getAngle() >= 360) {
                        player.setAngle(0);
                    }
                    System.out.println(player.getAngle());
                    break;
                default:
                    break;
            }
        });
    }
}