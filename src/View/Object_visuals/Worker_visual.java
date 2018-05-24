package View.Object_visuals;

import Model_pk.Behaviour.Task_Enum;
import Model_pk.Object;
import Model_pk.Resource_Type;
import Model_pk.Worker;
import Model_pk.Worker_State_Enum;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by christiaan on 16/05/18.
 */
public class Worker_visual extends Object_visual
{


    public Worker_visual(int x, int y, int size, Object model_object) {

        super(x, y, size, model_object);
    }

    @Override
    public void draw_visual(Graphics g, int offset_x, int offset_y)
    {
        Worker worker = (Worker) model_object;

        Color color  = Colors.getInstance().getWorker();
        Color color_hat  = Colors.getInstance().getPheroColor(worker.getTask().getTask(),worker.getResource_type());
        Color color_hat_edge  = Colors.getInstance().getPheroColor(Task_Enum.miner,worker.getResource_type());
        color = check_draw_selected(color);

        g.setColor(color);

        // visual
        g.fillOval(x  , y, size, size);
        view.draw_angled_line(g,x + size/2,y + size/2,worker.getCurrDirection(),size);

        g.setColor(color_hat_edge);
        int hat_edge = 5;
        g.fillOval(x + hat_edge, y+ hat_edge, size- hat_edge*2, size- hat_edge*2);
        g.setColor(color_hat);

        g.drawOval(x , y, size, size);

        g.setColor(color);

        if(selected)
        {
            draw_debug_info(g,worker);
        }
    }

    public void draw_debug_info(Graphics g, Worker worker)
    {


        draw_detection_range(g, worker );
        draw_load(g, worker, 0);
        //draw_target_coord(g,worker,1);
        draw_task(g,worker,1);
        draw_state(g,worker,2);
        draw_ID(g,worker,3);

        draw_target(g,worker);
        draw_visited(g,worker);

    }

    private void draw_detection_range(Graphics g, Worker worker)
    {
        //regular detection distance
        int dist = worker.getTask().getPhero_detect_dist();
        int display_x = view.x_model_to_view(worker.getX())-dist;
        int display_y = view.y_model_to_view(worker.getY())-dist;

        g.drawOval(display_x,display_y,dist*2,dist*2);

        //base detection distance
        dist = (int) (50*2.5);
        display_x = view.x_model_to_view(worker.getX())-dist;
        display_y = view.y_model_to_view(worker.getY())-dist;

        g.drawOval(display_x,display_y,dist*2,dist*2);
    }

    public void draw_ID(Graphics g, Worker worker, int height )
    {
        String ID = String.valueOf(worker.getID());
        g.drawString("ID: " +ID ,x,y-text_size*height);
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
        Resource_Type type = worker.getResource_type();

        //if(task == Task_Enum.miner)
        //{
        g.drawString(task + " " + type,x,y-text_size*height);
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

    public void draw_visited(Graphics g, Worker worker)
    {
        ArrayList<Object> visit = worker.getVisited_objects();
        for(int i = 0 ; i < visit.size(); i++ )
        {
            Object obj = visit.get(i);
            String target_x = String.valueOf(obj.getX());
            String target_y = String.valueOf(obj.getY());
            g.drawString(String.valueOf(i)+ " (" + target_x + "," + target_y + ")",worker.getX()-100,y-text_size*i);

        }

    }


}
