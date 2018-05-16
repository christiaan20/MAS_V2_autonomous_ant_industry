package View.Object_visuals;

import Model.Object;
import View.View;

import java.awt.*;

/**
 * Created by christiaan on 15/05/18.
 */
public class Base_visual extends Object_visual{

    public Base_visual(int x, int y, int size, Object model_object) {
        super(x, y, size, model_object);
    }

    @Override
    public void draw(Graphics g)
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

        g.setColor(Color.black);
        g.fillRect(x  , y, size, size);
        g.setColor(Color.lightGray);
        g.fillRect(x + size/2 - center_size/2 , y + size/2 - center_size/2 , center_size, center_size);
}
}
