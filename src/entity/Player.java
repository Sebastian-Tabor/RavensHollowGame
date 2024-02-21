package entity;
import main.GamePanel;
import main.KeyBinds;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    final GamePanel gamePanel;
    final KeyBinds keyBinds;
    public final int iScreenX;
    public int iScreenY;
    public Player(GamePanel gamePanel, KeyBinds keyBinds) {
        this.gamePanel = gamePanel;
        this.keyBinds = keyBinds;
        iScreenX = (gamePanel.iScreenWidth/2 - gamePanel.iTileSize/2);
        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        iPlayerX = 100;
        iPlayerY = 540;
        speed = 10;
        direction = "idle";
    }

    public void getPlayerImage() {
        try {
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/left2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/right2.png"));
            jump1 = ImageIO.read(getClass().getResourceAsStream("/player/jump1.png"));
            jump2 = ImageIO.read(getClass().getResourceAsStream("/player/jump2.png"));
            leftjump1 = ImageIO.read(getClass().getResourceAsStream("/player/leftjump1.png"));
            leftjump2 = ImageIO.read(getClass().getResourceAsStream("/player/leftjump2.png"));
            rightjump1 = ImageIO.read(getClass().getResourceAsStream("/player/rightjump1.png"));
            rightjump2 = ImageIO.read(getClass().getResourceAsStream("/player/rightjump2.png"));
            crouch1 = ImageIO.read(getClass().getResourceAsStream("/player/crouch1.png"));
            crouch2 = ImageIO.read(getClass().getResourceAsStream("/player/crouch2.png"));
            idle1 = ImageIO.read(getClass().getResourceAsStream("/player/idle1.png"));
            idle2 = ImageIO.read(getClass().getResourceAsStream("/player/idle2.png"));
        } catch (IOException e) {e.printStackTrace();}}

    public void update() {
        //Gravity and jump counter
        int gravity = 10;
        if (iPlayerY > 540) {
            iPlayerY =540;}
        if (iPlayerY < 540) {
            iPlayerY += gravity;}
        if (jumpCounter == 12) {canJump = false; falling = true;}
        if (iPlayerY == 540) {canJump = true; jumpCounter = 0; falling = false;}
        //Key mapping
        if (KeyBinds.bDownPressed || KeyBinds.bUpPressed || KeyBinds.bSpacePressed || KeyBinds.bRightPressed || KeyBinds.bLeftPressed) {
            if (KeyBinds.bLeftPressed) {
                iPlayerX -= speed;
                direction = "left";
            }
            if (KeyBinds.bRightPressed) {
                iPlayerX += speed;
                direction = "right";
            }
            if (KeyBinds.bUpPressed && canJump) {
                iPlayerY -= 2 * speed;
                direction = "jump";
                jumpCounter ++;
            } else if (KeyBinds.bSpacePressed && canJump) {
                iPlayerY -= 2 * speed;
                direction = "jump";
                jumpCounter ++;
            }
            if (KeyBinds.bLeftPressed && KeyBinds.bUpPressed && canJump) {
                direction = "left jump";
            }
            if (KeyBinds.bRightPressed && KeyBinds.bUpPressed && canJump) {
                direction = "right jump";
            }
            if (KeyBinds.bLeftPressed && KeyBinds.bSpacePressed && canJump) {
                direction = "left jump";
            }
            if (KeyBinds.bRightPressed && KeyBinds.bSpacePressed && canJump) {
                direction = "right jump";
            }
            if (KeyBinds.bDownPressed) {direction = "crouch";}
        }
        else {direction = "idle";}
//Sprite counter
        spriteCounter ++;
        if (spriteCounter > 12) {
            if (spriteNumber == 2){spriteNumber = 1;}
            else if (spriteNumber == 1) {spriteNumber = 2;}
        spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        switch (direction) {
            case "left":
                if (spriteNumber == 1) {image = left1;}
                if (spriteNumber == 2) {image = left2;}
                break;
            case "right":
                if (spriteNumber == 1) {image = right1;}
                if (spriteNumber == 2) {image = right2;}
                break;
            case "crouch":
                if (spriteNumber == 1) {image = crouch1;}
                if (spriteNumber == 2) {image = crouch2;}
                break;
            case "jump":
                if (spriteNumber == 1) {image = jump1;}
                if (spriteNumber == 2) {image = jump2;}
                break;
            case "left jump":
                if (spriteNumber == 1) {image = leftjump1;}
                if (spriteNumber == 2) {image = leftjump2;}
                break;
            case "right jump":
                if (spriteNumber == 1) {image = rightjump1;}
                if (spriteNumber == 2) {image = rightjump2;}
                break;
            case "idle":
                if (spriteNumber == 1) {image = idle1;}
                if (spriteNumber == 2) {image = idle2;}
                break;
        }
        g2.drawImage(image, iScreenX, iPlayerY, gamePanel.iTileSize, gamePanel.iTileSize, null);
    }
}