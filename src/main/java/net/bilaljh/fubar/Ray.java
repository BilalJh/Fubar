package net.bilaljh.fubar;

public class Ray {
    private double originX;
    private double originY;
    private double directionX;
    private double directionY;

    public Ray(double originX, double originY, double directionX, double directionY) {
        this.originX = originX;
        this.originY = originY;
        this.directionX = directionX;
        this.directionY = directionY;
        double magnitude = Math.sqrt(directionX * directionX + directionY * directionY);
        this.directionX = directionX / magnitude;
        this.directionY = directionY / magnitude;
    }

    public double getOriginX() {
        return originX;
    }
    public double getOriginY() {
        return originY;
    }
    public double getDirectionX() {
        return directionX;
    }
    public double getDirectionY() {
        return directionY;
    }
    public double getMagnitude() {
        return Math.sqrt(directionX * directionX + directionY * directionY);
    }

}
