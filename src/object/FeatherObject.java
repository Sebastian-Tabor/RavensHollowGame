package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class FeatherObject extends Object {
    GamePanel gp;
    public FeatherObject(GamePanel gp) {
        this.gp = gp;

        sName = "Feather";
        try {
            if (iSpriteNumber == 1) {
                image = ImageIO.read(new File("./res/objects/feather1.png"));
                uTool.scaleImage(image, gp.tileSize, gp.tileSize);
            } else {
                image = ImageIO.read(new File("./res/objects/feather2.png"));
                uTool.scaleImage(image, gp.tileSize, gp.tileSize);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
