package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

public class Hare extends SuperEntity {

    public Hare (GamePanel gp) {
        super (gp);
        direction = "left";
        moveState = "idle";
        iSpeedOriginal = 6;
    //GET IMAGE
        getHareImage();
    }
//HARE IMAGE SETUP METHOD
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
//HARE IMAGES
    public void getHareImage() {


            right1 = setup("./res/entities/hare/right1");
            right2 = setup("./res/entities/hare/right2");
            jump1 = setup("./res/entities/hare/jump1");
            jump2 = setup("./res/entities/hare/jump2");
            crouch1 = setup("./res/entities/hare/crouch1");
            crouch2 = setup("./res/entities/hare/crouch2");
            idle1 = setup("./res/entities/hare/idle1");
            idle2 = setup("./res/entities/hare/idle2");

    }
//HARE SET ACTION
    public void setAction() {

        Random random = new Random();
        int iCounter = random.nextInt(9);

        switch (iCounter) {
            case 0:
                direction = "left";
                facing = direction;
                moveLeft();
                break;
            case 1:
                direction = "right";
                facing = direction;
                moveRight();
                break;
            case 2:
                moveState = "jump";
                jump();
                break;
            case 3:
                moveState = "crouch";

                break;
            case 8:
                moveState = "idle";
                //DO NOTHING
                break;

        }
    }
}
