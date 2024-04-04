package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Arrow extends SuperObject {

    GamePanel gp;
    public Rectangle hitBox = new Rectangle(0, 0, 32, 16);
    public int iHitBoxDefaultX = 0;
    public int iHitBoxDefaultY = 0;


    public Arrow(GamePanel gp, int damage, int speed) {

        this.gp = gp;
        sName = "Arrow";

        try {
            if (iSpriteNumber == 1) {
                image = ImageIO.read(new File("./res/objects/feather1.png"));
                uTool.scaleImage(image, gp.iTileSize, gp.iTileSize);
            } else {
                image = ImageIO.read(new File("./res/objects/feather2.png"));
                uTool.scaleImage(image, gp.iTileSize, gp.iTileSize);
            }
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }
}
