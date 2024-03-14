package main;

import entity.Entity;
import entity.Player;
import object.Object;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
//FRAME SETTING VARIABLES
    Color bg = new Color(128,128,128);
    public final int iOriginalTileSize = 64;
    public final int iScale = 1;
    public final int iTileSize = iOriginalTileSize * iScale;
    public final int iMaxScreenColumns = 30;
    public final int iMaxScreenRows = 17;
    public final int iScreenWidth = iMaxScreenColumns * iTileSize;
    public final int iScreenHeight = iMaxScreenRows * iTileSize;
//MAP SIZE
    public final int iMaxMapCol = 90;
    public final int iMaxMapRow = 22;
//CLASS OBJECT CREATION
    public KeyBinds keyBinds = new KeyBinds(this);
    public Sound music = new Sound();
    public Sound soundeffect = new Sound();
    public Player player = new Player(this, keyBinds);
    public Object[] obj = new Object[5];
    public Object[] projectile = new Object[10];
    public Entity[] npc = new Entity[5];
    public AssetSetter aSetter = new AssetSetter(this);
    public TileManager tileManager = new TileManager(this);
    public UserInterface ui = new UserInterface(this);
    public CollisionCheck cCheck = new CollisionCheck(this);
    Thread gameThread;
//GAME STATE
    public int iGameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int loadingState = 3;
    public final int endState = 4;
//FPS
    int iFPS = 60;

    public GamePanel() {
        this.setPreferredSize(new Dimension(iScreenWidth,iScreenHeight));
        this.setBackground(bg);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyBinds);
        this.setFocusable(true);
        this.setAlignmentX(0);
        this.setAlignmentY(0);
    }
    public void setupGame() {
        aSetter.setObject();
        aSetter.setNPC();
        playMusic(1);
        iGameState = titleState;

    }
    public void startGameThread(){
        gameThread = new Thread(this);gameThread.start();
    }

    @Override
    public void run() {
        double dDrawIntervalNS = 1000000000/((double)iFPS);
        double dNextDrawTimeNS = System.nanoTime() + dDrawIntervalNS;
        while(gameThread != null){
            long runTime = System.nanoTime();
        //Update
            update();
        //Draw
            repaint();
           try {
               double dRemainingTimeMS = (dNextDrawTimeNS - System.nanoTime())/1000000;
               Thread.sleep(Math.abs((long) dRemainingTimeMS));
               if(dRemainingTimeMS < 0) dRemainingTimeMS = 0;
               dNextDrawTimeNS += dDrawIntervalNS;
           } catch (InterruptedException e) {
               throw new RuntimeException(e);
           }
        }
    }
    public void update() {
        if (iGameState == playState) {
            for (Object object : obj) {
                if (object != null) {
                    object.update();
                }
            }
            for (Object object : projectile) {
                if (object != null) {
                    object.update();
                }
            }
            for (Entity entity : npc) {
                if (entity != null) {
                    entity.update();
                }
            }
            player.update();
            ui.update();
        }
        if (iGameState == titleState) {
            if (KeyBinds.bInput) {
                iGameState = playState;
                stopMusic();
            }
        }
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        //TITLE SCREEN
        if (iGameState == titleState || iGameState == endState) {
            ui.draw(g2);
        }
        else {
            //TILES
            tileManager.draw(g2);
            //OBJECT
            for (Object object : obj) {
                if (object != null) {
                    object.draw(g2, this);
                }
            }
            for (Object object : projectile) {
                if (object != null) {
                    object.update();
                }
            }
            //NPC
            for (Entity entity : npc) {
                if (entity != null) {
                    entity.draw(g2);
                }
            }
            //PLAYER
            player.draw(g2);
            //UI
            ui.draw(g2);
            g2.dispose();
        }
    }
    public void playMusic(int track) {
        music.setFile(track);
        music.playSound();
        music.loopSound();
    }
    public void stopMusic() {
        music.stopSound();
    }
    public void playSoundEffect(int track) {
        soundeffect.setFile(track);
        soundeffect.playSound();
    }
    public void stopSound() {
        soundeffect.stopSound();
    }
}
