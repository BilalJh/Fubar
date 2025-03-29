package net.bilaljh.fubar;

public abstract class Actor {

    public int life, mapX, mapY, score;
    public double angle, posX, posY, angleX, angleY, deltaDistX, deltaDistY, endX, endY;

    /**
     * Bewegt den Actor in seine Blickrichtung
     * @param offset Schrittweite
     * @param isLostSoul Existent als LostSoul
     */
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

    /**
     * Bewegt den Actor relativ zu seiner Blickrichtung
     * @param offset Schrittweite
     * @param direction Richtung
     */
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

    /**
     * Setzt die Position des Actors
     * @param posX x-Koordinate
     * @param posY y-Koordinate
     */
    public void setLocation(int posX, int posY) {
        setPosX(posX);
        setPosY(posY);
    }

    /**
     * Gibt Lebenspunkte des Actors zurück
     * @return Lebenspunkte
     */
    public int getLife() {
        return life;
    }

    /**
     * Setzt Lebenspunkte des Actors
     * @param life Lebenspunkte
     */
    public void setLife(int life) {
        this.life = life;
    }

    /**
     * Gibt die Blickrichtung des Actors zurück
     * @return Blickrichtung
     */
    public double getAngle() {
        return angle;
    }

    /**
     * Setzt die Blickrichtung des Actors
     * @param angle Blickrichtung
     */
    public void setAngle(double angle) {
        this.angle = angle;
    }

    /**
     * Gibt die x-Koordinate des Actors zurück
     * @return x-Koordinate
     */
    public double getPosX() {
        return posX;
    }

    /**
     * Setzt die x-Koordinate des Actors
     * @param posX x-Koordinate
     */
    public void setPosX(int posX) {
        this.posX = posX;
    }

    /**
     * Gibt die y-Koordinate des Actors zurück
     * @return y-Koordinate
     */
    public double getPosY() {
        return posY;
    }

    /**
     * Setzt die y-Koordinate des Actors
     * @param posY y-Koordinate
     */
    public void setPosY(int posY) {
        this.posY = posY;
    }

    /**
     * Gibt den Punktestand zurück
     * @return Punktestand
     */
    public int getScore() {
        return score;
    }

    /**
     * Setzt den Punktestand
     * @param score Punktestand
     */
    public void setScore(int score) {
        this.score = score;
    }
}
