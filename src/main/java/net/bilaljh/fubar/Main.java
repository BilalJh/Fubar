package net.bilaljh.fubar;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Screen;
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


    // Variablen für Bildschirmgröße
    public static int height = (int) Screen.getPrimary().getBounds().getHeight();
    public static int width = (int) Screen.getPrimary().getBounds().getWidth();
    //public static int height = 1000;
    //public static int width = 1000;
    public static int SCREEN_HEIGHT;
    public static int SCREEN_WIDTH;
    public static int GAME_WIDTH;
    public static final int RAY_NUMBER = 360;

    public static int menuSelection;                            //Aktuelle Auswahl
                                                                //1 = restart, 2 = credits, 3 = exit
    public static int gameState;                                //Aktueller Zustand im Spiel
                                                                //1 = menu,    2 = ongoing, 3 = gameOver, 4 = settings
    public static int randomScreen, soundVolume, musicVolume;
    public static boolean showCredits;

    public static Ray[] rays, enemyRays;


    public static void main(String[] args) {

        if(width >= height) {
            SCREEN_HEIGHT = (int) (9.0/16.0 * width);
            SCREEN_WIDTH = width;
        } else {
            SCREEN_WIDTH = (int) (16.0/9.0 * height);
            SCREEN_HEIGHT = height;
        }

        GAME_WIDTH = SCREEN_WIDTH - Display.calcNewWidth(150, 640, SCREEN_HEIGHT);

        Application.launch(args);                               //Startet die JavaFX-RuntimeEnvironment und erstellt eine Instanz vom Typ Application.class
    }

    @Override
    public void start(Stage stage){                              //Methode zum Starten, gewissermaßen ein Pseuso Konstruktor
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

        display.setVolume(display.soundPlayer, 50);
        display.setVolume(display.musicPlayer, 50);
        display.playSound(display.musicPlayer);

        controls();
        Platform.runLater(() -> {                               //Verschiebt display Instanz auf den JavaFX Thread
            Main.display.draw();
        });
    }

    public void controls() {
        Main.display.getPrimaryScene().setOnKeyPressed(event -> {   //Eventhandler zur Steuerung
            KeyCode keyCode = event.getCode();
            if(gameState == 2) {                                    //Steuerung im Spiel, also WASD QE und Space zur Fortbewegung und zum Schießen
                switch(keyCode) {
                    case W:
                        player.move(10, false);
                        break;
                    case A:
                        player.setAngle(player.getAngle()-Math.toRadians(7));   //Manipuliert Winkel des Spielers
                        if(player.getAngle() <= 0) {
                            player.setAngle(Math.toRadians(360));
                        }
                        break;
                    case S:
                        player.move(-10, false);                //Bewegt Spieler
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
                    case SPACE:                                                //Lässt Spieler mit einem Cooldown von einer Sekunde schießen und lässt Sounds abspielen
                        if(System.currentTimeMillis() - player.getMarker() > 1000) {
                            player.fire();
                            player.setMark();
                            display.setMark();
                        }
                        break;
                    default:
                        break;
                }
            }
            else if(gameState == 1) {                                           //Steuerung von UI Elementen im Startmenü mithilfe von Pfeiltasten + Abspielen von Sounds
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
                            display.setVolume(display.soundPlayer, soundVolume);
                            display.playSound(display.soundPlayer);
                            display.soundPlayer = new MediaPlayer(display.itemSound);
                        }
                        break;
                    case SPACE:
                        if(menuSelection == 1) {
                            if(showCredits) {
                                showCredits = false;            //Schließt Dankaussagungen
                            }
                            gameState = 2;                      //Versetzt Spiel in Zustand 2
                            display.setVolume(display.soundPlayer, musicVolume);
                            display.playSound(display.soundPlayer);
                            display.stopSound(display.musicPlayer);
                            display.music = new Media(new File(display.musicFiles[1]).toURI().toString());
                            display.musicPlayer = new MediaPlayer(display.music);
                            display.musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                            display.setVolume(display.musicPlayer, Main.musicVolume);
                            display.musicPlayer.setAutoPlay(true);
                            display.playSound(display.musicPlayer);
                            showCredits = false;
                        } else if(menuSelection == 2) {
                            gameState = 4;                      //Versetzt Spiel in Zustand 4
                            showCredits = false;
                            display.soundPlayer = new MediaPlayer(display.startSound);
                            display.setVolume(display.soundPlayer, soundVolume);
                            display.playSound(display.soundPlayer);
                            display.soundPlayer = new MediaPlayer(display.startSound);
                        } else if(menuSelection == 3){
                            menuSelection = 3;
                            showCredits = !showCredits;         //Öffnet Dankaussagungen
                            display.soundPlayer = new MediaPlayer(display.startSound);
                            display.setVolume(display.soundPlayer, soundVolume);
                            display.playSound(display.soundPlayer);
                            display.soundPlayer = new MediaPlayer(display.startSound);
                        } else if(menuSelection == 4) {
                            System.exit(0);                //Schließt das Spiel
                        }
                        break;
                    default:
                        break;
                }
            }
            else if(gameState == 3) {                              //Steuerung von UI Elementen im GameOverScreen mithilfe von Pfeiltasten + Abspielen von Sounds
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
            } else if(gameState == 4) {                         //Steuerung von UI Elementen in den Einstellungen mithilfe von Pfeiltasten + Abspielen von Sounds
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
                                display.stopSound(display.soundPlayer);
                                display.soundPlayer = new MediaPlayer(display.startSound);
                                display.setVolume(display.soundPlayer, soundVolume);
                                display.playSound(display.soundPlayer);
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
                                display.stopSound(display.soundPlayer);
                                display.soundPlayer = new MediaPlayer(display.startSound);
                                display.setVolume(display.soundPlayer, soundVolume);
                                display.playSound(display.soundPlayer);
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