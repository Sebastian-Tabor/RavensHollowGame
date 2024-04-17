package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class ArrowProjectile extends ProjectileEntity implements HasImages {
    public ArrowProjectile(GamePanel gp, Entity source) {
        super(gp);
        this.gp = gp;

        name = "arrow";
        if (source.facing.equals("left")) {
            worldX = source.worldX - gp.iTileSize - gp.player.worldX + gp.player.iScreenPosX;
        } else {
            worldX = source.worldX + gp.iTileSize - gp.player.worldX + gp.player.iScreenPosX;
        }
        worldY = source.worldY - gp.player.worldY + gp.player.iScreenPosY;
        collisionDmg = 4;
        healthMax = 2;
        health = healthMax;
        armor = 0;
        speed = 10;
        velocityX = findVelocity(this).x;
        velocityY = findVelocity(this).y;

    }

    @Override
    public void getImage() {

        image = setup("./res/entities/projectiles/arrow");

    }

    @Override
    public BufferedImage setup(String imageName) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage scaledImage = null;
        try {
            scaledImage = ImageIO.read(new File(imageName + ".png"));
            scaledImage = uTool.scaleImage(scaledImage, gp.iTileSize/2, gp.iTileSize/2);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return scaledImage;
    }
}
