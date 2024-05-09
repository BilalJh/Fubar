package net.bilaljh.fubar;

public class LostSoul extends Actor{

    public final int HEIGHT = Main.SCREEN_HEIGHT;

    public LostSoul(int posX, int posY, double angle) {
        this.posX = posX;
        this.posY = posY;
        this.angle = angle;

    }

    public void idle(double screenX, double screenY) {
        Player player = Main.player;

        double distance = Math.sqrt(((posX - player.getPosX()) * (posX - player.getPosX())) + ((posY - player.getPosY()) * (posY - player.getPosY())));
        double height = (16 * HEIGHT) / distance;

        if(height > HEIGHT) {
            height = HEIGHT;
        }

        Main.display.drawEnemy("file:src/resource/LS00.png", screenX, screenY, distance, Main.display.root);
    }

    public void die() {
        Player player = Main.player;
        player.setScore(player.getScore() + 100);
        System.out.println(player.getScore());
    }
}