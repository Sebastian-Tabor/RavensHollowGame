package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBinds implements KeyListener{

    public static boolean bUpPressed, bDownPressed, bLeftPressed, bRightPressed, bEPressed, bRPressed, bEnterPressed, bEscapePressed;
    GamePanel gp;
    public KeyBinds(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    } //not used yet

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (gp.iGameState == gp.titleState) {
            if (code == KeyEvent.VK_ESCAPE) {bEscapePressed = true;}
            if (code == KeyEvent.VK_ENTER) bEnterPressed = true;
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP || code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) bUpPressed = true;
            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN || code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) bDownPressed = true;
        }
        else if (gp.iGameState == gp.playState) {
            if (code == KeyEvent.VK_ESCAPE) {bEscapePressed = true;}
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP || code == KeyEvent.VK_SPACE) bUpPressed = true;
            if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) bLeftPressed = true;
            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) bDownPressed = true;
            if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) bRightPressed = true;
            if (code == KeyEvent.VK_R) bRPressed = true;
            if (code == KeyEvent.VK_E) bEPressed = true;

            //TEMP KEYS DELETE BEFORE PUBLISHING
            if (code == KeyEvent.VK_1) gp.iScene = 0;
            if (code == KeyEvent.VK_2) gp.iScene = 1;
        }
        else if (gp.iGameState == gp.pauseState) {
            if (code == KeyEvent.VK_ESCAPE) {
                bEscapePressed = true;
            }
            if (code == KeyEvent.VK_ENTER) bEnterPressed = true;
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                bUpPressed = true;
            }
            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                bDownPressed = true;
            }
        }

    }
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (gp.iGameState == gp.pauseState || gp.iGameState == gp.titleState) {
            if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) bUpPressed = false;
            if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) bDownPressed = false;
        }
        if (code == KeyEvent.VK_ESCAPE) bEscapePressed = false;
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP || code == KeyEvent.VK_SPACE) bUpPressed = false;
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) bLeftPressed = false;
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) bDownPressed = false;
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_LEFT) bRightPressed = false;
        if (code == KeyEvent.VK_R) bRPressed = false;
        if (code == KeyEvent.VK_E) bEPressed = false;
        if (code == KeyEvent.VK_ENTER) bEnterPressed = false;
    }

}
