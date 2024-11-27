package net.bilaljh.fubar;

import javafx.scene.media.MediaPlayer;

import java.util.Random;

public class Player extends Actor {

    private int[][] map;
    private long marker;
    private Random randomizer;
    private boolean fired;

    public Player(double posX, double posY, double angle) {
        map = Main.map.map;
        score = 0;
        marker = 0;
        randomizer = new Random();
        fired = false;

        this.posX = posX;
        this.posY = posY;
        this.angle = angle; //In Bogenmaß!
        setLife(100);
    }

    public void regenerate() {                                                  //Methode um Teil der Spielerleben zu generieren
        int random = randomizer.nextInt(10000);

        if(random == 0) {
            setLife(getLife() + 15);
        }
    }

    public void fire() {                                                        //Methode zum Schießen
        Main.engine.castShot();

        fired = true;
        Main.display.soundPlayer = new MediaPlayer(Main.display.pistolSound);
        Main.display.setVolume(Main.display.soundPlayer, Main.soundVolume);
        Main.display.playSound(Main.display.soundPlayer);
        Main.display.soundPlayer = new MediaPlayer(Main.display.pistolSound);
    }

    public boolean isFired() {
        return fired;
    }
    public void setFired(boolean fired) {
        this.fired = fired;
    }

    // -- Methoden für Timer
    public void setMark() {
        marker = System.currentTimeMillis();
    }
    public long getMarker() {
        return marker;
    }
}
