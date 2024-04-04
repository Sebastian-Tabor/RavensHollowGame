package entity;

import main.GamePanel;
import main.UtilityTool;
import org.w3c.dom.Entity;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

public class Dummy extends SuperEntity {

        public Dummy (GamePanel gp) {
            super (gp);
            direction = "idle";
            iSpeedOriginal = 0;
            iCollisionDmg = 1;
            //GET IMAGE
            getImage();
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
        public void getImage() {

            idle1 = setup("./res/entities/hare/idle1");
            idle2 = setup("./res/entities/hare/idle2");

        }
        //HARE SET ACTION
        public void setAction() {

            direction = "idle";
            //DO NOTHING
        }
        public void update() {
            gp.cCheck.checkTile(this);
            if (bWouldBeStuck) --iWorldY;
            bCollisionDetected = false;
            //JUMP CONDITIONS
            if (bCollisionBottom) {
                iVelocityY = 0;
            }
            else {
                ++iWorldY;
            }

        }
}

