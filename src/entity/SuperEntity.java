package entity;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperEntity {
    public GamePanel gp;
//ANIMATIONS
    public String facing;
    public int spriteCounter = 0;
    public int iSpriteNumber = 1;
    public int deathCounter = 0;
    public boolean dying = false;
//COLLISION
    public int immunityCounter = 0;
    public boolean bImmune = false;
    public int iHitBoxDefaultX, iHitBoxDefaultY;
    public Rectangle hitBox, hitBoxLeftSense, hitBoxRightSense, hitBoxTopSense, hitBoxBotSense;
    public boolean bCollisionLeft, bCollisionRight, bCollisionTop, bCollisionBottom, bCollisionDetected, bWouldBeStuck = false;
//ATTACKING
    public boolean bCanAttack = true;
    public int iMeleeDamage, iCollisionDmg;
    public int attackCounter, iFrameNumber = 0;
    public Rectangle attackBox = new Rectangle();
    public boolean bRangedAttacking, bMeleeAttacking, bAttacking = false;
//MOVEMENT
    public int iSpeed;
    public int iSpeedOriginal;
    public int iGravity;
    public int iVelocityX;
    public int iVelocityY;
    public int iWorldX, iWorldY;
    public int iMovementCounter = 0;
    public int iJumpCooldown;
    public int iRecoveryTime;
    public boolean bCanMove = true;
    public boolean bCanJump = true;
    public boolean bFalling = false;
    public String moveState;
    public String direction;
    public BufferedImage attack1, attack2, attack3, attack4;
    public BufferedImage jump1, jump2, crouch1, crouch2, right1, right2, idle1, idle2;
//STAT TRACKERS/IDENTIFIERS
    public String sName;
    public int iHealth;
    public int iHealthMax;
    public int iUltimate;
    public int iUltimateMax;
    public int iArmor;

//CONSTRUCTOR
    public SuperEntity(GamePanel gp) {
        this.gp = gp;
        hitBox = new Rectangle(8, 2, 48, 60);
        iHitBoxDefaultX = hitBox.x;
        iHitBoxDefaultY = hitBox.y;
        iGravity = -gp.iTileSize/10;
        direction = "right";
        moveState = "idle";
        facing = "right";
    }
//SET ACTION ERROR MESSAGE
    public void setAction() {
        throw new RuntimeException("setAction not defined in subclass");
    }
//UPDATE
    public void update() {
    //RANDOMIZED MOVEMENT COUNTER
        iMovementCounter++;
        if (iMovementCounter == 120) {
            iMovementCounter = 0;
            setAction();
        }
    //SETTING COLLISION VECTORS
        bCollisionDetected = false;
        gp.cCheck.checkTile(this);
        gp.cCheck.checkObject(this, false);
        gp.cCheck.checkEntity(this, gp.npc);
        gp.cCheck.checkEntity(this, gp.monster);
        gp.cCheck.checkPlayer(this);
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
                iSpeed = iSpeed/2;
                break;
        }
        if (!moveState.equals("crouch")) {
            iSpeed = iSpeedOriginal;
        }
        //COLLISION CHECK
        gp.cCheck.checkTile(this);
        if (bWouldBeStuck) --iWorldY;
        bCollisionDetected = false;
        //IMMUNITY COUNTER
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
//DRAW
    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        int iScreenX = iWorldX - gp.player.iWorldX + gp.player.iScreenPosX;
        int iScreenY = iWorldY - gp.player.iWorldY + gp.player.iScreenPosY;

        if (iWorldX + gp.iTileSize > gp.player.iWorldX - gp.player.iScreenPosX &&
            iWorldX - gp.iTileSize < gp.player.iWorldX + gp.player.iScreenPosX &&
            iWorldY + gp.iTileSize > gp.player.iWorldY - gp.player.iScreenPosY &&
            iWorldY - gp.iTileSize < gp.player.iWorldY + gp.player.iScreenPosY) {
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
                g2.drawImage(image, iScreenX + gp.iTileSize, iScreenY, -gp.iTileSize, gp.iTileSize, null);
            } else {
                g2.drawImage(image, iScreenX, iScreenY, gp.iTileSize, gp.iTileSize, null);
            }
        }
    }
//MOVEMENT METHODS
    public void moveLeft() {
        if (!bCollisionLeft && bCanMove) {
            iWorldX += iVelocityX;
        }
    }
    public void moveRight() {
        if (!bCollisionRight && bCanMove) {
            iWorldX += iVelocityX;
        }
    }
    public void jump() {
        if (bCanJump && !bCollisionTop && bCollisionBottom && bCanMove) {
            iJumpCooldown = iRecoveryTime;
            iWorldY -= iSpeed;
            iVelocityY = iSpeed + 10;
            bCanJump = false;
        }
    }
//DAMAGE
    public void damagePlayer(int amount) {
        if (!gp.player.bImmune){
           gp.player.iHealth -= amount;
           gp.player.immunityCounter = 60;
           gp.player.bImmune = true;
        }
        if (gp.player.iHealth <= 0) {
            gp.player.dying = true;
        }
    }
    public void meleeMonster(int target, SuperEntity source) {
        if (target != 999) {
            if (!gp.monster[target].bImmune){
                gp.monster[target].iHealth -= source.iMeleeDamage;
                gp.monster[target].immunityCounter = 60;
                gp.monster[target].bImmune = true;
            }
            if (gp.monster[target].iHealth <= 0) {
                gp.monster[target].dying = true;
            }
        }
    }
    public void meleeNPC(int target, SuperEntity source) {
        if (target != 999) {
            if (!gp.npc[target].bImmune) {
                gp.npc[target].iHealth -= source.iMeleeDamage;
                gp.npc[target].immunityCounter = 60;
                gp.npc[target].bImmune = true;
            }
            if (gp.npc[target].iHealth <= 0) {
                gp.npc[target].dying = true;
            }
        }
    }
    public void dyingAnimation(){
        bCanMove = false;

    }
}
