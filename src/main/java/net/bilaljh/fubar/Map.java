package net.bilaljh.fubar;

import java.util.Random;

public class Map {


    private Random randomizer = new Random();
    private int mapBorderX, mapBorderY, borderAverage, spawnX, spawnY;

    public int[][] map;

    public Map() {
        mapBorderX = randomizer.nextInt(24) + 8;
        mapBorderY = randomizer.nextInt(24) + 8;
        borderAverage = ((mapBorderX + mapBorderY));
        map = new int[mapBorderX + 1][mapBorderY + 1];
        spawnX = (int)(mapBorderX / 2);
        spawnY = (int)(mapBorderY / 2);

        System.out.println(mapBorderX + ", " + mapBorderY);

        createWorld();
        generateObstacles();
        generateSpawn(spawnX, spawnY);

//        map = new int[][] {
//                {1, 1, 1, 1, 1, 1, 1, 1},
//                {1, 0, 0, 1, 0, 0, 0, 1},
//                {1, 0, 0, 1, 0, 0, 0, 1},
//                {1, 0, 0, 1, 0, 0, 0, 1},
//                {1, 0, 0, 1, 0, 0, 0, 1},
//                {1, 0, 0, 0, 0, 1, 0, 1},
//                {1, 0, 0, 0, 0, 0, 0, 1},
//                {1, 1, 1, 1, 1, 1, 1, 1,},
//        };
    }

    public void createWorld() {
        for(int i = 0; i <= mapBorderX; i++) {
            for(int ii = 0; ii <= mapBorderY; ii++) {
                map[i][ii] = 0;
            }
        }
        for(int i = 0; i <= mapBorderX; i++) {
            map[i][0] = 1;
            map[i][mapBorderY] = 1;
        }
        for(int i = 0; i < mapBorderY; i++) {
            map[0][i] = 1;
            map[mapBorderX][i] = 1;
        }
    }

    public void generateObstacles() {
        for(int i = 0; i < borderAverage; i++) {
            int randX = randomizer.nextInt(mapBorderX);
            int randY = randomizer.nextInt(mapBorderY);
            map[randX][randY] = 1;
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
    public int getSpawnX() {
        return spawnX;
    }
    public int getSpawnY() {
        return spawnY;
    }
}
