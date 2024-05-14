package net.bilaljh.fubar;

public class Actor {

    public int life, mapX, mapY, score;
    public double angle, posX, posY, angleX, angleY, deltaDistX, deltaDistY, endX, endY;

    public void move(double offset, boolean isLostSoul) {                   //Siehe castRays() Methode von Raycaster.class
        angleX = Math.cos(angle);                                           //Berechnet Richtung der Bewegung
        angleY = Math.sin(angle);

        deltaDistX = Math.sqrt(1 + (angleY * angleY) / (angleX * angleX));
        deltaDistY = Math.sqrt(1 + (angleX * angleX) / (angleY * angleY));

        if (deltaDistX < deltaDistY) {                                      //Wenn z.B. horizontale Grenze näher ist, wird damit gearbeitet
            endX = getPosX() + deltaDistX * angleX;                         //Endpunkte der
            endY = getPosY() + deltaDistX * angleX;
        } else {
            endX = getPosX() + deltaDistY * angleY;
            endY = getPosY() + deltaDistY * angleY;
        }

                                                                            //Spieler: Wenn keine wand im Weg, aktualisiere Position
                                                                            //LostSoul: aktualisiere Position ohne Rücksicht auf Wände
        if ((Main.map.map[(int) ((posX + angleX * offset) / 64)][(int) ((posY + angleY * offset) / 64)] == 0) || isLostSoul) {
            posX += angleX * offset;
            posY += angleY * offset;
        }
    }
    public void move(double offset, String direction) {                     //Überladung der Methode zur seitlchen Bewegung
        switch(direction) {
            case "right":
                angleX = Math.cos(angle + Math.toRadians(90));              //90 Grad hinzugefügt/entfernt um seitlich zu bewegen
                angleY = Math.sin(angle + Math.toRadians(90));

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
                }
                break;
            case "left":
                angleX = Math.cos(angle - Math.toRadians(90));
                angleY = Math.sin(angle - Math.toRadians(90));

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

                if(Main.map.map[(int) ((posX + angleX * offset) / 64)][(int) ((posY + angleY * offset) / 64)] == 0) {
                    posX += angleX * offset;
                    posY += angleY * offset;
                }
                break;
        }

    }

    // -- Getter / Setter Methoden

    public void setLocation(int posX, int posY) {
        setPosX(posX);
        setPosY(posY);
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
    }
    public double getPosY() {
        return posY;
    }
    public void setPosY(int posY) {
        this.posY = posY;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
}
