package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
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
//DIRECTION
    public int iWorldX, iWorldY;
    public int iSpeed;
    public int iVelocityY;
    public int iVelocityX;
    public BufferedImage jump1, jump2, leftjump1,leftjump2, rightjump1, rightjump2, crouch1, crouch2, right1, right2, left1, left2, idle1, idle2;
    public String direction;
//MOVEMENT
    public void moveLeft(Entity entity) {
        if (!bCollisionLeft) {
            iWorldX += entity.iVelocityX;
        }
    }
    public void moveRight(Entity entity) {
        if (!bCollisionRight) {
            iWorldX += entity.iVelocityX;
        }
    }
    public void jump(Entity entity) {
        if (bCanJump && !bCollisionTop && bCollisionBottom) {
            iJumpCooldown = iRecoveryTime;
            iWorldY -= 1 + iSenseRange;
            iVelocityY = entity.iSpeed + 10;
            bCanJump = false;
        }
    }
}
