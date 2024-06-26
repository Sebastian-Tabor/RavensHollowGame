package world;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class SceneManager {

    GamePanel gp;
    public Scene[] scene;
    UtilityTool uTool = new UtilityTool();
    BufferedImage image;
    int x;
    int y;
    int width;
    int height;

    public SceneManager(GamePanel gp) {
        this.gp = gp;
        scene = new Scene[2];

        getScene();

    }
//DRAW METHODS
    public void drawForeground(Graphics2D g2){
        x = gp.player.iWorldX - gp.player.iScreenPosX;
        y = gp.player.iWorldY - gp.player.iScreenPosY;
        width = gp.iScreenWidth;
        height = gp.iScreenHeight;
        image = scene[gp.iScene].foreground.getSubimage(x, y, width, height);
        x = 0;
        y = 0;
        g2.drawImage(image, x, y, null);
    }
    public void drawMidground(Graphics2D g2){
        x = gp.player.iWorldX - gp.player.iScreenPosX;
        y = gp.player.iWorldY - gp.player.iScreenPosY;
        width = gp.iScreenWidth;
        height = gp.iScreenHeight;
        image = scene[gp.iScene].midground.getSubimage(x, y, width, height);
        x = 0;
        y = 0;
        g2.drawImage(image, x, y, null);
    }
    public void drawBackground(Graphics2D g2){
        x = gp.player.iWorldX - gp.player.iScreenPosX;
        y = gp.player.iWorldY - gp.player.iScreenPosY;
        width = gp.iScreenWidth;
        height = gp.iScreenHeight;
        image = scene[gp.iScene].background.getSubimage(x, y, width, height);
        x = 0;
        y = 0;
        g2.drawImage(image, x, y, null);
    }
//SCENE CREATION
    public void getScene() {

        setup(0);
        setup(1);

    }
    public void setup(int index){
        try {

            scene[index] = new Scene();

            scene[index].foreground = ImageIO.read(new File("./res/world/" + index + "f.png"));
            scene[index].foreground = uTool.scaleImage(scene[index].foreground, gp.iMapWidth, gp.iMapHeight);
            scene[index].midground = ImageIO.read(new File("./res/world/" + index + "m.png"));
            scene[index].midground = uTool.scaleImage(scene[index].midground, gp.iMapWidth, gp.iMapHeight);
            scene[index].background = ImageIO.read(new File("./res/world/" + index + "b.png"));
            scene[index].background = uTool.scaleImage(scene[index].background, gp.iMapWidth, gp.iMapHeight);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
