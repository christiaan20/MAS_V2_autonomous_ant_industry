package View.Object_visuals;

import Model_pk.Objects.Enterables.Resource_pool;
import Model_pk.Objects.Abstr_Object;
import Model_pk.Enums.Resource_Type_Enum;

import java.awt.*;

/**
 * Created by christiaan on 16/05/18.
 */
public class Resource_pool_visual extends Object_visual
{
    Resource_Type_Enum type;

    public Resource_pool_visual(int x, int y, int size, Abstr_Object model_object, Resource_Type_Enum type)
    {
        super(x, y, size, model_object);
        this.type = type;
    }

    @Override
    public void draw_visual(Graphics g, int offset_x, int offset_y)
    {
        Resource_pool res = (Resource_pool) model_object;
        if( type == Resource_Type_Enum.Stone)
        {
            draw_type_stone(g);
        }
        if(type == Resource_Type_Enum.Coal )
        {
            draw_type_coal(g);
        }
        if(type == Resource_Type_Enum.Copper )
        {
            draw_type_Copper(g);
        }
        if(type == Resource_Type_Enum.Uranium )
        {
            draw_type_uranium(g);
        }
        if(type == Resource_Type_Enum.Iron )
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
    Color color = Colors.getInstance().get_resource_pool_color(Resource_Type_Enum.Coal);

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
        Color color = Colors.getInstance().get_resource_pool_color(Resource_Type_Enum.Copper);

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
        Color color = Colors.getInstance().get_resource_pool_color(Resource_Type_Enum.Uranium);

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
