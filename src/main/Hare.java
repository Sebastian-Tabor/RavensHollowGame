package main;

import entity.Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Hare extends Entity {

    public Hare (GamePanel gp) {
        super (gp);
        hitBox = new Rectangle(8, 2, 48, 60);
        iHitBoxDefaultX = hitBox.x;
        iHitBoxDefaultY = hitBox.y;
        direction = "idle";
        iSpeed = 12;
    //GET IMAGE
        getHareImage();
    }
    public BufferedImage setup(String imageName) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage scaledImage = null;
        try {
            scaledImage = ImageIO.read(new File(imageName + ".png"));
            scaledImage = uTool.scaleImage(scaledImage, gp.iTileSize, gp.iTileSize);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return scaledImage;
    }
    public void getHareImage() {

            left1 = setup("./res/entities/hare/left1");
            left2 = setup("./res/entities/hare/left2");
            right1 = setup("./res/entities/hare/right1");
            right2 = setup("./res/entities/hare/right2");
            jump1 = setup("./res/entities/hare/jump1");
            jump2 = setup("./res/entities/hare/jump2");
            leftjump1 = setup("./res/entities/hare/leftjump1");
            leftjump2 = setup("./res/entities/hare/leftjump2");
            rightjump1 = setup("./res/entities/hare/rightjump1");
            rightjump2 = setup("./res/entities/hare/rightjump2");
            crouch1 = setup("./res/entities/hare/crouch1");
            crouch2 = setup("./res/entities/hare/crouch2");
            idle1 = setup("./res/entities/hare/idle1");
            idle2 = setup("./res/entities/hare/idle2");

    }
    public void setAction() {
        Random random = new Random();
        int iCounter = random.nextInt(7);

        switch (iCounter) {
            case 0:
                direction = "left";
                break;
            case 1:
                direction = "right";
                break;
            case 2:
                direction = "jump";
                break;
            case 3:
                direction = "left jump";
                break;
            case 4:
                direction = "right jump";
                break;
            case 5:
                direction = "crouch";
                break;
            case 6:
                direction = "idle";
                //DO NOTHING
                break;

        }
    }
}
