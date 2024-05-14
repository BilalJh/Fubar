package net.bilaljh.fubar;

import javafx.scene.paint.Color;

public class Ray {
    private double originX, originY, endX, endY, length;
    private Color color;
    private boolean hit;

    public Ray(double originX, double originY, double endX, double endY, double length, Color color, boolean hit) {
        this.originX = originX;
        this.originY = originY;
        this.endX = endX;
        this.endY = endY;
        this.length = length;
        this.color = color;
        this.hit = hit;
    }


    // -- Getter / Setter Methoden

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
    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
    }
    public boolean isHit() {
        return hit;
    }
    public void setHit(boolean hit) {
        this.hit = hit;
    }
}
