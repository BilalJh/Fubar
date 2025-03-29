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

    /**
     * Regeneriert Kondition
     */
    public void regenerate() {
        int random = randomizer.nextInt(10000);

        if(random == 0) {
            setLife(getLife() + 15);
        }
    }

    /**
     * Feuert einen Schuss ab
     */
    public void fire() {                                                        //Methode zum Schießen
        Main.engine.castShot();

        fired = true;
        Main.display.soundPlayer = new MediaPlayer(Main.display.pistolSound);
        Main.display.setVolume(Main.display.soundPlayer, Main.soundVolume);
        Main.display.playSound(Main.display.soundPlayer);
        Main.display.soundPlayer = new MediaPlayer(Main.display.pistolSound);
    }

    /**
     * Überprüft, ob der Spieler einen Schuss abgegeben hat
     * @return true, wenn der Spieler einen Schuss abgegeben hat
     */
    public boolean isFired() {
        return fired;
    }

    /**
     * Setzt den Schussstatus des Spielers
     * @param fired true, wenn der Spieler einen Schuss abgegeben hat
     */
    public void setFired(boolean fired) {
        this.fired = fired;
    }

    /**
     * Setzt den Marker auf die aktuelle Zeit
     */
    public void setMark() {
        marker = System.currentTimeMillis();
    }

    /**
     * Gibt den Marker zurück
     * @return Marker
     */
    public long getMarker() {
        return marker;
    }
}
