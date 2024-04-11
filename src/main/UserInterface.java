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
    public UtilityTool uTool;
    public String text;
    public BufferedImage displayedImage, titleImage, playImage, pauseImage, endImage, loadingImage, tempImage;
    public int menuState;
    public int commandNumber;
    public int bClicked = 0;
    public String[] optionArray;


    public UserInterface(GamePanel gp) {
        this.gp = gp;
        uTool = new UtilityTool();
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
        optionArray = new String[3];
        optionArray[0] = "New Game";
        optionArray[1] = "Load Game";
        optionArray[2] = "Quit";
        drawMenuButtons(optionArray, gp.iScreenWidth/4, gp.iScreenHeight/4);
        if (bClicked == 1){
            switch (commandNumber){
                case 0 -> {
                    if (!MouseBinds.bMouse1Clicked) {
                        gp.iGameState = gp.playState;
                        gp.stopMusic();
                        gp.playMusic(2);
                        bClicked = 0;
                    }

                }
                case 1 -> System.out.println("Create " + optionArray[commandNumber] + " function.");
                case 2 -> {
                    if (!MouseBinds.bMouse1Clicked) System.exit(0);
                }
            }
        } else bClicked = 0;

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

        g2.setFont(defaultFont);
        optionArray = new String[3];
        optionArray[0] = "Save Game";
        optionArray[1] = "Settings";
        optionArray[2] = "Main Menu";
        drawMenuButtons(optionArray, gp.iScreenWidth/2, gp.iScreenHeight/2);
        if (bClicked == 1){
            switch (commandNumber){
                case 0, 1 -> System.out.println("Create " + optionArray[commandNumber] + " function.");
                case 2 -> {
                    if (!MouseBinds.bMouse1Clicked){
                        gp.iGameState = gp.titleState;
                        bClicked = 0;
                    }
                }
            }
        } else bClicked = 0;

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
//BUTTON METHODS
    public void drawButton(int optionNumber, String text, int x, int y, Color color1, Color color2, Color highlight){

        Rectangle rect2 = new Rectangle();
        Rectangle rect3 = new Rectangle();
        Rectangle rect1 = new Rectangle();

        //SETUP RECTANGLES
        rect1.width = stringWidth(text) + gp.iTileSize + 2;
        rect1.height = stringHeight(text) + gp.iTileSize/2 + 2;
        rect1.x = x - rect1.width/2;
        rect1.y = y - rect1.height/2;

        rect2.width = stringWidth(text) + gp.iTileSize;
        rect2.height = stringHeight(text) + gp.iTileSize/2;
        rect2.x = x - rect2.width/2;
        rect2.y = y - rect2.height/2;

        rect3.width = (int) (rect2.width*0.95);
        rect3.height = (int) (rect2.height*0.9);
        rect3.x = centerToRectX(rect2.x, rect2, rect3);
        rect3.y = centerToRectY(rect2.y, rect2, rect3);

        if (gp.mouseBinds.isMouseOver(rect3)) {
            highlight = Color.white;
            commandNumber = optionNumber;
            if (MouseBinds.bMouse1Clicked){
                color2 = uTool.ravensDarkGrey;
                bClicked = 1;
            }
        }
        //DRAW RECTANGLES
        drawRoundRectangle(highlight, rect1.x, rect1.y, rect1.width, rect1.height, 10);
        drawRoundRectangle(color1, rect2.x, rect2.y, rect2.width, rect2.height, 10);
        drawRoundRectangle(color2, rect3.x, rect3.y, rect3.width, rect3.height, 10);

        g2.setColor(Color.WHITE);
        g2.drawString(text, centerTextToRectX(text, rect3), centerTextToRectY(text, rect3));
    }
    public int stringWidth(String text){
        return (int) g2.getFontMetrics().getStringBounds(text ,g2).getWidth();
    }
    public int stringHeight(String text){
        return (int) g2.getFontMetrics().getStringBounds(text, g2).getHeight();
    }
    public void drawRoundRectangle(Color color, int x, int y, int width, int height, int arc) {
        g2.setColor(color);
        g2.fillRoundRect(x, y, width, height, arc, arc );
    }
    public int centerTextToRectX(String text, Rectangle rect){
        return rect.x + (rect.width/2) - stringWidth(text)/2;
    }
    public int centerTextToRectY(String text, Rectangle rect){
        return (rect.y + stringHeight(text));
    }
    public int centerRectX(Rectangle rect){
        return (gp.iScreenWidth/2 - rect.width/2);
    }
    public int centerToRectX(int x, Rectangle rect1, Rectangle rect2){
        return x + (rect1.width - rect2.width)/2;
    }
    public int centerToRectY(int y, Rectangle rect1, Rectangle rect2){
        return y + (rect1.height - rect2.height)/2;
    }
    public void drawMenuButtons(String[] optionsArray, int x, int y){
        for (int i = 0; i < optionsArray.length; i++) {
            if (optionsArray[i] != null) {
                drawButton(i, optionsArray[i], x, y + 2*i*stringHeight(optionsArray[i]), uTool.ravensDarkGrey, uTool.ravensLightGrey, uTool.ravensDarkGrey);
            }
        }

    }
}

