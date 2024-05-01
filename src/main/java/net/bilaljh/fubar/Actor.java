package net.bilaljh.fubar;

public class Actor {

    public int cellX, cellY, life;
    public double angle, posX, posY, deltaX, deltaY;


    public void move(double offset) {
        if(!isColliding(offset)) {
            deltaX = Math.cos(angle);
            deltaY = Math.sin(angle);
            posX += deltaX * offset;
            posY += deltaY * offset;
            cellX = (int) (posX / 64);
            cellY = (int) (posY / 64);
        }
    }

    public void setLocation(double posX, double posY) {
        this.posX = posX;
        this.posY = posY;
        this.cellX = (int) (this.posX / 64);
        this.cellY = (int) (this.posY / 64);
        System.out.println("posX: " + this.posX + " posY: " + this.posY);
    }

    public boolean isColliding() {
        if(cellX > 0 || cellY > 0) {
            return false;
        } else {
            return true;
        }
    }
    public boolean isColliding(double offset) {
        if(Main.map.map[(int) ((posX + offset) / 64)][(int) ((posY + offset) / 64)] == 0) {
            return false;
        } else {
            return true;
        }
    }
    public boolean isColliding(Actor other) {
        if(posX == other.posX && posY == other.posY) {
            return true;
        } else {
            return false;
        }
    }


    public int getCellX() {
        return cellX;
    }
    public void setCellX(int cellX) {
        this.cellX = cellX;
        this.posX = cellX * 64;
    }
    public int getCellY() {
        return cellY;
    }
    public void setCellY(int cellY) {
        this.cellY = cellY;
        this.posY = cellY * 64;
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
}
