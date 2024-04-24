package net.bilaljh.fubar;

import java.lang.Math;

public class Raycaster {

    private final double PI = Math.PI;
    private final double PI_2 = Math.PI / 2;
    private final double PI_3 = 3 * Math.PI / 2;
    //private final double oneRadian = Math.toRadians(1);
    private final double oneRadian = 0.0174533;

    private double angle, tan, koTan, negTan, rayX, rayY, offsetX, offsetY;
    private double playerX, playerY, endX, endY, rayLength, distance, distanceH, distanceV, horizontalX, horizontalY, verticalX, verticalY, pointX, pointY, angleClear;
    private int depthOfField, mapX, mapY, mapPos;

    private int[][] map = Main.map.map;


    public Raycaster() {
    }

    public void castRays() {
        playerX = Main.player.getPosX();
        playerY = Main.player.getPosY();
        angleClear = Main.player.getAngle();
        angle = Main.player.getAngle() - Math.toRadians(45);
        tan = Math.tan(angle);

        for(int i = 0; i < 90; i++) {

            // -- Horizontal --
            tan = Math.tan(angle);
            tan *= 1.0;
            if(Math.sin(angle) > 0.001) { //Oben
                endY = (((int) playerY >> 6) << 6) - 0.001;
                endX = (playerY - endY) * tan + playerX;
                offsetY = - 64;
                offsetX = - offsetY * tan;
            } if(Math.sin(angle) < -0.001) { //Unten
                endY = (((int) playerY >> 6) << 6) + 64;
                endX = (playerY - endY) * tan + playerX;
                offsetY = 64;
                offsetX = - offsetY * tan;
            } if(angle == PI || angle == 0) { //Seite
                endX = playerX;
                endY = playerY;
                depthOfField = 8;
            }
            while(depthOfField < 8) {
                mapX = (int) (endX) >> 6;
                mapY = (int) (endY) >> 6;
                mapPos = mapY * Main.map.getMapBorderX() + mapX;
                if(mapPos < Main.map.getMapBorderX() * Main.map.getMapBorderY() && map[mapX][mapY] == 1) {
                    depthOfField = 8;
                } else {
                    endX += offsetX;
                    endY += offsetY;
                    depthOfField++;
                }
            }

//            endX = Main.player.getPosX() + 200 * Math.cos(angle);
//            endY = Main.player.getPosY() + 200 * Math.sin(angle);

            Main.rays[i] = new Ray(playerX, playerY, endX, endY, Math.sqrt(endX*endX + endY*endY));
            angle += oneRadian;
        }
    }

    public void castRaysT() {

        playerX = Main.player.getPosX();
        playerY = Main.player.getPosY();
        angle = Main.player.getAngle() - oneRadian * 45;
        tan = Math.tan(angle);
        koTan = -1 / tan;
        negTan = -1 * tan;

        if(angle > 2 * PI) {
            angle -= 2 * PI;
        }


        for (int i = 0; i < 90; i++) {


            // -- Horizontal --
            depthOfField = 0;
            distanceH = 10;
            horizontalX = playerX;
            horizontalY = playerY;

            if (angle > PI) { //Schaut nach unten
                rayY = playerY / 64 - 0.0001;
                rayX = (playerX - rayY) * koTan + playerX;
                offsetY = -64;
                offsetX = -offsetY * koTan;
            } if (angle < PI) { //Schaut nach Oben
                rayY = playerY / 64 + 64;
                rayX = (playerX - rayY) * koTan + playerX;
                offsetY = 64;
                offsetX = -offsetY * koTan;
            } else {
                //(angle == PI || angle == 0) { //Schaut nach Links/Rechts
                rayX = playerX;
                rayY = playerY;
                depthOfField = 8;
                horizontalX = 10;
                horizontalY = 10;
            }
            while(depthOfField < 8) {
                mapX = (int) (rayX / 64);
                mapY = (int) (rayY / 64);
                mapPos = mapY * Main.map.getMapBorderX() + mapX;
                if (mapPos >= 0 && mapPos < map.length && mapX >= 0 && mapX < map.length && mapY >= 0 && mapY < map[0].length) {
                    if (map[mapX][mapY] == 1) {
                        horizontalX = rayX;
                        horizontalY = rayY;
                        //distanceH = Math.cos(angle) * (horizontalX - playerX) - Math.sin(angle) * (horizontalY - playerY);
                        distanceH = Math.sqrt(Math.pow(horizontalX - playerX, 2) + Math.pow(horizontalY - playerY, 2));
                        //System.out.println(distanceH);
                        depthOfField = 8;
                    }
                    else {
                        depthOfField++;
                    }
                } else {
                    rayX += offsetX;
                    rayY += offsetY;
                    depthOfField++;
                }
            }


            // -- Vertikal --
            depthOfField = 0;
            distanceV = 10;
            verticalX = playerX;
            verticalY = playerY;

            if (angle > PI_2 && angle < PI_3) { //Schaut nach Links
                rayX = playerX / 64 - 0.0001;
                rayY = (playerY - rayX) * negTan + playerY;
                offsetX = -64;
                offsetY = -offsetX * negTan;
            } if (angle < PI_2 || angle > PI_3) { //Schaut nach Rechts
                rayX = playerX / 64 + 64;
                rayY = (playerY - rayX) * negTan + playerY;
                offsetX = 64;
                offsetY = -offsetX * negTan;
            } else {
                //(angle == PI || angle == PI_3) { //Schaut nach Oben/Unten
                rayX = playerX;
                rayY = playerY;
                depthOfField = 8;
                verticalX = 10;
                verticalY = 10;
            }
            while(depthOfField < 8) {
                mapX = (int) (rayX / 64);
                mapY = (int) (rayY / 64);
                mapPos = mapY * Main.map.getMapBorderX() + mapX;
                if (mapPos >= 0 && mapPos < map.length && mapX >= 0 && mapX < map.length && mapY >= 0 && mapY < map[0].length) {
                    if (map[mapX][mapY] == 1) {
                        verticalX = rayX;
                        verticalY = rayY;
                        //distanceV = Math.cos(angle) * (verticalX - playerX) - Math.sin(angle) * (verticalY - playerY);
                        distanceV = Math.sqrt(Math.pow(verticalX - playerX, 2) + Math.pow(verticalY - playerY, 2));
                        //System.out.println(distanceV);
                        depthOfField = 8;
                    }
                    else {
                        depthOfField++;
                    }
                } else {
                    rayX += offsetX;
                    rayY += offsetY;
                    depthOfField++;
                }
            }
            if(distanceV < distanceH) {
                rayX = horizontalX;
                rayY = horizontalY;
                distance = distanceV;
            } if(distanceV > distanceH) {
                rayX = verticalX;
                rayY = verticalY;
                distance = distanceH;
            } if(distanceH == distanceV) {
                //System.out.println("SYNCHRON");
            }
            if(angle > 2 * PI) {
                angle -= 2 * PI;
            }

            //System.out.println(rayX + ", " + rayY);
            Main.rays[i] = new Ray(playerX, playerY, rayX, rayY, distance);
            angle += oneRadian;
            tan = Math.tan(angle);
            koTan = -1 / tan;
            negTan = -1 * tan;
        }
    }

    public double getPointX() {
        return pointX;
    }
    public double getPointY() {
        return pointY;
    }
}