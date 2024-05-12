package net.bilaljh.fubar;

import java.lang.Math;
import javafx.scene.paint.Color;

public class Raycaster {

    private int[][] map = Main.map.map;
    Color wallColor;


    public void castRays() {
        Player player = Main.player;
        double playerPosX = player.getPosX(); // Spielerposition X
        double playerPosY = player.getPosY(); // Spielerposition Y
        double angle = player.getAngle() - Math.toRadians(45); // Startwinkel für das Sichtfeld
        int mapBorderX = Main.map.getMapBorderX();
        int mapBorderY = Main.map.getMapBorderY();
        boolean hit;

        for (int i = 0; i < Main.RAY_NUMBER; i++) {
            hit = false;
            double rayAngle = angle + Math.toRadians((double) i / 4); // Aktueller Winkel des Strahls

            // Richtung des Strahls in x- und y-Koordinaten
            double rayX = Math.cos(rayAngle);
            double rayY = Math.sin(rayAngle);

            // Delta-Entfernungen für vertikalen und horizontalen Strahl
            double deltaDistX = Math.sqrt(1 + (rayY * rayY) / (rayX * rayX));
            double deltaDistY = Math.sqrt(1 + (rayX * rayX) / (rayY * rayY));


            double startX = playerPosX;
            double startY = playerPosY;

            // Schrittweises Bewegen des Strahls entlang der Karte
            while(true) {
                // Welche Seite des nächsten Zellgrenzkreuzung wird getroffen zuerst?
                if (deltaDistX < deltaDistY) {
                    // Der Ray trifft vertikal auf eine Wand
                    startX += deltaDistX * rayX;
                    startY += deltaDistX * rayY;
                } else {
                    // Der Ray trifft horizontal auf eine Wand
                    startX += deltaDistY * rayX;
                    startY += deltaDistY * rayY;
                }

                // Kollisionserkennung: Ist die aktuelle Zelle eine Wand?
                int mapX = (int) (startX / 64);
                int mapY = (int) (startY / 64);

                double distanceToLostSoul = Math.sqrt(Math.pow(startX - Main.lostSoul.getPosX(), 2) + Math.pow(startY - Main.lostSoul.getPosY(), 2));
                if (distanceToLostSoul < 1) { // Anpassen der Schwellenwert für die Kollisionsabfrage
                    hit = true;
                }

                if (mapX < 0 || mapX >= mapBorderX || mapY < 0 || mapY >= mapBorderY || Main.map.map[mapX][mapY] >= 1 || (startX == Main.lostSoul.getPosX() && startY == Main.lostSoul.getPosY())) {
                    switch(Main.map.map[mapX][mapY]) {
                        case 1:
                            wallColor = Color.rgb(159,0,0);
                            break;
                        case 2:
                            wallColor = Color.rgb(47,47,47);
                            break;
                        case 3:
                            wallColor = Color.rgb(107,71,39);
                            break;
                        case 4:
                            wallColor = Color.rgb(127, 0, 0);
                            break;
                        case 5:
                            wallColor = Color.rgb(135,67,7);
                            break;
                        case 6:
                            wallColor = Color.rgb(139,139,139);
                            break;
                        default:
                            break;
                    }
                    break;
                }
            }

            double endX = startX + rayX;
            double endY = startY + rayY;
            double relEndX = endX - playerPosX;
            double relEndY = endY - playerPosY;


            Main.rays[i] = new Ray(startX, startY, endX, endY, Math.sqrt(relEndX * relEndX + relEndY * relEndY), wallColor, hit);
        }
    }

    public void castShot() {
        Player player = Main.player;
        double playerPosX = player.getPosX(); // Spielerposition X
        double playerPosY = player.getPosY(); // Spielerposition Y
        double angle = player.getAngle() - Math.toRadians(15); // Startwinkel für das Sichtfeld
        double rayAngle = angle;

        //for(int i = 0; i < 60; i++) {
        double startX = playerPosX;
        double startY = playerPosY;

        // Schrittweite für den Strahl
        double stepX = Math.cos(rayAngle);
        double stepY = Math.sin(rayAngle);

        while(true) {
            // Überprüfen, ob der Strahl die LostSoul trifft
            if(collidesWithLostSoul(startX, startY, Main.lostSoul.getPosX(), Main.lostSoul.getPosY(), rayAngle)) {
                Main.lostSoul.die();
                break;
            }
            else if(startX > 1000 || startX < -1000 || startY > 1000 || startY < -1000) {
                System.out.println("missed");
                break;
            }

            // Schrittweise Bewegung des Strahls entlang seiner Richtung
            startX += stepX;
            startY += stepY;
        }
        //}
    }

    // Methode zur Überprüfung der Kollision mit dem LostSoul
    public boolean collidesWithLostSoul(double playerX, double playerY, double soulX, double soulY, double playerDirection) {
        // Berechnung des Winkels zwischen der Blickrichtung des Spielers und der Position der verlorenen Seele
        double angle = Math.atan2(soulY - playerY, soulX - playerX);

        // Umrechnung des Winkels in Grad
        angle = Math.toDegrees(angle);
        playerDirection = Math.toDegrees(playerDirection);

        // Überprüfen, ob der Winkel innerhalb eines bestimmten Bereichs liegt
        double angleDifference = Math.abs(angle - playerDirection);
        // Normalisierung des Winkelunterschieds auf den Bereich [-180, 180]
        angleDifference = (angleDifference + 180) % 360 - 180;

        // Schwellenwert für den maximalen Kollisionswinkel
        double collisionAngleThreshold = 20; // Zum Beispiel 45 Grad

        // Rückgabe, ob der Winkel innerhalb des Schwellenwerts liegt
        return Math.abs(angleDifference) <= collisionAngleThreshold;
    }
}