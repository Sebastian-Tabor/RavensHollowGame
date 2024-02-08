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

    public Player(GamePanel gamePanel, KeyBinds keyBinds) {
        this.gamePanel = gamePanel;
        this.keyBinds = keyBinds;
        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        x = 100;
        y = 540;
        speed = 10;
        direction = "down";
    }

    public void getPlayerImage() {
        try {
            jump1 = ImageIO.read(getClass().getResourceAsStream("/player/jump1.png"));
            jump2 = ImageIO.read(getClass().getResourceAsStream("/player/jump1.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/down1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/down1.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/left1.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/right1.png"));
        }
        catch (IOException e) {e.printStackTrace();}
    }

    public void update() {
        int iOldCharPosY = y;
        if (KeyBinds.bLeftPressed) {
            x -= speed;
            direction = "left";
        }
        if (KeyBinds.bRightPressed) {
            x += speed;
            direction = "right";
        }
        if (KeyBinds.bUpPressed) {
            y -= 2 * speed;
            direction = "jump";
        }
        if (KeyBinds.bSpacePressed) {
            y -= 2 * speed;
            direction = "jump";
        }
        if (KeyBinds.bDownPressed) {
            direction = "crouch";
        }
        if (y > 540) {
            y = 100;
        }
        if (y - iOldCharPosY >= 6 * speed) {
            y += speed;
        }
        if (y < 540) {
            y += speed;
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = switch (direction) {
            case "left" -> left1;
            case "right" -> right1;
            case "jump" -> jump1;
            case "crouch" -> down1;
            default -> null;
        };
        g2.drawImage(image, x, y, gamePanel.iTileSize, gamePanel.iTileSize, null);


    }
}