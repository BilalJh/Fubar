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
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Display {

    // !!! Images und deren ImageViews !!!
    // !!! Chronologisch nach Aufruf   !!!
    private Image hud;
    private ImageView hudView;
    private Image background;
    private ImageView backgroundView;
    private Image gameOver;
    private ImageView gameOverView;
    private Image score;
    private ImageView scoreView;
    private Image exit;
    private ImageView exitView;
    private Image credits;
    private ImageView creditsView;
    private Image ls1;
    private ImageView ls1View;
    private Image ls2;
    private ImageView ls2View;
    private Image credit;
    private ImageView creditView;
    private Image startBackground;
    private ImageView startBackgroundView;
    private Image fubar;
    private ImageView fubarView;
    private Image start;
    private ImageView startView;
    private Image settings;
    private ImageView settingsView;
    private Image musicImage;
    private ImageView musicView;
    private Image sound;
    private ImageView soundView;
    private Image gun;
    private ImageView gunView;


    private Stage primaryStage, mapStage;
    private Group root, mapRoot;
    private Scene primaryScene, mapScene;

    private Random randomizer;
    private Player player;
    private LostSoul lostSoul;
    private Line playerLine, enemyLine;
    private Line[] lines;
    private Line[] blackLines;
    public String[] soundFiles, musicFiles;
    private Image lostSoulImage;
    private ImageView lostSoulView;
    private Text lifeOutline, lifeInline, scoreOutline, scoreInline;

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


    private Ray enemyRay;

    private double enemyX;

    private final int WIDTH = Main.SCREEN_WIDTH;
    private final int HEIGHT = Main.SCREEN_HEIGHT;
    private final int GAME_WIDTH = Main.GAME_WIDTH;


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
                "src/resource/Soundtrack/Music/AtDoomsGate.wav",
                "src/resource/Soundtrack/Music/TheOnlyThingTheyFearIsYou.wav",
                "src/resource/Soundtrack/Music/OnTheHunt.wav"
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
        musicPlayer.setAutoPlay(true);

        // !!! Images und deren ImageViews !!!
        // !!! Chronologisch nach Aufruf   !!!
        {
            hud = new Image("file:src/resource/Hud.png");
            hudView = new ImageView(hud);
            background = new Image("file:src/resource/GameOver/Background.png");
            backgroundView = new ImageView(background);
            gameOver = new Image("file:src/resource/GameOver/GameOver.png");
            gameOverView = new ImageView(gameOver);
            score = new Image("file:src/resource/GameOver/Score.png");
            scoreView = new ImageView(score);
            exit = new Image("file:src/resource/GameOver/Exit.png");
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
        }

        // -- Hauptfenster --
        primaryStage = new Stage();
        root = new Group();
        primaryScene = new Scene(root, Color.rgb(91,0,0));

        primaryStage.getIcons().add(lostSoulImage);
        primaryStage.setTitle("Fubar! - PreAlpha v0.2");
        primaryStage.setWidth(WIDTH);
        primaryStage.setHeight(HEIGHT);
        primaryStage.setResizable(false);
//        primaryStage.setX(Screen.getPrimary().getBounds().getWidth() / 2);
//        primaryStage.setY(Screen.getPrimary().getBounds().getWidth() / 2);
        primaryStage.initStyle(StageStyle.UNDECORATED);

        primaryStage.setScene(primaryScene);
        primaryStage.show();

        startUpdateTimer();

    }

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

    public void draw() {
        if(Main.gameState == 1) {
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

    public void draw3D() {
        root.getChildren().clear();

        drawRect(0, (double) HEIGHT / 2, GAME_WIDTH, HEIGHT, Color.rgb(0,0,94), root);
        for(int i = 0; i < Main.RAY_NUMBER; i++) {
            blackLines[i] = new Line();
            lines[i] = new Line();
            if(Main.rays[i] != null) {
                Ray ray = Main.rays[i];
                double rayLength = ray.getLength();
                double x = i * 3 + 1;

                // Höhe der Linie relativ zur Länge des Strahls berechnen
                double lineHeight = (64 * HEIGHT) / rayLength;

                // Sicherstellen, dass die Linie nicht über die Höhe des Bildschirms hinausgeht
                if(lineHeight > HEIGHT) {
                    lineHeight = HEIGHT;
                }

                // Vertikalen Offset berechnen, um die Linie zentriert auf dem Bildschirm zu positionieren
                double offset = (HEIGHT - lineHeight) / 2;

                // Start- und Endpunkte der Linie berechnen
                double endY = HEIGHT - offset;

                // Linie zeichnen
                drawLine(blackLines[i], x, offset + 1, x, endY - 1, 4, Color.BLACK, root);
                drawLine(lines[i], x, offset, x, endY, 3, ray.getColor(), root);

                if(ray.isHit()) {
                    enemyRay = ray;
                    enemyX = x;
                }
            }
        }
        if(enemyRay != null) {
            if(enemyRay.isHit()) {
                Main.lostSoul.show(enemyX, 300);
                enemyRay = null;
            }
        }
    }

    public void drawHUD() {
        int score = player.getScore();
        int life = player.getLife();
        int xScore1 = WIDTH - 90,
                xScore2 = WIDTH - 90,
                yScore1 = HEIGHT / 5 * 4 + 25,
                yScore2 = yScore1 - 3,
                size1 = 120,
                size2 = 110,
                xLife1 = WIDTH - 120,
                xLife2 = xLife1,
                yLife1 = HEIGHT / 5 + 10,
                yLife2 = yLife1 - 3;



        drawPicture(hudView, WIDTH - 150, 0, root);
        drawPicture(gunView, (double) GAME_WIDTH / 2 - 65, HEIGHT - 170, root);

        // -- Lebensanzeige --
        if(life == 100) {
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
        if(player.getScore() >= 10000) {
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

        Main.face.idle();
    }

    public void drawGameOver() {
        root.getChildren().clear();

        drawPicture(backgroundView, 0, 0, root);

        drawPicture(gameOverView, (double) WIDTH / 2 - 186, (double) HEIGHT / 5 * 1, root);
        drawPicture(scoreView, (double) WIDTH / 6 * 2 - 118, (double) HEIGHT / 4 * 2 - 60, root);

        if(Main.menuSelection == 1) {
            drawPicture(ls1View, (double) WIDTH / 2 - 138 - 120, HEIGHT - 250 - 40, root);
            drawPicture(ls2View, (double) WIDTH / 2 - 138 + 20 + 279, HEIGHT - 250 - 40, root);
        }

        if(Main.menuSelection == 2) {
            drawPicture(ls1View, (double) WIDTH / 2 - 138 - 120, HEIGHT - 150 - 40, root);
            drawPicture(ls2View, (double) WIDTH / 2 - 138 + 20 + 279, HEIGHT - 150 - 40, root);
        }

        drawPicture(exitView, (double) WIDTH / 2 - 69, HEIGHT - 250, root);
        drawPicture(creditsView, (double) WIDTH / 2 - 132, HEIGHT - 150, root);

        drawText((double) WIDTH / 6 * 4, (double) HEIGHT / 4 * 2, String.valueOf(player.getScore()), 100, Color.rgb(149, 0, 0), root);

        if(Main.showCredits) {
            root.getChildren().clear();



            drawPicture(backgroundView, 0, 0, root);
            drawPicture(creditsView, (double) WIDTH / 2 - 132, 50, root);
            drawPicture(creditView, (double) WIDTH / 2 - 595, (double) HEIGHT / 2 - 192, root);
        }
    }

    public void drawMenu() {
        root.getChildren().clear();


        drawPicture(backgroundView,0, 0, root);
        drawRect(0, 0, WIDTH, HEIGHT, Color.rgb(0, 0, 0), 0.6, root);
        drawPicture(fubarView, (double) WIDTH / 2 - 168, (double) HEIGHT / 4 * 1 - 60, root);

        if(Main.menuSelection == 1) {
            drawPicture(ls1View, (double) WIDTH / 2 - 138 - 120, HEIGHT - 350 - 40, root);
            drawPicture(ls2View, (double) WIDTH / 2 - 138 + 20 + 279, HEIGHT - 350 - 40, root);
        }

        if(Main.menuSelection == 2) {
            drawPicture(ls1View, (double) WIDTH / 2 - 138 - 120, HEIGHT - 270 - 40, root);
            drawPicture(ls2View, (double) WIDTH / 2 - 138 + 20 + 279, HEIGHT - 270 - 40, root);
        }

        if(Main.menuSelection == 3) {
            drawPicture(ls1View, (double) WIDTH / 2 - 138 - 120, HEIGHT - 190 - 40, root);
            drawPicture(ls2View, (double) WIDTH / 2 - 138 + 20 + 279, HEIGHT - 190 - 40, root);
        }

        if(Main.menuSelection == 4) {
            drawPicture(ls1View, (double) WIDTH / 2 - 138 - 120, HEIGHT - 110 - 40, root);
            drawPicture(ls2View, (double) WIDTH / 2 - 138 + 20 + 279, HEIGHT - 110 - 40, root);
        }

        drawPicture(startView, (double) WIDTH / 2 - 99, HEIGHT - 350, root);
        drawPicture(settingsView, (double) WIDTH / 2 - 150, HEIGHT - 270, root);
        drawPicture(creditsView, (double) WIDTH / 2 - 132, HEIGHT - 190, root);
        drawPicture(exitView, (double) WIDTH / 2 - 69, HEIGHT - 110, root);

        if(Main.showCredits) {
            root.getChildren().clear();

            drawPicture(backgroundView, 0, 0, root);
            drawRect(0, 0, WIDTH, HEIGHT, Color.rgb(0, 0, 0), 0.6, root);
            drawPicture(creditsView, (double) WIDTH / 2 - 132, 50, root);
            drawPicture(creditView, (double) WIDTH / 2 - 595, (double) HEIGHT / 2 - 192, root);
        }
    }

    public void drawSettings() {
        root.getChildren().clear();

        drawPicture(backgroundView, 0, 0, root);
        drawRect(0, 0, WIDTH, HEIGHT, Color.rgb(0, 0, 0), 0.6, root);
        drawPicture(settingsView, (double) WIDTH / 2 - 150, 100, root);

        drawPicture(musicView, (double) WIDTH / 6 * 2 - 118, (double) HEIGHT / 4 * 2 - 100, root);
        drawPicture(soundView, (double) WIDTH / 6 * 2 - 118, (double) HEIGHT / 4 * 2 + 40, root);
        drawPicture(exitView, (double) WIDTH / 2 - 69, HEIGHT - 150, root);


        if(Main.menuSelection == 1) {
            drawPicture(ls1View, (double) WIDTH / 6 * 4 - 78 - 120, (double) HEIGHT / 4 * 2 - 100 - 40, root);
            drawPicture(ls2View, (double) WIDTH / 6 * 4 - 78 + 75, (double) HEIGHT / 4 * 2 - 100 - 40, root);
        }

        if(Main.menuSelection == 2) {
            drawPicture(ls1View, (double) WIDTH / 6 * 4 - 78 - 120, (double) HEIGHT / 4 * 2, root);
            drawPicture(ls2View, (double) WIDTH / 6 * 4 - 78 + 75, (double) HEIGHT / 4 * 2, root);
        }

        if(Main.menuSelection == 3) {
            drawPicture(ls1View, (double) WIDTH / 2 - 138 - 40, HEIGHT - 150 - 40, root);
            drawPicture(ls2View, (double) WIDTH / 2 - 138 + 20 + 199, HEIGHT - 150 - 40, root);
        }

        drawText((double) WIDTH / 6 * 4 - 78, (double) HEIGHT / 4 * 2 - 40, String.valueOf(Main.musicVolume), 100, Color.rgb(183, 146, 56), root);
        drawText((double) WIDTH / 6 * 4 - 78, (double) HEIGHT / 4 * 2 + 100, String.valueOf(Main.soundVolume), 100, Color.rgb(183, 146, 56), root);
    }

    public void playSound(MediaPlayer mediaPlayer){
        mediaPlayer.play();
    }
    public void stopSound(MediaPlayer mediaPlayer){
        mediaPlayer.stop();
    }
    public void setVolume(MediaPlayer mediaPlayer, double percent) {
        mediaPlayer.setVolume(percent / 100);
    }


    public void drawRect(double x, double y, double width, double height, int state, Group group) {
        Rectangle rect = new Rectangle();
        rect.setX(x);
        rect.setY(y);
        rect.setWidth(width);
        rect.setHeight(height);
        switch(state) {
            case 1:
                rect.setFill(Color.rgb(159,0,0));
                break;
            case 2:
                rect.setFill(Color.rgb(47,47,47));
                break;
            case 3:
                rect.setFill(Color.rgb(107,71,39));
                break;
            case 4:
                rect.setFill(Color.rgb(127, 0, 0));
                break;
            case 5:
                rect.setFill(Color.rgb(135,67,7));
                break;
            case 6:
                rect.setFill(Color.rgb(139,139,139));
                break;
            default:
                rect.setFill(Color.BLACK);
                break;
        }
        group.getChildren().add(rect);
    }

    public void drawRect(double x, double y, double width, double height, Color color, Group group) {
        Rectangle rect = new Rectangle();
        rect.setX(x);
        rect.setY(y);
        rect.setWidth(width);
        rect.setHeight(height);
        rect.setFill(color);

        group.getChildren().add(rect);
    }

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

    public void drawLine (Line line, double startX, double startY, double endX, double endY, double width, Color color, Group group){
        line.setStartX(startX);
        line.setStartY(startY);
        line.setEndX(endX);
        line.setEndY(endY);
        line.setStrokeWidth(width);
        line.setStroke(color);
        group.getChildren().add(line);
    }

    public void drawText(double x, double y, String string, int size, Color color, Group group) {
        Text texts = new Text();
        texts.setX(x);
        texts.setY(y);
        texts.setText(string);
        texts.setFont(Font.loadFont("file:src/resource/Doom2016Text-GOlBq.ttf", size));
        texts.setFill(color);

        group.getChildren().add(texts);
    }

    public void drawPicture(ImageView view, double x, double y, Group group) {
        view.setX(x);
        view.setY(y);

        group.getChildren().add(view);
    }

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

    public Group getRoot() {
        return root;
    }
    public Scene getPrimaryScene() {
        return primaryScene;
    }
}