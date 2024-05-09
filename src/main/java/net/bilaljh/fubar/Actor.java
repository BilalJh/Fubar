package net.bilaljh.fubar;

public class Actor {

    public int cellX, cellY, life, mapX, mapY, score;
    public double angle, posX, posY, angleX, angleY, deltaDistX, deltaDistY, endX, endY;
    public Ray shot;


    public void move(double offset) {
        angleX = Math.cos(angle);
        angleY = Math.sin(angle);

        deltaDistX = Math.sqrt(1 + (angleY * angleY) / (angleX * angleX));
        deltaDistY = Math.sqrt(1 + (angleX * angleX) / (angleY * angleY));

        if (deltaDistX < deltaDistY) {
            endX = getPosX() + deltaDistX * angleX;
            endY = getPosY() + deltaDistX * angleX;
        } else {
            endX = getPosX() + deltaDistY * angleY;
            endY = getPosY() + deltaDistY * angleY;
        }

        mapX = (int) (endX / 64);
        mapY = (int) (endY / 64);

        if (Main.map.map[(int) ((posX + angleX * offset) / 64)][(int) ((posY + angleY * offset) / 64)] == 0) {
            posX += angleX * offset;
            posY += angleY * offset;
            cellX = (int) (posX / 64);
            cellY = (int) (posY / 64);
        }
    }


    public int getLife() {
        return life;
    }
    public void setLife(int life) {
        this.life = life;
    }
    public double getAngle() {
        return angle;
    }
    public void setAngle(double angle) {
        this.angle = angle;
    }
    public double getPosX() {
        return posX;
    }
    public void setPosX(int posX) {
        this.posX = posX;
        this.cellX = posX / 64;
    }
    public double getPosY() {
        return posY;
    }
    public void setPosY(int posY) {
        this.posY = posY;
        this.cellY = posY / 64;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
}
