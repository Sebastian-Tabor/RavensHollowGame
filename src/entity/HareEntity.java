package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

public class HareEntity extends Entity implements ActionsAI, HasImages{

    public HareEntity(GamePanel gp) {
        super (gp);
        direction = "idle";
        facing  = "right";
        moveState = "idle";
        speedOriginal = 6;
        type = 1;
        healthMax = 10;
        health = healthMax;
    //GET IMAGE
        getImage();
    }
//HARE IMAGE SETUP METHOD
    @Override
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
    @Override
    public void getImage() {


            right1 = setup("./res/entities/npcs/hare/right1");
            right2 = setup("./res/entities/npcs/hare/right2");
            jump1 = setup("./res/entities/npcs/hare/jump1");
            jump2 = setup("./res/entities/npcs/hare/jump2");
            crouch1 = setup("./res/entities/npcs/hare/crouch1");
            crouch2 = setup("./res/entities/npcs/hare/crouch2");
            idle1 = setup("./res/entities/npcs/hare/idle1");
            idle2 = setup("./res/entities/npcs/hare/idle2");
            dying1 = setup("./res/entities/npcs/hare/dying1");
            dying2 = setup("./res/entities/npcs/hare/dying2");
            dying3 = setup("./res/entities/npcs/hare/dying3");
            dying4 = setup("./res/entities/npcs/hare/dying4");

    }
//HARE SET ACTION
    @Override
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
                direction = "idle";
                //DO NOTHING
                break;

        }
    }
}
