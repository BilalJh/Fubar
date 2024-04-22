package net.bilaljh.fubar;

import java.awt.image.PixelGrabber;
import java.lang.Math;

public class Raycaster {

    private final double PI = Math.PI;
    private final double PI_2 = Math.PI / 2;
    private final double PI_3 = 3 * Math.PI / 2;
    private final double oneRadian = Math.toRadians(1);

    private double angle, tan, invTan, negTan, rayX, rayY, offsetX, offsetY;
    private double playerX, playerY, endX, endY, rayLength, distance, distanceH, distanceV, horizontalX, horizontalY, verticalX, verticalY;
    private int depthOfField, mapX, mapY, mapPos;

    private int[][] map = Main.map.map;


    public Raycaster() {
    }

    public void castRays(Player player) {

        playerX = Main.player.getPosX();
        playerY = Main.player.getPosY();
        angle = Main.player.getAngle() - oneRadian * 45;
        tan = Math.tan(angle);
        invTan = -1 / tan;
        negTan = -1 * tan;

        if(angle > 2 * PI) {
            angle -= 2 * PI;
        }

        for (int i = 0; i < 90; i++) {

            // -- Horizontal --
            depthOfField = 0;
            distanceH = 100000;
            horizontalX = playerX;
            horizontalY = playerY;

            if (angle > PI) { //Schaut nach unten
                rayY = playerY / 64 - 0.0001;
                rayX = (playerX - rayY) * invTan + playerX;
                offsetY = -64;
                offsetX = -offsetY * invTan;
            } else if (angle < PI) { //Schaut nach Oben
                rayY = playerY / 64 + 64;
                rayX = (playerX - rayY) * invTan + playerX;
                offsetY = 64;
                offsetX = -offsetY * invTan;
            }
            if (angle == PI || angle == 0) { //Schaut nach Links/Rechts
                rayX = playerX;
                rayY = playerY;
                depthOfField = 8;
            }
            while(depthOfField < 8) {
                mapX = (int) (rayX / 64);
                mapY = (int) (rayY / 64);
                mapPos = mapY * mapX + mapX;
                if (mapPos >= 0 && mapPos < map.length && mapX >= 0 && mapX < map.length && mapY >= 0 && mapY < map[0].length) {
                    if (map[mapX][mapY] == 1) {
                        horizontalX = rayX;
                        horizontalY = rayY;
                        distanceH = Math.cos(Math.toRadians(angle)) * (horizontalX - playerX) - Math.sin(Math.toRadians(angle)) * (horizontalY - playerY);
                        depthOfField = 8;
                    }
                } else {
                    rayX += offsetX;
                    rayY += offsetY;
                    depthOfField++;
                }
                //System.out.println("Syso");
            }


            // -- Vertikal --
            depthOfField = 0;
            distanceV = 100000;
            verticalX = playerX;
            verticalY = playerY;

            if (angle > PI_2 && angle < PI_3) { //Schaut nach Links
                rayX = playerX / 64 - 0.0001;
                rayY = (playerY - rayX) * negTan + playerY;
                offsetX = -64;
                offsetY = -offsetX * negTan;
            } else if (angle < PI_2 || angle > PI_3) { //Schaut nach Rechts
                rayX = playerX / 64 + 64;
                rayY = (playerY - rayX) * negTan + playerY;
                offsetX = 64;
                offsetY = -offsetX * negTan;
            } if(angle == PI || angle == PI_3) { //Schaut nach Oben/Unten
                rayX = playerX;
                rayY = playerY;
                depthOfField = 8;
            }
            while(depthOfField < 8) {
                mapX = (int) (rayX / 64);
                mapY = (int) (rayY / 64);
                mapPos = mapY * mapX + mapX;
                if (mapPos >= 0 && mapPos < map.length && mapX >= 0 && mapX < map.length && mapY >= 0 && mapY < map[0].length) {
                    if (map[mapX][mapY] == 1) {
                        verticalX = rayX;
                        verticalY = rayY;
                        distanceV = Math.cos(Math.toRadians(angle)) * (verticalX - playerX) - Math.sin(Math.toRadians(angle)) * (verticalY - playerY);
                        depthOfField = 8;
                    }
                } else {
                    rayX += offsetX;
                    rayY += offsetY;
                    depthOfField++;
                }
                //System.out.println("Syso");
            }
            if(distanceV < distanceH) {
                rayX = verticalX;
                rayY = verticalY;
                distance = distanceV;
            } if(distanceV > distanceH) {
                rayX = horizontalX;
                rayY = horizontalY;
                distance = distanceH;
            }
            angle += oneRadian;
            if(angle > 2 * PI) {
                angle -= 2 * PI;
            }

            Main.rays[i] = new Ray(playerX, playerY, rayX, rayY, distance);

        }
    }
}