package net.bilaljh.fubar;

import java.util.Random;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class Main extends Application {

    public static Random randomizer = new Random();
    public static Raycaster engine;
    public static Player player;
    public static LostSoul lostSoul;
    public static Display display;
    public static Face face;
    public static Map map;
    public static Gun gun;

    public static final int RAY_NUMBER = 360;
    public static final int SCREEN_HEIGHT = 640;
    public static final int SCREEN_WIDTH = 1110;
    public static final int GAME_WIDTH = 960;
    public static final boolean DEVELOPER_MODE = true;

    public static Ray[] rays, enemyRays;


    public static void main(String[] args) {
        rays = new Ray[RAY_NUMBER];
        enemyRays = new Ray[RAY_NUMBER];

        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        randomizer = new Random();
        map = new Map();
        engine = new Raycaster();
        player = new Player(map.getPlayerSpawnX() * 64, map.getPlayerSpawnY() * 64, 0, 100);
        lostSoul = new LostSoul(randomizer.nextInt(map.getMapBorderX()) * 64, randomizer.nextInt(map.getMapBorderY()) * 64, 0);
        display = new Display();
        face = new Face();
        gun = new Gun();

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
                case SPACE:
                    player.fire();
                    gun.fire();
                    break;
                default:
                    break;
            }
        });
    }
}