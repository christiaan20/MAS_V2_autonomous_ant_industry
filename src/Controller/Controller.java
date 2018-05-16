package Controller;

import View.View;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Created by christiaan on 10/05/18.
 */
public class Controller implements MouseListener, MouseMotionListener
{
    View view;

    public Controller() {
        this.view = View.getInstance();

        view.addMouseListener(this);
        view.addMouseMotionListener(this);

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        view.setMouse_x(e.getX());
        view.setMouse_y(e.getY());
    }



}
