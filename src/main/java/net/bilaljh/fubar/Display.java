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

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;


public class Display {

    Stage primaryStage, mapStage;
    Group root, mapRoot;
    Scene primaryScene, mapScene;

    Player player;
    Line playerLine;
    Line[] lines;
    Line[] blackLines;
    Rectangle playerRect;
    Rectangle floor;
    Rectangle[] rectangles;
    Text life1, life2;
    Image playerImage, hud;
    ImageView playerImageView, hudView;

    private final int WIDTH = 1110;
    private final int HEIGHT = 640;
    private final int GAME_WIDTH = 960;


    public Display() {
        player = Main.player;
        playerRect = new Rectangle();
        floor = new Rectangle();
        playerLine = new Line();
        lines = new Line[Main.rayNumber];
        blackLines = new Line[Main.rayNumber];
        life1 = new Text();
        life2 = new Text();

        // -- Hauptfenster --
        primaryStage = new Stage();
        root = new Group();
        primaryScene = new Scene(root, Color.rgb(91,0,0));

        primaryStage.setTitle("Fubar! - PreAlpha v0.2");
        primaryStage.setWidth(WIDTH);
        primaryStage.setHeight(HEIGHT);
        primaryStage.setResizable(false);
        //primaryStage.setX(Screen.getPrimary().getBounds().getWidth() / 2);
        //primaryStage.setY(Screen.getPrimary().getBounds().getWidth() / 2);

        //primaryStage.initStyle(StageStyle.UNDECORATED);


        // ---- 2D Ansicht ----
        mapStage = new Stage();
        mapRoot = new Group();
        mapScene = new Scene(mapRoot, Color.BLACK);

        mapStage.setTitle("Fubar! - PreAlpha v0.2 - Map");
        mapStage.setWidth(GAME_WIDTH);
        mapStage.setHeight(HEIGHT);
        mapStage.setResizable(true);
        //mapStage.setX(Screen.getScreens().get(0).getBounds().getWidth() / 2 - 300);
        //mapStage.setY(Screen.getScreens().get(0).getBounds().getWidth() / 2);




        primaryStage.setScene(primaryScene);
        primaryStage.show();

        mapStage.setScene(mapScene);
        //mapStage.show();

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

        drawRect(playerRect, Main.player.getPosX() - 10, Main.player.getPosY() - 10, 20, 20, 6, mapRoot);
        for(int i = 0; i < Main.rayNumber; i++) {
            lines[i] = new Line();
            drawLine(lines[i], Main.player.getPosX(), Main.player.getPosY(), Main.rays[i].getEndX(), Main.rays[i].getEndY(), 1, Main.rays[i].getColor(), mapRoot);
        }
        drawLine(playerLine, Main.player.getPosX(), Main.player.getPosY(), Main.player.getPosX() + 50 * Math.cos(Main.player.getAngle()), Main.player.getPosY() + 50 * Math.sin(Main.player.getAngle()), 1, Color.RED, mapRoot);
    }

    public void draw3D() {
        root.getChildren().clear();

        drawRect(floor, 0, (int) (HEIGHT / 2), GAME_WIDTH, HEIGHT, Color.rgb(0,0,94), root);
        for(int i = 0; i < Main.rayNumber; i++) {
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
            }
        }
    }

    public void drawHUD() {
        int middleX = (int) (GAME_WIDTH + ((WIDTH - GAME_WIDTH) / 2));

        drawPicture(hud, "file:src/resource/hud.png", hudView, middleX - 75, 0, root);

        // -- Lebensanzeige --
        if(player.getLife() == 100) {
            drawText(life1, GAME_WIDTH + 25, (int) (HEIGHT / 5), String.valueOf(Main.player.getLife()), 120, Color.rgb(94, 18, 36), root);
            drawText(life2, GAME_WIDTH + 25, (int) (HEIGHT / 5) - 3, String.valueOf(Main.player.getLife()), 110, Color.rgb(255, 0, 0), root);
        } else if(player.getLife() < 10 && player.getLife() >= 0) {
            drawText(life1, GAME_WIDTH + 57, (int) (HEIGHT / 5), String.valueOf(Main.player.getLife()), 120, Color.rgb(94, 18, 36), root);
            drawText(life2, GAME_WIDTH + 57, (int) (HEIGHT / 5) - 3, String.valueOf(Main.player.getLife()), 110, Color.rgb(255, 0, 0), root);
        } else {
            drawText(life1, GAME_WIDTH + 40, (int) (HEIGHT / 5), String.valueOf(Main.player.getLife()), 120, Color.rgb(94, 18, 36), root);
            drawText(life2, GAME_WIDTH + 40, (int) (HEIGHT / 5) - 3, String.valueOf(Main.player.getLife()), 110, Color.rgb(255, 0, 0), root);
        }
            Main.face.idle();
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

    public void drawPicture(Image image, String file, ImageView view, double x, double y, Group group) {
        image = new Image(file);
        view = new ImageView(image);
        view.setX(x);
        view.setY(y);

        group.getChildren().add(view);
    }

    /*
    public String getPlayerImage(Player player) {
        if(player.getLife() >= 80) {
            return "file:src/resource/Standard/STFST01.png";
        } else if(player.getLife() >= 60) {
            return "file:src/resource/Standard/STFST11.png";
        } else if(player.getLife() >= 40) {
            return "file:src/resource/Standard/STFST21.png";
        } else if(player.getLife() >= 20) {
            return "file:src/resource/Standard/STFST31.png";
        } else if(player.getLife() >= 1) {
            return "file:src/resource/Standard/STFST41.png";
        } else {
            return "file:src/resource/Standard/STFST51.png";
        }
    } */

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
