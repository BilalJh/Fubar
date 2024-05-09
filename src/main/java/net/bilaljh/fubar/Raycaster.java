package net.bilaljh.fubar;

import java.lang.Math;
import javafx.scene.paint.Color;

public class Raycaster {

    private int[][] map = Main.map.map;
    Color wallColor;

    public Raycaster() {
    }

    public void castRays() {
        Player player = Main.player;
        double playerPosX = player.getPosX(); // Spielerposition X
        double playerPosY = player.getPosY(); // Spielerposition Y
        double angle = player.getAngle() - Math.toRadians(45); // Startwinkel für das Sichtfeld
        int mapBorderX = Main.map.getMapBorderX();
        int mapBorderY = Main.map.getMapBorderY();

        for (int i = 0; i < Main.rayNumber; i++) { // 90 Strahlen für ein Sichtfeld von 90 Grad
            double rayAngle = angle + Math.toRadians((double) i / 4); // Aktueller Winkel des Strahls

            // Richtung des Strahls in x- und y-Koordinaten
            double rayX = Math.cos(rayAngle);
            double rayY = Math.sin(rayAngle);

            // Delta-Entfernungen für vertikalen und horizontalen Strahl
            double deltaDistX = Math.sqrt(1 + (rayY * rayY) / (rayX * rayX));
            double deltaDistY = Math.sqrt(1 + (rayX * rayX) / (rayY * rayY));

            // Startposition des Strahls in der Kachel-Grid (vor dem Spieler)
            double startX = playerPosX;
            double startY = playerPosY;

            // Schrittweises Bewegen des Strahls entlang der Karte
            while (true) {
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

                if (mapX < 0 || mapX >= mapBorderX || mapY < 0 || mapY >= mapBorderY || Main.map.map[mapX][mapY] >= 1) {
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
            Main.rays[i] = new Ray(startX, startY, endX, endY, Math.sqrt(relEndX * relEndX + relEndY * relEndY), wallColor);
        }
    }
}