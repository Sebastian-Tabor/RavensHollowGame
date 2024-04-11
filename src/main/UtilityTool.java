package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Method;

public class UtilityTool {
    public GamePanel gp;
    public Graphics2D g2;
    public Color ravensDarkGrey = new Color(35,35,35);
    public Color ravensGrey = new Color(60,60,60);
    public Color ravensLightGrey = new Color(100,100,100);

    public BufferedImage scaleImage(BufferedImage original, int width, int height){

        BufferedImage scaledImage = new BufferedImage(width, height, original.getType()==0?5:original.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original, 0,0, width, height, null);
        g2.dispose();

        return scaledImage;
    }
}
