package entity;
import main.GamePanel;
import main.KeyBinds;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.security.Key;


public class Player extends SuperEntity {
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
        iSpeedOriginal = 5;
        iSpeed = iSpeedOriginal;
        iRecoveryTime = 10;
        iHealth = 10;
        iHealthMax = 10;
        iUltimate = 0;
        iUltimateMax = 20;
        iArmor = 0;
        moveState = "idle";
        direction = "right";
        facing = "right";
    }
//IMAGE SETUP METHOD
    public BufferedImage setup(String imageName) {
        BufferedImage scaledImage = null;
        try {
            scaledImage = ImageIO.read(new File( imageName + ".png"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return scaledImage;
    }
//PLAYER IMAGES
    public void getPlayerImage() {

            right1 = setup("./res/player/right1");
            right2 = setup("./res/player/right2");
            jump1 = setup("./res/player/jump1");
            jump2 = setup("./res/player/jump2");
            crouch1 = setup("./res/player/crouch1");
            crouch2 = setup("./res/player/crouch2");
            idle1 = setup("./res/player/idle1");
            idle2 = setup("./res/player/idle2");
            attack1 = setup("./res/player/attack1");
            attack2 = setup("./res/player/attack2");
            attack3 = setup("./res/player/attack3");
            attack4 = setup("./res/player/attack4");
    }
//OBJECT METHODS
    public void pickupObject(int index) {
        if(index != 999){
            String sObjectName = gp.obj[index].sName;
            switch (sObjectName) {
                case "Feather":
                    gp.obj[index] = null;
                    gp.playSoundEffect(0);
                    iSpeed += 1;
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
    public void interactNPC(int index) {
        if (index != 999) {
            String name = gp.npc[index].sName;
            switch (name) {
                case "Carver":
                    //Do nothing now
                    break;
                case "Bone":
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
                moveState = "moving";
            }
            if (KeyBinds.bRightPressed) {
                direction = "right";
                moveState = "moving";
            }
            if (KeyBinds.bUpPressed) {
                moveState = "jump";
            }
            if (KeyBinds.bDownPressed) {
                moveState = "crouch";
            }
        }
        else {
            direction = "idle";
        }
    //MOVEMENT ACTIONS
        switch (direction) {
            case "left":
                iVelocityX = -iSpeed;
                facing = direction;
                moveLeft();
                break;
            case "right":
                iVelocityX = iSpeed;
                facing = direction;
                moveRight();
                break;
        }
        switch (moveState) {
            case "jump":
                jump();
                break;
            case "crouch":
                iSpeed = iSpeedOriginal/2;
                break;
        }
        if (!moveState.equals("crouch")) {
            iSpeed = iSpeedOriginal;
        }
    //ATTACKING
        bAttacking = bMeleeAttacking || bRangedAttacking;

        if (KeyBinds.bMeleePressed && bCanAttack) {
            bCanAttack = false;
            bMeleeAttacking = true;
        }
        if (bMeleeAttacking) {
            int iAttackIndex = gp.cCheck.checkEntityAttack(this, gp.monster);
            meleeAttackMonster(iAttackIndex);
            attackCounter ++;
        }
        if (attackCounter == 11) {
            attackCounter = 0;
            ++iFrameNumber;
        }
        if (iFrameNumber == 4) {
            iFrameNumber = 0;
            bMeleeAttacking = false;
            bRangedAttacking = false;
            bAttacking = false;
            bCanAttack = true;
        }
    //COLLISION CHECK
        gp.cCheck.checkTile(this);
        if (bWouldBeStuck) --iWorldY;

        int iObjectIndex = gp.cCheck.checkObject(this, true);
        pickupObject(iObjectIndex);
        int iNPCIndex = gp.cCheck.checkEntity(this, gp.npc);
        interactNPC(iNPCIndex);
        int iMonsterIndex = gp.cCheck.checkEntity(this, gp.monster);
        collisionMonster(iMonsterIndex);

        //Leave this here VV pls
        bCollisionDetected = false;
        //Do not touch this ^^
        if (immunityCounter > 0) {
            --immunityCounter;
        }
        if (immunityCounter <= 0) {
            bImmune = false;
            immunityCounter = 0;
        }
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
        if (iVelocityY < iGravity) iVelocityY = iGravity;
        //TO HIT HEAD ON CEILING (DO NOT SET 0 OR YOU WILL STICK)
        if (bCollisionTop) iVelocityY = -5;
        //SET FALLING
        bFalling = iVelocityY < 0;

        //SPRITE COUNTER
        spriteCounter++;
        if (spriteCounter > 12) {
            if (iSpriteNumber == 2){
                iSpriteNumber = 1;}
            else if (iSpriteNumber == 1) {
                iSpriteNumber = 2;}
        spriteCounter = 0;
        }
    }
//DRAW METHOD
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        if (bAttacking) {
            image = switch (iFrameNumber) {
                case 1 -> attack1;
                case 2 -> attack2;
                case 3 -> attack3;
                case 4 -> attack4;
                default -> image;
            };
        } else switch (moveState) {
            case "moving":
                if (iSpriteNumber == 1) {
                    image = right1;
                }
                if (iSpriteNumber == 2) {
                    image = right2;
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
            case "crouch":
                if (iSpriteNumber == 1) {
                    image = crouch1;
                }
                if (iSpriteNumber == 2) {
                    image = crouch2;
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
        if (facing.equals("left")) {
            g2.drawImage(image, iScreenPosX + gp.iTileSize, iScreenPosY, -gp.iTileSize, gp.iTileSize, null);
        } else {
            g2.drawImage(image, iScreenPosX, iScreenPosY, gp.iTileSize, gp.iTileSize, null);
        }
    }

}