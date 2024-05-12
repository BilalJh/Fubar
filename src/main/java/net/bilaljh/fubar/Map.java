package net.bilaljh.fubar;

import java.util.Random;

public class Map {

    private Random randomizer;
    private int mapBorderX, mapBorderY, borderAverage, playerSpawnX, playerSpawnY, lostSoulSpawnX, lostSoulSpawnY;

    public int[][] map;

    public Map() {
        randomizer = new Random();

        mapBorderX = randomizer.nextInt(24) + 8;
        mapBorderY = randomizer.nextInt(24) + 8;
        borderAverage = ((mapBorderX + mapBorderY));
        map = new int[mapBorderX + 1][mapBorderY + 1];

        lostSoulSpawnX = randomizer.nextInt(mapBorderX);
        lostSoulSpawnY = randomizer.nextInt(mapBorderY);
        if(lostSoulSpawnX < 2) {
            lostSoulSpawnX++;
        }
        if(lostSoulSpawnX > mapBorderX) {
            lostSoulSpawnX--;
        }
        if(lostSoulSpawnY < 2) {
            lostSoulSpawnY++;
        }
        if(lostSoulSpawnY > mapBorderY) {
            lostSoulSpawnY--;
        }

        System.out.println("lostSoulSpawnX: " + lostSoulSpawnX + ", lostSoulSpawnY: " + lostSoulSpawnY);
        playerSpawnX = mapBorderX / 2;
        playerSpawnY = mapBorderY / 2;

        if(Main.DEVELOPER_MODE) {
            System.out.println(mapBorderX + ", " + mapBorderY);
        }

        createWorld();
        generateObstacles();
        generateSpawn(playerSpawnX, playerSpawnY);
    }

    public void createWorld() {
        for(int i = 0; i <= mapBorderX; i++) {
            for(int ii = 0; ii <= mapBorderY; ii++) {
                map[i][ii] = 0;
            }
        }
        for(int i = 0; i <= mapBorderX; i++) {
            int random = randomizer.nextInt(9) + 1;
            map[i][0] = randomizer.nextInt(6) + 1;
            map[i][mapBorderY] = randomizer.nextInt(6) + 1;
        }
        for(int i = 0; i < mapBorderY; i++) {
            int random = randomizer.nextInt(9) + 1;
            map[0][i] = randomizer.nextInt(6) + 1;
            map[mapBorderX][i] = randomizer.nextInt(6) + 1;
        }
    }

    public void generateObstacles() {
        for(int i = 0; i < borderAverage; i++) {
            int randX = randomizer.nextInt(mapBorderX);
            int randY = randomizer.nextInt(mapBorderY);
            map[randX][randY] = randomizer.nextInt(6) + 1;
        }
    }

    public void generateSpawn(int x, int y) {
        for(int i = -1; i < 2; i++) {
            for(int ii = -1; ii < 2; ii++) {
                map[x + i][y + ii] = 0;
            }
        }
    }

    public int getMapBorderX() {
        return mapBorderX;
    }
    public int getMapBorderY() {
        return mapBorderY;
    }
    public int getBorderAverage() {
        return borderAverage;
    }
    public int getPlayerSpawnX() {
        return playerSpawnX;
    }
    public int getPlayerSpawnY() {
        return playerSpawnY;
    }
    public int getLostSoulSpawnX() {
        return lostSoulSpawnX;
    }
    public int getLostSoulSpawnY() {
        return lostSoulSpawnY;
    }
}
