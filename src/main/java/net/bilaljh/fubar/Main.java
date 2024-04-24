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
        engine = new Raycaster();
        display = new Display();
        player = new Player((double) (map.getSpawnX() * 64), (double) (map.getSpawnY() * 64),0);


        controls();
        Platform.runLater(() -> Main.display.draw());

    }

    public void controls() {
        Main.display.primaryScene.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            switch (keyCode) {
                case W:
                    player.moveForward();
                    System.out.println(player.getAngle());
                    System.out.println(player.getPosX());
                    System.out.println(player.getPosY());
                    break;
                case A:
                    player.setAngle(player.getAngle()-Math.toRadians(15));
                    if(player.getAngle() <= 0) {
                        player.setAngle(Math.toRadians(360));
                    }
                    System.out.println(player.getAngle());
                    System.out.println(player.getPosX());
                    System.out.println(player.getPosY());
                    break;
                case S:
                    player.moveBackward();
                    System.out.println(player.getAngle());
                    System.out.println(player.getPosX());
                    System.out.println(player.getPosY());
                    break;
                case D:
                    player.setAngle(player.getAngle()+Math.toRadians(15));
                    if(player.getAngle() >= Math.toRadians(360)) {
                        player.setAngle(0);
                    }
                    System.out.println(player.getAngle());
                    System.out.println(player.getPosX());
                    System.out.println(player.getPosY());
                    break;
                default:
                    break;
            }
//            if(player.getPosX() > 512 || player.getPosX() < 0 || player.getPosY() > 512 || player.getPosY() < 0) {
//                player.setPosX(200);
//                player.setPosY(200);
//            }
        });
    }
}