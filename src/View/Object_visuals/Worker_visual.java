package View.Object_visuals;

import Model_pk.Behaviour.Task_Enum;
import Model_pk.Object;
import Model_pk.Worker;
import Model_pk.Worker_State_Enum;
import View.View;

import java.awt.*;

/**
 * Created by christiaan on 16/05/18.
 */
public class Worker_visual extends Object_visual
{


    public Worker_visual(int x, int y, int size, Object model_object) {

        super(x, y, size, model_object);
    }

    @Override
    public void draw_visual(Graphics g)
    {
        Worker worker = (Worker) model_object;

        Color color  = Colors.getInstance().getWorker();

        color = check_draw_selected(color);

        g.setColor(color);

        // visual
        g.fillOval(x  , y, size, size);
        view.draw_angled_line(g,x + size/2,y + size/2,worker.getCurrDirection(),size);

        if(selected)
        {
            draw_debug_info(g,worker);
        }
    }

    public void draw_debug_info(Graphics g, Worker worker)
    {


        draw_load(g, worker, 0);
        //draw_target_coord(g,worker,1);
        draw_task(g,worker,1);
        draw_state(g,worker,2);

        draw_target(g,worker);

    }

    public void draw_load(Graphics g, Worker worker, int height )
    {
        String amount_load = String.valueOf(worker.get_total_amount_of_load());
        g.drawString("load: " + amount_load,x,y-text_size*height);
    }

    public void draw_target_coord(Graphics g, Worker worker, int height )
    {
        String target_x = String.valueOf(worker.getTask().getTarget_x());
        String target_y = String.valueOf(worker.getTask().getTarget_y());
        g.drawString("(" + target_x + "," + target_y + ")",x,y-text_size*height);
    }

    public void draw_task(Graphics g, Worker worker, int height )
    {
        Task_Enum task = worker.getTask().getTask();

        //if(task == Task_Enum.miner)
        //{
            g.drawString(task + " " + String.valueOf(worker.getResource_type()),x,y-text_size*height);
        //}
        //g.drawString(String.valueOf(task),x,y-text_size*height);
    }

    public void draw_state(Graphics g, Worker worker, int height )
    {
        Worker_State_Enum state = worker.getState();
        g.drawString(String.valueOf(state),x,y-text_size*height);
    }

    public void draw_target(Graphics g, Worker worker)
    {
        int oval_size = 10;
        int x_target = view.x_model_to_view(worker.getTask().getTarget_x());
        int y_target = view.y_model_to_view(worker.getTask().getTarget_y());

        g.drawOval(x_target-oval_size/2,y_target-oval_size/2, oval_size, oval_size);
    }


}
