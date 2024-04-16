package entity;

import main.GamePanel;
import main.UtilityTool;
import org.w3c.dom.Entity;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

public class Dummy extends SuperEntity implements ActionsAI{

        public Dummy (GamePanel gp) {
            super (gp);
            direction = "idle";
            iType = 2;
            iSpeedOriginal = 0;
            iCollisionDmg = 1;
            iHealthMax = 10;
            iHealth = iHealthMax;
            bImmune = false;
            //GET IMAGE
            getImage();
        }
        //HARE IMAGE SETUP METHOD
        public BufferedImage setup(String imageName) {
            BufferedImage scaledImage = null;
            try {
                scaledImage = ImageIO.read(new File(imageName + ".png"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return scaledImage;
        }
        //HARE IMAGES
        public void getImage() {

            idle1 = setup("./res/entities/hare/idle1");
            idle2 = setup("./res/entities/hare/idle2");
            dying1 = setup("./res/entities/hare/dying1");
            dying2 = setup("./res/entities/hare/dying2");
            dying3 = setup("./res/entities/hare/dying3");
            dying4 = setup("./res/entities/hare/dying4");

        }
        //HARE SET ACTION
        public void setAction() {
            facing = "left";
            direction = "idle";
            moveState = "idle";
            //DO NOTHING
        }
}

