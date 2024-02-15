package entity;

import java.awt.image.BufferedImage;

public class Entity {
    public int iMaxHealth;
    public int iCurrentHealth;
    public int iDamagePerHit;
    public int iMaxUltimate;
    public int iCurrentUltimate;
    public int iPerHitUltimate;
    public int iUltimateDamage;
    public int x, y;
    public int speed;
    public int spriteCounter = 0;
    public int spriteNumber = 1;
    public boolean canJump;
    public int jumpCounter = 0;
    public BufferedImage jump1, jump2, crouch1, crouch2, right1, right2, left1, left2, idle1, idle2;
    public String direction;

}
