package View.Object_visuals;

import java.awt.*;

import Model_pk.Objects.Abstr_Object;
import View.View;

/**
 * Created by christiaan on 15/05/18.
 */
public abstract class Object_visual {
    protected View view;

    protected int x;    //x position as drawn by the view
    protected int y;    //y position as drawn by the view
    protected int size;
    protected Abstr_Object model_object;

    protected boolean selected;
    protected boolean hover_over;
    protected boolean broken; //only used by worker to set broken

    protected int text_size;

    public Object_visual(int x, int y, int size, Abstr_Object model_object)
    {
        view = View.getInstance();
        this.update_parameter(x,y,size);
        this.model_object =  model_object;
        text_size = view.getText_size();
    }

    public abstract void draw_visual(Graphics g, int offset_x, int offset_y);

    public void draw(Graphics g, int offset_x, int offset_y)
    {
        draw_visual(g, offset_x,offset_y);
        check_draw_hover(g,offset_x ,offset_y );

    }

    public void update_parameter(int x, int y, int size)
    {
        //if the object has model coordinates and view coordinates are calculated at draw()
        this.size = view.size_model_to_view(size);
        this.x = view.x_model_to_view(x)-(size/2);
        this.y = view.y_model_to_view(y)-(size/2);
    }

    public void check_draw_hover(Graphics g, int offset_x, int offset_y)
    {
        if(hover_over)
        {

            g.setColor(Color.black);
            g.drawRect(x- 2, y - 2, size + 4, size + 4);
        }
    }

    public Color check_draw_selected(Color current)
    {
        if(selected)
        {
            return Colors.getInstance().getSelected();
        }
        return current;

    }

    public boolean check_within(int x, int y, int offset_x, int offset_y)
    {


        int top_border =this.y + size;
        int bottom_border = this.y;
        int left_border = this.x;
        int right_border = this.x+ size;

        if(     (top_border >= y) &&  (bottom_border <= y) &&
                (right_border >= x) &&  (left_border <= x))
        {
            return true;
        }
        return false;
    }

    public void set_offset(int offset_x, int offset_y)
    {
        setX(x - offset_x);
        setY(y - offset_y);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isHover_over() {
        return hover_over;
    }

    public void setHover_over(boolean hover_over) {
        this.hover_over = hover_over;
    }

    public Abstr_Object getModel_object() {
        return model_object;
    }

    public void setModel_object(Abstr_Object model_object) {
        this.model_object = model_object;
    }

    public void setBroken(boolean broken)
    {


    }
}
