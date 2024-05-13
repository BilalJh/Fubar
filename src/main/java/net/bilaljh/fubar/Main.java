package net.bilaljh.fubar;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.util.Random;

public class Main extends Application {

    public static Random randomizer;
    public static Raycaster engine;
    public static Player player;
    public static LostSoul lostSoul;
    public static Display display;
    public static Face face;
    public static Map map;

    public static final int RAY_NUMBER = 360;
    public static final int SCREEN_HEIGHT = 640;
    public static final int SCREEN_WIDTH = 1205;
    public static final int GAME_WIDTH = 1080;
    public static final boolean DEVELOPER_MODE = true;

    public static int menuSelection;                            //1 = restart, 2 = credits, 3 = exit
    public static int gameState;                                //1 = menu,    2 = ongoing, 3 = gameOver, 4 = settings
    public static int randomScreen, soundVolume, musicVolume;
    public static boolean showCredits;

    public static Ray[] rays, enemyRays;


    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage){
        randomizer = new Random();
        map = new Map();
        engine = new Raycaster();
        player = new Player(map.getPlayerSpawnX() * 64, map.getPlayerSpawnY() * 64, 0);
        lostSoul = new LostSoul(map.getLostSoulSpawnX() * 64, map.getLostSoulSpawnY() * 64, 0);
        display = new Display();
        face = new Face();
        rays = new Ray[RAY_NUMBER];
        enemyRays = new Ray[RAY_NUMBER];
        menuSelection = 1;
        gameState = 1;
        randomScreen = randomizer.nextInt(2);
        soundVolume = 50;
        musicVolume = 50;
        showCredits = false;


        display.playSound(display.musicPlayer);


        controls();
        Platform.runLater(() -> {
            Main.display.draw();
        });
    }

    public void controls() {
        Main.display.getPrimaryScene().setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if(gameState == 2) {
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
                        if(System.currentTimeMillis() - player.getMarker() > 1000) {
                            player.fire();
                            player.setMark();
                            display.soundPlayer = new MediaPlayer(display.pistolSound);
                            display.setVolume(display.soundPlayer, soundVolume);
                            display.playSound(display.soundPlayer);
                            display.soundPlayer = new MediaPlayer(display.pistolSound);
                        }
                        break;
                    default:
                        break;
                }
            }
            else if(gameState == 1) {
                switch(keyCode) {
                    case UP:
                        if(menuSelection > 1) {
                            menuSelection--;
                            display.soundPlayer = new MediaPlayer(display.itemSound);
                            display.setVolume(display.soundPlayer, soundVolume);
                            display.playSound(display.soundPlayer);
                            display.soundPlayer = new MediaPlayer(display.itemSound);
                        }
                        break;
                    case DOWN:
                        if(menuSelection < 3) {
                            menuSelection++;
                            display.soundPlayer = new MediaPlayer(display.itemSound);
                            display.setVolume(display.soundPlayer, soundVolume);
                            display.playSound(display.soundPlayer);
                            display.soundPlayer = new MediaPlayer(display.itemSound);
                        }
                        break;
                    case SPACE:
                        if(menuSelection == 1) {
                            if(showCredits) {
                                showCredits = false;
                            }
                            gameState = 2;
                            display.setVolume(display.soundPlayer, musicVolume);
                            display.playSound(display.soundPlayer);
                            display.stopSound(display.musicPlayer);
                            display.music = new Media(new File(display.musicFiles[randomizer.nextInt(3) + 1]).toURI().toString());
                            display.musicPlayer = new MediaPlayer(display.music);
                            display.musicPlayer.setAutoPlay(true);
                            display.setVolume(display.musicPlayer, Main.musicVolume);
                            display.playSound(display.musicPlayer);
                            showCredits = false;
                        } else if(menuSelection == 2) {
                            if(showCredits) {
                                showCredits = false;
                            }
                            gameState = 4;
                            showCredits = false;
                            display.soundPlayer = new MediaPlayer(display.startSound);
                            display.setVolume(display.soundPlayer, soundVolume);
                            display.playSound(display.soundPlayer);
                            display.soundPlayer = new MediaPlayer(display.startSound);
                        } else {
                            menuSelection = 3;
                            showCredits = !showCredits;
                            display.soundPlayer = new MediaPlayer(display.startSound);
                            display.setVolume(display.soundPlayer, soundVolume);
                            display.playSound(display.soundPlayer);
                            display.soundPlayer = new MediaPlayer(display.startSound);
                        }
                        break;
                    default:
                        break;
                }
            }
            else if(gameState == 3) {
                switch(keyCode) {
                    case UP:
                        if(menuSelection != 1) {
                            menuSelection--;
                            display.soundPlayer = new MediaPlayer(display.itemSound);
                            display.setVolume(display.soundPlayer, soundVolume);
                            display.playSound(display.soundPlayer);
                            display.soundPlayer = new MediaPlayer(display.itemSound);
                        }
                        break;
                    case DOWN:
                        if(menuSelection != 2) {
                            menuSelection++;
                            display.soundPlayer = new MediaPlayer(display.itemSound);
                            display.setVolume(display.soundPlayer, soundVolume);
                            display.playSound(display.soundPlayer);
                            display.soundPlayer = new MediaPlayer(display.itemSound);
                        }
                        break;
                    case SPACE:
                        if(menuSelection == 1) {
                            display.soundPlayer = new MediaPlayer(display.startSound);
                            display.setVolume(display.soundPlayer, soundVolume);
                            display.playSound(display.soundPlayer);
                            display.soundPlayer = new MediaPlayer(display.startSound);
                            System.exit(0);
                        } else if(menuSelection == 2) {
                            display.soundPlayer = new MediaPlayer(display.startSound);
                            display.setVolume(display.soundPlayer, soundVolume);
                            display.playSound(display.soundPlayer);
                            display.soundPlayer = new MediaPlayer(display.startSound);
                            showCredits = !showCredits;
                        }
                        break;
                }
            } else if(gameState == 4) {
                switch(keyCode) {
                    case UP:
                        if(menuSelection > 1) {
                            menuSelection--;
                            display.soundPlayer = new MediaPlayer(display.itemSound);
                            display.setVolume(display.soundPlayer, soundVolume);
                            display.playSound(display.soundPlayer);
                            display.soundPlayer = new MediaPlayer(display.itemSound);
                        }
                        break;
                    case DOWN:
                        if(menuSelection < 4) {
                            menuSelection++;
                            display.soundPlayer = new MediaPlayer(display.itemSound);
                            display.setVolume(display.soundPlayer , soundVolume);
                            display.playSound(display.soundPlayer);
                            display.soundPlayer = new MediaPlayer(display.itemSound);
                        }
                        break;
                    case LEFT:
                        if(menuSelection == 1) {
                            if(musicVolume != 0) {
                                musicVolume -= 10;
                                display.setVolume(display.musicPlayer, musicVolume);
                            }
                        } else if(menuSelection == 2) {
                            if(soundVolume != 0) {
                                soundVolume -= 10;
                                display.setVolume(display.soundPlayer, musicVolume);
                            }
                        }
                        break;
                    case RIGHT:
                        if(menuSelection == 1) {
                            if(musicVolume != 100) {
                                musicVolume += 10;
                                display.setVolume(display.musicPlayer, musicVolume);
                            }
                        } else if(menuSelection == 2) {
                            if(soundVolume != 100) {
                                soundVolume += 10;
                                display.setVolume(display.soundPlayer, musicVolume);
                            }
                        }
                        break;
                    case SPACE:
                        if(menuSelection == 3) {
                            gameState = 1;
                            menuSelection = 2;
                            display.soundPlayer = new MediaPlayer(display.startSound);
                            display.setVolume(display.soundPlayer, soundVolume);
                            display.playSound(display.soundPlayer);
                            display.soundPlayer = new MediaPlayer(display.startSound);
                        }
                        break;
                }
            }
        });
    }
}