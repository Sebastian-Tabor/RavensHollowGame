package world;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;

public class SceneManager {

    GamePanel gp;
    public Scene[] scene;


    public SceneManager(GamePanel gp) {
        this.gp = gp;
        scene = new Scene[2];

        getScene();

    }
//DRAW METHODS
    public void drawForeground(Graphics2D g2){

        int x = gp.player.iStartPosX - gp.player.iWorldX;
        int y = gp.player.iStartPosY - gp.player.iWorldY;

        g2.drawImage(scene[gp.iScene].foreground, x ,y,null);

    }
    public void drawMidground(Graphics2D g2){

        int x = gp.player.iStartPosX - gp.player.iWorldX;
        int y = gp.player.iStartPosY - gp.player.iWorldY;

        g2.drawImage(scene[gp.iScene].midground, x ,y,null);

    }
    public void drawBackground(Graphics2D g2){

        int x = gp.player.iStartPosX - gp.player.iWorldX;
        int y = gp.player.iStartPosY - gp.player.iWorldY;

        g2.drawImage(scene[gp.iScene].background, x, y, null);

    }
//SCENE CREATION
    public void getScene() {

        setup(0);
        setup(1);

    }
    public void setup(int index){
        UtilityTool uTool = new UtilityTool();
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
