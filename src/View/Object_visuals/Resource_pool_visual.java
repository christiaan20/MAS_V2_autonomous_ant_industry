package View.Object_visuals;

import Model_pk.Enterables.Resource_pool;
import Model_pk.Object;
import Model_pk.Resource;
import Model_pk.Resource_Type;
import Model_pk.Worker;
import com.sun.corba.se.impl.orbutil.graph.Graph;

import java.awt.*;

/**
 * Created by christiaan on 16/05/18.
 */
public class Resource_pool_visual extends Object_visual
{
    Resource_Type type;

    public Resource_pool_visual(int x, int y, int size, Object model_object, Resource_Type type)
    {
        super(x, y, size, model_object);
        this.type = type;
    }

    @Override
    public void draw_visual(Graphics g)
    {
        Resource_pool res = (Resource_pool) model_object;
        if( type == Resource_Type.Stone)
        {
            draw_type_stone(g);
        }
        if(type == Resource_Type.Coal )
        {
            draw_type_coal(g);
        }
        if(type == Resource_Type.Copper )
        {
            draw_type_Copper(g);
        }
        if(type == Resource_Type.Uranium )
        {
            draw_type_uranium(g);
        }
        if(type == Resource_Type.Iron )
        {
            draw_type_iron(g);
        }

        if(selected)
        {
            draw_debug_info(g,res);
        }
    }



    public void draw_type_stone(Graphics g)
    {
        Color color = Colors.getInstance().getStone();

        color = check_draw_selected(color);

        g.setColor(color);
        int w = size/2;
        g.fillOval(x  , y, w, w);
        g.fillOval(x + w  , y, w, w);
        g.fillOval(x,y+ w , w, w);
        g.fillOval(x + size/4,y + size/4  , ((size*3)/4),((size*3)/4));
    }

    public void draw_type_iron(Graphics g)
    {
        Color color = Colors.getInstance().getIron();

        color = check_draw_selected(color);

        g.setColor(color);
        int w = size/2;
        g.fillOval(x  , y, w, w);
        g.fillOval(x + w  , y, w, w);
        g.fillOval(x,y+ w , w, w);
        g.fillOval(x + size/4,y + size/4  , ((size*3)/4),((size*3)/4));
    }

    public void draw_type_coal(Graphics g)
{
    Color color = Colors.getInstance().get_resource_pool_color(Resource_Type.Coal);

    color = check_draw_selected(color);

    g.setColor(color);

    g.setColor(color);
    int w = size/2;
    g.fillOval(x  , y, w, w);
    g.fillOval(x + w  , y, w, w);
    g.fillOval(x,y+ w , w, w);
    g.fillOval(x + size/4,y + size/4  , ((size*3)/4),((size*3)/4));
}

    public void draw_type_Copper(Graphics g)
    {
        Color color = Colors.getInstance().get_resource_pool_color(Resource_Type.Copper);

        color = check_draw_selected(color);

        g.setColor(color);

        g.setColor(color);
        int w = size/2;
        g.fillOval(x  , y, w, w);
        g.fillOval(x + w  , y, w, w);
        g.fillOval(x,y+ w , w, w);
        g.fillOval(x + size/4,y + size/4  , ((size*3)/4),((size*3)/4));
    }

    public void draw_type_uranium(Graphics g)
    {
        Color color = Colors.getInstance().get_resource_pool_color(Resource_Type.Uranium);

        color = check_draw_selected(color);

        g.setColor(color);

        g.setColor(color);

        g.fillOval(x  , y, size, size);

    }

 //   public void draw_collection_rock()

    public void draw_debug_info(Graphics g, Resource_pool res)
    {
        draw_capacity(g,res, 0);
        draw_type(g,res,1);
    }

    public void draw_capacity(Graphics g, Resource_pool res, int height)
    {

        String amount_capacity = String.valueOf(res.getCapacity());
        g.drawString("cap : " + amount_capacity,x,y-text_size*height);
    }

    public void draw_type(Graphics g, Resource_pool res, int height)
    {
        String type= String.valueOf(res.getType());
        g.drawString(type,x,y-text_size*height);
    }



}
