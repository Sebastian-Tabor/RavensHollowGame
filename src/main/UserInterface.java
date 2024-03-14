package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UserInterface {
    Font defaultFont;
    public GamePanel gp;
    public Graphics2D g2;
    public String text;
    public BufferedImage displayedImage, titleImage, playImage, pauseImage, endImage, loadingImage;

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
        g2.setFont(defaultFont);
        g2.setColor(Color.white);
        if (gp.iGameState == gp.titleState) drawTitleScreen();
        if (gp.iGameState == gp.playState) drawPlayScreen();
        if (gp.iGameState == gp.loadingState) drawLoadingScreen();
        if (gp.iGameState == gp.pauseState) drawPauseScreen();
        if (gp.iGameState == gp.endState) drawEndScreen();
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
            loadingImage = ImageIO.read(new File("./res/ui/loadingscreen.png"));
            endImage = ImageIO.read(new File("./res/ui/endscreen.png"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
//DRAW TITLE SCREEN
    public void drawTitleScreen() {
        text = "Title";
        g2.drawString(text, getXCenterString(), getYCenterString());
        displayedImage = titleImage;
    }
//DRAW PLAY SCREEN
    public void drawPlayScreen() {
        displayedImage = playImage;
    }
    public void drawLoadingScreen() {
        text = "Loading";
        g2.drawString(text, getXCenterString(), getYCenterString());
        displayedImage = loadingImage;
    }
//DRAW PAUSE SCREEN
    public void drawPauseScreen() {
        text = "Pause";
        g2.drawString(text, getXCenterString(), getYCenterString());
        displayedImage = pauseImage;
    }
//DRAW END SCREEN
    public void drawEndScreen() {
        text = "End";
        g2.drawString(text, getXCenterString(), getYCenterString());
        displayedImage = endImage;
    }
//FONT METHODS
    public int getXCenterString(){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.iScreenWidth/2 - length/2;
    }
    public int getYCenterString(){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getHeight();
        return gp.iScreenHeight/2 - length/2;
    }
    public int getYTopQuarterString(){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getHeight();
        return gp.iScreenHeight/4 - length/2;
    }

}
