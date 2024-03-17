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

    public SceneManager(GamePanel gp) {
        this.gp = gp;
        scene = new Scene[2];

        getScene();

    }
//DRAW METHODS
    public void drawForeground(Graphics2D g2){
        int w = scene[gp.iScene].foreground.getWidth() - gp.player.iWorldX + gp.player.iScreenPosX;
        int h = scene[gp.iScene].foreground.getHeight() - gp.player.iWorldY + gp.player.iScreenPosY;
        int x = gp.player.iWorldX - gp.player.iScreenPosX;
        int y = gp.player.iWorldY - gp.player.iScreenPosY;
        scene[gp.iScene].foreground.getSubimage(x, y, w, h);
        x = gp.player.iScreenPosX - gp.player.iWorldX;
        y = gp.player.iScreenPosY  - gp.player.iWorldY;
        g2.drawImage(scene[gp.iScene].foreground, x ,y,null);

    }
    public void drawMidground(Graphics2D g2){

        int w = scene[gp.iScene].midground.getWidth() - gp.player.iWorldX + gp.player.iScreenPosX;
        int h = scene[gp.iScene].midground.getHeight() - gp.player.iWorldY + gp.player.iScreenPosY;
        int x = gp.player.iWorldX - gp.player.iScreenPosX;
        int y = gp.player.iWorldY - gp.player.iScreenPosY;
        scene[gp.iScene].midground.getSubimage(x, y, w, h);
        x = gp.player.iScreenPosX - gp.player.iWorldX;
        y = gp.player.iScreenPosY  - gp.player.iWorldY;
        g2.drawImage(scene[gp.iScene].midground, x ,y,null);

    }
    public void drawBackground(Graphics2D g2){

        int w = scene[gp.iScene].background.getWidth() - gp.player.iWorldX + gp.player.iScreenPosX;
        int h = scene[gp.iScene].background.getHeight() - gp.player.iWorldY + gp.player.iScreenPosY;
        int x = gp.player.iWorldX - gp.player.iScreenPosX;
        int y = gp.player.iWorldY - gp.player.iScreenPosY;
        scene[gp.iScene].background.getSubimage(x, y, w, h);
        x = gp.player.iScreenPosX - gp.player.iWorldX;
        y = gp.player.iScreenPosY  - gp.player.iWorldY;
        g2.drawImage(scene[gp.iScene].background, x ,y,null);

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
