package entity;
import main.GamePanel;
import main.KeyBinds;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;


public class Player extends Entity {
    final KeyBinds keyBinds;
    public final int iScreenPosX;
    public final int iScreenPosY;
    public final int iStartPosX = gp.iTileSize * 17;
    public final int iStartPosY = gp.iTileSize * 11;


//PLAYER OBJECT
    public Player(GamePanel gp, KeyBinds keyBinds) {
        super (gp);
        this.keyBinds = keyBinds;
    //PLAYER POS ON SCREEN
        iScreenPosX = (gp.iScreenWidth/2 - gp.iTileSize/2);
        iScreenPosY = (gp.iScreenHeight/2 - gp.iTileSize/2);
    //PLAYER POS METHOD IMPLEMENTATION
        setDefaultValues();
    //PLAYER IMAGE METHOD IMPLEMENTATION
        getPlayerImage();
    }

//SETTING PLAYER VALUES
    public void setDefaultValues() {
        iWorldX = iStartPosX;
        iWorldY = iStartPosY;
        iSpeed = 10;
        iRecoveryTime = 10;
        iHealth = 10;
        iHealthMax = 10;
        iUltimate = 20;
        iUltimateMax = 20;
        iArmor = 0;
        direction = "idle";
    }
//IMAGE SETUP METHOD
    public BufferedImage setup(String imageName) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage scaledImage = null;
        try {

            scaledImage = ImageIO.read(new File( imageName + ".png"));
            scaledImage = uTool.scaleImage(scaledImage, gp.iTileSize, gp.iTileSize);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return scaledImage;
    }
//PLAYER IMAGES
    public void getPlayerImage() {

            left1 = setup("./res/player/left1");
            left2 = setup("./res/player/left2");
            right1 = setup("./res/player/right1");
            right2 = setup("./res/player/right2");
            jump1 = setup("./res/player/jump1");
            jump2 = setup("./res/player/jump2");
            leftjump1 = setup("./res/player/leftjump1");
            leftjump2 = setup("./res/player/leftjump2");
            rightjump1 = setup("./res/player/rightjump1");
            rightjump2 = setup("./res/player/rightjump2");
            crouch1 = setup("./res/player/crouch1");
            crouch2 = setup("./res/player/crouch2");
            idle1 = setup("./res/player/idle1");
            idle2 = setup("./res/player/idle2");

    }
//OBJECT METHODS
    public void pickupObject(int index) {
        if(index != 999){
            String sObjectName = gp.obj[index].sName;
            switch (sObjectName) {
                case "Feather":
                    iSpeed += 1;
                    gp.playSoundEffect(0);
                    gp.obj[index] = null;
                    break;
                case "Bone":
                    gp.obj[index] = null;
                    //sound effect
                    break;
                case "Arrow":
                    gp.obj[index] = null;
                    //sound effect
                    iHealth --;
                    break;
            }
        }

    }
//UPDATE
    public void update() {
    //KEYBIND MOVEMENT
        if (KeyBinds.bDownPressed || KeyBinds.bUpPressed || KeyBinds.bRightPressed || KeyBinds.bLeftPressed) {
            if (KeyBinds.bLeftPressed) {
                direction = "left";
                iVelocityX = -iSpeed;
                moveLeft();
            }
            if (KeyBinds.bRightPressed) {
                direction = "right";
                iVelocityX = iSpeed;
                moveRight();
            }
            if (KeyBinds.bUpPressed) {
                jump();
                direction = "jump";
            } else if (KeyBinds.bDownPressed) {
                direction = "crouch";
                hitBox = halfHitBox;
            }
            if (KeyBinds.bLeftPressed && KeyBinds.bUpPressed) {
                direction = "left jump";
                iVelocityX = -iSpeed;
            }
            if (KeyBinds.bRightPressed && KeyBinds.bUpPressed) {
                direction = "right jump";
                iVelocityX = iSpeed;
            }
        }
        else {
            direction = "idle";
            hitBox = fullHitBox;
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
        }
        else {
        //JUMPING/FALLING MOVEMENT
            this.iWorldY -= iVelocityY;
            iVelocityY --;
        }
    //MAX GRAVITY
        if (iVelocityY < -10) iVelocityY = -10;
    //TO HIT HEAD ON CEILING (DO NOT SET 0 OR YOU WILL STICK)
        if (bCollisionTop) iVelocityY = -5;
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
        g2.drawImage(image, iScreenPosX, iScreenPosY, gp.iTileSize, gp.iTileSize, null);
    }
}