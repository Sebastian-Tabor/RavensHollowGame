package main;

import entity.Entity;

import java.awt.*;
import java.util.Random;

public class Particles extends Entity {
    Graphics2D g2;


    public Particles(GamePanel gp, Entity source) {
        super(gp);

        color = source.color;
        worldX = source.worldX;
        worldY = source.worldY;
        speed = 4;
        int rand = new Random().nextInt(4) - 2;
        velocityX = speed * rand;
        rand = new Random().nextInt(4) - 2;
        velocityY = speed * rand;

        hitBox = new Rectangle(worldX, worldY, 5, 5);

    }
    public void update(){

        worldX += velocityX;
        worldY -= velocityY;
        worldY++;

        for (int i = 0; i < gp.particleCounter.size(); i++){
            int i1 = gp.particleCounter.get(i) + 1;
            gp.particleCounter.set(i, i1);
            if (gp.particleCounter.get(i) == 60){
                gp.particleCounter.remove(i);
                gp.particle.remove(i);
                break;
            }
        }
    }
    public void draw(Graphics2D g2){
        this.g2 = g2;

        int iScreenX = worldX - gp.player.worldX + gp.player.iScreenPosX;
        int iScreenY = worldY - gp.player.worldY + gp.player.iScreenPosY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.iScreenPosX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.iScreenPosX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.iScreenPosY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.iScreenPosY) {
            g2.setColor(Color.black);
            g2.fillRect(iScreenX, iScreenY, hitBox.width + 2, hitBox.height + 2);
            g2.setColor(color);
            g2.fillRect(iScreenX, iScreenY, hitBox.width, hitBox.height);
        }
    }
}

