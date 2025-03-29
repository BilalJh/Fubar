package net.bilaljh.fubar;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Screen;

import java.io.File;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Display {

    // !!! Images und deren ImageViews !!!
    // !!! Chronologisch nach Aufruf   !!!
    private final Image screenBackground;
    private final ImageView screenBackgroundView;
    private final Image hud;
    private final ImageView hudView;
    private final Image background;
    private final ImageView backgroundView;
    private final Image gameOver;
    private final ImageView gameOverView;
    private final Image score;
    private final ImageView scoreView;
    private final Image exit;
    private final ImageView exitView;
    private final Image restart;
    private final ImageView restartView;
    private final Image credits;
    private final ImageView creditsView;
    private final Image ls1;
    private final ImageView ls1View;
    private final Image ls2;
    private final ImageView ls2View;
    private final Image credit;
    private final ImageView creditView;
    private final Image startBackground;
    private final ImageView startBackgroundView;
    private final Image fubar;
    private final ImageView fubarView;
    private final Image start;
    private final ImageView startView;
    private final Image settings;
    private final ImageView settingsView;
    private final Image musicImage;
    private final ImageView musicView;
    private final Image sound;
    private final ImageView soundView;
    private final Image gun;
    private final ImageView gunView;
    private final Image gunShot;
    private final ImageView gunShotView;


    private final Stage primaryStage;
    private final Group root;
    private final Scene primaryScene;

    private final Random randomizer;
    private final Player player;
    private final LostSoul lostSoul;
    private final Line playerLine;
    private final Line enemyLine;
    private final Line[] lines;
    private final Line[] blackLines;
    public String[] soundFiles, musicFiles;
    private final Image lostSoulImage;
    private final ImageView lostSoulView;
    private final Text lifeOutline;
    private final Text lifeInline;
    private final Text scoreOutline;
    private final Text scoreInline;

    // !!! Sounds und deren Player !!!
    // -- Sounds
    public Media itemSound;
    public Media pistolSound;
    public Media deathSound;
    public Media painSound;
    public Media startSound;
    public Media lostSoulSound;
    public MediaPlayer soundPlayer;

    // -- Musik
    public Media music;
    public MediaPlayer musicPlayer;

    // X- Koordinate des anzuzeigenden Gegners und der jeweilige Ray
    private Ray enemyRay;
    private Ray medkitRay;
    private double enemyX;

    private long marker = 0;

    private final int WIDTH = Main.SCREEN_WIDTH;
    private final int HEIGHT = Main.SCREEN_HEIGHT;
    private final int GAME_WIDTH = Main.GAME_WIDTH;
    private final int GAMEX = (int) (Screen.getPrimary().getBounds().getHeight() - (Screen.getPrimary().getBounds().getHeight() - HEIGHT));

    private int controlTimer = 0;


    public Display() {
        randomizer = new Random();
        player = Main.player;
        lostSoul = Main.lostSoul;
        playerLine = new Line();
        enemyLine = new Line();
        lines = new Line[Main.RAY_NUMBER];
        blackLines = new Line[Main.RAY_NUMBER];
        lifeOutline = new Text();
        lifeInline = new Text();
        scoreOutline = new Text();
        scoreInline = new Text();

        lostSoulImage = new Image("file:src/resource/LS00.png");
        lostSoulView = new ImageView(lostSoulImage);

        soundFiles = new String[] {
                "src/resource/Soundtrack/Sound/DSITEMUP.wav",
                "src/resource/Soundtrack/Sound/DSPISTOL.wav",
                "src/resource/Soundtrack/Sound/DSPLDETH.wav",
                "src/resource/Soundtrack/Sound/DSPLPAIN.wav",
                "src/resource/Soundtrack/Sound/DSWPNUP.wav",
                "src/resource/Soundtrack/Sound/DSSKLATK.wav"
        };
        musicFiles = new String[] {
                "src/resource/Soundtrack/Music/DoomEternal.wav",
                "src/resource/Soundtrack/Music/FUBAR!.wav",
        };

        // !!! Sounds und deren Player !!!
        // -- Sounds
        itemSound = new Media(new File(soundFiles[0]).toURI().toString());
        pistolSound = new Media(new File(soundFiles[1]).toURI().toString());
        deathSound = new Media(new File(soundFiles[2]).toURI().toString());
        painSound = new Media(new File(soundFiles[3]).toURI().toString());
        startSound = new Media(new File(soundFiles[4]).toURI().toString());
        lostSoulSound = new Media(new File(soundFiles[5]).toURI().toString());
        soundPlayer = new MediaPlayer(itemSound);

        // -- Musik
        music = new Media(new File(musicFiles[0]).toURI().toString());
        musicPlayer = new MediaPlayer(music);
        musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        musicPlayer.setAutoPlay(true);

        // !!! Images und deren ImageViews !!!
        // !!! Chronologisch nach Aufruf   !!!
        {
            screenBackground = new Image("file:src/resource/StartMenu/Background.png");
            screenBackgroundView = new ImageView(screenBackground);
            hud = new Image("file:src/resource/Hud.png");
            hudView = new ImageView(hud);
            background = new Image("file:src/resource/GameOver/Background.png");
            backgroundView = new ImageView(background);
            gameOver = new Image("file:src/resource/GameOver/GameOver.png");
            gameOverView = new ImageView(gameOver);
            score = new Image("file:src/resource/GameOver/Score.png");
            scoreView = new ImageView(score);
            restart = new Image("file:src/resource/GameOver/Exit.png");
            restartView = new ImageView(restart);
            exit = new Image("file:src/resource/StartMenu/Settings/Exit.png");
            exitView = new ImageView(exit);
            credits = new Image("file:src/resource/Credits.png");
            creditsView = new ImageView(credits);
            ls1 = new Image("file:src/resource/LS00.png");
            ls1View = new ImageView(ls1);
            ls2 = new Image("file:src/resource/LS00.png");
            ls2View = new ImageView(ls2);
            credit = new Image("file:src/resource/Credit.png");
            creditView = new ImageView(credit);
            startBackground = new Image("file:src/resource/StartMenu/Background.png");
            startBackgroundView = new ImageView(startBackground);
            fubar = new Image("file:src/resource/StartMenu/Fubar.png");
            fubarView = new ImageView(fubar);
            start = new Image("file:src/resource/StartMenu/Start.png");
            startView = new ImageView(start);
            settings = new Image("file:src/resource/StartMenu/Settings.png");
            settingsView = new ImageView(settings);
            musicImage = new Image("file:src/resource/StartMenu/Settings/Music.png");
            musicView = new ImageView(musicImage);
            sound = new Image("file:src/resource/StartMenu/Settings/Sound.png");
            soundView = new ImageView(sound);
            gun = new Image("file:src/resource/Pistol.png");
            gunView = new ImageView(gun);
            gunShot = new Image("file:src/resource/Pistol1.png");
            gunShotView = new ImageView(gunShot);
        }

        // -- Hauptfenster --
        primaryStage = new Stage();
        root = new Group();
        primaryScene = new Scene(root, Color.rgb(91,0,0));

        primaryStage.getIcons().add(lostSoulImage);
        primaryStage.setTitle("Fubar! - v1.0");
        primaryStage.setWidth(WIDTH);
        primaryStage.setHeight(HEIGHT);
        primaryStage.setResizable(false);
        primaryStage.setFullScreen(true);
        primaryStage.initStyle(StageStyle.UNDECORATED);

        primaryStage.setScene(primaryScene);
        primaryStage.show();


        startUpdateTimer();
    }

    /**
     * Gameloop, aktualisiert alle 16ms -> 60 FPS
     */
    private void startUpdateTimer() {
        Timer timer = new Timer(true); // Daemon-Thread für den Timer
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    draw();
                });
            }
        }, 0, 16); // 16 Millisekunden (~60 FPS)
    }

    /**
     * Zeichnet aktuelle Szene
     */
    public void draw() {
        if(controlTimer > 16 * 5) {
            Main.controls();
        } else {
            controlTimer++;
        }
        if(controlTimer >= 1000) {
            controlTimer = 0;
        }
        if(Main.gameState == 1) {               //Je nach gameState werden verschiedene Anzeigen geladen
            drawMenu();
        }
        if(Main.gameState == 2) {
            Main.engine.castRays();
            Main.player.regenerate();
            Main.lostSoul.idle();
            draw3D();
            drawHUD();
        }
        if(Main.gameState == 3) {
            drawGameOver();
        }
        if(Main.gameState == 4) {
            drawSettings();
        }
    }

    /**
     * Zeichnet das Hauptmenü
     */
    public void drawMenu() {
        root.getChildren().clear();

        drawPicture(screenBackgroundView, 0, GAMEX, WIDTH, HEIGHT, HEIGHT, root);
        drawPicture(backgroundView,0, 0, Main.SCREEN_HEIGHT, 1280, 720, root);
        drawRect(0, 0, WIDTH, Screen.getPrimary().getBounds().getHeight(), Color.rgb(0, 0, 0), 0.6, root);
        drawPicture(fubarView, (double) WIDTH / 2 - 168, (double) HEIGHT / 4 * 1 - 60, root);

        double lsX1 = WIDTH * (1.0/3.0) - 100;
        double lsX2 = WIDTH * (2.0/3.0);
        double lsY;
        if(Main.menuSelection == 1) {
            lsY = HEIGHT - 350 - 40;
        } else if(Main.menuSelection == 2) {
            lsY = HEIGHT - 270 - 40;
        } else {
            lsY = HEIGHT - 190 - 40;
        }

        drawPicture(ls1View, lsX1, lsY, root);
        drawPicture(ls2View, lsX2, lsY, root);

        drawPicture(startView, (double) WIDTH / 2 - 99, HEIGHT - 350, root);
        drawPicture(settingsView, (double) WIDTH / 2 - 150, HEIGHT - 270, root);
        drawPicture(creditsView, (double) WIDTH / 2 - 132, HEIGHT - 190, root);

        if(Main.showCredits) {
            root.getChildren().clear();

            drawPicture(backgroundView, 0, 0, root);
            drawPicture(screenBackgroundView, 0, GAMEX, WIDTH, HEIGHT, HEIGHT, root);
            drawRect(0, 0, WIDTH, Screen.getPrimary().getBounds().getHeight(), Color.rgb(0, 0, 0), 0.6, root);
            drawPicture(creditsView,WIDTH / 2.0 - 132, (double) HEIGHT / 4 * 1 - 60, root);
            drawPicture(creditView,WIDTH / 2.0 - calcNewWidth(1680, 552, HEIGHT - HEIGHT * (4.5/10.0)) / 2.0, HEIGHT * (3.5/10.0), HEIGHT - HEIGHT * (4.5/10.0), 1680, 552, root);
        }
    }

    /**
     * Zeichnet die Einstellungen
     */
    public void drawSettings() {
        root.getChildren().clear();

        drawPicture(backgroundView, 0, 0, root);
        drawPicture(screenBackgroundView, 0, GAMEX, WIDTH, HEIGHT, HEIGHT, root);
        drawRect(0, 0, WIDTH, Screen.getPrimary().getBounds().getHeight(), Color.rgb(0, 0, 0), 0.6, root);
        drawPicture(settingsView, (double) WIDTH / 2 - 150, (double) HEIGHT / 4 * 1 - 60, root);

        double newHeight = calcNewHeight(107);
        double lsX1, lsX2, lsY;

        if(Main.menuSelection == 1) {
            lsY = HEIGHT / 5.0 * 2 + 60 - 107;
            lsX1 = WIDTH / 6.0 * 4 - 40 - 150;
            lsX2 = WIDTH / 6.0 * 4 + 50 - 50;
        } else if(Main.menuSelection == 2) {
            lsY = HEIGHT / 5.0 * 3 + 60 - 107;
            lsX1 = WIDTH / 6.0 * 4 - 40 - 150;
            lsX2 = WIDTH / 6.0 * 4 + 50 - 50;
        } else {
            lsY = HEIGHT - 110 - 53;
            lsX1 = WIDTH * (1.0/3.0) - 100;
            lsX2 = WIDTH * (2.0/3.0);
        }
        drawPicture(ls1View, lsX1, lsY, root);
        drawPicture(ls2View, lsX2, lsY, root);

        drawPicture(musicView, (double) WIDTH / 6 * 2 - calcNewWidth(118), HEIGHT / 5.0 * 2, root);
        drawPicture(soundView, (double) WIDTH / 6 * 2 - calcNewWidth(118), HEIGHT / 5.0 * 3, root);
        drawPicture(exitView, (double) WIDTH / 2 - 69, HEIGHT - 110, root);

        drawText(WIDTH / 6.0 * 4 - 78, HEIGHT / 5.0 * 2 + 60, String.valueOf(Main.musicVolume), 100, Color.rgb(183, 146, 56), root);
        drawText(WIDTH / 6.0 * 4 - 78, HEIGHT / 5.0 * 3 + 60, String.valueOf(Main.soundVolume), 100, Color.rgb(183, 146, 56), root);
    }

    /**
     * Zeichnet das Spiel
     */
    public void draw3D() {
        root.getChildren().clear(); // Leert die aktuelle Scene

        double screenHeight = Main.SCREEN_HEIGHT; // Dynamische Bildschirmhöhe
        double screenWidth = Main.GAME_WIDTH;  // Dynamische Bildschirmbreite

        // Berechnung der dynamischen Linienbreiten
        double lineWidth = 3 * (screenWidth / 1080);     // Proportionale Breite für Linien
        double backgroundLineWidth = 4 * (screenWidth / 1080); // Proportionale Breite für den Hintergrund

        drawRect(0, screenHeight / 2, screenWidth, screenHeight, Color.rgb(0, 0, 94), root); // Zeichnet den Himmel

        for (int i = 0; i < Main.RAY_NUMBER; i++) { // Iteriere durch jeden Ray
            blackLines[i] = new Line();
            lines[i] = new Line();

            if (Main.rays[i] != null) {
                Ray ray = Main.rays[i];
                double rayLength = ray.getLength();
                double x = i * (screenWidth / Main.RAY_NUMBER) + 1; // Proportionale X-Start-Koordinate

                // Berechnet die Höhe der Linie anhand der Ray-Länge
                double lineHeight = (64 * screenHeight) / rayLength;

                // Linie darf nicht größer als Bildschirmhöhe sein
                if (lineHeight > screenHeight) {
                    lineHeight = screenHeight;
                }

                double offset = (screenHeight - lineHeight) / 2; // Offset, um Linie zentral zu platzieren
                double endY = screenHeight - offset;            // End-Koordinate Y

                // Zeichne Linie mit angepasster Breite
                drawLine(blackLines[i], x, offset + 1, x, endY - 1, backgroundLineWidth, Color.BLACK, root); // Schwarzer Hintergrund
                drawLine(lines[i], x, offset, x, endY, lineWidth, ray.getColor(), root);                    // Linie im Vordergrund

                // Wenn ein Gegner im Sichtfeld ist
                if(ray.isHit()) {
                    enemyRay = ray;
                    enemyX = x;
                }
            }
        }

        // Gegner anzeigen, wenn getroffen
        if (enemyRay != null) {
            if (enemyRay.isHit()) {
                Main.lostSoul.show(enemyX, screenHeight * 0.5); // Gegner relativ zur Bildschirmhöhe zeichnen
                enemyRay = null;
            }
        }
        drawPicture(screenBackgroundView, 0, GAMEX, WIDTH, HEIGHT, HEIGHT, root);
    }

    /**
     * Zeichnet das HUD
     */
    public void drawHUD() {
        int score = player.getScore();
        int life = player.getLife();
        int xScore1 = WIDTH - 90,                                                                              //Speichert Koordinaten der Lebensanzeige
                xScore2 = WIDTH - 90,
                yScore1 = HEIGHT / 5 * 4 + 25,
                yScore2 = yScore1 - 3,
                size1 = 120,
                size2 = 110,
                xLife1 = WIDTH - 120,
                xLife2 = xLife1,
                yLife1 = HEIGHT / 5 + 10,
                yLife2 = yLife1 - 3;

        drawPicture(hudView, Main.GAME_WIDTH, 0, Main.SCREEN_HEIGHT, 150, 640, root);

        // Berechne die neue Höhe der Waffe basierend auf der aktuellen Bildschirmhöhe
        double newHeight;

        // Berechne die neue Breite basierend auf der neuen Höhe, um das Seitenverhältnis zu erhalten
        double newWeaponWidth;


        // Berechne die Positionen und rufe drawPicture auf
        // Rufe die drawPicture-Methode auf und setze die Bildposition und -größe


        if(Main.player.isFired()) {
            newHeight = 200 * (Main.SCREEN_HEIGHT / 640.0);
            newWeaponWidth = calcNewWidth(130, 200, newHeight);
            drawPicture(gunShotView, Main.GAME_WIDTH / 2.0 - newWeaponWidth / 2.0 - calcNewWidth(13), Main.SCREEN_HEIGHT - newHeight, newHeight, 130, 200, root);
            if(System.currentTimeMillis() - getMarker() > 300) {
                Main.player.setFired(false);
            }
        } else {
            newHeight = 142 * (Main.SCREEN_HEIGHT / 640.0);
            newWeaponWidth = calcNewWidth(130, 142, newHeight);
            drawPicture(gunView, Main.GAME_WIDTH / 2.0 - newWeaponWidth / 2.0 - calcNewWidth(13), Main.SCREEN_HEIGHT - newHeight, newHeight, 130, 142, root);
        }

        drawLine(new Line(), GAME_WIDTH / 2.0, HEIGHT / 2.0 - HEIGHT / 100.0 * 1.7, GAME_WIDTH / 2.0, HEIGHT / 2.0 + HEIGHT / 100.0 * 1.7, 5, Color.BLACK, root);
        drawLine(new Line(), GAME_WIDTH / 2.0 - GAME_WIDTH / 100.0 * 1, HEIGHT / 2.0, GAME_WIDTH / 2.0 + GAME_WIDTH / 100.0 * 1, HEIGHT / 2.0, 5, Color.BLACK, root);


        // -- Lebensanzeige --
        if(life == 100) {                                                                                       //Berechnet je nach Wert neue Koordinaten
            xLife1 = WIDTH - 120;
            xLife2 = xLife1;
            yLife1 = HEIGHT / 5 + 10;
            yLife2 = yLife1 - 3;
        } else if(life < 100 && life > 19) {
            xLife1 = WIDTH - 105;
            xLife2 = xLife1;
            yLife1 = HEIGHT / 5 + 10;
            yLife2 = yLife1 - 3;
        } else if(life < 20 && life > 9) {
            xLife1 = WIDTH - 100;
            xLife2 = xLife1;
            yLife1 = HEIGHT / 5 + 10;
            yLife2 = yLife1 - 3;
        } else if(life < 10) {
            xLife1 = WIDTH - 90;
            xLife2 = xLife1;
            yLife1 = HEIGHT / 5 + 10;
            yLife2 = yLife1 - 3;
        }
        drawText(xLife1, yLife1, String.valueOf(life), size1, Color.rgb(94, 18, 36), root);
        drawText(xLife2, yLife2, String.valueOf(life), size2, Color.rgb(255, 0, 0), root);

        // -- Punkteanzeige --
        if(player.getScore() >= 10000) {                                                                        //Berechnet je nach Wert neue Koordinaten
            drawText(GAME_WIDTH - 20, (double) HEIGHT / 5 * 4 + 7, "GODLIKE", 70, Color.rgb(94, 18, 36), root);
            drawText(GAME_WIDTH - 20 + 10, ((double) HEIGHT / 5 * 4) - 3 + 7, "GODLIKE", 60, Color.rgb(255, 0, 0), root);
        } else if(score <= 9) {
            xScore1 = WIDTH - 90;
            xScore2 = WIDTH - 90;
            size1 = 120;
            size2 = 110;
        } else if(score < 100) {
            xScore1 = WIDTH - 105;
            xScore2 = xScore1;
        } else if(score < 1000) {
            xScore1 = WIDTH - 130;
            xScore2 = xScore1;
        } else if(score < 2000) {
            xScore1 = WIDTH - 130;
            xScore2 = xScore1;
            size1 = 100;
            size2 = 90;
        } else if(score < 10000) {
            xScore1 = WIDTH - 140;
            xScore2 = xScore1;
            size1 = 100;
            size2 = 90;
        }

        drawText(xScore1, yScore1, String.valueOf(score), size1, Color.rgb(94, 18, 36), root);
        drawText(xScore2, yScore2, String.valueOf(score), size2, Color.rgb(255, 0, 0), root);

        if(player.getScore() >= 10000) {
            drawText(GAME_WIDTH - 20, (double) HEIGHT / 5 * 4 + 7, "GODLIKE", 70, Color.rgb(94, 18, 36), root);
            drawText(GAME_WIDTH - 20 + 10, ((double) HEIGHT / 5 * 4) - 3 + 7, "GODLIKE", 60, Color.rgb(255, 0, 0), root);
        }

        Main.face.idle();                                                                                       //Ruft Methode zum Animieren des Gesichts auf
    }

    /**
     * Zeichnet den GameOverScreen
     */
    public void drawGameOver() {
        root.getChildren().clear();

        drawPicture(backgroundView, 0, 0, root);
        drawPicture(screenBackgroundView, 0, GAMEX, WIDTH, HEIGHT, HEIGHT, root);
        drawRect(0, 0, WIDTH, Screen.getPrimary().getBounds().getHeight(), Color.rgb(0, 0, 0), 0.6, root);

        drawPicture(gameOverView, (double) WIDTH / 2 - 186, (double) HEIGHT / 5 * 1, root);
        drawPicture(scoreView, (double) WIDTH / 6 * 2 - 118, (double) HEIGHT / 4 * 2 - 60, root);

        double lsY = 0;
        double lsX1 = WIDTH * (1.0/3.0) - 100;
        double lsX2 = WIDTH * (2.0/3.0);

        if(Main.menuSelection == 1) {                                                                       //Je nach ausgewählter Option werden Koordinaten für Zeiger berechnet
            lsY = HEIGHT - 250 - 40; // 350 - 40;

            //drawPicture(ls1View, (double) WIDTH / 2 - 138 - 120, HEIGHT - 250 - 40, newHeight, 100, 107, root);
            //drawPicture(ls2View, (double) WIDTH / 2 - 138 + 20 + 279, HEIGHT - 250 - 40, newHeight, 100, 107, root);
        }

        if(Main.menuSelection == 2) {
            lsY = HEIGHT - 150 - 40;

            //drawPicture(ls1View, (double) WIDTH / 2 - 138 - 120, HEIGHT - 150 - 40, newHeight, 100, 107, root);
            //drawPicture(ls2View, (double) WIDTH / 2 - 138 + 20 + 279, HEIGHT - 150 - 40, newHeight, 100, 107, root);
        }

        drawPicture(ls1View, lsX1, lsY, root);
        drawPicture(ls2View, lsX2, lsY, root);

        drawPicture(restartView, (double) WIDTH / 2 - 140, HEIGHT - 250, root);
        drawPicture(creditsView, (double) WIDTH / 2 - 132, HEIGHT - 150, root);

        drawText((double) WIDTH / 6 * 4, (double) HEIGHT / 4 * 2, String.valueOf(player.getScore()), 100, Color.rgb(149, 0, 0), root);

        if(Main.showCredits) {                                                                              //Zeigt Credits an
            root.getChildren().clear();

            drawPicture(backgroundView, 0, 0, root);
            drawPicture(screenBackgroundView, 0, GAMEX, WIDTH, HEIGHT, HEIGHT, root);
            drawRect(0, 0, WIDTH, Screen.getPrimary().getBounds().getHeight(), Color.rgb(0, 0, 0), 0.6, root);
            drawPicture(creditsView, (double) WIDTH / 2 - 132, (double) HEIGHT / 4 * 1 - 60, root);
            drawPicture(creditView,WIDTH / 2.0 - calcNewWidth(1680, 552, HEIGHT - HEIGHT * (4.5/10.0)) / 2.0, HEIGHT * (3.5/10.0), HEIGHT - HEIGHT * (4.5/10.0), 1680, 552, root);
        }
    }

    /**
     * Spielt einen Sound ab
     * @param mediaPlayer MediaPlayer, der den Sound abspielt
     */
    public void playSound(MediaPlayer mediaPlayer){                                                           //Methode zum Abspielen von Musik/Sounds
        mediaPlayer.play();
    }

    /**
     * Pausiert einen Sound
     * @param mediaPlayer MediaPlayer, der den Sound pausiert
     */
    public void stopSound(MediaPlayer mediaPlayer){                                                           //Methode zum Stoppen von Musik/Sounds
        mediaPlayer.stop();
    }

    /**
     * Setzt die Lautstärke eines Sounds
     * @param mediaPlayer MediaPlayer, dessen Lautstärke gesetzt wird
     * @param percent Prozentuale Lautstärke (0-100)
     */
    public void setVolume(MediaPlayer mediaPlayer, double percent) {                                          //Manipuliert Lautstärke der Musik/Sounds
        mediaPlayer.setVolume(percent / 100);
    }

    /**
     * Setzt den Marker auf die aktuelle Zeit
     */
    public void setMark() {
        marker = System.currentTimeMillis();
    }

    /**
     * Gibt den Marker zurück
     * @return Marker
     */
    public long getMarker() {
        return marker;
    }

    //Folgende Methoden stellen Objekte je nach Parameter dar oder sind Überladungen bereits vorhandener Methoden

    /**
     * Zeichnet ein Rechteck
     * @param x x-Koordinate
     * @param y y-Koordinate
     * @param width Breite
     * @param height Höhe
     * @param color Farbe
     * @param group Gruppe, in der das Rechteck gezeichnet wird
     */
    public void drawRect(double x, double y, double width, double height, Color color, Group group) {
        Rectangle rect = new Rectangle();
        rect.setX(x);
        rect.setY(y);
        rect.setWidth(width);
        rect.setHeight(height);
        rect.setFill(color);

        group.getChildren().add(rect);
    }

    /**
     * Zeichnet ein Rechteck mit Opazität
     * @param x x-Koordinate
     * @param y y-Koordinate
     * @param width Breite
     * @param height Höhe
     * @param color Farbe
     * @param opacity Transparenzwert
     * @param group Gruppe, in der das Rechteck gezeichnet wird
     */
    public void drawRect(double x, double y, double width, double height, Color color, double opacity, Group group) {
        Rectangle rect = new Rectangle();
        rect.setX(x);
        rect.setY(y);
        rect.setWidth(width);
        rect.setHeight(height);
        rect.setFill(color);
        rect.setOpacity(opacity);

        group.getChildren().add(rect);
    }

    /**
     * Zeichnet eine Linie
     * @param line Linie, die gezeichnet wird
     * @param startX x-Koordinate des Startpunkts
     * @param startY y-Koordinate des Startpunkts
     * @param endX x-Koordinate des Endpunkts
     * @param endY y-Koordinate des Endpunkts
     * @param width Breite der Linie
     * @param color Farbe der Linie
     * @param group Gruppe, in der die Linie gezeichnet wird
     */
    public void drawLine (Line line, double startX, double startY, double endX, double endY, double width, Color color, Group group){
        line.setStartX(startX);
        line.setStartY(startY);
        line.setEndX(endX);
        line.setEndY(endY);
        line.setStrokeWidth(width);
        line.setStroke(color);
        group.getChildren().add(line);
    }

    /**
     * Zeichnet einen Text
     * @param x x-Koordinate
     * @param y y-Koordinate
     * @param string Text, der gezeichnet wird
     * @param size Schriftgröße
     * @param color Farbe des Textes
     * @param group Gruppe, in der der Text gezeichnet wird
     */
    public void drawText(double x, double y, String string, int size, Color color, Group group) {
        Text texts = new Text();
        texts.setX(x);
        texts.setY(y);
        texts.setText(string);
        texts.setFont(Font.loadFont("file:src/resource/Doom2016Text-GOlBq.ttf", size));
        texts.setFill(color);

        group.getChildren().add(texts);
    }

    /**
     * Zeichnet ein Bild
     * @param view ImageView, das gezeichnet wird
     * @param x x-Koordinate
     * @param y y-Koordinate
     * @param group Gruppe, in der das Bild gezeichnet wird
     */
    public void drawPicture(ImageView view, double x, double y, Group group) {
        view.setX(x);
        view.setY(y);

        view.setPreserveRatio(true);

        group.getChildren().add(view);
    }

    /**
     * Zeichnet ein Bild und skaliert es
     * @param view ImageView, das gezeichnet wird
     * @param x x-Koordinate
     * @param y y-Koordinate
     * @param newHeight Neue Höhe
     * @param ogX Originalbreite
     * @param ogY Originalhöhe
     * @param group Gruppe, in der das Bild gezeichnet wird
     */
    public void drawPicture(ImageView view, double x, double y, double newHeight, double ogX, double ogY, Group group) {
        view.setX(x);
        view.setY(y);

        // Berechne die Breite basierend auf der neuen Höhe und setze die Breite
        view.setFitWidth(calcNewWidth(ogX, ogY, newHeight)); // Skalierung der Breite entsprechend der neuen Höhe
        view.setFitHeight(newHeight); // Setzt die Höhe des Bildes

        group.getChildren().add(view);
    }

    /**
     * Berechnet die neue Breite basierend auf der Höhe
     * @param width bereits vorhandene Breite
     * @param height bereits vorhandene Höhe
     * @param newHeight Neue Höhe
     * @return neue Breite
     */
    public static int calcNewWidth(double width, double height, double newHeight) {
        return (int) (width * (newHeight / height));
    }

    /**
     * Berechnet die neue Höhe basierend auf der Breite
     * @param height bereits vorhandene Höhe
     * @param width bereits vorhandene Breite
     * @param newWidth Neue Breite
     * @return neue Höhe
     */
    public static int calcNewHeight(double height, double width, double newWidth) {
        return (int) (height * (newWidth / width));
    }

    /**
     * Berechnet die neue Breite basierend auf der Breite
     * @param width bereits vorhandene Breite
     * @return neue Breite
     */
    public static int calcNewWidth(double width) {
        return (int) (width * (Main.SCREEN_WIDTH / 1205.0));
    }

    /**
     * Berechnet die neue Höhe basierend auf der Höhe
     * @param height bereits vorhandene Höhe
     * @return neue Höhe
     */
    public static int calcNewHeight(double height) {
        return (int) (height * (Main.SCREEN_HEIGHT / 640.0));
    }

    /**
     * Zeichnet einen Gegner
     * @param file Dateipfad des Bildes
     * @param x x-Koordinate auf dem Bildschirm
     * @param y y-Koordinate auf dem Bildschirm
     * @param distance Distanz zum Spieler
     * @param group Gruppe, in der der Gegner gezeichnet wird
     */
    public void drawEnemy(String file, double x, double y, double distance, Group group) {
        Image image = new Image(file);
        ImageView view = new ImageView(image);


        double imageHeight = (64 * HEIGHT) / distance;
        view.setFitHeight(imageHeight);
        view.setFitWidth(imageHeight);
        view.setX(x - (imageHeight / 2));
        view.setY(y - (imageHeight / 2));
        group.getChildren().add(view);
    }

    /**
     * Gibt die Gruppe zurück
     * @return Gruppe
     */
    public Group getRoot() {
        return root;
    }
}