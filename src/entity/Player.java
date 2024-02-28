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
        if (gp.bEnterLevel){
            setPosStartLevel();
        } else {setDefaultValues();}
    //PLAYER IMAGE METHOD IMPLEMENTATION
        getPlayerImage();
    }

//SETTING PLAYER POSITION
    public void setDefaultValues() {
        iWorldX = (gp.iTileSize * (int)(0.5 * gp.iMaxScreenColumns));
        iWorldY = (gp.iTileSize * (int)(0.5 * gp.iMaxScreenRows));
        speed = 10;
        direction = "idle";
    }
    public void setPosStartLevel() {
        iWorldX = (gp.iTileSize * (int)(0.1 * gp.iMaxScreenColumns));
        iWorldY = (gp.iTileSize * (int)(0.5 * gp.iMaxScreenRows));
        speed = 10;
        direction = "idle";
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

    //KEYBIND MAPPING
        if (KeyBinds.bDownPressed || KeyBinds.bUpPressed || KeyBinds.bSpacePressed || KeyBinds.bRightPressed || KeyBinds.bLeftPressed) {
            if (KeyBinds.bLeftPressed) {
                direction = "left";
            }
            if (KeyBinds.bRightPressed) {
                direction = "right";
            }
            if (KeyBinds.bUpPressed) {
                direction = "jump";
            } else if (KeyBinds.bSpacePressed) {
                direction = "jump";
            } else if (KeyBinds.bDownPressed) {
                direction = "crouch";
            }
            if (KeyBinds.bLeftPressed && KeyBinds.bUpPressed ) {
                direction = "left jump";
            }
            if (KeyBinds.bRightPressed && KeyBinds.bUpPressed ) {
                direction = "right jump";
            }
            if (KeyBinds.bLeftPressed && KeyBinds.bSpacePressed) {
                direction = "left jump";
            }
            if (KeyBinds.bRightPressed && KeyBinds.bSpacePressed ) {
                direction = "right jump";
            }
        }
        else {
            direction = "idle";
        }

    //COLLISION CHECK
        bCollisionVertical = false;
        bCollisionHorizontal = false;
        gp.cCheck.checkTile(this);

        if (!bCollisionVertical) {
            switch (direction) {
                case "jump":
                    iWorldY -= 2 * speed;
                    break;
                case "crouch", "idle":
                    iWorldY += 2 * speed;
                    break;
            }
        }
        if (!bCollisionHorizontal) {
            switch (direction) {
                case "left jump":
                    iWorldY -= 2 * speed;
                    iWorldX -= speed;
                    break;
                case "right jump":
                    iWorldY -= 2 * speed;
                    iWorldX += speed;
                    break;
                case "left":
                    iWorldX -= speed;
                    break;
                case "right":
                    iWorldX += speed;
                    break;
            }
        }

    //SPRITE COUNTER
        spriteCounter ++;
        if (spriteCounter > 12) {
            if (spriteNumber == 2){spriteNumber = 1;}
            else if (spriteNumber == 1) {spriteNumber = 2;}
        spriteCounter = 0;
        }
    }

//DRAW METHOD
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
            g2.drawImage(image, iScreenX, iScreenY, gp.iTileSize, gp.iTileSize, null);
    }
}