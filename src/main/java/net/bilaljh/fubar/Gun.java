package net.bilaljh.fubar;

public class Gun {

    private final String[] spriteSheet = {
            "file:src/resource/Gun/Pistol0.png",
            "file:src/resource/Gun/Pistol1.png"
    };

    public void idle() {
        Main.display.drawGun(spriteSheet[0]);
    }
    public void fire() {
        Main.display.drawGun(spriteSheet[1]);
    }
}
