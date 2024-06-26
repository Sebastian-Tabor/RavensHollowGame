package object;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Bone extends SuperObject {

    public Bone() {
        sName = "Bone";
        try {
            if (iSpriteNumber == 1) {
                image = ImageIO.read(new File("./res/objects/bone1.png"));
            } else {
                image = ImageIO.read(new File("./res/objects/bone2.png"));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
