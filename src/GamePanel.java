import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    //Frame setting vars
    final int iOriginalTileSize = 120;
    final int iScale = 1;
    final int iTileSize = iOriginalTileSize * iScale;
    final int iMaxScreenColumns = 1920;
    final int iMaxScreenRows = 1080;
    final int iScreenWidth = iMaxScreenColumns * iTileSize;
    final int iScreenHeight = iMaxScreenRows * iTileSize;
    Thread gameThread;
    //FPS
    int iFPS = 60;
    //Keybind objects
    KeyBinds keyBinds = new KeyBinds();
    //Player pos vars
    int iPlayerPosX = 100;
    int iPlayerPosY = 540;
    //Character stats
    Character Fighter = new Character("Badger", 300, 300, 10, 50, 0, 5, 150, 2);
    Character Mage = new Character("Bunny", 100, 100, 15, 50, 0, 5, 200, 1);
    Character Player = new Character("InsertNameHere", 150, 150, 10, 50, 0, 5, 100, 20);
    Character Tank = new Character("Turtle", 500, 500, 5, 50, 0, 5, 0, 1);
    /*  New character base stats as follows:
        Fighter     MaxHP:250  DmgPerHit:5   MaxUlt:50    PerHitUlt:5     CharSpeed:2    Total:50
        Mage        MaxHP:100  DmgPerHit:10  MaxUlt:50    PerHitUlt:10    CharSpeed:1    Total:50
        Ranger      MaxHP:150  DmgPerHit:5   MaxUlt:50    PerHitUlt:5     CharSpeed:1    Total:50
        Rouge       MaxHP:200  DmgPerHit:15  MaxUlt:50    PerHitUlt:5     CharSpeed:3    Total:50
        Tank        MaxHP:500  DmgPerHit:2   MaxUlt:50    PerHitUlt:10    CharSpeed:1    Total:50
     */


    public GamePanel() {
        this.setPreferredSize(new Dimension(iScreenWidth,iScreenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyBinds);
        this.setFocusable(true);
    }
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
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

            try {double dRemainingTimeMS = (dNextDrawTimeNS - System.nanoTime())/1000000; Thread.sleep((long) dRemainingTimeMS); if(dRemainingTimeMS < 0){dRemainingTimeMS = 0;} dNextDrawTimeNS += dDrawIntervalNS;}
            catch (InterruptedException e) {throw new RuntimeException(e);}
        }
    }
    public void update(){
        if (keyBinds.bLeftPressed) {iPlayerPosX -= Player.getIvCharacterSpeed();}
        if (keyBinds.bRightPressed) {iPlayerPosX += Player.getIvCharacterSpeed();}
        //if (keyBinds.bDownPressed == true) {iPlayerPosX -= Player.getIvCharacterSpeed();}
        //if (keyBinds.bUpPressed == true) {iPlayerPosX -= Player.getIvCharacterSpeed();}
        //if (keyBinds.bSpacePressed == true) {iPlayerPosX -= Player.getIvCharacterSpeed();}

    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        //repainting character on each loop
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.darkGray);
        g2.fillRect(iPlayerPosX,iPlayerPosY,iTileSize,iTileSize);
        g2.dispose();
    }
}
