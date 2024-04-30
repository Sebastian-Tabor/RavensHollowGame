package world;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;

public class SceneManager {

    GamePanel gp;
    public Scene[] scene;
    public BackgroundImage[] images;

    public SceneManager(GamePanel gp) {
        this.gp = gp;
        scene = new Scene[2];
        images = new BackgroundImage[4];
        getSceneImage();

    }
//SCENE CREATION
    public void getSceneImage() {

        setup(0);
        setup(1);

    }
    public void setup(int index){
        try {
            scene[index] = new Scene();
            scene[index].topImage = ImageIO.read(new File("./res/world/" + index + "t.png"));
            scene[index].midImage = ImageIO.read(new File("./res/world/" + index + "m.png"));
            scene[index].botImage = ImageIO.read(new File("./res/world/" + index + "b.png"));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    //DRAW METHODS
    public void drawScene(Graphics2D g2){
        for (int i = 0; i < images.length; i++) {

            images[i] = new BackgroundImage();
            images[i].image = scene[gp.sceneNumber].midImage;
            images[i].x = gp.player.screenPosX - gp.player.worldX + (i * images[i].image.getWidth());
            images[i].y = gp.player.screenPosY - gp.player.worldY;

            if (images[i].x < -images[i].image.getWidth()) images[i].x += (4 * images[i].image.getWidth());
            if (images[i].x > (gp.screenWidth + images[i].image.getWidth())) images[i].x -= (4 * images[i].image.getWidth());
            if (images[i] != null) {
                g2.drawImage(images[i].image, images[i].x, images[i].y, null);
            }
        }
    }
}
