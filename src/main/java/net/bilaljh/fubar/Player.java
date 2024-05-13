package net.bilaljh.fubar;

import java.util.Random;

public class Player extends Actor {

    private int[][] map;
    private long marker;
    private Random randomizer;

    public Player(double posX, double posY, double angle) {
        map = Main.map.map;
        score = 0;
        marker = 0;
        randomizer = new Random();

        this.posX = posX;
        this.posY = posY;
        this.angle = angle; //In Bogenmaß!
        setLife(100);
    }

    public void regenerate() {
        int random = randomizer.nextInt(10000);
        System.out.println(Main.musicVolume);

        if(random == 0) {
            setLife(getLife() + 15);
        }
    }

    public void fire() {
        Main.engine.castShot();
    }

    public void setMark() {
        marker = System.currentTimeMillis();
    }
    public long getMarker() {
        return marker;
    }
}
