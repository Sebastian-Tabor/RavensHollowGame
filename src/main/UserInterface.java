package main;
import entity.SuperEntity;

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
    public int menuState;
    public int selectedOption;


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
        g2.drawString(text, gp.iScreenWidth/2, gp.iScreenHeight/2);
        displayedImage = titleImage;
        g2.drawImage(displayedImage, 0, 0, gp.iScreenWidth, gp.iScreenHeight, null);
    }
//DRAW PLAY SCREEN
    public void drawPlayScreen() {
       // displayedImage = playImage;
       // g2.drawImage(displayedImage, 0, 0, gp.iScreenWidth, gp.iScreenHeight, null);

        if (KeyBinds.bEscapePressed) {
            gp.iGameState = gp.pauseState;
            gp.stopMusic();
            menuState = 0;
            KeyBinds.bEscapePressed = false;
        }
    }
//DRAW PAUSE SCREEN
    public void drawPauseScreen() {
        if (menuState == 0) {
            drawMenu0();
        }
        else if (menuState == 1) {
            drawMenu1();
        }
        else if(menuState == 2) {
            drawMenu2();
        }
    }

    public void drawMenu0(){
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
        x = gp.iScreenWidth/2;
        y = gp.iScreenHeight/2;
        g2.setColor(Color.white);
        g2.drawString(text, x, y);
        if (drawButton(text, gp.iScreenWidth/2, gp.iScreenHeight/2).contains(gp.mouseBinds.getMouseLocation()) && MouseBinds.bMouse1Clicked) {
            System.out.println("Button Clicked");
            }
        }
    public void drawMenu1() {
        }
    public void drawMenu2() {
        }

//DRAW LOADING SCREEN
    public void drawLoadingScreen() {
    }
//DRAW END SCREEN
    public void drawEndScreen() {
    }

//FONT METHODS
    public Rectangle drawButton(String text, int x, int y){
        Rectangle rect = new Rectangle();
        rect.x = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        rect.y = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        rect.width = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        rect.height = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        g2.setColor(new Color(35,35,35));
        g2.fillRoundRect(x, y, stringWidth(text) + gp.iTileSize, stringHeight(text) + gp.iTileSize/2, 10, 10 );
        g2.setColor(new Color(100,100,100));
        g2.fillRoundRect(x + gp.iTileSize/2, y + gp.iTileSize/2, stringWidth(text) + gp.iTileSize/2, stringHeight(text) + gp.iTileSize/4, 10, 10 );
        g2.setColor(Color.BLACK);
        g2.drawString(text, x + gp.iTileSize/2, y + gp.iTileSize/4);
        return rect;
    }

    public int stringWidth(String text){
        return (int) g2.getFontMetrics().getStringBounds(text ,g2).getWidth();
    }
    public int stringHeight(String text){
        return (int) g2.getFontMetrics().getStringBounds(text, g2).getHeight();
    }
}

