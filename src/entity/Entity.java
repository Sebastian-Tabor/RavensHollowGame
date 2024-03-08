package entity;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    public GamePanel gp;
//ANIMATIONS
    public int iSpriteCounter = 0;
    public int iSpriteNumber = 1;
//JUMPING
    public int iJumpCooldown;
    public boolean bCanJump = true;
    public boolean bFalling = false;
    public int iRecoveryTime;
//COLLISION
    public int iSenseRange = 5;
    public Rectangle hitBox, hitBoxLeftSense, hitBoxRightSense, hitBoxTopSense, hitBoxBotSense;
    public int iHitBoxDefaultX, iHitBoxDefaultY;
    public boolean bCollisionLeft, bCollisionRight, bCollisionTop, bCollisionBottom, bCollisionDetected = false;
    public boolean bStuckTopLeft, bStuckTopRight, bStuckBotLeft, bStuckBotRight = false;
//MOVEMENT
    public int iMovementCounter = 0;
    public int iWorldX, iWorldY;
    public int iSpeed;
    public int iVelocityY;
    public int iVelocityX;
    public BufferedImage jump1, jump2, leftjump1,leftjump2, rightjump1, rightjump2, crouch1, crouch2, right1, right2, left1, left2, idle1, idle2;
    public String direction;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }
    public void setAction() {
        System.out.println("Undefined setAction in subclass");
    }
    public void update() {
        bCollisionDetected = false;
        gp.cCheck.checkTile(this);
        switch (direction) {
            case "left":
                iVelocityX = -iSpeed;
                moveLeft();
                break;
            case "right":
                iVelocityX = iSpeed;
                moveRight();
                break;
            case"jump":
                jump();
                break;
            case "left jump":
                iVelocityX = -iSpeed;
                moveLeft();
                jump();
                break;
            case "right jump":
                iVelocityX = iSpeed;
                moveRight();
                jump();
                break;
        }
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
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int iScreenX = iWorldX - gp.player.iWorldX + gp.player.iScreenPosX;
        int iScreenY = iWorldY - gp.player.iWorldY + gp.player.iScreenPosY;
        if (iWorldX + gp.iTileSize > gp.player.iWorldX - gp.player.iScreenPosX &&
                iWorldX - gp.iTileSize < gp.player.iWorldX + gp.player.iScreenPosX &&
                iWorldY + gp.iTileSize < gp.player.iWorldY - gp.player.iScreenPosY &&
                iWorldY - gp.iTileSize < gp.player.iWorldY + gp.player.iScreenPosY) {
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
//MOVEMENT METHODS
    public void moveLeft() {
        if (!bCollisionLeft) {
            iWorldX += iVelocityX;
        }
    }
    public void moveRight() {
        if (!bCollisionRight) {
            iWorldX += iVelocityX;
        }
    }
    public void jump() {
        if (bCanJump && !bCollisionTop && bCollisionBottom) {
            iJumpCooldown = iRecoveryTime;
            iWorldY -= 1 + iSenseRange;
            iVelocityY = iSpeed + 10;
            bCanJump = false;
        }
    }
    public void leftJump() {
        if (bCanJump && !bCollisionTop && bCollisionBottom) {
            iJumpCooldown = iRecoveryTime;
            iWorldY -= 1 + iSenseRange;
            iVelocityY = iSpeed + 10;
            bCanJump = false;
        }
        moveLeft();
    }
    public void rightJump() {
        if (bCanJump && !bCollisionTop && bCollisionBottom) {
            iJumpCooldown = iRecoveryTime;
            iWorldY -= 1 + iSenseRange;
            iVelocityY = iSpeed + 10;
            bCanJump = false;
        }
        moveRight();
    }
}
