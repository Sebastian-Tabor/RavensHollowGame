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
            worldX = source.worldX - gp.iTileSize;
        } else {
            worldX = source.worldX + gp.iTileSize + 3;
        }
        worldY = source.worldY;
        collisionDmg = 4;
        healthMax = 2;
        health = healthMax;
        armor = 0;
        speed = 10;
        velocityX = 3 * findVelocity(this).x;
        velocityY = 2 * findVelocity(this).y;
        getImage();
    }

    @Override
    public void getImage() {

        projectileimage = setup("./res/entities/projectiles/arrow");

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
