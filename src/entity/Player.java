package entity;
import main.GamePanel;
import main.KeyBinds;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;


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
        iType = 0;
        iSpeedOriginal = 7;
        iSpeed = iSpeedOriginal;
        iRecoveryTime = 10;
        iHealth = 10;
        iHealthMax = 10;
        iUltimate = 0;
        iUltimateMax = 20;
        iArmor = 0;
        iMeleeDamage = 2;
        moveState = "idle";
        direction = "idle";
        facing = "right";
    }
//IMAGE SETUP METHOD
    public BufferedImage setup(String imageName) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File( imageName + ".png"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return image;
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
            dying1 = setup("./res/player/dying1");
            dying2 = setup("./res/player/dying2");
            dying3 = setup("./res/player/dying3");
            dying4 = setup("./res/player/dying4");
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
            moveState = "idle";
            direction = "idle";
        }
    //MOVEMENT ACTIONS
        switch (direction) {
            case "left":
                moveLeft();
                break;
            case "right":
                moveRight();
                break;
            case "idle":
                break;
        }
        switch (moveState) {
            case "jump":
                jump();
                break;
            case "crouch":
                iSpeed = iSpeedOriginal/2;
                break;
            case "idle":
                iVelocityX = 0;
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
            attackCounter++;
        }
        if (attackCounter == 11) {
            attackCounter = 0;
            ++iFrameNumber;
        }
        if (iFrameNumber == 1 || iFrameNumber == 2) {
            int iAttackIndex = gp.cCheck.checkIf_Hit_(this, gp.monster);
            if (iAttackIndex != 999) {
                meleeMonster(iAttackIndex, this);
            } else {
                iAttackIndex = gp.cCheck.checkIf_Hit_(this, gp.npc);
                meleeNPC(iAttackIndex, this);
            }
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
        if (bDying) {
            dyingAnimation();
        }
    }
//DRAW METHOD
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        if (bAttacking) {
            image = switch (iFrameNumber) {
                case 0 -> attack1;
                case 1 -> attack2;
                case 2 -> attack3;
                case 3, 4 -> attack4;
                default -> throw new IllegalStateException("Unexpected value: " + iFrameNumber);
            };
        } else if (bDying) {
            image = switch (iFrameNumber) {
                case 0 -> dying1;
                case 1 -> dying2;
                case 2 -> dying3;
                case 3, 4 -> dying4;
                default -> throw new IllegalStateException("Unexpected value: " + iFrameNumber);
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
            g2.drawImage(image, iScreenPosX + gp.iTileSize, iScreenPosY, -image.getWidth(), image.getHeight(), null);
        } else {
            g2.drawImage(image, iScreenPosX, iScreenPosY, image.getWidth(), image.getHeight(), null);
        }
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

    public void collisionMonster(int index) {
        if(index != 999 && !bDying && bAlive){
            damagePlayer(gp.monster[index].iCollisionDmg);
        }
    }
    public void dyingAnimation(){
        bCanMove = false;
        bImmune = true;
        deathCounter++;
        if (deathCounter == 11) {
            ++iFrameNumber;
            deathCounter = 0;
        }
        if (iFrameNumber == 4) {
            bAlive = false;
            bDying = false;
        }
    }

}