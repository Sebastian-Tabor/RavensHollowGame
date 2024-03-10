package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UserInterface {
    public GamePanel gp;
    public Graphics2D g2;
    Font defaultFont;
    public BufferedImage displayedImage, titleImage, playImage, pauseImage, endImage;

    public UserInterface(GamePanel gp) {
        this.gp = gp;
        setFonts();
        setImages();
    }
//UPDATE
    public void update(){

    }
//DRAW
    public void draw(Graphics2D g2){
        this.g2 = g2;
        if (gp.iGameState == gp.titleState) {
            drawTitleScreen();
        }
        if (gp.iGameState == gp.playState){
            drawPlayScreen();
        }
        if (gp.iGameState == gp.pauseState){
            drawPauseScreen();
        }
        if (gp.iGameState == gp.endState){
            drawEndScreen();
        }
        g2.drawImage(displayedImage, 0, 0, gp.iScreenWidth, gp.iScreenHeight, null);
    }
//FONTS
    public void setFonts(){
        defaultFont = new Font("Arial", Font.PLAIN, 40);
    }
//IMAGES
    public void setImages() {
        try {

            titleImage = ImageIO.read(new File("./res/ui/titlescreen.png"));
            playImage = ImageIO.read(new File("./res/ui/playscreen.png"));
            pauseImage = ImageIO.read(new File("./res/ui/pausescreen.png"));
            endImage = ImageIO.read(new File("./res/ui/endscreen.png"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
//DRAW TITLE SCREEN
    public void drawTitleScreen() {
        displayedImage = titleImage;
    }
//DRAW PLAY SCREEN
    public void drawPlayScreen() {
        displayedImage = playImage;
    }
//DRAW PAUSE SCREEN
    public void drawPauseScreen() {
        displayedImage = pauseImage;
    }
//DRAW END SCREEN
    public void drawEndScreen() {
        displayedImage = endImage;
    }
}
