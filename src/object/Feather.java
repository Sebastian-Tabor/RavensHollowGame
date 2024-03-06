package object;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Feather extends Object {

    public Feather() {
        sName = "Feather";
        try {
            if (iSpriteNumber == 1) {
                biImage = ImageIO.read(new File("./res/objects/feather1.png"));
            } else {
                biImage = ImageIO.read(new File("./res/objects/feather2.png"));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
