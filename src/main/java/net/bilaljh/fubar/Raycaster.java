package net.bilaljh.fubar;

import javafx.scene.paint.Color;

public class Raycaster {

    private int[][] map = Main.map.map;
    Color wallColor;

    /**
     * Raycasting Algorithmus
     * Berechnet einen Ray
     */
    public void castRays() {                                                                            //Methode zum Berechnen der Anzeige, mithilfe des DDA Raycasting Algorithmus
        Player player = Main.player;
        double playerPosX = player.getPosX();                                                           // Spielerposition X
        double playerPosY = player.getPosY();                                                           // Spielerposition Y
        double angle = player.getAngle() - Math.toRadians(45);                                          // Startwinkel für das Sichtfeld
        int mapBorderX = Main.map.getMapBorderX();
        int mapBorderY = Main.map.getMapBorderY();
        boolean hit;

        for (int i = 0; i < Main.RAY_NUMBER; i++) {                                                     //Iteriert durch jeden Ray
            hit = false;
            double rayAngle = angle + Math.toRadians((double) i / 4);                                   //Aktueller Winkel des Strahls

                                                                                                        //Richtung des Strahls in x- und y-Koordinaten
            double rayX = Math.cos(rayAngle);
            double rayY = Math.sin(rayAngle);

                                                                                                        //Delta-Entfernungen für vertikalen und horizontalen Strahl
            double deltaDistX = Math.sqrt(1 + (rayY * rayY) / (rayX * rayX));
            double deltaDistY = Math.sqrt(1 + (rayX * rayX) / (rayY * rayY));


            double startX = playerPosX;
            double startY = playerPosY;

                                                                                                        //Schrittweises Bewegen des Strahls entlang der Karte
            while(true) {
                if (deltaDistX < deltaDistY) {                                                          //Berechnet Länge zur nächsten vertikalen oder horizontalen Grenze
                    // Der Ray trifft vertikal auf eine Wand
                    startX += deltaDistX * rayX;
                    startY += deltaDistX * rayY;
                } else {
                    // Der Ray trifft horizontal auf eine Wand
                    startX += deltaDistY * rayX;
                    startY += deltaDistY * rayY;
                }


                int mapX = (int) (startX / 64);                                                         //Anpassung der Werte um kompatibel mit Karte zu sein
                int mapY = (int) (startY / 64);
                                                                                                        //Distanz zum Gegner
                double distanceToLostSoul = Math.sqrt(Math.pow(startX - Main.lostSoul.getPosX(), 2) + Math.pow(startY - Main.lostSoul.getPosY(), 2));
                if (distanceToLostSoul < 1) {                                                           //Wenn Gegner auf aktuellem Ray
                    hit = true;
                }
                                                                                                        //Wenn innerhalb der Welt und Kollision mit Wand oder Gegner
                if (mapX < 0 || mapX >= mapBorderX || mapY < 0 || mapY >= mapBorderY || Main.map.map[mapX][mapY] >= 1 || (startX == Main.lostSoul.getPosX() && startY == Main.lostSoul.getPosY())) {
                    switch(Main.map.map[mapX][mapY]) {                                                  //Passe Farbe des Rays anhand der Textur der Wand an
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
                        default:                                                                        //Bei Gegner wird keine Farbe gespeichert
                            break;
                    }
                    break;                                                                              //Abruch der while(true) Schleife
                }
            }

            double endX = startX + rayX;                                                                //Endwerte des Rays
            double endY = startY + rayY;
            double relEndX = endX - playerPosX;                                                         //Endwerte des Rays relativ zum Spieler
            double relEndY = endY - playerPosY;

                                                                                                        //Initialisierung des Rays mithilfe der berechneten Daten
            Main.rays[i] = new Ray(startX, startY, endX, endY, Math.sqrt(relEndX * relEndX + relEndY * relEndY), wallColor, hit);
        }
    }

    /**
     * Raycasting Algorithmus
     * Berechnet einen Schuss
     */
    public void castShot() {                                                                        //Siehe castRays() Methode
        Player player = Main.player;
        double playerPosX = player.getPosX();
        double playerPosY = player.getPosY();
        double angle = player.getAngle() - Math.toRadians(15);
        double rayAngle = angle;

        double startX = playerPosX;
        double startY = playerPosY;

        double stepX = Math.cos(rayAngle);
        double stepY = Math.sin(rayAngle);

        while(true) {
            //Überprüfen ob der Strahl die LostSoul trifft
            if(collidesWithLostSoul(startX, startY)) {
                Main.lostSoul.die();
                break;
            }
            else if(startX > 1000 || startX < -1000 || startY > 1000 || startY < -1000) {
                break;
            }

            startX += stepX;
            startY += stepY;
        }
    }

    /**
     * Überprüft ob der Spieler mit der LostSoul kollidiert
     * @param playerX x-Koordinate des Spielers
     * @param playerY y-Koordinate des Spielers
     * @return true wenn Spieler und LostSoul auf der gleichen Position sind
     */
    public boolean collidesWithLostSoul(double playerX, double playerY) {
        LostSoul ls = Main.lostSoul;

        //Gebe true zurück wenn Position von Spieler ung Gegner identisch sind
        return ((int) (playerX / 64)) == (int) (ls.getPosX() / 64) && ((int) (playerY / 64)) == (int) (ls.getPosY() / 64);
    }
}