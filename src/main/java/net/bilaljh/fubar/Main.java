package net.bilaljh.fubar;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.shape.Line;
import javafx.stage.StageStyle;


public class Main extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
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

        Line[] lines = new Line[961];
        for (int i = 0; i <= 960; i++) {
            lines[i] = new Line();
            drawLine(lines[i], i, 640 / 2 - 100, i, 640 / 2 + 100, 1, Color.WHITE, root);
        }

        /*  -- How to draw a Line --
            Line line = new Line();
            line.setStartX();
            line.setStartY();
            line.setEndX();
            line.setEndY();
            line.setStrokeWidth();
            line.setStroke();

            root.getChildren().add(line);

         */


        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }

    public void drawLine(Line line, int startX, int startY, int endX, int endY, int width, Color color, Group root) {
        line.setStartX(startX);
        line.setStartY(startY);
        line.setEndX(endX);
        line.setEndY(endY);
        line.setStrokeWidth(width);
        line.setStroke(color);
        root.getChildren().add(line);
    }
}