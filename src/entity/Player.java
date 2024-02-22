package entity;
import main.GamePanel;
import main.KeyBinds;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player extends EntityManager {
    final GamePanel gp;
    final KeyBinds keyBinds;
    public final int iScreenX;
    //public final int iScreenY;
    public Player(GamePanel gp, KeyBinds keyBinds) {
        this.gp = gp;
        this.keyBinds = keyBinds;

        iScreenX = (gp.iScreenWidth/2 - gp.iTileSize/2);

        setPosStartLevel();
        getPlayerImage();
    }

    public void setDefaultValues() {
        iPlayerX = (gp.iTileSize * (int)(0.5 * gp.iMaxScreenColumns));
        iPlayerY = (gp.iTileSize * (int)(0.5 * gp.iMaxScreenRows));
        speed = 10;
        direction = "idle";
    }
    public void setPosStartLevel() {
        iPlayerX = (gp.iTileSize * (int)(0.1 * gp.iMaxScreenColumns));
        iPlayerY = (gp.iTileSize * (int)(0.5 * gp.iMaxScreenRows));
        speed = 10;
        direction = "idle";
    }

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

    public void update() {
        //Gravity
        int gravity = 10;
        if (iPlayerY > 540) {
            iPlayerY =540;}
        if (iPlayerY < 540) {
            iPlayerY += gravity;}

        //Jumping
        if (jumpCounter == 12) {
            canJump = false;
            falling = true;
        }
        if (iPlayerY == 540) {
            falling = false;
            if (jumpCounter > 0) {
                jumpCounter--;
            }
        }
        if (jumpCounter == 0) {
            canJump = true;
        }

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
                if (spriteNumber == 1) {
                    image = left1;
                }
                if (spriteNumber == 2) {
                    image = left2;
                }
                break;
            case "right":
                if (spriteNumber == 1) {
                    image = right1;
                }
                if (spriteNumber == 2) {
                    image = right2;
                }
                break;
            case "crouch":
                if (spriteNumber == 1) {
                    image = crouch1;
                }
                if (spriteNumber == 2) {
                    image = crouch2;
                }
                break;
            case "jump":
                if (spriteNumber == 1) {
                    image = jump1;
                }
                if (spriteNumber == 2) {
                    image = jump2;
                }
                break;
            case "left jump":
                if (spriteNumber == 1) {
                    image = leftjump1;
                }
                if (spriteNumber == 2) {
                    image = leftjump2;
                }
                break;
            case "right jump":
                if (spriteNumber == 1) {
                    image = rightjump1;
                }
                if (spriteNumber == 2) {
                    image = rightjump2;
                }
                break;
            case "idle":
                if (spriteNumber == 1) {
                    image = idle1;
                }
                if (spriteNumber == 2) {
                    image = idle2;
                }
                break;
        }
        if (gp.bScrollerLevel) {
            g2.drawImage(image, iScreenX, iPlayerY, gp.iTileSize, gp.iTileSize, null);
        } else {
            g2.drawImage(image, iPlayerX, iPlayerY, gp.iTileSize, gp.iTileSize, null);
        }
    }
}