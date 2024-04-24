package net.bilaljh.fubar;

public class Player {

    private double posX, posY, angle, deltaX, deltaY;


    public Player(double posX, double posY, double angle) {
        this.posX = posX;
        this.posY = posY;
        this.angle = angle; //In Bogenmaß!
    }

    public void moveForward() {
        deltaX = Math.cos(angle);
        deltaY = Math.sin(angle);
        posX += deltaX * 10;
        posY += deltaY * 10;
    }
    public void moveBackward() {
        deltaX = Math.cos(angle);
        deltaY = Math.sin(angle);
        posX -= deltaX * 5;
        posY -= deltaY * 5;
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
