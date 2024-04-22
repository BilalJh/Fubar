package net.bilaljh.fubar;

public class Ray {
    private double originX;
    private double originY;
    private double endX;
    private double endY;
    private double length;

    public Ray(double originX, double originY, double endX, double endY, double length) {
        this.originX = originX;
        this.originY = originY;
        this.endX = endX;
        this.endY = endY;
        this.length = length;
    }

    public double getOriginX() {
        return originX;
    }
    public double getOriginY() {
        return originY;
    }

    public double getLength() {
        return length;
    }
    public void setLength(double length) {
        this.length = length;
    }

    public double getEndX() {
        return endX;
    }

    public double getEndY() {
        return endY;
    }
}
