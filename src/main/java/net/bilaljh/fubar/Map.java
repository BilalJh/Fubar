package net.bilaljh.fubar;

import java.util.Random;

public class Map {

    private Random randomizer;
    private int mapBorderX, mapBorderY, borderAverage, playerSpawnX, playerSpawnY, lostSoulSpawnX, lostSoulSpawnY;

    /**
     * Layout der Welt
     */
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
        playerSpawnX = mapBorderX / 2;
        playerSpawnY = mapBorderY / 2;


        createWorld();
        generateObstacles();
        generateSpawn(playerSpawnX, playerSpawnY);
    }

    /**
     * Generiert die Maße und die Ränder der Welt
     */
    public void createWorld() {
        for(int i = 0; i <= mapBorderX; i++) {
            for(int ii = 0; ii <= mapBorderY; ii++) {
                map[i][ii] = 0;
            }
        }
        for(int i = 0; i <= mapBorderX; i++) {
            map[i][0] = randomizer.nextInt(6) + 1;
            map[i][mapBorderY] = randomizer.nextInt(6) + 1;
        }
        for(int i = 0; i < mapBorderY; i++) {
            map[0][i] = randomizer.nextInt(6) + 1;
            map[mapBorderX][i] = randomizer.nextInt(6) + 1;
        }
    }

    /**
     * Generiert Hindernisse in der Welt
     */
    public void generateObstacles() {
        for(int i = 0; i < borderAverage; i++) {
            int randX = randomizer.nextInt(mapBorderX);
            int randY = randomizer.nextInt(mapBorderY);
            map[randX][randY] = randomizer.nextInt(6) + 1;
        }
    }

    /**
     * Generiert den Spawnpunkt des Spielers und entfernt mögliche Hindernisse
     * @param x x-Koordinate
     * @param y y-Koordinate
     */
    public void generateSpawn(int x, int y) {
        for(int i = -1; i < 2; i++) {
            for(int ii = -1; ii < 2; ii++) {
                map[x + i][y + ii] = 0;
            }
        }
    }

    /**
     * GIbt die Breite der Welt zurück
     * @return Breite der Welt
     */
    public int getMapBorderX() {
        return mapBorderX;
    }

    /**
     * Gibt die Höhe der Welt zurück
     * @return Höhe der Welt
     */
    public int getMapBorderY() {
        return mapBorderY;
    }

    /**
     * Gibt x-Koordinate des Spieler-Spawns zurück
     * @return x-Koordinate des Spieler-Spawns
     */
    public int getPlayerSpawnX() {
        return playerSpawnX;
    }

    /**
     * Gibt y-Koordinate des Spieler-Spawns zurück
     * @return y-Koordinate des Spieler-Spawns
     */
    public int getPlayerSpawnY() {
        return playerSpawnY;
    }

    /**
     * Gibt x-Koordinate des LostSoul-Spawns zurück
     * @return
     */
    public int getLostSoulSpawnX() {
        return lostSoulSpawnX;
    }

    /**
     * Gibt y-Koordinate des LostSoul-Spawns zurück
     * @return
     */
    public int getLostSoulSpawnY() {
        return lostSoulSpawnY;
    }
}
