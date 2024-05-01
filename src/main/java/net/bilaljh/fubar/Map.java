package net.bilaljh.fubar;

import java.util.Random;

public class Map {


    private Random randomizer = new Random();
    private int mapBorderX, mapBorderY, borderAverage, spawnX, spawnY;

    public int[][] map;

    public Map() {
        mapBorderX = randomizer.nextInt(24) + 8;
        mapBorderY = randomizer.nextInt(24) + 8;
//        mapBorderX = 8;
//        mapBorderY = 8;
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
//                {2, 0, 2, 0, 0, 0, 0, 1},
//                {3, 0, 2, 0, 0, 0, 0, 1},
//                {4, 0, 3, 0, 0, 0, 0, 1},
//                {5, 0, 0, 0, 0, 0, 0, 1},
//                {6, 0, 0, 0, 0, 6, 0, 1},
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
    public int getSpawnX() {
        return spawnX;
    }
    public int getSpawnY() {
        return spawnY;
    }
}
