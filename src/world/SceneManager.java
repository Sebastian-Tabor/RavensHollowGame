package world;

import main.GamePanel;
import main.Main;
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
        //int x1 = gp.player.iScreenPosX - gp.player.iWorldX;
        //int y1 = gp.player.iScreenPosY  - gp.player.iWorldY;
        //int x2 = scene[gp.iScene].background.getWidth();
        //int y2 = scene[gp.iScene].background.getHeight();

        int sx1 = gp.player.iScreenPosX - gp.player.iWorldX;
        int sy1 = gp.player.iScreenPosY  - gp.player.iWorldY;
        //int sx2 = gp.iScreenWidth;
        //int sy2 = gp.iScreenHeight;

        g2.drawImage(scene[gp.iScene].foreground, sx1 ,sy1,null);
        //g2.drawImage(scene[gp.iScene].background, x1, y1, x2, y2, sx1, sy1, sx2, sy2, null);
    }
    public void drawMidground(Graphics2D g2){

        int x = gp.player.iScreenPosX - gp.player.iWorldX;
        int y = gp.player.iScreenPosY  - gp.player.iWorldY;

        g2.drawImage(scene[gp.iScene].midground, x ,y,null);

    }
    public void drawBackground(Graphics2D g2){


        int x = gp.player.iScreenPosX - gp.player.iWorldX;
        int y = gp.player.iScreenPosY  - gp.player.iWorldY;

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
