package net.bilaljh.fubar;

import java.util.Random;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class Main extends Application {

    public static Random randomizer;
    public static Raycaster engine;
    public static Player player;
    public static LostSoul lostSoul;
    public static Display display;
    public static Face face;
    public static Map map;
    public static Gun gun;

    public static final int RAY_NUMBER = 360;
    public static final int SCREEN_HEIGHT = 640;
    public static final int SCREEN_WIDTH = 1205;
    public static final int GAME_WIDTH = 1080;
    public static final boolean DEVELOPER_MODE = true;

    public static int menuSelection;                        //1 = restart, 2 = credits, 3 = exit
    public static boolean gameOver, showCredits;

    public static Ray[] rays, enemyRays;


    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        randomizer = new Random();
        map = new Map();
        engine = new Raycaster();
        player = new Player(map.getPlayerSpawnX() * 64, map.getPlayerSpawnY() * 64, 0);
        lostSoul = new LostSoul(map.getLostSoulSpawnX() * 64, map.getLostSoulSpawnY() * 64, 0);
        display = new Display();
        face = new Face();
        gun = new Gun();
        rays = new Ray[RAY_NUMBER];
        enemyRays = new Ray[RAY_NUMBER];
        menuSelection = 1;
        gameOver = false;
        showCredits = false;

        controls();
        Platform.runLater(() -> Main.display.draw());
    }

    public void controls() {
        Main.display.primaryScene.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if(!gameOver) {
                switch(keyCode) {
                    case W:
                        player.move(10, false);
                        break;
                    case A:
                        player.setAngle(player.getAngle()-Math.toRadians(7));
                        if(player.getAngle() <= 0) {
                            player.setAngle(Math.toRadians(360));
                        }
                        break;
                    case S:
                        player.move(-10, false);
                        break;
                    case D:
                        player.setAngle(player.getAngle()+Math.toRadians(7));
                        if(player.getAngle() >= Math.toRadians(360)) {
                            player.setAngle(0);
                        }
                        break;
                    case Q:
                        player.move(10, "left");
                        break;
                    case E:
                        player.move(10, "right");
                        break;
                    case SPACE:
                        player.setLife(player.getLife() - 100);
                        player.fire();
                        gun.fire();
                        break;
                    default:
                        break;
                }
            } else {
                switch(keyCode) {
                    case UP:
                        if(menuSelection != 1) {
                            menuSelection--;
                        }
                        break;
                    case DOWN:
                        if(menuSelection != 2) {
                            menuSelection++;
                        }
                        break;
                    case SPACE:
                        if(menuSelection == 1) {
                            System.exit(0);
                        } else if(menuSelection == 2) {
                            showCredits = !showCredits;
                        }
                        break;
                }
            }
        });
    }

    public void restart() {

    }
}