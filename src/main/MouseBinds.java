package main;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseBinds implements MouseListener {

    GamePanel gp;
    public static boolean bMouse1Clicked, bMouse2Clicked, bMouseOver, bMouseClicked;
    public MouseBinds(GamePanel gp) {
        this.gp = gp;
        bMouseClicked = bMouse1Clicked || bMouse2Clicked;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int code = e.getButton();
        if (code == MouseEvent.BUTTON1) bMouse1Clicked = true;
        if (code == MouseEvent.BUTTON3) bMouse2Clicked = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int code = e.getButton();

        if (code == MouseEvent.BUTTON1) bMouse1Clicked = false;
        if (code == MouseEvent.BUTTON3) bMouse2Clicked = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    public Point getMouseLocation(){
        int x = MouseInfo.getPointerInfo().getLocation().x;
        int y = MouseInfo.getPointerInfo().getLocation().y;
        Point mouseLocation = new Point();
        mouseLocation.x = x;
        mouseLocation.y = y;
        return mouseLocation;
    }
    public boolean isMouseOver(Rectangle rect){
        return rect.contains(getMouseLocation());
    }
}
