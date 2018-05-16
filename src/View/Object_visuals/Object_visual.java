package View.Object_visuals;

import java.awt.*;

import Model.Object;
import View.View;

/**
 * Created by christiaan on 15/05/18.
 */
public abstract class Object_visual {
    protected View view;

    protected int x;    //x position as drawn by the view
    protected int y;    //y position as drawn by the view
    protected int size;
    protected Object model_object;

    public Object_visual(int x, int y, int size, Object model_object)
    {
        view = View.getInstance();
        this.update_parameter(x,y,size);
        this.model_object =  model_object;
    }

    public abstract void draw(Graphics g);

    public void update_parameter(int x, int y, int size)
    {
        //if the object has model coordinates and view coordinates are calculated at draw()
        this.size = view.size_model_to_view(size);
        this.x = view.x_model_to_view(x)-(size/2);
        this.y = view.y_model_to_view(y)-(size/2);
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
}
