package main;

import entity.Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Hare extends Entity {
    final GamePanel gp;

    public Hare (GamePanel gp) {
        this.gp = gp;
        hitBox = new Rectangle(8, 2, 48, 60);
        iHitBoxDefaultX = hitBox.x;
        iHitBoxDefaultY = hitBox.y;
    //SET POSITION
        setDefaultValues();
    //GET IMAGE
        getHareImage();
    }

    public void setDefaultValues() {
        iWorldX = 17 * gp.iTileSize;
        iWorldY = 13 * gp.iTileSize;
    }
    public void getHareImage() {
        try {
            idle1 = ImageIO.read(new File("./res/entities/hare/idle1.png"));
            idle2 = ImageIO.read(new File("./res/entities/hare/idle2.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void update() {
    //RANDOMIZED MOVEMENT

    //COLLISION
        bCollisionDetected = false;
        gp.cCheck.checkTile(this);
    //STUCK PREVENTION
        if (bStuckTopLeft) {
            iWorldY++;
            iWorldX++;
        }
        if (bStuckTopRight) {
            iWorldY++;
            iWorldX--;
        }
        if (bStuckBotLeft) {
            iWorldY--;
            iWorldX++;
        }
        if (bStuckBotRight) {
            iWorldY--;
            iWorldX--;
        }
    }
    public void draw() {

    }
}
