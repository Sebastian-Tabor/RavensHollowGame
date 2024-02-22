package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBinds implements KeyListener {
    public static boolean bUpPressed, bDownPressed, bLeftPressed, bRightPressed, bSpacePressed, bEscPressed;

    @Override
    public void keyTyped(KeyEvent e) {} //not used yet

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP){bUpPressed = true;}
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {bLeftPressed = true;}
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){bDownPressed = true;}
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT){bRightPressed = true;}
        if (code == KeyEvent.VK_SPACE) {bSpacePressed = true;}
        if (code == KeyEvent.VK_ESCAPE) {bEscPressed = true;}

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP){bUpPressed = false;}
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {bLeftPressed = false;}
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){bDownPressed = false;}
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT){bRightPressed = false;}
        if (code == KeyEvent.VK_SPACE){bSpacePressed = false;}

    }
}
