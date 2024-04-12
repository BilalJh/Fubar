package net.bilaljh.fubar;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.stage.Window;

public class Display {

    public Display() {
        Stage primaryStage = new Stage();
        Group root = new Group();
        Scene primaryScene = new Scene(root, Color.BLACK);

        Image icon = new Image("LS00.png");

        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("Fubar! - PreAlpha v0.1");
        primaryStage.setWidth(960);
        primaryStage.setHeight(640);
        primaryStage.setResizable(false);
        //primaryStage.initStyle(StageStyle.UNDECORATED);



        Line[] lines = new Line[90];
        for (int i = 0; i < 90; i++) {
            double currentAngle = i - 45;
            double startX = Main.engine.rays[i].getOriginX();
            double startY = Main.engine.rays[i].getOriginY();
            double endX = startX - Math.cos(Math.toRadians(currentAngle)) * 100;
            double endY = startY - Math.sin(Math.toRadians(currentAngle)) * 100;
            lines[i] = new Line();
            drawLine(lines[i], Main.engine.rays[i].getOriginX(), Main.engine.rays[i].getOriginY(), endX, endY, 0.1, Color.WHITE, root);

        }

        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }

    public void drawLine(Line line, int startX, int startY, int endX, int endY, int width, Color color, Group root){
        line.setStartX(startX);
        line.setStartY(startY);
        line.setEndX(endX);
        line.setEndY(endY);
        line.setStrokeWidth(width);
        line.setStroke(color);
        root.getChildren().add(line);
    }
    public void drawLine(Line line, double startX, double startY, double endX, double endY, double width, Color color, Group root){
        line.setStartX(startX);
        line.setStartY(startY);
        line.setEndX(endX);
        line.setEndY(endY);
        line.setStrokeWidth(width);
        line.setStroke(color);
        root.getChildren().add(line);
    }
}
