package View.Object_visuals;

import Model_pk.Behaviour.Task_Enum;
import Model_pk.Object;
import Model_pk.Pheromone;
import Model_pk.Resource_Type;
import Model_pk.Worker;

import java.awt.*;

/**
 * Created by christiaan on 20/05/18.
 */
public class Pheromone_visual extends Object_visual
{

    public Pheromone_visual(int x, int y, int size, Object model_object) {
        super(x, y, size, model_object);
    }

    @Override
    public void draw_visual(Graphics g, int offset_x, int offset_y)
    {
        if(view.isDraw_pheromones())
        {
            Pheromone phero = (Pheromone) getModel_object();
            Color color = Colors.getInstance().getPheroColor(phero.getTask(),phero.getType());


            color = check_draw_selected(color);

            g.setColor(color);

            // visual
            g.fillOval(x , y, size, size);

            if(selected)
            {
                draw_debug_info(g,phero);
            }
        }



    }


    public void draw_debug_info(Graphics g, Pheromone phero)
    {
        draw_strength(g, phero, 0);
        draw_position(g,phero,1);
        draw_task(g,phero,2);

    }

    public void draw_strength(Graphics g, Pheromone phero, int height )
    {
        String amount_load = String.valueOf(phero.getStrength());
        g.drawString( amount_load,x,y-text_size*height);
    }

    public void draw_position(Graphics g, Pheromone phero, int height )
    {
        String target_x = String.valueOf(phero.getX());
        String target_y = String.valueOf(phero.getY());
        g.drawString("(" + target_x + "," + target_y + ")",x,y-text_size*height);
    }

    public void draw_task(Graphics g, Pheromone phero, int height )
    {
        Task_Enum task = phero.getTask();
        Resource_Type type = phero.getType();

        //if(task == Task_Enum.miner)
        //{
        g.drawString(task + " " + type,x,y-text_size*height);
        //}
        //g.drawString(String.valueOf(task),x,y-text_size*height);
    }


}
