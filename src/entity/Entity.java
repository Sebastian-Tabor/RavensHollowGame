package entity;

import main.CollisionCheck;
import main.GamePanel;
import main.UtilityTool;
import world.TileManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    public GamePanel gp;
    public UtilityTool uTool = new UtilityTool();
//ANIMATIONS
    public int iSpriteCounter = 0;
    public int iSpriteNumber = 1;
//JUMPING
    public int iJumpCooldown;
    public int iRecoveryTime;
    public boolean bCanJump = true;
    public boolean bFalling = false;
//COLLISION
    public int iHitBoxDefaultX, iHitBoxDefaultY;
    public boolean bCollisionLeft, bCollisionRight, bCollisionTop, bCollisionBottom, bCollisionDetected, bWouldBeStuck = false;
    public Rectangle hitBox, fullHitBox, hitBoxLeftSense, hitBoxRightSense, hitBoxTopSense, hitBoxBotSense;
//MOVEMENT
    public int iSpeed;
    public int iSpeedOriginal;
    public int iGravity;
    public int iVelocityY;
    public int iVelocityX;
    public int iWorldX, iWorldY;
    public int iMovementCounter = 0;
    public String direction;
    public BufferedImage jump1, jump2, leftjump1,leftjump2, rightjump1, rightjump2, crouch1, crouch2, right1, right2, left1, left2, idle1, idle2, leftcrouch1, leftcrouch2, rightcrouch1, rightcrouch2;
    //STAT TRACKERS
    public int iHealth;
    public int iHealthMax;
    public int iUltimate;
    public int iUltimateMax;
    public int iArmor;
//CONSTRUCTOR
    public Entity(GamePanel gp) {
        this.gp = gp;
        fullHitBox = new Rectangle(8, 2, 48, 60);
        hitBox = fullHitBox;
        iHitBoxDefaultX = hitBox.x;
        iHitBoxDefaultY = hitBox.y;
        iGravity = -gp.iTileSize/10;
    }
//SET ACTION ERROR MESSAGE
    public void setAction() {
        throw new RuntimeException("setAction not defined in subclass");
    }
//UPDATE
    public void update() {
    //RANDOMIZED MOVEMENT COUNTER
        iMovementCounter++;
        if (iMovementCounter == 30) {
            iMovementCounter = 0;
            setAction();
        }
    //SETTING COLLISION VECTORS
        bCollisionDetected = false;
        gp.cCheck.checkTile(this);
        hitBox = fullHitBox;
    //MOVEMENT ACTIONS
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
            case "crouch":
                iSpeed = iSpeed/2;
                break;
            case "left crouch":
                iSpeed = iSpeed/2;
                iVelocityX = -iSpeed;
                moveLeft();
                break;
            case "right crouch":
                iSpeed = iSpeed/2;
                iVelocityX = iSpeed;
                moveRight();
                break;
            case "idle":
                break;
        }
    //COLLISION
        bCollisionDetected = false;
        gp.cCheck.checkTile(this);

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
        preventStuck();
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
//DRAW
    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        int iScreenX = iWorldX - gp.player.iWorldX + gp.player.iScreenPosX;
        int iScreenY = iWorldY - gp.player.iWorldY + gp.player.iScreenPosY;

        if (iWorldX + gp.iTileSize > gp.player.iWorldX - gp.player.iScreenPosX &&
            iWorldX - gp.iTileSize < gp.player.iWorldX + gp.player.iScreenPosX &&
            iWorldY + gp.iTileSize > gp.player.iWorldY - gp.player.iScreenPosY &&
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
                case "left crouch":
                    if (iSpriteNumber == 1) {
                        image = leftcrouch1;
                    }
                    if (iSpriteNumber == 2) {
                        image = leftcrouch2;
                    }
                    break;
                case "right crouch":
                    if (iSpriteNumber == 1) {
                        image = rightcrouch1;
                    }
                    if (iSpriteNumber == 2) {
                        image = rightcrouch2;
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
            iWorldY -= iSpeed;
            iVelocityY = iSpeed + 10;
            bCanJump = false;
        }
    }
    //ANTISTUCK
    public void preventStuck(){
        int futureY = iWorldY + hitBox.y + hitBox.height + iVelocityY;
        int distance = uTool.findDistance(iWorldX, iWorldY, iWorldX, futureY);
        if (distance <= 0 && bFalling) {
            iVelocityY = 0;
            iWorldY += Math.abs(distance);
        }
    }
}
