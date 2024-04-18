package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ArrowProjectile extends ProjectileEntity implements HasImages {
    public ArrowProjectile(GamePanel gp, Point targetPoint, Entity source) {
        super(gp);
        this.gp = gp;

        name = "arrow";
        worldX = source.worldX + gp.tileSize /2;
        worldY = source.worldY + gp.tileSize /2;
        this.source = source;
        collisionDmg = 4;
        healthMax = 2;
        health = healthMax;
        armor = 0;
        speed = 17;
        Point sourcePoint = new Point();
        if (source == gp.player) {
            sourcePoint.x = gp.screenWidth /2;
            sourcePoint.y = gp.screenHeight /2;
        }
        else {
            sourcePoint.x = worldX;
            sourcePoint.y = worldY;
        }

        velocityX = findVelocityX(this, targetPoint, sourcePoint);
        velocityY = findVelocityY(this, targetPoint, sourcePoint);
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
            scaledImage = uTool.scaleImage(scaledImage, gp.tileSize /2, gp.tileSize /2);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return scaledImage;
    }
}
