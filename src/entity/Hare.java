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
        direction = "idle";
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
//HARE SET ACTION
    public void setAction() {

        Random random = new Random();
        int iCounter = random.nextInt(9);

        switch (iCounter) {
            case 0:
                direction = "left";
                moveLeft();
                break;
            case 1:
                direction = "right";
                moveRight();
                break;
            case 2:
                direction = "jump";
                jump();
                break;
            case 3:
                direction = "left jump";
                moveLeft();
                jump();
                break;
            case 4:
                direction = "right jump";
                moveRight();
                jump();
                break;
            case 5:
                direction = "crouch";

                break;
            case 6:
                direction = "left crouch";
                moveLeft();
                break;
            case 7:
                direction = "right crouch";
                moveRight();
                break;
            case 8:
                direction = "idle";
                //DO NOTHING
                break;

        }
    }
}
