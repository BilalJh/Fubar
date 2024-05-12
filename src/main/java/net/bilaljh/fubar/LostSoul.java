package net.bilaljh.fubar;

import javafx.scene.paint.Color;

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
            Main.display.drawEnemy("file:src/resource/LS00.png", screenX, screenY, distance, Main.display.root);
        }
    }

    public void idle() {
        Player player = Main.player;

        angle = Math.atan2(player.getPosY() - posY, player.getPosX() - posX);

        if(player.getLife() <= 0) {
            player.setLife(0);
            Main.gameOver = true;
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
        if(player.getLife() <= 0) {
            player.setLife(0);
            Main.gameOver = true;
        }
        display.drawRect(0, 0, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT, Color.RED, 0.5, Main.display.root);
        respawn();
    }

    public void die() {
        Player player = Main.player;
        player.setScore(player.getScore() + 100);
        System.out.println(player.getScore());

        respawn();
    }
}