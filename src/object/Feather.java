package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Feather extends SuperObject {
    GamePanel gp;
    public Feather(GamePanel gp) {
        this.gp = gp;

        sName = "Feather";
        try {
            if (iSpriteNumber == 1) {
                image = ImageIO.read(new File("./res/objects/feather1.png"));
                uTool.scaleImage(image, gp.iTileSize, gp.iTileSize);
            } else {
                image = ImageIO.read(new File("./res/objects/feather2.png"));
                uTool.scaleImage(image, gp.iTileSize, gp.iTileSize);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
