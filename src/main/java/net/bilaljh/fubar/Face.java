package net.bilaljh.fubar;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Timer;

public class Face {

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

    private String[][] sideSheet = {
            {
                "file:src/resource/Left/STFL0.png",
                "file:src/resource/Left/STFL1.png",
                "file:src/resource/Left/STFL2.png",
                "file:src/resource/Left/STFL3.png",
                "file:src/resource/Left/STFL4.png"
            },
            {
                "file:src/resource/Right/STFR0.png",
                "file:src/resource/Right/STFR1.png",
                "file:src/resource/Right/STFR2.png",
                "file:src/resource/Right/STFR3.png",
                "file:src/resource/Right/STFR4.png"
            },
    };

    private String[] ouch = {
            "file:src/resource/Ouch/STFOUCH0.png",
            "file:src/resource/Ouch/STFOUCH1.png",
            "file:src/resource/Ouch/STFOUCH2.png",
            "file:src/resource/Ouch/STFOUCH3.png",
            "file:src/resource/Ouch/STFOUCH4.png",
    };
    private String[] evil = {
            "file:src/resource/Evil/STFEVL0.png",
            "file:src/resource/Evil/STFEVL1.png",
            "file:src/resource/Evil/STFEVL2.png",
            "file:src/resource/Evil/STFEVL3.png",
            "file:src/resource/Evil/STFEVL4.png",
    };
    private String[] kill = {
            "file:src/resource/Kill/STFKILL0.png",
            "file:src/resource/Kill/STFKILL1.png",
            "file:src/resource/Kill/STFKILL2.png",
            "file:src/resource/Kill/STFKILL3.png",
            "file:src/resource/Kill/STFKILL4.png",
    };
    private String dead = "file:src/resource/STFDEAD0.png";
    public String god = "file:src/resource/STFGOD0.png";

    public String current;
    private Image image;
    private ImageView imageView;
    private int middleX;
    private long lastMark;
    public Face() {
            middleX = (int) (Main.display.getGAME_WIDTH() + ((Main.display.getWIDTH() - Main.display.getGAME_WIDTH()) / 2));
            current = standardSheet[0][1];
    }

    public void idle() {
        Player player = Main.player;
        int bloodLevel;
        if(player.getLife() >= 80) {
            bloodLevel = 0;
        } else if(player.getLife() >= 60) {
            bloodLevel = 1;
        } else if(player.getLife() >= 40) {
            bloodLevel = 2;
        } else if(player.getLife() >= 20) {
            bloodLevel = 3;
        } else if(player.getLife() >= 1) {
            bloodLevel = 4;
        } else {
            bloodLevel = 4;
        }


        drawFace(standardSheet[bloodLevel][1]);
        mark();

        if(millisElapsed() > 1000) {
            drawFace(standardSheet[bloodLevel][0]);
            System.out.println(bloodLevel + "." + 0);
            mark();
        }
        if(millisElapsed() > 1000) {
            drawFace(standardSheet[bloodLevel][2]);
            System.out.println(bloodLevel + "." + 2);
            mark();
        }
        if(millisElapsed() > 1000) {
            drawFace(standardSheet[bloodLevel][1]);
            System.out.println(bloodLevel + "." + 1);
            mark();
        }
    }

    public void drawFace(String currentString) {
        drawImage(image, currentString, imageView, middleX - 50, Main.display.getHEIGHT() / 2 - 52, Main.display.root);
    }
    public void drawImage(Image image, String file, ImageView view, double x, double y, Group group) {
        image = new Image(file);
        view = new ImageView(image);
        view.setX(x);
        view.setY(y);

        group.getChildren().add(view);
    }

    public void mark() {
        lastMark = System.currentTimeMillis();
    }
    public long millisElapsed() {
        return System.currentTimeMillis() - lastMark;
    }
}
