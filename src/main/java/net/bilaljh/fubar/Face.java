package net.bilaljh.fubar;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Face {

    //SpriteSheets für Gesicht
    private String[][] standardSheet = {
            {
                "file:src/resource/Standard/STFST00.png",
                "file:src/resource/Standard/STFST01.png",
                "file:src/resource/Standard/STFST02.png"
            },
            {
                "file:src/resource/Standard/STFST10.png",
                "file:src/resource/Standard/STFST11.png",
                "file:src/resource/Standard/STFST12.png"
            },
            {
                "file:src/resource/Standard/STFST20.png",
                "file:src/resource/Standard/STFST21.png",
                "file:src/resource/Standard/STFST22.png"
            },
            {
                "file:src/resource/Standard/STFST30.png",
                "file:src/resource/Standard/STFST31.png",
                "file:src/resource/Standard/STFST32.png"
            },
            {
                "file:src/resource/Standard/STFST40.png",
                "file:src/resource/Standard/STFST41.png",
                "file:src/resource/Standard/STFST42.png"
            }
    };
    private String[] evil = {
            "file:src/resource/Evil/STFEVL0.png",
            "file:src/resource/Evil/STFEVL1.png",
            "file:src/resource/Evil/STFEVL2.png",
            "file:src/resource/Evil/STFEVL3.png",
            "file:src/resource/Evil/STFEVL4.png",
    };
    private String[] ouch = {
            "file:src/resource/Ouch/STFOUCH0.png",
            "file:src/resource/Ouch/STFOUCH1.png",
            "file:src/resource/Ouch/STFOUCH2.png",
            "file:src/resource/Ouch/STFOUCH3.png",
            "file:src/resource/Ouch/STFOUCH4.png",
    };
    private String god = "file:src/resource/STFGOD0.png";
    private String dead = "file:src/resource/STFDEAD0.png";

    private String current;
    private int bloodLevel;
    private long marker;                    //Timer Vsriable
    private boolean isEvil;                 //variablen für Gesichtsausdruck
    private boolean isHurt;

    public Face() {
        bloodLevel = 0;
        current = standardSheet[bloodLevel][1];
        isEvil = false;
        isHurt = false;
    }

    public void idle() {                    //Methode zum Animieren des Gesicht
        Player player = Main.player;

        if(player.getLife() > 100) {                    //Je nach Anzahl der Leben wird Wert für Blut im Gesicht geändert
            current = god;
        } else if(player.getLife() >= 80) {
            bloodLevel = 0;
            current = standardSheet[bloodLevel][1];
        } else if(player.getLife() >= 60) {
            bloodLevel = 1;
            current = standardSheet[bloodLevel][1];
        } else if(player.getLife() >= 40) {
            bloodLevel = 2;
            current = standardSheet[bloodLevel][1];
        } else if(player.getLife() >= 20) {
            bloodLevel = 3;
            current = standardSheet[bloodLevel][1];
        } else if(player.getLife() >= 1) {
            bloodLevel = 4;
            current = standardSheet[bloodLevel][1];
        } else {
            bloodLevel = 4;
            current = dead;
        }

        if(isEvil) {                                    //Bei Abschuss vom Gegner besonderer Ausdrück
            if(System.currentTimeMillis() - marker > 1000) {
                isEvil = false;
            } else {
                drawFace(evil[bloodLevel]);
            }
        } else if(isHurt) {                             //Bei Schaden besonderer Ausdrück
            if(System.currentTimeMillis() - marker > 1000) {
                isHurt = false;
            } else {
                drawFace(ouch[bloodLevel]);
            }
        } else {
            drawFace(current);
        }
    }

    public void drawFace(String currentString) {        //Methode zum Zeichnen des Gesichts
        Image face = new Image(currentString);
        ImageView faceView = new ImageView(face);
        Main.display.drawPicture(faceView, Main.SCREEN_WIDTH - 75 - 45, Main.SCREEN_HEIGHT / 2 - 52, Main.display.getRoot());
    }

    public void setEvil() {
        isEvil = true;
        setMark();
    }
    public void setHurt() {
        isHurt = true;
        setMark();
    }


    public void setMark() {                             //Methode zum Speichern der Zeit
        marker = System.currentTimeMillis();
    }
}