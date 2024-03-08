package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UserInterface {
    public GamePanel gp;
    Font defaultFont;
    public BufferedImage image;
    public boolean bMessageOn = false;

    public UserInterface(GamePanel gp) {
        this.gp = gp;
        setFonts();
        setImage();
    }

    public void update(){

    }

    public void draw(Graphics2D g2){
        g2.drawImage(image, 0 ,0,gp.iScreenWidth,gp.iScreenHeight,null);
        // if (bMessageOn) {
        //    g2.drawString(message, 0, 0);
        //}
    }

    public void setFonts(){
        defaultFont = new Font("Arial", Font.PLAIN, 40);
    }
    public void setImage() {
        try {
            image = ImageIO.read(new File("./res/ui/ui1.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
