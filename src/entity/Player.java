package entity;
import main.GamePanel;
import main.KeyBinds;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player extends Entity {
    final GamePanel gp;
    final KeyBinds keyBinds;
    public final int iScreenX;
    public final int iScreenY;

//PLAYER OBJECT
    public Player(GamePanel gp, KeyBinds keyBinds) {
        this.gp = gp;
        this.keyBinds = keyBinds;
    //PLAYER POS ON SCREEN
        iScreenX = (gp.iScreenWidth/2 - gp.iTileSize/2);
        iScreenY = (gp.iScreenHeight/2 - gp.iTileSize/2);
    //PLAYER HITBOX
        hitBox = new Rectangle(8, 0, 48, 64);
    //PLAYER POS METHOD IMPLEMENTATION
        setDefaultValues();
    //PLAYER IMAGE METHOD IMPLEMENTATION
        getPlayerImage();
    }

//SETTING PLAYER VALUES POSITION
    public void setDefaultValues() {
        iWorldX = (gp.iTileSize * (int)(0.1 * gp.iMaxScreenColumns));
        iWorldY = (gp.iTileSize * (int)(0.5 * gp.iMaxScreenRows));
        iSpeed = 10;
        iRecoveryTime = 120;
        direction = "idle";
        setCollision(false);
    }

//PLAYER IMAGE METHOD
    public void getPlayerImage() {
        try {
            left1 = ImageIO.read(new File("./res/player/left1.png"));
            left2 = ImageIO.read(new File("./res/player/left2.png"));
            right1 = ImageIO.read(new File("./res/player/right1.png"));
            right2 = ImageIO.read(new File("./res/player/right2.png"));
            jump1 = ImageIO.read(new File("./res/player/jump1.png"));
            jump2 = ImageIO.read(new File("./res/player/jump2.png"));
            leftjump1 = ImageIO.read(new File("./res/player/leftjump1.png"));
            leftjump2 = ImageIO.read(new File("./res/player/leftjump2.png"));
            rightjump1 = ImageIO.read(new File("./res/player/rightjump1.png"));
            rightjump2 = ImageIO.read(new File("./res/player/rightjump2.png"));
            crouch1 = ImageIO.read(new File("./res/player/crouch1.png"));
            crouch2 = ImageIO.read(new File("./res/player/crouch2.png"));
            idle1 = ImageIO.read(new File("./res/player/idle1.png"));
            idle2 = ImageIO.read(new File("./res/player/idle2.png"));
        } catch (IOException e) {e.printStackTrace();}}

//UPDATE
    public void update() {
    //MOVEMENT AND KEYBINDS
        if (KeyBinds.bDownPressed || KeyBinds.bUpPressed || KeyBinds.bSpacePressed || KeyBinds.bRightPressed || KeyBinds.bLeftPressed) {
            if (KeyBinds.bLeftPressed) {
                direction = "left";
                moveLeft(this);
            }
            if (KeyBinds.bRightPressed) {
                direction = "right";
                moveRight(this);
            }
            if (KeyBinds.bUpPressed && this.bCanJump()) {
                jump(this);
                direction = "jump";
            } else if (KeyBinds.bSpacePressed && this.bCanJump()) {
                jump(this);
                direction = "jump";
            } else if (KeyBinds.bDownPressed) {
                direction = "crouch";
            }
            // ADD JUMP LEFT AND JUMP RIGHT
        }
        else {
            direction = "idle";
        }
    //COLLISION CHECK
        gp.cCheck.checkCollision(this);
    //CAN JUMP
        if (bCollisionFloor) {
            bFalling = false;
        }
    //PLAYER RESET
        if (iWorldY > gp.floor) {
            iWorldY = 780;
        }
    //SPRITE COUNTER
        iSpriteCounter++;
        if (iSpriteCounter > 12) {
            if (iSpriteNumber == 2){
                iSpriteNumber = 1;}
            else if (iSpriteNumber == 1) {
                iSpriteNumber = 2;}
        iSpriteCounter = 0;
        }
    }

//DRAW METHOD
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        switch (direction) {
            case "left":
                if (iSpriteNumber == 1) {
                    image = left1;
                }
                if (iSpriteNumber == 2) {
                    image = left2;
                }
                break;
            case "right":
                if (iSpriteNumber == 1) {
                    image = right1;
                }
                if (iSpriteNumber == 2) {
                    image = right2;
                }
                break;
            case "crouch":
                if (iSpriteNumber == 1) {
                    image = crouch1;
                }
                if (iSpriteNumber == 2) {
                    image = crouch2;
                }
                break;
            case "jump":
                if (iSpriteNumber == 1) {
                    image = jump1;
                }
                if (iSpriteNumber == 2) {
                    image = jump2;
                }
                break;
            case "left jump":
                if (iSpriteNumber == 1) {
                    image = leftjump1;
                }
                if (iSpriteNumber == 2) {
                    image = leftjump2;
                }
                break;
            case "right jump":
                if (iSpriteNumber == 1) {
                    image = rightjump1;
                }
                if (iSpriteNumber == 2) {
                    image = rightjump2;
                }
                break;
            case "idle":
                if (iSpriteNumber == 1) {
                    image = idle1;
                }
                if (iSpriteNumber == 2) {
                    image = idle2;
                }
                break;
        }
            g2.drawImage(image, iScreenX, iScreenY, gp.iTileSize, gp.iTileSize, null);
    }
}