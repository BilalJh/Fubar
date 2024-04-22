package net.bilaljh.fubar;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.util.Timer;
import java.util.TimerTask;


public class Display {

    Stage primaryStage;
    Group root;
    Scene primaryScene;

    Line[] lines;
    Rectangle[] rectangles;
    Rectangle player = new Rectangle();

    double currentAngle;

    public Display() {
        primaryStage = new Stage();
        root = new Group();
        primaryScene = new Scene(root, Color.BLACK);

        Image icon = new Image("LS00.png");

        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("Fubar! - PreAlpha v0.1");
        primaryStage.setWidth(990);
        primaryStage.setHeight(640);
        primaryStage.setResizable(false);
        //primaryStage.initStyle(StageStyle.UNDECORATED);


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
                    Main.engine.castRays(Main.player);
                    draw();
                });
            }
        }, 0, 16); // 16 Millisekunden (~60 FPS)
    }

    public void draw() {

        lines = new Line[90];
        rectangles = new Rectangle[64];
        root.getChildren().clear();
        double posX = 0;
        double posY = 0;
        int counter = 0;

        for(int i = 0; i < 8; i++) {
            for(int ii = 0; ii < 8; ii++) {
                rectangles[counter] = new Rectangle();
                drawRect(rectangles[counter], posX, posY, 64, 64, Main.map.map[i][ii], root);
                posY += 64 + 1;
                counter++;
            }
            posX += 64 + 1;
            posY = 0;
        }

        drawRect(player, Main.player.getPosX(), Main.player.getPosY(), 20, 20, 6, root);

        for(int i = 0; i < 90; i++) {
            lines[i] = new Line();
            drawLine(lines[i], Main.player.getPosX(), Main.player.getPosY(), Main.rays[i].getEndX(), Main.rays[i].getEndY(), 1, Color.RED, root);
        }


//        for(int i = 0; i < 90; i++) {
//            lines[i] = new Line();
//            if(Main.rays[i] != null){
//                double startX = i * 11 + 0.5;
//                double startY = 640 / 2 - (64 * 640 / Main.rays[i].getLength() / 2);
//                double endX = startX;
//                double endY = 640 / 2 + (64 * 640 / Main.rays[i].getLength() / 2);
//
//                drawLine(lines[i], startX, startY, endX, endY, 11, Color.WHITE, root);
//                Main.rays[i] = null;
//            }
//        }
    }

    public void drawRect(Rectangle rect, double x, double y, double width, double height, int state, Group root) {
        rect.setX(x);
        rect.setY(y);
        rect.setWidth(width);
        rect.setHeight(height);
        if(state == 6) {
            rect.setFill(Color.ORANGE);
        } if(state == 1) {
            rect.setFill(Color.WHITE);
        } if(state == 0) {
            rect.setFill(Color.BLACK);
        }
        root.getChildren().add(rect);
    }

    public void drawLine (Line line,double startX, double startY, double endX, double endY, double width, Color color, Group root){
    line.setStartX(startX);
    line.setStartY(startY);
    line.setEndX(endX);
    line.setEndY(endY);
    line.setStrokeWidth(width);
    line.setStroke(color);
    root.getChildren().add(line);
    }
}
