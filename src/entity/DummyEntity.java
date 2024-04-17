package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class DummyEntity extends Entity implements ActionsAI{

        public DummyEntity(GamePanel gp) {
            super (gp);
            direction = "idle";
            type = 2;
            speedOriginal = 0;
            collisionDmg = 1;
            healthMax = 10;
            health = healthMax;
            immune = false;
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

            idle1 = setup("./res/entities/npcs/hare/idle1");
            idle2 = setup("./res/entities/npcs/hare/idle2");
            dying1 = setup("./res/entities/npcs/hare/dying1");
            dying2 = setup("./res/entities/npcs/hare/dying2");
            dying3 = setup("./res/entities/npcs/hare/dying3");
            dying4 = setup("./res/entities/npcs/hare/dying4");

        }
        //HARE SET ACTION
        public void setAction() {
            facing = "left";
            direction = "idle";
            moveState = "idle";
            //DO NOTHING
        }
}

