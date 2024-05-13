package net.bilaljh.fubar;

import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

import java.util.Random;

public class LostSoul extends Actor{

    public LostSoul(int posX, int posY, double angle) {
        this.posX = posX;
        this.posY = posY;
        this.angle = angle;
    }

    public void show(double screenX, double screenY) {
        Player player = Main.player;
        double distance = Math.sqrt(((posX - player.getPosX()) * (posX - player.getPosX())) + ((posY - player.getPosY()) * (posY - player.getPosY())));

        if(!(player.getPosX() == posX && player.getPosY() == posY)) {
            Main.display.drawEnemy("file:src/resource/LS00.png", screenX, screenY, distance, Main.display.getRoot());
        }
    }

    public void idle() {
        Player player = Main.player;

        angle = Math.atan2(player.getPosY() - posY, player.getPosX() - posX);

        if(player.getLife() <= 0) {
            player.setLife(0);
        }

        move(1, true);
        if(((int) (player.getPosX()) == (int) (posX)) && ((int) (player.getPosY()) == (int) (posY))) {
            attack();
        }
    }

    public void attack() {
        Player player = Main.player;
        Display display = Main.display;

        player.setLife(player.getLife() - 25);

        display.soundPlayer = new MediaPlayer(display.painSound);
        display.setVolume(display.soundPlayer, Main.soundVolume);
        display.playSound(display.soundPlayer);
        display.soundPlayer = new MediaPlayer(display.painSound);

        Main.face.setHurt();
        if(player.getLife() <= 0) {
            display.soundPlayer = new MediaPlayer(display.deathSound);
            display.setVolume(display.soundPlayer, Main.soundVolume);
            display.playSound(display.soundPlayer);
            display.soundPlayer = new MediaPlayer(display.deathSound);
            player.setLife(0);
        }
        display.drawRect(0, 0, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT, Color.RED, 0.5, Main.display.getRoot());

        respawn();
    }

    public void die() {
        Player player = Main.player;
        player.setScore(player.getScore() + 100);
        System.out.println(player.getScore());

        Main.display.soundPlayer = new MediaPlayer(Main.display.lostSoulSound);
        Main.display.setVolume(Main.display.soundPlayer, Main.soundVolume);
        Main.display.playSound(Main.display.soundPlayer);
        Main.display.soundPlayer = new MediaPlayer(Main.display.lostSoulSound);

        Main.face.setEvil();

        respawn();
    }

    public void respawn() {
        Map map = Main.map;
        Random randomizer = new Random();
        int x = randomizer.nextInt(map.getMapBorderX() - 1) + 1;
        int y = randomizer.nextInt(map.getMapBorderY() - 1) + 1;

        setLocation(x * 64 + 32, y * 64 + 32);
    }
}