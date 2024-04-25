package main;

import entity.Entity;
import entity.PlayerEntity;
import entity.ProjectileEntity;
import object.Object;
import world.SceneManager;
import world.TileManager;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;


public class GamePanel extends JPanel implements Runnable {
//FRAME SETTING VARIABLES
    Color bg = new Color(128,128,128);
    public int screenWidth = 1920;
    public int screenHeight = 1080;
    public int tileSize = 64;
    public boolean fullscreen = true;
//MAP SIZE
    public final int maxMapCol = 90;
    public final int maxMapRow = 22;
    public final int mapWidth = tileSize * maxMapCol;
    public final int mapHeight = tileSize * maxMapRow;
//CLASS OBJECT CREATION
    public KeyBinds keyBinds = new KeyBinds(this);
    public MouseBinds mouseBinds = new MouseBinds(this);
    public Sound music = new Sound();
    public Sound soundeffect = new Sound();
    public PlayerEntity player = new PlayerEntity(this, keyBinds);
    public Object[] obj = new Object[5];
    public Entity[] npc = new Entity[5];
    public ArrayList<ProjectileEntity> projectile = new ArrayList<>();
    public ArrayList<Particles> particle = new ArrayList<>();
    public ArrayList<Integer> particleCounter = new ArrayList<>();
    public Entity[] monster = new Entity[10];
    public AssetSetter aSetter = new AssetSetter(this);
    public TileManager tileManager = new TileManager(this);
    public SceneManager sceneManager = new SceneManager(this);
    public UserInterface ui = new UserInterface(this);
    public LoadingTool loadTool = new LoadingTool(this);
    public Transitions sceneSwap = new Transitions(this);
    public CollisionCheck cCheck = new CollisionCheck(this);
    Thread gameThread;
//GAME STATE
    public boolean transitionIn, transitionOut;
    public int transitionCounter;
    public int sceneNumber = 0;
    public int winNumber = 0;
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int loadingState = 3;
    public final int endState = 4;
//SAVING GAME
    public int[] saveData = new int[8];
    public int[] monsterSaveData = new int[8];
    public int maxLines = monster.length + 2;
//FPS
    int iFPS = 80;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(bg);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyBinds);
        this.addMouseListener(mouseBinds);
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
        gameState = titleState;

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
               dNextDrawTimeNS += dDrawIntervalNS;
           } catch (InterruptedException e) {
               throw new RuntimeException(e);
           }
        }
    }
    public void update() {
        if (gameState == playState) {
            for (Object object : obj) {
                if (object != null) {
                    object.update();
                }
            }
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    if (npc[i].alive) {
                        npc[i].update();
                    }
                    if (!npc[i].alive){
                        npc[i] = null;
                    }
                }
            }
            for (int i = 0; i < monster.length; i++) {
                if (monster[i] != null) {
                    if (monster[i].alive) {
                        monster[i].update();
                    }
                    if (!monster[i].alive){
                        monster[i] = null;
                    }

                }
            }
            for (int i = 0; i < projectile.size(); i++) {
                if (projectile.get(i) != null) {
                    projectile.get(i).update();
                    if (projectile.get(i).collisionDetected || projectile.get(i).collisionEntity) {
                        //ADDS PARTICLE(S)
                        int rand = new Random().nextInt(5, 10);
                        for (int i1 = 0; i1 < rand; i1++) {
                            particle.add(new Particles(this, projectile.get(i)));
                            particleCounter.add(0);
                        }
                        projectile.remove(i);
                        break;
                    }
                }
            }
            for (int i = 0; i < particle.size(); i++) {
                if (particle.get(i) != null) {
                    particle.get(i).update();
                }
            }

            if (player.alive) {
                player.update();
            } else {
                System.exit(0);
            }

        }
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (gameState == playState || gameState == pauseState) {
            sceneManager.drawBackground(g2);
            sceneManager.drawMidground(g2);
        //TILES
            tileManager.draw(g2);
            for (Object object : obj) {
                if (object != null) {
                    object.draw(g2, this);
                }
            }
        //NPC
            for (Entity entity : npc) {
                if (entity != null) {
                    entity.draw(g2);
                }
            }
            for (Entity entity : monster) {
                if (entity != null) {
                    entity.draw(g2);
                }
            }
            for (ProjectileEntity projectileEntity : projectile) {
                if (projectileEntity != null) {
                    projectileEntity.draw(g2);
                }
            }
        //PLAYER
            player.draw(g2);
        //PARTICLES
            for (Particles particles : particle) {
                if (particles != null) {
                    particles.draw(g2);
                }
            }
        //FOREGROUND
            sceneManager.drawForeground(g2);

        }
        //UI
        ui.draw(g2);
        sceneSwap.draw(g2);
        g2.dispose();
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
