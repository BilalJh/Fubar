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

    /**
     * Gibt x-Koordinate des Ursprungs zurück
     * @return x-Koordinate des Ursprungs
     */
    public double getOriginX() {
        return originX;
    }

    /**
     * Gibt y-Koordinate des Ursprungs zurück
     * @return y-Koordinate des Ursprungs
     */
    public double getOriginY() {
        return originY;
    }

    /**
     * Gibt die Länge des Strahls zurück
     * @return Länge des Strahls
     */
    public double getLength() {
        return length;
    }

    /**
     * Setzt die Länge des Strahls
     * @param length Länge des Strahls
     */
    public void setLength(double length) {
        this.length = length;
    }

    /**
     * Gibt x-Koordinate des Endes zurück
     * @return x-Koordinate des Endes
     */
    public double getEndX() {
        return endX;
    }

    /**
     * Gibt y-Koordinate des Endes zurück
     * @return y-Koordinate des Endes
     */
    public double getEndY() {
        return endY;
    }

    /**
     * Gibt die Farbe des Strahls zurück
     * @return Farbe des Strahls
     */
    public Color getColor() {
        return color;
    }

    /**
     * Setzt die Farbe des Strahls
     * @param color Farbe des Strahls
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Gibt zurück, ob der Strahl ein Objekt getroffen hat
     * @return true, wenn der Strahl ein Objekt getroffen hat, sonst false
     */
    public boolean isHit() {
        return hit;
    }

    /**
     * Setzt, ob der Strahl ein Objekt getroffen hat
     * @param hit true, wenn der Strahl ein Objekt getroffen hat, sonst false
     */
    public void setHit(boolean hit) {
        this.hit = hit;
    }
}
