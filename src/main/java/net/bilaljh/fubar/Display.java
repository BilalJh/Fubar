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
    Line playerLine;
    Line[] lines;
    Line[] blackLines;
    Rectangle playerRect, enemyRect, floor;
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
        playerRect = new Rectangle();
        enemyRect = new Rectangle();
        floor = new Rectangle();
        playerLine = new Line();
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
            mapStage.setWidth(GAME_WIDTH);
            mapStage.setHeight(HEIGHT);
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
                    Main.engine.castRays();
                    draw();
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

        rectangles = new Rectangle[1024];
        mapRoot.getChildren().clear();
        double posX = 0;
        double posY = 0;
        int counter = 0;

        for(int i = 0; i < Main.map.getMapBorderX(); i++) {
            for(int ii = 0; ii < Main.map.getMapBorderY(); ii++) {
                rectangles[counter] = new Rectangle();
                drawRect(rectangles[counter], posX, posY, 64, 64, Main.map.map[i][ii], mapRoot);
                posY += 64;
                counter++;
            }
            posX += 64;
            posY = 0;
        }

        drawRect(playerRect, player.getPosX() - 10, player.getPosY() - 10, 20, 20, 6, mapRoot);
        drawRect(enemyRect, lostSoul.getPosX() - 10, lostSoul.getPosY() - 10, 20, 20, 6, mapRoot);

        for(int i = 0; i < Main.RAY_NUMBER; i++) {
            lines[i] = new Line();
            Color rayColor;
            if(Main.rays[i].isHit()) {
                rayColor = Color.rgb(0, 255, 0);
            } else {
                rayColor = Main.rays[i].getColor();
            }
            drawLine(lines[i], Main.player.getPosX(), Main.player.getPosY(), Main.rays[i].getEndX(), Main.rays[i].getEndY(), 1, rayColor, mapRoot);
        }
        drawLine(playerLine, Main.player.getPosX(), Main.player.getPosY(), Main.player.getPosX() + 50 * Math.cos(Main.player.getAngle()), Main.player.getPosY() + 50 * Math.sin(Main.player.getAngle()), 1, Color.RED, mapRoot);
    }

    public void draw3D() {
        root.getChildren().clear();

        drawRect(floor, 0, (double) HEIGHT / 2, GAME_WIDTH, HEIGHT, Color.rgb(0,0,94), root);
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
                Main.lostSoul.idle(enemyX, 300);
                enemyRay = null;
            }
        }
    }

    public void drawHUD() {
        drawPicture("file:src/resource/Hud.png", WIDTH - 150, 0, root);

        // -- Lebensanzeige --
        if(player.getLife() == 100) {
            drawText(lifeOutline, WIDTH - 120, HEIGHT / 5, String.valueOf(Main.player.getLife()), 120, Color.rgb(94, 18, 36), root);
            drawText(lifeInline, WIDTH - 120, (HEIGHT / 5) - 3, String.valueOf(Main.player.getLife()), 110, Color.rgb(255, 0, 0), root);
        } else if(player.getLife() < 10 && player.getLife() >= 0) {
            drawText(lifeOutline, WIDTH - 100, HEIGHT / 5, String.valueOf(Main.player.getLife()), 120, Color.rgb(94, 18, 36), root);
            drawText(lifeInline, WIDTH - 100, (HEIGHT / 5) - 3, String.valueOf(Main.player.getLife()), 110, Color.rgb(255, 0, 0), root);
        } else {
            drawText(lifeOutline, WIDTH - 120, HEIGHT / 5, String.valueOf(Main.player.getLife()), 120, Color.rgb(94, 18, 36), root);
            drawText(lifeInline, WIDTH - 120, (HEIGHT / 5) - 3, String.valueOf(Main.player.getLife()), 110, Color.rgb(255, 0, 0), root);
        }

        // -- Punkteanzeige --
        if(player.getScore() <= 9) {
            drawText(scoreOutline, GAME_WIDTH + 55, HEIGHT / 5 * 4 + 17, String.valueOf(Main.player.getScore()), 120, Color.rgb(94, 18, 36), root);
            drawText(scoreInline, GAME_WIDTH + 55, (HEIGHT / 5 * 4) - 3 + 17, String.valueOf(Main.player.getScore()), 110, Color.rgb(255, 0, 0), root);
        } else if(player.getScore() < 100) {
            drawText(scoreOutline, GAME_WIDTH + 40, HEIGHT / 5 * 4 + 17, String.valueOf(Main.player.getScore()), 120, Color.rgb(94, 18, 36), root);
            drawText(scoreInline, GAME_WIDTH + 40, (HEIGHT / 5 * 4) - 3 + 17, String.valueOf(Main.player.getScore()), 110, Color.rgb(255, 0, 0), root);
        } else if(player.getScore() < 1000 ) {
            drawText(scoreOutline, GAME_WIDTH + 25, HEIGHT / 5 * 4 + 17, String.valueOf(Main.player.getScore()), 120, Color.rgb(94, 18, 36), root);
            drawText(scoreInline, GAME_WIDTH + 25, (HEIGHT / 5 * 4) - 3 + 17, String.valueOf(Main.player.getScore()), 110, Color.rgb(255, 0, 0), root);
        } else if(player.getScore() > 999) {
            drawText(scoreOutline, GAME_WIDTH + 20, HEIGHT / 5 * 4 + 7, String.valueOf(Main.player.getScore()), 100, Color.rgb(94, 18, 36), root);
            drawText(scoreInline, GAME_WIDTH + 20, (HEIGHT / 5 * 4) - 3 + 7, String.valueOf(Main.player.getScore()), 90, Color.rgb(255, 0, 0), root);
        }

        Main.face.idle();

        // -- Waffe --
        Main.gun.idle();
    }

    public void drawRect(Rectangle rect, double x, double y, double width, double height, int color, Group group) {
        rect.setX(x);
        rect.setY(y);
        rect.setWidth(width);
        rect.setHeight(height);
        switch(color) {
            case 1:
                rect.setFill(Color.WHITE);
                break;
            case 2:
                rect.setFill(Color.RED);
                break;
            case 3:
                rect.setFill(Color.BLUE);
                break;
            case 4:
                rect.setFill(Color.GREEN);
                break;
            case 5:
                rect.setFill(Color.YELLOW);
                break;
            case 6:
                rect.setFill(Color.PURPLE);
                break;
            case 7:
                rect.setFill(Color.ORANGE);
                break;
            default:
                rect.setFill(Color.BLACK);
                break;
        }
        group.getChildren().add(rect);
    }

    public void drawRect(Rectangle rect, double x, double y, double width, double height, Color color, Group group) {
        rect.setX(x);
        rect.setY(y);
        rect.setWidth(width);
        rect.setHeight(height);
        rect.setFill(color);
        group.getChildren().add(rect);
    }

    public void drawLine (Line line,double startX, double startY, double endX, double endY, double width, Color color, Group group){
    line.setStartX(startX);
    line.setStartY(startY);
    line.setEndX(endX);
    line.setEndY(endY);
    line.setStrokeWidth(width);
    line.setStroke(color);
    group.getChildren().add(line);
    }

    public void drawText(Text texts, double x, double y, String string, int size, Color color, Group group) {
        texts.setX(x);
        texts.setY(y);
        texts.setText(string);
        texts.setFont(Font.loadFont("file:src/resource/Doom2016Text-GOlBq.ttf", size));
        texts.setFill(color);

        group.getChildren().add(texts);
    }

    public void drawPicture(String file, double x, double y, Group group) {
        Image image = new Image(file);
        ImageView view = new ImageView(image);
        view.setX(x);
        view.setY(y);

        group.getChildren().add(view);
    }

    public void drawGun(String file) {
        drawPicture(file, (double) GAME_WIDTH / 2 - 65, HEIGHT - 170, root);
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