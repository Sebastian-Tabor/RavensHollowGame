package main;
import entity.Entity;

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
        g2.drawString(text, getXCenterString(text), getYCenterString(text));
        displayedImage = titleImage;
        g2.drawImage(displayedImage, 0, 0, gp.iScreenWidth, gp.iScreenHeight, null);
    }
//DRAW PLAY SCREEN
    public void drawPlayScreen() {
        displayedImage = playImage;
        g2.drawImage(displayedImage, 0, 0, gp.iScreenWidth, gp.iScreenHeight, null);
        drawPlayerBars();
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
        x = getXCenterString(text);
        y = getYCenterString(text) - gp.iTileSize;
        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        drawCenterMenuTextOption("Resume", 0);
        if (commandNumber == 0 && KeyBinds.bEnterPressed) {
            gp.iGameState = gp.playState;
            gp.resumeMusic();
        }

        drawCenterMenuTextOption("Options", 1);
        if (commandNumber == 1 && KeyBinds.bEnterPressed) {
            menuState = 1;
            commandNumber = 0;
            KeyBinds.bEnterPressed = false;
        }
        //DOESNT WORK RIGHT NOW
        if (gp.bFullscreen) text = "Set Windowed";
        else text = "Set Fullscreen";
        drawCenterMenuTextOption(text, 2);
        if (commandNumber == 2 && KeyBinds.bEnterPressed) {
            if (gp.bFullscreen) {
                Main.setWindowed(gp);
            }
            else {
                Main.setFullscreen(gp);
            }
            KeyBinds.bEnterPressed = false;
        }
        drawCenterMenuTextOption("Quit", 3);
        if (commandNumber == 3 && KeyBinds.bEnterPressed) {
            System.exit(0);
        }
        //ALWAYS ADD THIS
        if (KeyBinds.bUpPressed) {
            --commandNumber;
            KeyBinds.bUpPressed = false;
        }
        if (KeyBinds.bDownPressed) {
            ++commandNumber;
            KeyBinds.bDownPressed = false;
        }
        if (KeyBinds.bEscapePressed) {
            gp.iGameState = gp.playState;
            gp.resumeMusic();
            KeyBinds.bEscapePressed = false;
        }
        //LOOP
        if (commandNumber > 3) {
            commandNumber = 0;
        }
        if (commandNumber < 0) {
            commandNumber = 3;
        }
    }
    public void drawMenu1() {
        int x;
        int y;

        g2.setColor(new Color(0,0,0,60));
        g2.fillRect(0,0, gp.iScreenWidth, gp.iScreenHeight);

        g2.setColor(new Color(0,0,0,200));
        g2.fillRect(gp.iScreenWidth/4,gp.iScreenHeight/4, gp.iScreenWidth/2, gp.iScreenHeight/2);

        displayedImage = pauseImage;
        g2.drawImage(tempImage,gp.iScreenWidth/4,gp.iScreenHeight/4, gp.iTileSize, gp.iTileSize, null);

        text = "Options";
        g2.setFont(titleFont);
        x = getXCenterString(text);
        y = getYCenterString(text) - gp.iTileSize;
        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        drawCenterMenuTextOption("Audio", 0);
        if (commandNumber == 0 && KeyBinds.bEnterPressed) {
           menuState = 2;
           selectedOption = 999;
           KeyBinds.bEnterPressed = false;
        }
        drawCenterMenuTextOption("Hotkeys", 1);
        if (commandNumber == 1 && KeyBinds.bEnterPressed) {
            System.out.println("Implement hotkey options in UserInterface.java");
            System.exit(0);
        }
        drawCenterMenuTextOption("Accessibility", 2);
        if (commandNumber == 2 && KeyBinds.bEnterPressed) {
            System.out.println("Implement accessibility options in UserInterface.java");
            System.exit(0);
        }
        drawCenterMenuTextOption("Back", 3);
        if (commandNumber == 3 && KeyBinds.bEnterPressed) {
            menuState = 0;
            commandNumber = 0;
            KeyBinds.bEnterPressed = false;
        }
        //ALWAYS ADD THIS
        if (KeyBinds.bUpPressed) {
            --commandNumber;
            KeyBinds.bUpPressed = false;
        }
        if (KeyBinds.bDownPressed) {
            ++commandNumber;
            KeyBinds.bDownPressed = false;
        }
        //LOOP
        if (commandNumber > 3) {
            commandNumber = 0;
        }
        if (commandNumber < 0) {
            commandNumber = 3;
        }
        if (KeyBinds.bEscapePressed) {
           menuState = 0;
           commandNumber = 0;
            KeyBinds.bEscapePressed = false;
        }
    }
    public void drawMenu2() {
        int x;
        int y;

        g2.setColor(new Color(0,0,0,60));
        g2.fillRect(0,0, gp.iScreenWidth, gp.iScreenHeight);

        g2.setColor(new Color(0,0,0,200));
        g2.fillRect(gp.iScreenWidth/4,gp.iScreenHeight/4, gp.iScreenWidth/2, gp.iScreenHeight/2);

        displayedImage = pauseImage;
        g2.drawImage(tempImage,gp.iScreenWidth/4,gp.iScreenHeight/4, gp.iTileSize, gp.iTileSize, null);

        text = "Audio";
        g2.setFont(titleFont);
        x = getXCenterString(text);
        y = getYCenterString(text) - gp.iTileSize;
        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        if (selectedOption == 999 && commandNumber != 1) {
            if (KeyBinds.bEnterPressed){
                selectedOption = commandNumber;
                KeyBinds.bEnterPressed = false;
            }
        }

        drawMenuSliderTextOption("Music", 0, gp.music.iSoundVolume);
        if (commandNumber == 0 && selectedOption == 0) {
            if (KeyBinds.bUpPressed) {
                ++gp.music.iSoundVolume;
                if (gp.music.iSoundVolume > 100) {
                    gp.music.iSoundVolume = 100;
                }
                KeyBinds.bUpPressed = false;
            }
            if (KeyBinds.bDownPressed) {
                --gp.music.iSoundVolume;
                if (gp.music.iSoundVolume < 0) {
                    gp.music.iSoundVolume = 0;
                }
                KeyBinds.bDownPressed = false;
            }
            if (KeyBinds.bEscapePressed) {
                selectedOption = 999;
                KeyBinds.bEscapePressed = false;
            }
            if (KeyBinds.bEnterPressed){
                selectedOption = 999;
                KeyBinds.bEnterPressed = false;
            }
        }

        drawCenterMenuTextOption("Back", 1);
        if (commandNumber == 1 && KeyBinds.bEnterPressed) {
            menuState = 1;
            commandNumber = 0;
            KeyBinds.bEnterPressed = false;
        }
        //ALWAYS ADD THIS
        if (KeyBinds.bUpPressed) {
            --commandNumber;
            KeyBinds.bUpPressed = false;
        }
        if (KeyBinds.bDownPressed) {
            ++commandNumber;
            KeyBinds.bDownPressed = false;
        }
        //LOOP
        if (commandNumber > 3) {
            commandNumber = 0;
        }
        if (commandNumber < 0) {
            commandNumber = 3;
        }
        if (KeyBinds.bEscapePressed) {
            menuState = 1;
            commandNumber = 0;
            KeyBinds.bEscapePressed = false;
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
    public int getXLeftCenterString(String text){
        return (gp.iScreenWidth/4) + (2*gp.iTileSize);
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
    public void drawCenterMenuTextOption(String text, int menuOptionNumber) {
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
    public void drawMenuSliderTextOption(String text, int menuOptionNumber, int slider) {
        text = text;
        g2.setFont(defaultFont);
        int x;
        int y;
        x = getXLeftCenterString(text);
        y = getYCenterString(text) + (gp.iTileSize * menuOptionNumber);
        g2.setColor(Color.white);
        g2.drawString(text, x, y);
        g2.drawString(String.valueOf(slider), (int)(x + g2.getFontMetrics().getStringBounds(text, g2).getWidth() + 2*gp.iTileSize), y );
        if (commandNumber == menuOptionNumber && commandNumber == selectedOption) {
            g2.drawString(">", (int)(x + g2.getFontMetrics().getStringBounds(text, g2).getWidth() + gp.iTileSize), y );
            g2.drawString("<", (int)(x + g2.getFontMetrics().getStringBounds(String.valueOf(slider), g2).getWidth() + gp.iTileSize), y );
        }
        else if (commandNumber == menuOptionNumber) {
            g2.drawString(">", x - gp.iTileSize, y);
            g2.drawString("<", (int)(x + g2.getFontMetrics().getStringBounds(text, g2).getWidth() + gp.iTileSize), y );
        }
    }
}
