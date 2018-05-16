package View;

import java.awt.*;
import java.util.ArrayList;

import View.Object_visuals.Object_visual;
/**
 * Created by christiaan on 10/05/18.
 */
public class View extends Canvas {
    private static View view;

    private double scale_from_model = 1; //the scale that the view objects have relative to the sizes in the model
    private int size_x;
    private int size_y;

    private int size_x_field;
    private int size_y_field;


    private int mouse_x;
    private int mouse_y;

    private int y_offset; //offset from top to compensate for the top_panel

    private Image buffer;

    private Color background_color = new Color(194, 60, 42); //red color for the mars ground

    private ArrayList<Object_visual> object_visuals = new ArrayList<Object_visual>();

    /**
     * Create private constructor for a singleton
     */
    private View() {


    }


    /**
     * Create a static method to get instance of the singleton
     */
    public static View getInstance() {
        if (view == null) {
            view = new View();
        }
        return view;
    }

    public void initialise()
    {
        size_x = getWidth();
        size_y = getHeight()-10;
    }

    public void paint() {
        //necessary preparation before drawing
        buffer = create_buffer_image();
        Graphics g = buffer.getGraphics();
        int x = getX();
        int y = getY();

       /*
       System.out.println("paint x: "  + String.valueOf(x) + " y: "
                                        + String.valueOf(y) + " w: "
                                        + String.valueOf(size_x) + " h: "
                                        + String.valueOf(size_y))  ;
                                        */

        g.clearRect(x, y, size_x, size_y);

        //methods that draw the required graphics
        draw_background(g);
        draw_object_visuals(g);
        draw_mouse_coordinates(g);

        getGraphics().drawImage(buffer, 0, 0, this);
    }

    public void draw_background(Graphics g) {
        g.setColor(background_color);

        g.fillRect(0, 0, size_x, size_y);

        //the borders of the field
        g.setColor(Color.black);
        int x_origin = x_model_to_view(0);
        int y_origin = y_model_to_view(size_y_field);
        g.drawRect(x_origin, y_origin,size_x_field,size_y_field);
    }

    public void draw_object_visuals(Graphics g)
    {
        for (Object_visual obj :object_visuals)
        {
            obj.draw(g);
        }
    }

    public void set_y_offset(int y_offset) {
        this.y_offset = y_offset;
    }

    public void drawAngledLine(Graphics g, int x, int y, double angle, int length) {
        int tr_y = transform_y(y);

        int x_dist = (int) (Math.cos(angle) * (double) length);
        int y_dist = (int) (Math.sin(angle) * (double) length);

        g.drawLine(x, tr_y, x + x_dist, tr_y - (y + y_dist));
    }

    public void draw_mouse_coordinates(Graphics g)
    {
        int text_size = 15;
        g.setColor(Color.green);
        String point_view = "V:(" + String.valueOf(mouse_x) + ',' + String.valueOf(mouse_y) + ')';
        g.drawString(point_view, mouse_x, mouse_y - text_size*1);
        String point_model = "M:(" + String.valueOf(x_view_to_model(mouse_x)) + ',' + String.valueOf(y_view_to_model(mouse_y)) + ')';
        g.drawString(point_model, mouse_x, mouse_y - text_size*0);
    }

    public Image create_buffer_image()
    {
        if (buffer == null) {
            int h = getHeight();
            int w = getWidth();
            // h  = 895
            //w = 984

            buffer = createImage(w, h);
        }

        return buffer;
    }


    public  int x_model_to_view(int x)
    {
        return (int)(x*scale_from_model);
    }

    public int y_model_to_view(int y)
    {
        return (int) (size_y - (y*scale_from_model));
    }

    public int size_model_to_view(int size)
    {
        return (int)(size*scale_from_model);
    }


    public  int x_view_to_model(int x)
    {

        return (int) (x/scale_from_model);
    }

    public  int y_view_to_model(int y)
    {
        return (int) ((size_y - y)/scale_from_model);
    }

    public void set_size(int size_x, int size_y) {
        this.size_x = size_x;
        this.size_y = size_y;
    }

    public void set_field_size(int size_x, int size_y) {
        this.size_x_field = (int)(size_x*scale_from_model);
        this.size_y_field = (int)(size_y*scale_from_model);
    }


    public int transform_y(int y)
    {
        return getHeight()- y;
    }

    public double getScale_from_model() {
        return scale_from_model;
    }

    public void setScale_from_model(double scale_from_model) {
        this.scale_from_model = scale_from_model;
    }

    public void add_visual(Object_visual visual) {
        object_visuals.add(visual);
    }

    public int getViewHeight()
    {
        return getHeight();
    }

    public int getMouse_x() {
        return mouse_x;
    }

    public void setMouse_x(int mouse_x) {
        this.mouse_x = mouse_x; //- getX();
    }

    public int getMouse_y() {
        return mouse_y;
    }

    public void setMouse_y(int mouse_y) {
        this.mouse_y = mouse_y;// - getY();
    }
}
