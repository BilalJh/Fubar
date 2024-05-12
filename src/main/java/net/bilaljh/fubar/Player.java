package net.bilaljh.fubar;

public class Player extends Actor {

    private int[][] map;

    public Player(double posX, double posY, double angle) {
        map = Main.map.map;
        score = 0;

        this.posX = posX;
        this.posY = posY;
        this.angle = angle; //In Bogenmaß!
        setLife(100);
    }

    public void fire() {
        Main.engine.castShot();
    }
}
