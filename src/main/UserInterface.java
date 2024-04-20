package main;

import entity.Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class UserInterface {
    Font tinyFont, defaultFont, titleFont;
    public GamePanel gp;
    public Graphics2D g2;
    public UtilityTool uTool;
    public String text;
    public BufferedImage displayedImage, titleImage, playImage, pauseImage, endImage, loadingImage, tempImage;
    public int menuState;
    public int commandNumber = 0;
    public boolean bClicked = false;
    public String[] optionArray;
    public int iStandardArc = 20;
    public ArrayList<Integer> popupText = new ArrayList<>();
    public ArrayList<Integer> popupTextCounter = new ArrayList<>();
    public ArrayList<Integer> popupTextType = new ArrayList<>();
    public ArrayList<Point> popupTextPos = new ArrayList<>();

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
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }
        if (gp.gameState == gp.playState) {
            drawPlayScreen();
            drawPopupText();
        }
        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }
        if (gp.gameState == gp.loadingState) {
            drawLoadingScreen();
        }
        if (gp.gameState == gp.endState) {
            drawEndScreen();
        }
    }
//FONTS
    public void setFonts(){
        tinyFont = new Font("Arial", Font.BOLD, gp.screenWidth/60);
        defaultFont = new Font("Arial", Font.PLAIN, gp.screenWidth/30);
        titleFont = new Font("Arial", Font.BOLD, gp.screenWidth/15);
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
        g2.drawString(text, gp.screenWidth /2, gp.screenHeight /2);
        displayedImage = titleImage;
        g2.drawImage(displayedImage, 0, 0, gp.screenWidth, gp.screenHeight, null);
        optionArray = new String[4];
        optionArray[0] = "New Game";
        optionArray[1] = "Load Game";
        optionArray[2] = "Fullscreen";
        optionArray[3] = "Quit";
        drawMenuButtons(optionArray, (int)(0.25*gp.screenWidth), (int)(0.25*gp.screenHeight));
        if (bClicked){
            switch (commandNumber){
                case 0 -> {
                    if (!MouseBinds.bMouse1Clicked || !KeyBinds.bEnterPressed) {
                        gp.loadTool.newGame();
                        gp.gameState = gp.playState;
                        gp.stopMusic();
                        gp.playMusic(2);
                        bClicked = false;
                        KeyBinds.bEnterPressed = false;
                    }

                }
                case 1 -> {
                    gp.loadTool.loadGame();
                    gp.gameState = gp.playState;
                    gp.stopMusic();
                    gp.playMusic(2);
                    bClicked = false;
                    KeyBinds.bEnterPressed = false;
                }
                case 2 -> {
                    if (!MouseBinds.bMouse1Clicked || !KeyBinds.bEnterPressed) {
                        if (!gp.fullscreen) {
                            Main.window.dispose();
                            Main.window.setResizable(false);
                            Main.window.setUndecorated(true);
                            Main.window.setExtendedState(Frame.MAXIMIZED_BOTH);
                            Main.window.setVisible(true);
                            gp.fullscreen = true;
                        } else {
                            Main.window.dispose();
                            Main.window.setResizable(true);
                            Main.window.setUndecorated(false);
                            Main.window.setVisible(true);
                            //Main.window.setPreferredSize(new Dimension(1280, 720));
                            gp.fullscreen = false;
                        }
                        bClicked = false;
                        KeyBinds.bEnterPressed = false;
                    }
                }
                case 3 -> {
                    if (!MouseBinds.bMouse1Clicked || !KeyBinds.bEnterPressed) {
                        System.exit(0);
                        bClicked = false;
                        KeyBinds.bEnterPressed = false;
                    }
                }
            }
        }
        if (KeyBinds.bUpPressed) {
            --commandNumber;
            KeyBinds.bUpPressed = false;
        }
        if (KeyBinds.bDownPressed) {
            ++commandNumber;
            KeyBinds.bDownPressed = false;
        }
        if (commandNumber >= optionArray.length) {
            commandNumber = optionArray.length-1;
        }
        if (commandNumber < 0) {
            commandNumber = 0;
        }

    }
//DRAW PLAY SCREEN
    public void drawPlayScreen() {
       // displayedImage = playImage;
       // g2.drawImage(displayedImage, 0, 0, gp.iScreenWidth, gp.iScreenHeight, null);
        drawHealthUltBar(gp.player, gp.screenWidth /3, 4*gp.screenHeight /5, gp.screenWidth /3, gp.tileSize /2);
        if (KeyBinds.bEscapePressed) {
            gp.gameState = gp.pauseState;
            gp.stopMusic();
            menuState = 0;
            KeyBinds.bEscapePressed = false;
        }
    }
//DRAW PAUSE SCREEN
    public void drawPauseScreen() {
        if (menuState == 0) {
            drawMenu0();
            if (KeyBinds.bEscapePressed) {
                gp.gameState = gp.playState;
                gp.resumeMusic();
                KeyBinds.bEscapePressed = false;
            }
        }
        else if (menuState == 1) {
            drawMenu1();
        }
        else if(menuState == 2) {
            drawMenu2();
        }
    }
    public void drawMenu0(){

        g2.setColor(uTool.transparentBackground);
        g2.fillRect(0,0, gp.screenWidth, gp.screenHeight);

        optionArray = new String[4];
        optionArray[0] = "Resume";
        optionArray[1] = "Save Game";
        optionArray[2] = "Settings";
        optionArray[3] = "Main Menu";

        Rectangle menuBox = new Rectangle(gp.screenWidth /3, gp.screenHeight /4, gp.screenWidth /3, gp.screenHeight /2);
        drawMenuBackground(menuBox, uTool.ravensGrey, uTool.ravensLightGrey);

        g2.setFont(defaultFont);
        drawMenuButtons(optionArray, gp.screenWidth /2, (int)(gp.screenHeight /2.4));
        if (bClicked){
            switch (commandNumber){
                case 0 -> {
                    if (!MouseBinds.bMouse1Clicked || KeyBinds.bEnterPressed || KeyBinds.bEscapePressed){
                        gp.gameState = gp.playState;
                        gp.resumeMusic();
                        bClicked = false;
                        KeyBinds.bEnterPressed = false;
                    }
                }
                case 1 -> {
                    gp.loadTool.saveGame();
                    bClicked = false;
                    KeyBinds.bEnterPressed = false;
                }
                case 2 -> {
                    System.out.println("Create " + optionArray[commandNumber] + " function.");
                    bClicked = false;
                    KeyBinds.bEnterPressed = false;
                }
                case 3 -> {
                    if (!MouseBinds.bMouse1Clicked || KeyBinds.bEnterPressed){
                        gp.gameState = gp.titleState;
                        commandNumber = 0;
                        bClicked = false;
                        KeyBinds.bEnterPressed = false;
                    }
                }
            }
        }
        if (KeyBinds.bUpPressed) {
            --commandNumber;
            KeyBinds.bUpPressed = false;
        }
        if (KeyBinds.bDownPressed) {
            ++commandNumber;
            KeyBinds.bDownPressed = false;
        }
        if (commandNumber >= optionArray.length) {
            commandNumber = optionArray.length-1;
        }
        if (commandNumber < 0) {
            commandNumber = 0;
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
//BUTTON METHODS
    public void drawButton(int optionNumber, String text, int x, int y, Color color1, Color color2, Color highlight){

        Rectangle rect2 = new Rectangle();
        Rectangle rect3 = new Rectangle();
        Rectangle rect1 = new Rectangle();

        //SETUP RECTANGLES
        rect1.width = stringWidth(text) + gp.tileSize + 2;
        rect1.height = stringHeight(text) + gp.tileSize /2 + 2;
        rect1.x = x - rect1.width/2;
        rect1.y = y - rect1.height/2;

        rect2.width = stringWidth(text) + gp.tileSize;
        rect2.height = stringHeight(text) + gp.tileSize /2;
        rect2.x = x - rect2.width/2;
        rect2.y = y - rect2.height/2;

        rect3.width = (int) (rect2.width*0.95);
        rect3.height = (int) (rect2.height*0.9);
        rect3.x = centerToRectX(rect2.x, rect2, rect3);
        rect3.y = centerToRectY(rect2.y, rect2, rect3);

        if (gp.mouseBinds.isMouseOver(rect3)) {
            commandNumber = optionNumber;
            highlight = Color.white;
            if (MouseBinds.bMouse1Clicked || KeyBinds.bEnterPressed){
                color2 = uTool.ravensDarkGrey;
                bClicked = true;
            }
        }

        //DRAW RECTANGLES
        drawRoundRectangle(highlight, rect1.x, rect1.y, rect1.width, rect1.height, iStandardArc);
        drawRoundRectangle(color1, rect2.x, rect2.y, rect2.width, rect2.height, iStandardArc);
        drawRoundRectangle(color2, rect3.x, rect3.y, rect3.width, rect3.height, iStandardArc);

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
    public void drawMenuBackground(Rectangle rect, Color color1, Color color2){
        g2.setColor(color1);
        g2.fillRoundRect(rect.x - 4, rect.y - 4, rect.width + 8, rect.height + 8, iStandardArc, iStandardArc);
        g2.setColor(color2);
        g2.fillRoundRect(rect.x - 2, rect.y - 2, rect.width + 4, rect.height + 4, iStandardArc, iStandardArc);
        g2.setColor(color1);
        g2.fillRoundRect(rect.x, rect.y, rect.width, rect.height, iStandardArc, iStandardArc);
    }
//HEALTHBARS AND DAMAGE
    public void drawHealthUltBar(Entity entity, int x, int y, int width, int height){
        g2.setColor(Color.darkGray);
        g2.fillRect(x, y, width, height);
        g2.setColor(Color.darkGray);
        g2.fillRect(x, y + height, width, height);

        int hScale = width/entity.healthMax;
        int healthPercent = hScale*entity.health;
        g2.setColor(Color.red);
        g2.fillRoundRect(x + 1, y + 1, healthPercent, height - 2, iStandardArc, iStandardArc);

        int uScale = width/entity.ultimateMax;
        int ultPercent = uScale*entity.ultimate;
        g2.setColor(Color.lightGray);
        g2.fillRoundRect(x + 1, y + height + 1, ultPercent, height - 2, iStandardArc, iStandardArc);
    }
    public void addPopupText(int amount, int x, int y, int type){
        popupText.add(amount);
        popupTextCounter.add(0);
        popupTextPos.add(new Point(x, y));
        popupTextType.add(type);
    }
    public void drawPopupText(){
        g2.setFont(tinyFont);
        for (int i = 0; i < popupText.size(); i++){

            if (popupText.get(i) != null){
                int x = popupTextPos.get(i).x;
                int y = popupTextPos.get(i).y;
                x = x - gp.player.worldX + gp.player.iScreenPosX;
                y = y - gp.player.worldY + gp.player.iScreenPosY;

                g2.setColor(Color.black);
                g2.drawString(Integer.toString(popupText.get(i)), x + 2, y - popupTextCounter.get(i) + 2);
                if (popupTextType.get(i) == 0) {
                    g2.setColor(uTool.damagedRed);
                } else if (popupTextType.get(i) == 1) {
                    g2.setColor(uTool.reflectedGrey);
                } else {
                    g2.setColor(uTool.healingGreen);
                }
                g2.drawString(Integer.toString(popupText.get(i)), x, y - popupTextCounter.get(i));

                int counter = popupTextCounter.get(i) + 1;
                popupTextCounter.set(i, counter);

                if (popupTextCounter.get(i) > 90) {
                    popupText.remove(i);
                    popupTextCounter.remove(i);
                    popupTextPos.remove(i);
                    break;
                }
            }
        }
    }
}

