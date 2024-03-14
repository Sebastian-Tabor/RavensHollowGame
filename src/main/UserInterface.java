package main;
import entity.Entity;

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
        g2.drawString(text, getXCenterString(text), getYCenterString(text));
        displayedImage = titleImage;
        g2.drawImage(displayedImage, 0, 0, gp.iScreenWidth, gp.iScreenHeight, null);
    }
//DRAW PLAY SCREEN
    public void drawPlayScreen() {
        displayedImage = playImage;
        g2.drawImage(displayedImage, 0, 0, gp.iScreenWidth, gp.iScreenHeight, null);
        drawPlayerBars();
    }
//DRAW PAUSE SCREEN
    public void drawPauseScreen() {
        displayedImage = pauseImage;
        g2.setColor(new Color(0,0,0,70));
        g2.fillRect(0,0, gp.iScreenWidth, gp.iScreenHeight);
        g2.drawImage(displayedImage, 0, 0, gp.iScreenWidth, gp.iScreenHeight, null);

        text = "Pause";
        g2.setColor(Color.white);
        g2.drawString(text, getXCenterString(text), getYCenterString(text));

        text = "Quit";
        int x = gp.iTileSize;
        int y = getYCenterString(text);
        g2.drawString(text, x ,y);
    }
//DRAW LOADING SCREEN
    public void drawLoadingScreen() {
        text = "Loading";
        g2.drawString(text, getXCenterString(text), getYCenterString(text));
        displayedImage = loadingImage;
        g2.drawImage(displayedImage, 0, 0, gp.iScreenWidth, gp.iScreenHeight, null);
    }
//DRAW END SCREEN
    public void drawEndScreen() {
        text = "End";
        g2.drawString(text, getXCenterString(text), getYCenterString(text));
        displayedImage = endImage;
    }
//FONT METHODS
    public int getXCenterString(String text){
        int length = getStringWidth(text);
        return gp.iScreenWidth/2 - length/2;
    }
    public int getYCenterString(String text){
        int length = getStringHeight(text);
        return gp.iScreenHeight/2 - length/2;
    }

    public int getStringWidth(String text){
        return (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
    }
    public int getStringHeight(String text){
        return (int) g2.getFontMetrics().getStringBounds(text, g2).getHeight();
    }
//CLICKBOX METHODS
    public Point mouseLocation(){
        return MouseInfo.getPointerInfo().getLocation();
    }
//STAT BARS
    public void drawPlayerBars(){
        drawHealthBar(gp.player, "bottom health", Color.red);
        drawUltBar(gp.player, "bottom ult", Color.lightGray);
    }
    public void drawHealthBar(Entity entity, String barType, Color color){

        int width = 320;
        int subWidth = (int)(0.96 * width) * (entity.iHealth / entity.iHealthMax);
        int subX = (int)(0.02 * width);

        Point p = getBarPosition(barType);
        g2.setColor(Color.black);
        g2.fillRoundRect(p.x, p.y, width, 24, 10, 10);
        g2.setColor(color);
        g2.fillRoundRect(p.x + subX, p.y + 3, subWidth, 18, 10, 10);
    }
    public void drawUltBar(Entity entity, String barType, Color color){

        int width = 300;
        int subWidth = (int)(0.96 * width) * (entity.iUltimate / entity.iUltimateMax) ;
        int subX = (int)(0.02 * width);

        Point p = getBarPosition(barType);
        g2.setColor(Color.black);
        g2.fillRoundRect(p.x, p.y, width, 24, 5, 5);
        g2.setColor(color);
        g2.fillRoundRect(p.x + subX, p.y + 3, subWidth, 18, 5, 5);
    }
    public Point getBarPosition(String barType){
        Point p = new Point();
        switch (barType) {
            case "top health":
                p.x = (int)(0.4 * gp.iScreenWidth);
                p.y = 108;
                break;
            case "top ult":
                p.x = (int)(0.4 * gp.iScreenWidth);
                p.y = 140;
                break;
            case "bottom health":
                p.x = (int)(0.4 * gp.iScreenWidth);
                p.y = 972;
                break;
            case "bottom ult":
                p.x = (int)(0.4 * gp.iScreenWidth);
                p.y = 1004;
                break;
        }
        return p;
    }
}
