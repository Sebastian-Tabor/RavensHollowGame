package object;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Object {
     public BufferedImage biImage;
     public String sName;
     public boolean bCollision = false;
     public int iWorldX, iWorldY;
     public int iSpriteCounter = 0;
     public int iSpriteNumber = 1;

     public void update(){
          iSpriteCounter++;
          if (iSpriteCounter > 30) {
               if (iSpriteNumber == 2){
                    iSpriteNumber = 1;}
               else if (iSpriteNumber == 1) {
                    iSpriteNumber = 2;}
               iSpriteCounter = 0;
          }
     }

     public void draw(Graphics2D g2, GamePanel gp) {
          int iScreenX = iWorldX - gp.player.iWorldX + gp.player.iScreenX;
          int iScreenY = iWorldY - gp.player.iWorldY + gp.player.iScreenY;

          if (iWorldX + gp.iTileSize > gp.player.iWorldX - gp.player.iScreenX && iWorldX - gp.iTileSize < gp.player.iWorldX + gp.player.iScreenX) {
               g2.drawImage(biImage, iScreenX, iScreenY, gp.iTileSize, gp.iTileSize, null);
          }
     }
}
