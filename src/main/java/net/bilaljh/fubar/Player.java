package net.bilaljh.fubar;

public class Player extends Actor {

    private int[][] map = Main.map.map;

    public Player(double posX, double posY, double angle, int life) {
        this.posX = posX;
        this.posY = posY;
        cellX = (int) (posX / 64);
        cellY = (int) (posY / 64);
        this.angle = angle; //In Bogenmaß!
        this.life = life;
    }

//    public void moveForward() {
//        int collisionX = (int) (posX + 15);
//        int collisionY = (int) (posY + 15);
//        if(map[collisionX / 64][collisionY / 64] == 0){
//            deltaX = Math.cos(angle);
//            deltaY = Math.sin(angle);
//            posX += deltaX * 10;
//            posY += deltaY * 10;
//            cellX = (int) (posX / 64);
//            cellY = (int) (posY / 64);
//            life--;
//        }
//    }
//    public void moveBackward() {
//        int collisionX = (int) (posX - 15);
//        int collisionY = (int) (posY - 15);
//        if(map[collisionX / 64][collisionY / 64] == 0){
//            deltaX = Math.cos(angle);
//            deltaY = Math.sin(angle);
//            posX -= deltaX * 5;
//            posY -= deltaY * 5;
//            cellX = (int) (posX / 64);
//            cellY = (int) (posY / 64);
//        }
//    }
}
