package net.bilaljh.fubar;

public class Player extends Actor {

    private int[][] map;

    public Player(double posX, double posY, double angle, int life) {
        map = Main.map.map;
        score = 0;

        this.posX = posX;
        this.posY = posY;
        cellX = (int) (posX / 64);
        cellY = (int) (posY / 64);
        this.angle = angle; //In Bogenmaß!
        this.life = life;
    }

    public void fire() {
        Main.engine.castShot();
    }
}
