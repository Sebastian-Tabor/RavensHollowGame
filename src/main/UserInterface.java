package main;
import entity.Entity;
import object.Object;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UserInterface {
    Font defaultFont, titleFont;
    public GamePanel gp;
    public Graphics2D g2;
    public String text;
    public BufferedImage displayedImage, titleImage, playImage, pauseImage, endImage, loadingImage, tempImage;
    public int commandNumber;


    public UserInterface(GamePanel gp) {
        this.gp = gp;
        setFonts();
        setImages();
    }

//DRAW
    public void draw(Graphics2D g2){
        this.g2 = g2;
        g2.setFont(defaultFont);
        g2.setColor(Color.white);
        if (gp.iGameState == gp.titleState) {
            drawTitleScreen();
        }
        if (gp.iGameState == gp.playState) {
            drawPlayScreen();
        }
        if (gp.iGameState == gp.pauseState) {
            drawPauseScreen();
        }
        if (gp.iGameState == gp.optionsState) {
            drawOptionsScreen();
        }
        if (gp.iGameState == gp.loadingState) {
            drawLoadingScreen();
        }
        if (gp.iGameState == gp.endState) {
            drawEndScreen();
        }
    }
//FONTS
    public void setFonts(){
        defaultFont = new Font("Arial", Font.PLAIN, 40);
        titleFont = new Font("Arial", Font.BOLD, 80);
    }
//IMAGES
    public void setImages() {
        try {

            titleImage = ImageIO.read(new File("./res/ui/titlescreen.png"));
            playImage = ImageIO.read(new File("./res/ui/playscreen.png"));
            pauseImage = ImageIO.read(new File("./res/ui/pausescreen.png"));
            loadingImage = ImageIO.read(new File("./res/ui/loadingscreen.png"));
            endImage = ImageIO.read(new File("./res/ui/endscreen.png"));
            tempImage = ImageIO.read(new File("./res/ui/tempimage.png"));

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
        int x;
        int y;

        g2.setColor(new Color(0,0,0,60));
        g2.fillRect(0,0, gp.iScreenWidth, gp.iScreenHeight);

        g2.setColor(new Color(0,0,0,200));
        g2.fillRect(gp.iScreenWidth/4,gp.iScreenHeight/4, gp.iScreenWidth/2, gp.iScreenHeight/2);

        displayedImage = pauseImage;
        g2.drawImage(tempImage,gp.iScreenWidth/4,gp.iScreenHeight/4, gp.iTileSize, gp.iTileSize, null);

        text = "Paused";
        g2.setFont(titleFont);
        x = getXCenterString(text);
        y = getYCenterString(text);
        g2.setColor(Color.white);
        g2.drawString(text, x, y - gp.iTileSize);

        drawMenuTextOption("Resume", 0);
            if (commandNumber == 0 && KeyBinds.bEnterPressed) {
                gp.iGameState = gp.playState;
                gp.resumeMusic();
            }

        drawMenuTextOption("Options", 1);
            if (commandNumber == 1 && KeyBinds.bEnterPressed) {
                gp.iGameState = gp.optionsState;
                commandNumber = 0;
            }
        drawMenuTextOption("Quit", 2);
            if (commandNumber == 2 && KeyBinds.bEnterPressed) {
                System.exit(0);
            }
        //LOOP
        if (commandNumber > 2) {
            commandNumber = 0;
        }
        if (commandNumber < 0) {
            commandNumber = 2;
        }
    }
    public void drawOptionsScreen() {
        text = "Loading";
        g2.setFont(titleFont);
        g2.drawString(text, getXCenterString(text), getYCenterString(text));

        g2.setColor(new Color(0,0,0,200));
        g2.fillRect(gp.iScreenWidth/4,gp.iScreenHeight/4, gp.iScreenWidth/2, gp.iScreenHeight/2);

        drawMenuTextOption("Audio", 0);
        if (commandNumber == 0 && KeyBinds.bEnterPressed) {
            System.out.println("Implement audio options");
            System.exit(0);
        }
        drawMenuTextOption("Hotkeys", 1);
        if (commandNumber == 1 && KeyBinds.bEnterPressed) {
            System.out.println("Implement hotkey options");
            System.exit(0);
        }
        drawMenuTextOption("Accessibility", 1);
        if (commandNumber == 2 && KeyBinds.bEnterPressed) {
            System.out.println("Implement accessibility options");
            System.exit(0);
        }
        drawMenuTextOption("Back", 2);
        if (commandNumber == 3 && KeyBinds.bEnterPressed) {
            gp.iGameState = gp.pauseState;
            commandNumber = 0;
        }
        //LOOP
        if (commandNumber > 2) {
            commandNumber = 0;
        }
        if (commandNumber < 0) {
            commandNumber = 2;
        }
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
        int length = getBufferedStringWidth(text);
        return gp.iScreenWidth/2 - length/2;
    }
    public int getYCenterString(String text){
        int length = getBufferedStringHeight(text);
        return gp.iScreenHeight/2 - length/2;
    }
    public int getBufferedStringWidth(String text){
        return (int) (g2.getFontMetrics().getStringBounds(text, g2).getWidth() + 6);
    }
    public int getBufferedStringHeight(String text){
        return (int) (g2.getFontMetrics().getStringBounds(text, g2).getHeight() + 6);
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
    public void drawMenuTextOption(String text, int menuOptionNumber) {
        text = text;
        g2.setFont(defaultFont);
        int x;
        int y;
        x = getXCenterString(text);
        y = getYCenterString(text) + (gp.iTileSize * menuOptionNumber);
        g2.setColor(Color.white);
        g2.drawString(text, x, y);
        if (commandNumber == menuOptionNumber) {
            g2.drawString(">", x - gp.iTileSize, y);
            g2.drawString("<", (int)(x + g2.getFontMetrics().getStringBounds(text, g2).getWidth() + gp.iTileSize), y );
        }
    }
}
