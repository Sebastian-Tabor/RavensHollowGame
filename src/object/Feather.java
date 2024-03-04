package object;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Feather extends Object{

    public Feather() {
        sName = "Feather";
        try {
            biImage1 = ImageIO.read(new File("./res/objects/feather1.png"));
            biImage2 = ImageIO.read(new File("./res/objects/feather2.png"));
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
