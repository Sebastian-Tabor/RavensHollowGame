package entity;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ProjectileEntity extends Entity {
    BufferedImage image;
    public ProjectileEntity(GamePanel gp) {
        super(gp);
        this.gp = gp;
        hitBox = new Rectangle(8, 2, 48, 60);
        hitBoxDefaultX = hitBox.x;
        hitBoxDefaultY = hitBox.y;
        gravity = -gp.iTileSize/5;
    }
    public void update(){
        //SETTING COLLISION
        collisionDetected = false;
        //COLLISION
        gp.cCheck.checkTile(this);
        if (source != gp.player) {
            int npcIndex = gp.cCheck.checkEntity(this, gp.npc);
            hitNPC(npcIndex);
            if (gp.cCheck.checkPlayer(this)){
                damagePlayer(collisionDmg);
            }
        }
        int monsterIndex = gp.cCheck.checkEntity(this, gp.monster);
        hitMonster(monsterIndex);
        //GRAVITY
        worldY -= velocityY;
        velocityY--;
        if (velocityY < gravity) velocityY = gravity;
        worldX += velocityX;
        if (velocityX < 0) facing = "left";
    }
    public void draw(Graphics2D g2){

        int iScreenX = worldX - gp.player.worldX + gp.player.iScreenPosX;
        int iScreenY = worldY - gp.player.worldY + gp.player.iScreenPosY;

        if (worldX + gp.iTileSize > gp.player.worldX - gp.player.iScreenPosX &&
                worldX - gp.iTileSize < gp.player.worldX + gp.player.iScreenPosX &&
                worldY + gp.iTileSize > gp.player.worldY - gp.player.iScreenPosY &&
                worldY - gp.iTileSize < gp.player.worldY + gp.player.iScreenPosY) {
            if (facing.equals("left")) {
                g2.drawImage(image, iScreenX + gp.iTileSize, iScreenY, -gp.iTileSize, gp.iTileSize, null);
            } else {
                g2.drawImage(image, iScreenX, iScreenY, gp.iTileSize, gp.iTileSize, null);
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
    public Point findVelocity(ProjectileEntity projectile){
        int x = gp.player.iScreenPosX;
        int y = gp.player.iScreenPosY;
        Point p1 = gp.mouseBinds.getMouseLocation();
        x = p1.x - x;
        y = p1.y - y;

        x = (int) (projectile.speed * Math.cos(Math.atan2(y, x)));
        y = (int) (projectile.speed * Math.sin(Math.atan2(y, x)));

        p1.x = x;
        p1.y = y;
        return p1;
    }
}