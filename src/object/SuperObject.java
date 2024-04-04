package object;

import main.GamePanel;
import main.UtilityTool;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {
//IMAGE AND STRING
     public BufferedImage image;
     public String sName;
     public UtilityTool uTool = new UtilityTool();
//POSITION
     public int iWorldX, iWorldY;
//SPRITE VARIABLES
     public int iSpriteCounter = 0;
     public int iSpriteNumber = 1;
//HITBOX
     public Rectangle hitBox = new Rectangle(8,8, 48, 48);
     public int iHitBoxDefaultX = 0;
     public int iHitBoxDefaultY = 0;
//STATUS BOOLEANS
     public boolean bStorable;
//UPDATE METHOD
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
//DRAW METHOD
     public void draw(Graphics2D g2, GamePanel gp) {
          int iScreenX = iWorldX - gp.player.iWorldX + gp.player.iScreenPosX;
          int iScreenY = iWorldY - gp.player.iWorldY + gp.player.iScreenPosY;

          if (iWorldX + gp.iTileSize > gp.player.iWorldX - gp.player.iScreenPosX && iWorldX - gp.iTileSize < gp.player.iWorldX + gp.player.iScreenPosX) {
               g2.drawImage(image, iScreenX, iScreenY, gp.iTileSize, gp.iTileSize, null);
          }
     }
}
