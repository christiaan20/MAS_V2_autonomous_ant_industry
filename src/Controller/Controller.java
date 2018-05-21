package Controller;

import Model_pk.Model;
import Model_pk.Worker;
import View.Object_visuals.Object_visual;
import View.View;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Created by christiaan on 10/05/18.
 */
public class Controller implements MouseListener, MouseMotionListener
{
    Model model;
    View view;

    public Controller() {
        this.view = View.getInstance();
        this.model = Model.getInstance();

        view.addMouseListener(this);
        view.addMouseMotionListener(this);

    }

    //clicked only registers really short clicks MousePressed is more reliabler
    @Override
    public void mouseClicked(MouseEvent e)
    {
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        if(e.getButton()== 1 ) // left-mouse pressed
        {
            //select the objeect that is being hovered over currently
            view.select_hovering_over();
        }
        else if(e.getButton()== 3 ) //right-mouse Pressed
        {
            //send currently selected worker to spot of pressing
            int model_x = view.x_view_to_model(e.getX());
            int model_y = view.y_view_to_model(e.getY());

            Object obj = view.getSelected_visual().getModel_object();
            if(obj instanceof Worker)
            {
                Worker worker = (Worker) obj;
                worker.setTarget( model_x,model_y);
            }

        }

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
        view.check_hovering_over(e.getX(),e.getY());

        view.setMouse_x(e.getX());
        view.setMouse_y(e.getY());
    }



}
