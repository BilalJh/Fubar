package net.bilaljh.fubar;

import javafx.scene.input.KeyCode;

public class Player {

    private double posX;
    private double posY;
    private double angle;


    public Player(double posX, double posY, double angle) {
        this.posX = posX;
        this.posY = posY;
        this.angle = angle;
    }

    public void moveForward() {
        double deltaX = Math.cos(Math.toRadians(angle));
        double deltaY = Math.sin(Math.toRadians(angle));
        posX += deltaX * 10;
        posY += deltaY * 10;
    }
    public void moveBackward() {
        double deltaX = Math.cos(Math.toRadians(angle));
        double deltaY = Math.sin(Math.toRadians(angle));
        posX -= deltaX * 2.5;
        posY -= deltaY * 2.5;
    }

    public double getPosX() {
        return posX;
    }
    public void setPosX(double posX) {
        this.posX = posX;
    }
    public double getPosY() {
        return posY;
    }
    public void setPosY(double posY) {
        this.posY = posY;
    }
    public double getAngle() {
        return angle;
    }
    public void setAngle(double radian) {
        this.angle = radian;
    }
}
