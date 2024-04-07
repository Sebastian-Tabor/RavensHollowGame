package main;

import entity.SuperEntity;
import entity.Player;
import object.SuperObject;
import world.SceneManager;
import world.TileManager;
import javax.swing.*;
import java.awt.*;


public class GamePanel extends JPanel implements Runnable {
//FRAME SETTING VARIABLES
    Color bg = new Color(128,128,128);
    public int iMaxScreenColumns = 30;
    public int iMaxScreenRows = 17;
    public int iOriginalTileSize = 64;
    public int iScale = 1;
    public int iTileSize = iOriginalTileSize * iScale;
    public int iScreenWidth = iMaxScreenColumns * iTileSize;
    public int iScreenHeight = iMaxScreenRows * iTileSize;
    public boolean bFullscreen = true;
//MAP SIZE
    public final int iMaxMapCol = 90;
    public final int iMaxMapRow = 22;
    public final int iMapWidth = iTileSize * iMaxMapCol;
    public final int iMapHeight = iTileSize * iMaxMapRow;
//CLASS OBJECT CREATION
    public KeyBinds keyBinds = new KeyBinds(this);
    public Sound music = new Sound();
    public Sound soundeffect = new Sound();
    public Player player = new Player(this, keyBinds);
    public SuperObject[] obj = new SuperObject[5];
    public SuperEntity[] npc = new SuperEntity[5];
    public SuperObject[] projectile = new SuperObject[10];
    public SuperEntity[] monster = new SuperEntity[10];
    public AssetSetter aSetter = new AssetSetter(this);
    public TileManager tileManager = new TileManager(this);
    public SceneManager sceneManager = new SceneManager(this);
    public UserInterface ui = new UserInterface(this);
    public CollisionCheck cCheck = new CollisionCheck(this);
    Thread gameThread;
//GAME STATE
    public int iScene = 0;
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
        music.iSoundVolume = 50;
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
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
            for (SuperObject object : obj) {
                if (object != null) {
                    object.update();
                }
            }
            for (SuperObject object : projectile) {
                if (object != null) {
                    object.update();
                }
            }
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    if (npc[i].bAlive) {
                        npc[i].update();
                    }
                    if (!npc[i].bAlive){
                        npc[i] = null;
                    }
                }
            }
            for (int i = 0; i < monster.length; i++) {
                if (monster[i] != null) {
                    if (monster[i].bAlive) {
                        monster[i].update();
                    }
                    if (!monster[i].bAlive){
                        monster[i] = null;
                    }

                }
            }
            player.update();
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
            sceneManager.drawBackground(g2);
            sceneManager.drawMidground(g2);
        //TILES
            tileManager.draw(g2);
            for (SuperObject object : obj) {
                if (object != null) {
                    object.draw(g2, this);
                }
            }
            for (SuperObject object : projectile) {
                if (object != null) {
                    object.update();
                }
            }
        //NPC
            for (SuperEntity entity : npc) {
                if (entity != null) {
                    entity.draw(g2);
                }
            }
            for (SuperEntity entity : monster) {
                if (entity != null) {
                    entity.draw(g2);
                }
            }
        //PLAYER
            player.draw(g2);
        //FOREGROUND
            sceneManager.drawForeground(g2);
        //UI
            ui.draw(g2);
            g2.dispose();
        }
    }
//SOUNDS
    public void playMusic(int track) {
        music.setFile(track);
        music.playSound();
        music.loopSound();
    }
    public void resumeMusic() {
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
}
