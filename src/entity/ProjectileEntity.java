package entity;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ProjectileEntity extends Entity {
    BufferedImage projectileimage;
    public ProjectileEntity(GamePanel gp) {
        super(gp);
        this.gp = gp;
        hitBox = new Rectangle(0, 13, 6, 6);
        hitBoxDefaultX = hitBox.x;
        hitBoxDefaultY = hitBox.y;
        gravity = -gp.tileSize /15;
    }
    public void update(){
        //SETTING COLLISION
        collisionDetected = false;
        //COLLISION
        int monsterIndex = gp.cCheck.checkEntity(this, gp.monster);   //HIT MONSTER
        hitMonster(monsterIndex);                                           //HIT MONSTER
        if (source != gp.player) {
            if (gp.cCheck.checkPlayer(this)) {              //HIT PLAYER
                damagePlayer(collisionDmg);                       //HIT PLAYER
            }
            int npcIndex = gp.cCheck.checkEntity(this, gp.npc);  //HIT NPC
            hitNPC(npcIndex);                                           //HIT NPC
        }
        gp.cCheck.checkTile(this);
        //GRAVITY
        //velocityY--;
        worldY -= velocityY;
        if (velocityY < gravity) velocityY = gravity;
        worldX += velocityX;
        if (velocityX < 0) facing = "left";
    }
    public void draw(Graphics2D g2){
        BufferedImage image = projectileimage;
        int iScreenX = worldX - gp.player.worldX + gp.player.iScreenPosX;
        int iScreenY = worldY - gp.player.worldY + gp.player.iScreenPosY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.iScreenPosX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.iScreenPosX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.iScreenPosY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.iScreenPosY) {
            if (facing.equals("left")) {
                g2.drawImage(image, iScreenX + image.getWidth(), iScreenY, -image.getWidth(), image.getHeight(), null);
            } else {
                g2.drawImage(image, iScreenX, iScreenY, image.getWidth(), image.getHeight(), null);
            }
        }
    }
    public void hitNPC(int index) {
        if(index != 999 && !dying && alive){
            damageNPC(index, this);
        }
    }
    public void hitMonster(int index) {
        if(index != 999 && !dying && alive){
            damageMonster(index, this);
        }
    }
    public int findVelocityX(ProjectileEntity projectile, Point targetPoint, Point sourcePoint){
        double x = sourcePoint.x;
        double y = sourcePoint.y;
        x = x - targetPoint.x;
        y = targetPoint.y - y;

        double angle = Math.toDegrees(Math.atan2(y, x)) + 180;

        x = (projectile.speed * Math.cos(Math.toRadians(angle)));

        return (int) x;

    }
    public int findVelocityY(ProjectileEntity projectile, Point targetPoint, Point sourcePoint){
        double x = sourcePoint.x;
        double y = sourcePoint.y;
        x = x - targetPoint.x;
        y = targetPoint.y - y;


        double angle = Math.toDegrees(Math.atan2(y, x)) + 180;

        y = (projectile.speed * Math.sin(Math.toRadians(angle)));

        return (int) y;

    }
}
