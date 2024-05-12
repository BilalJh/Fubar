package net.bilaljh.fubar;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Screen;

import java.util.Timer;
import java.util.TimerTask;

public class Display {

    Stage primaryStage, mapStage;
    Group root, mapRoot;
    Scene primaryScene, mapScene;

    Player player;
    LostSoul lostSoul;
    Line playerLine, enemyLine;
    Line[] lines;
    Line[] blackLines;
    Rectangle playerRect, enemyRect, floorRect, damageRect, menuSelectionRestartRect, menuSelectionExitRect;
    Rectangle[] rectangles;
    Text lifeOutline, lifeInline, scoreOutline, scoreInline;
    Image lostSoulImage;

    Ray enemyRay;

    double enemyX;

    private final int WIDTH = Main.SCREEN_WIDTH;
    private final int HEIGHT = Main.SCREEN_HEIGHT;
    private final int GAME_WIDTH = Main.GAME_WIDTH;


    public Display() {
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

        // -- Hauptfenster --
        primaryStage = new Stage();
        root = new Group();
        primaryScene = new Scene(root, Color.rgb(91,0,0));

        primaryStage.getIcons().add(lostSoulImage);
        primaryStage.setTitle("Fubar! - PreAlpha v0.2");
        primaryStage.setWidth(WIDTH);
        primaryStage.setHeight(HEIGHT);
        primaryStage.setResizable(false);
        primaryStage.setX(Screen.getPrimary().getBounds().getWidth() / 2);
        primaryStage.setY(Screen.getPrimary().getBounds().getWidth() / 2);
        //primaryStage.initStyle(StageStyle.UNDECORATED);


        // ---- 2D Ansicht ----
        if(Main.DEVELOPER_MODE) {
            mapStage = new Stage();
            mapRoot = new Group();
            mapScene = new Scene(mapRoot, Color.BLACK);

            mapStage.getIcons().add(lostSoulImage);
            mapStage.setTitle("Fubar! - PreAlpha v0.3 - Map");
            mapStage.setWidth(1920);
            mapStage.setHeight(1080);
            mapStage.setResizable(true);

            mapStage.setScene(mapScene);
            mapStage.show();
        }

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
                    if(!Main.gameOver) {
                        Main.engine.castRays();
                        Main.lostSoul.idle();
                        draw();
                    } else {
                        gameOver();
                    }
                });
            }
        }, 0, 16); // 16 Millisekunden (~60 FPS)
    }

    public void draw() {
        draw2D();
        draw3D();
        drawHUD();
    }

    public void draw2D() {
        mapRoot.getChildren().clear();
        double posX = 0;
        double posY = 0;

        for(int i = 0; i < Main.map.getMapBorderX(); i++) {
            for(int ii = 0; ii < Main.map.getMapBorderY(); ii++) {
                drawRect(posX, posY, 64, 64, Main.map.map[i][ii], mapRoot);
                posY += 64;
            }
            posX += 64;
            posY = 0;
        }

        drawRect(player.getPosX() - 10, player.getPosY() - 10, 20, 20, 6, mapRoot);
        drawRect(lostSoul.getPosX() - 10, lostSoul.getPosY() - 10, 20, 20, 6, mapRoot);

        for(int i = 0; i < Main.RAY_NUMBER; i++) {
            lines[i] = new Line();
            Color rayColor;
            if(Main.rays[i] != null) {
                if(Main.rays[i].isHit()) {
                    rayColor = Color.rgb(0, 255, 0);
                } else {
                    rayColor = Main.rays[i].getColor();
                }
                drawLine(lines[i], Main.player.getPosX(), Main.player.getPosY(), Main.rays[i].getEndX(), Main.rays[i].getEndY(), 1, rayColor, mapRoot);
            }
        }
        drawLine(playerLine, Main.player.getPosX(), Main.player.getPosY(), Main.player.getPosX() + 50 * Math.cos(Main.player.getAngle()), Main.player.getPosY() + 50 * Math.sin(Main.player.getAngle()), 1, Color.RED, mapRoot);
        drawLine(enemyLine, Main.lostSoul.getPosX(), Main.lostSoul.getPosY(), Main.lostSoul.getPosX() + 50 * Math.cos(Main.lostSoul.getAngle()), Main.lostSoul.getPosY() + 50 * Math.sin(Main.lostSoul.getAngle()), 1, Color.RED, mapRoot);
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
                double startX = i * 3 + 1;
                double endX = startX;

                // Höhe der Linie relativ zur Länge des Strahls berechnen
                double lineHeight = (64 * HEIGHT) / rayLength;

                // Sicherstellen, dass die Linie nicht über die Höhe des Bildschirms hinausgeht
                if(lineHeight > HEIGHT) {
                    lineHeight = HEIGHT;
                }

                // Vertikalen Offset berechnen, um die Linie zentriert auf dem Bildschirm zu positionieren
                double offset = (HEIGHT - lineHeight) / 2;

                // Start- und Endpunkte der Linie berechnen
                double startY = offset;
                double endY = HEIGHT - offset;

                // Linie zeichnen
                drawLine(blackLines[i], startX, startY + 1, endX, endY - 1, 4, Color.BLACK, root);
                drawLine(lines[i], startX, startY, endX, endY, 3, ray.getColor(), root);

                if(ray.isHit()) {
                    enemyRay = ray;
                    enemyX = startX;
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

        drawPicture("file:src/resource/Hud.png", WIDTH - 150, 0, root);

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
            drawText(GAME_WIDTH - 20, HEIGHT / 5 * 4 + 7, "GODLIKE", 70, Color.rgb(94, 18, 36), root);
            drawText(GAME_WIDTH - 20 + 10, (HEIGHT / 5 * 4) - 3 + 7, "GODLIKE", 60, Color.rgb(255, 0, 0), root);
        } else if(score <= 9) {
            xScore1 = WIDTH - 90;
            xScore2 = WIDTH - 90;
            size1 = 120;
            size2 = 110;
        } else if(score < 100) {
            xScore1 = WIDTH - 105;
            xScore2 = xScore1;
            size1 = 120;
            size2 = 110;
        } else if(score < 1000) {
            xScore1 = WIDTH - 130;
            xScore2 = xScore1;
            size1 = 120;
            size2 = 110;
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
            drawText(GAME_WIDTH - 20, HEIGHT / 5 * 4 + 7, "GODLIKE", 70, Color.rgb(94, 18, 36), root);
            drawText(GAME_WIDTH - 20 + 10, (HEIGHT / 5 * 4) - 3 + 7, "GODLIKE", 60, Color.rgb(255, 0, 0), root);
        }

        Main.face.idle();

        // -- Waffe --
        Main.gun.idle();
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
        rect = null;
    }

    public void drawRect(double x, double y, double width, double height, Color color, Group group) {
        Rectangle rect = new Rectangle();
        rect.setX(x);
        rect.setY(y);
        rect.setWidth(width);
        rect.setHeight(height);
        rect.setFill(color);

        group.getChildren().add(rect);
        rect = null;
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
        rect = null;
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
        texts = null;
    }

    public void drawPicture(String file, double x, double y, Group group) {
        Image image = new Image(file);
        ImageView view = new ImageView(image);
        view.setX(x);
        view.setY(y);

        group.getChildren().add(view);
        image = null;
        view = null;
    }

    public void drawGun(String file) {
        drawPicture(file, (double) GAME_WIDTH / 2 - 65, HEIGHT - 170, root);
    }

    public void gameOver() {
        int score = Main.player.getScore();

        root.getChildren().clear();
        drawPicture("file:src/resource/GameOver/Background.png", 0, 0, root);

        drawPicture("file:src/resource/GameOver/GameOver.png", WIDTH / 2 - 186, HEIGHT / 5 * 1, root);
        drawPicture("file:src/resource/GameOver/Score.png", WIDTH / 6 * 2 - 118, HEIGHT / 4 * 2 - 60, root);

        if(Main.menuSelection == 1) {
            drawPicture("file:src/resource/LS00.png", WIDTH / 2 - 138 - 120, HEIGHT - 250 - 40, root);
            drawPicture("file:src/resource/LS00.png", WIDTH / 2 - 138 + 20 + 279, HEIGHT - 250 - 40, root);
        }

        if(Main.menuSelection == 2) {
            drawPicture("file:src/resource/LS00.png", WIDTH / 2 - 138 - 120, HEIGHT - 150 - 40, root);
            drawPicture("file:src/resource/LS00.png", WIDTH / 2 - 138 + 20 + 279, HEIGHT - 150 - 40, root);
        }

        drawPicture("file:src/resource/GameOver/Exit.png", WIDTH / 2 - 69, HEIGHT - 250, root);
        drawPicture("file:src/resource/GameOver/Credits.png", WIDTH / 2 - 132, HEIGHT - 150, root);

        drawText(WIDTH / 6 * 4, HEIGHT / 4 * 2, String.valueOf(player.getScore()), 100, Color.rgb(149, 0, 0), root);

        if(Main.showCredits) {
            root.getChildren().clear();
            drawPicture("file:src/resource/GameOver/Background.png", 0, 0, root);
            drawPicture("file:src/resource/GameOver/Credit.png", WIDTH / 2 - 600, HEIGHT / 2 - 192, root);
        }
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

    public int getGAME_WIDTH() {
        return GAME_WIDTH;
    }
    public int getHEIGHT() {
        return HEIGHT;
    }
    public int getWIDTH() {
        return WIDTH;
    }
}