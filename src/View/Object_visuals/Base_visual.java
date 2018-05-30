package View.Object_visuals;

import Model_pk.Objects.Enterables.Base;
import Model_pk.Objects.Abstr_Object;

import java.awt.*;

/**
 * Created by christiaan on 15/05/18.
 */
public class Base_visual extends Object_visual{

    public Base_visual(int x, int y, int size, Abstr_Object model_object) {
        super(x, y, size, model_object);
    }

    @Override
    public void draw_visual(Graphics g, int offset_x, int offset_y)
    {
        //Objects only have a height after rendering so this is the only way

       /*
        int view_size = view.size_model_to_view(size);
        int view_x =    view.x_model_to_view(x)-(view_size/2);
        int view_y =    view.y_model_to_view(y)-(view_size/2);

        double beam_width = 0.1;
        g.setColor(Color.black);
        g.fillRect(view_x  , view_y, view_size, view_size);
        */


        int center_size = (int) (size*0.2);

        Color brown = new Color(148,61,7);
        Color outer_color = brown;

        outer_color = check_draw_selected(outer_color);


        g.setColor(outer_color);
        g.fillRect(x  , y, size, size);
        g.setColor(Color.lightGray);
        g.fillRect(x+ size/2 - center_size/2 , y+ size/2 - center_size/2 , center_size, center_size);

        if(selected)
        {
            draw_detect_range(g);
        }
    }

    public void draw_detect_range(Graphics g)
    {
        Base  base = ((Base) getModel_object());
        int dist = base.getDetect_dist();
        int display_x = view.x_model_to_view(base.getX())-dist;
        int display_y = view.y_model_to_view(base.getY())-dist;

        g.drawOval(display_x,display_y,dist*2,dist*2);
    }



}
