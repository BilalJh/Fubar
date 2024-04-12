package net.bilaljh.fubar;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.lang.Math;

public class Raycaster {

    Ray[] rays = new Ray[90];
    private double dx;
    private double dy = 1;

    public Raycaster() {
        for (int i = 0; i < 90; i++) {
            dx = Math.cos(Math.toRadians(i));
            dy = 1;
            //dy = Math.sin(Math.toRadians(i));


            rays[i] = new Ray(Main.player.getPosX(), Main.player.getPosY(), dx, dy);
        }
    }

    public double getDx() {
        return dx;
    }
    public double getDy() {
        return dy;
    }
}