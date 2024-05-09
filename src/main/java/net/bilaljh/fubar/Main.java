package net.bilaljh.fubar;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class Main extends Application {

    public static Raycaster engine;
    public static Player player;
    public static Display display;
    public static Face face;
    public static Map map;

    public static int rayNumber = 360;

    public static Ray[] rays;


    public static void main(String[] args) {
        rays = new Ray[rayNumber];
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        map = new Map();
        engine = new Raycaster();
        player = new Player(map.getSpawnX() * 64, map.getSpawnY() * 64, 0, 100);
        display = new Display();
        face = new Face();

        controls();
        Platform.runLater(() -> Main.display.draw());
    }

    public void controls() {
        Main.display.primaryScene.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            switch(keyCode) {
                case W:
                    player.move(10);
                    //player.setLife(player.getLife()-1);
                    break;
                case A:
                    player.setAngle(player.getAngle()-Math.toRadians(15));
                    if(player.getAngle() <= 0) {
                        player.setAngle(Math.toRadians(360));
                    }
                    break;
                case S:
                    player.move(-10);
                    break;
                case D:
                    player.setAngle(player.getAngle()+Math.toRadians(15));
                    if(player.getAngle() >= Math.toRadians(360)) {
                        player.setAngle(0);
                    }
                    break;
                default:
                    break;
            }
        });
    }
}