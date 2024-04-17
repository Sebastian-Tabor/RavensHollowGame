package main;

import entity.ProjectileEntity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Method;

public class UtilityTool {
    public GamePanel gp;
    public Color ravensDarkGrey = new Color(35,35,35);
    public Color ravensGrey = new Color(60,60,60);
    public Color ravensLightGrey = new Color(100,100,100);
    public Color transparentBackground = new Color(0, 0, 0, 100);
    public Color damagedRed = new Color(100, 0, 0, 255);
    public Color healingGreen = new Color(0, 100, 50, 255);
    public Color reflectedGrey = new Color( 110, 110 ,150);

    public UtilityTool() {
    }

    public BufferedImage scaleImage(BufferedImage original, int width, int height){

        BufferedImage scaledImage = new BufferedImage(width, height, original.getType()==0?5:original.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original, 0,0, width, height, null);
        g2.dispose();

        return scaledImage;
    }
}
