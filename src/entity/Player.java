package entity;
import main.GamePanel;
import main.KeyBinds;
import object.Object;

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
        hitBox = new Rectangle(8, 2, 48, 60);
        iHitBoxDefaultX = hitBox.x;
        iHitBoxDefaultY = hitBox.y;
    //PLAYER POS METHOD IMPLEMENTATION
        setDefaultValues();
    //PLAYER IMAGE METHOD IMPLEMENTATION
        getPlayerImage();
    }

//SETTING PLAYER VALUES
    public void setDefaultValues() {
        iWorldX = (gp.iTileSize * (int)(0.1 * gp.iMaxScreenColumns));
        iWorldY = (gp.iTileSize * (int)(0.5 * gp.iMaxScreenRows));
        iSpeed = 10;
        iRecoveryTime = 10;
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//UPDATE
    public void update() {
    //KEYBIND MOVEMENT
        if (KeyBinds.bDownPressed || KeyBinds.bUpPressed || KeyBinds.bSpacePressed || KeyBinds.bRightPressed || KeyBinds.bLeftPressed) {
            if (KeyBinds.bLeftPressed) {
                direction = "left";
                iVelocityX = -iSpeed;
                moveLeft(this);
            }
            if (KeyBinds.bRightPressed) {
                direction = "right";
                iVelocityX = iSpeed;
                moveRight(this);
            }
            if (KeyBinds.bUpPressed) {
                jump(this);
                direction = "jump";
            } else if (KeyBinds.bSpacePressed) {
                jump(this);
                direction = "jump";
            } else if (KeyBinds.bDownPressed) {
                direction = "crouch";
            }
            if (KeyBinds.bLeftPressed && KeyBinds.bUpPressed || KeyBinds.bLeftPressed && KeyBinds.bSpacePressed) {
                direction = "left jump";
                iVelocityX = -iSpeed;
            }
            if (KeyBinds.bRightPressed && KeyBinds.bUpPressed || KeyBinds.bRightPressed && KeyBinds.bSpacePressed) {
                direction = "right jump";
                iVelocityX = iSpeed;
            }
        }
        else {
            direction = "idle";
        }
    //COLLISION CHECK
        bCollisionDetected = false;
        gp.cCheck.checkTile(this);
        int iObjectIndex = gp.cCheck.checkObject(this, true);
        pickupObject(iObjectIndex);
    //STUCK PREVENTION
        if (bStuckTopLeft) {
            iWorldY++;
            iWorldX++;
        }
        if (bStuckTopRight) {
            iWorldY++;
            iWorldX--;
        }
        if (bStuckBotLeft) {
            iWorldY--;
            iWorldX++;
        }
        if (bStuckBotRight) {
            iWorldY--;
            iWorldX--;
        }
    //POSSIBLE IMPLEMENTS JUMP INTERFACE?
        //JUMP CONDITIONS
        if (bCollisionBottom) {
            iVelocityY = 0;
            iJumpCooldown--;
            if (iJumpCooldown < 0) {
                iJumpCooldown = 0;
                bCanJump = true;
            }
        } else {
        //JUMPING/FALLING MOVEMENT
            this.iWorldY -= iVelocityY;
            iVelocityY --;
        //GRAVITY
            if (iVelocityY < -10) iVelocityY = -10;
        //TO HIT HEAD ON CEILING (DO NOT SET 0 OR YOU WILL STICK)
            if (bCollisionTop) iVelocityY = -5;
        }
    //SET FALLING
        bFalling = iVelocityY <= 0;
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

//OBJECT METHODS
    public void pickupObject(int index) {
        if(index != 999 && gp.obj[index].bStorable){
            String sObjectName = gp.obj[index].sName;
            switch (sObjectName) {
                case "Feather":
                    iSpeed += 5;
                    gp.obj[index] = null;
                    break;
                case "Bone":
                    gp.obj[index] = null;
                    break;
            }
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