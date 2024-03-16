package main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UtilityTool {

    public BufferedImage scaleImage(BufferedImage original, int width, int height){

        BufferedImage scaledImage = new BufferedImage(width, height, original.getType()==0?5:original.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original, 0,0, width, height, null);
        g2.dispose();

        return scaledImage;
    }
    public int findDistance(int x1, int y1, int x2, int y2){
        int a = (y1/x1)^2;
        int b = (y2/x2)^2;
        int c = a + b;
        c = (int) Math.sqrt(c);
        return c;
    }
}
