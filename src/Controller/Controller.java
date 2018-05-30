package Controller;

import Model_pk.Objects.Abstr_Object;
import Model_pk.Behaviour.Task_Enum;
import Model_pk.Model;
import Model_pk.Enums.Resource_Type_Enum;
import Model_pk.Objects.Worker;
import View.Object_visuals.Object_visual;
import View.View;

import java.awt.event.*;

/**
 * Created by christiaan on 10/05/18.
 */
public class Controller implements MouseListener, MouseMotionListener, KeyListener
{
    Model model;
    View view;

    private boolean create_phero = false;
    private Resource_Type_Enum create_type = Resource_Type_Enum.Coal;
    private Task_Enum create_task = Task_Enum.explorer;
    private Worker creat_worker = null;

    public Controller() {
        this.view = View.getInstance();
        this.model = Model.getInstance();

        view.addMouseListener(this);
        view.addMouseMotionListener(this);
        view.addKeyListener(this);

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
            if(create_phero)
            {
                create_phero(e);
            }
            else
            {
                //select the objeect that is being hovered over currently
                view.select_hovering_over();
            }

        }
        else if(e.getButton()== 3 ) //right-mouse Pressed
        {
            //send currently selected worker to spot of pressing
            int model_x = view.x_view_to_model(e.getX());
            int model_y = view.y_view_to_model(e.getY());

            Abstr_Object obj = view.getSelected_visual().getModel_object();
            if(obj instanceof Worker)
            {
                Worker worker = (Worker) obj;
                worker.getTask().setTarget( worker, model_x,model_y);
            }

        }

    }

    private void create_phero(MouseEvent e)
    {
        int model_x = view.x_view_to_model(e.getX());
        int model_y = view.y_view_to_model(e.getY());

        Object_visual vis = view.getSelected_visual();
        Abstr_Object obj = null;
        if(vis != null)
        {
           obj = view.getSelected_visual().getModel_object();
        }


        if(obj != null)
        {
            if( obj instanceof  Worker)
            {
                Worker worker = (Worker) obj;

                model.drop_seperate_pheromone(worker,model_x,model_y,4,create_task,create_type,10,150,15);
            }

        }
        else
        {
            model.drop_seperate_pheromone(null,model_x,model_y,4,create_task,create_type,10,150,15);
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


    @Override
    public void keyTyped(KeyEvent e)
    {

    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        //System.out.println("key " + e.getKeyCode());
        if(e.getKeyCode() == 38) //left key
        switch(e.getKeyCode() )
        {
            case 37: //left key
                //view.decrease_offset_x();
                break;
            case 38: //up key
                //view.increase_offset_y();
                break;
            case 39: //right key
                //view.increase_offset_x();
                break;
            case 40: //down key
               // view.decrease_offset_y();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public boolean isCreate_phero() {
        return create_phero;
    }

    public void setCreate_phero(boolean create_phero) {
        this.create_phero = create_phero;
    }

    public Resource_Type_Enum getCreate_type() {
        return create_type;
    }

    public void setCreate_type(Resource_Type_Enum create_type) {
        this.create_type = create_type;
    }

    public Task_Enum getCreate_task() {
        return create_task;
    }

    public void setCreate_task(Task_Enum create_task) {
        this.create_task = create_task;
    }

    public Worker getCreat_worker() {
        return creat_worker;
    }

    public void setCreat_worker(Worker creat_worker) {
        this.creat_worker = creat_worker;
    }
}
