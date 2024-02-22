package entity;

import java.awt.image.BufferedImage;

public class EntityManager {
    public int iMaxHealth;
    public int iCurrentHealth;
    public int iDamagePerHit;
    public int iMaxUltimate;
    public int iCurrentUltimate;
    public int iPerHitUltimate;
    public int iUltimateDamage;
    public int iPlayerX, iPlayerY;
    public int speed;

    // Animations
    public int spriteCounter = 0;
    public int spriteNumber = 1;

    //Jump vars
    public boolean canJump;
    public boolean falling;
    public int jumpCounter = 0;

    public BufferedImage jump1, jump2, leftjump1,leftjump2, rightjump1, rightjump2, crouch1, crouch2, right1, right2, left1, left2, idle1, idle2;
    public String direction;

}