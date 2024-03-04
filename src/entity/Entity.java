package entity;

import main.CollisionCheck;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    public int iMaxHealth;
    public int iCurrentHealth;
    public int iDamagePerHit;
    public int iMaxUltimate;
    public int iCurrentUltimate;
    public int iPerHitUltimate;
    public int iUltimateDamage;
    public int iWorldX, iWorldY;
    public int iSpeed;

//ANIMATIONS
    public int iSpriteCounter = 0;
    public int iSpriteNumber = 1;

//JUMPING
    public int iJump;
    public int iJumpCooldown;
    public boolean bCanJump;
    public boolean bFalling = false;
    public int iRecoveryTime;
//COLLISION
    public int iSenseRange = 10;
    public Rectangle hitBox;
    public boolean bCollisionLeft, bCollisionRight, bCollisionTop, bCollisionBottom, bCollisionFloor = false;

    public void setCollision(boolean Boolean){
        bCollisionLeft = Boolean;
        bCollisionRight = Boolean;
        bCollisionTop = Boolean;
        bCollisionBottom = Boolean;
    }


//DIRECTION
    public BufferedImage jump1, jump2, leftjump1,leftjump2, rightjump1, rightjump2, crouch1, crouch2, right1, right2, left1, left2, idle1, idle2;
    public String direction;
//MOVEMENT
    public void moveLeft(Entity entity) {
        if (!bCollisionLeft) {
            iWorldX -= entity.iSpeed;
        }
    }
    public void moveRight(Entity entity) {
        if (!bCollisionRight) {
            iWorldX += entity.iSpeed;
        }
    }
    public void jump(Entity entity) {
        if (bCanJump) {
            iJumpCooldown = 30;
            entity.iWorldY -= (1 + iSenseRange);
            iJump = 30;
            bCanJump = false;
        }
    }
}
