package View;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

import View.Object_visuals.Colors;
import View.Object_visuals.Object_visual;
/**
 * Created by christiaan on 10/05/18.
 */
public class View extends Canvas {
    private static View view;

    private Colors colors;

    private double scale_from_model = 1; //the scale that the view objects have relative to the sizes in the model
    private int size_x;
    private int size_y;
    private int offset_x;
    private int offset_y;
    private int offset_delta = 50;

    private int size_x_field;
    private int size_y_field;

    private int mouse_x;
    private int mouse_y;
    private Object_visual hovering_over_visual;
    private Object_visual selected_visual;

    private Image buffer;

    private Color background_color = new Color(194, 60, 42); //red color for the mars ground
    private int text_size = 15;

    private boolean draw_pheromones = true;


    private ArrayList<Object_visual> object_visuals = new ArrayList<Object_visual>();
    private ArrayList<Object_visual> object_worker_visuals = new ArrayList<Object_visual>();

    /**
     * Create private constructor for a singleton
     */
    private View() {
        colors = Colors.getInstance();

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
        draw_worker_visuals(g);
        draw_mouse_coordinates(g);

        getGraphics().drawImage(buffer, 0, 0, this);
    }

    public void draw_background(Graphics g) {
        g.setColor(colors.getBackground());

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
            obj.draw(g,offset_x ,offset_y );
        }
    }
    public void draw_worker_visuals(Graphics g)
    {
        for (Object_visual obj :object_worker_visuals)
        {
            obj.draw(g,offset_x ,offset_y );
        }
    }



    public void draw_angled_line(Graphics g, int x, int y, double angle, int length) {
        //int tr_y = transform_y(y);

        //System.out.println("angle " + String.valueOf(angle));


        int x_dist = (int) (Math.cos(angle) * (double) length);
        int y_dist = (int) (Math.sin(angle) * (double) length);

        //System.out.println("x dist " +  String.valueOf(x_dist));
        //System.out.println("y_dist " +  String.valueOf(y_dist));

        g.drawLine(x, y, x + x_dist, (y - y_dist));
    }

    public void draw_mouse_coordinates(Graphics g)
    {
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

    public void check_hovering_over(int x, int y)
    {


        Object_visual hovering = get_visual_if_within(x,y);

        if(hovering_over_visual != null)
        {
            if( hovering_over_visual == hovering)
            {
                return;
            }
            else if(hovering != null)
            {
                hovering_over_visual.setHover_over(false);
                hovering_over_visual = hovering;
                hovering_over_visual.setHover_over(true);
            }
            else
            {
                hovering_over_visual.setHover_over(false);
                hovering_over_visual = null;
            }
        }
        else
        {
            if(hovering != null)
            {
                hovering_over_visual = hovering;
                hovering_over_visual.setHover_over(true);
            }
        }

    }

    public void select_hovering_over()
    {
        if(selected_visual != null)
        {
            if(selected_visual == hovering_over_visual )
            {
                return;
            }
            else if( hovering_over_visual != null)
            {
                selected_visual.setSelected(false);
                selected_visual =  hovering_over_visual;
                selected_visual.setSelected(true);
            }
            else
            {
                selected_visual.setSelected(false);
                selected_visual = null;
            }
        }
        else
        {
            if(hovering_over_visual != null)
            {
                selected_visual = hovering_over_visual;
                selected_visual.setSelected(true);
            }
        }

    }


    public Object_visual get_visual_if_within(int x, int y)
    {
        //called by the controller in a different thread so to ensure threadsafety
        Object_visual[] ObjectenArr = new Object_visual[object_visuals.size()];
        ObjectenArr = object_visuals.toArray(ObjectenArr);

        Object_visual[] ObjectenWorkerArr = new Object_visual[object_worker_visuals.size()];
        ObjectenWorkerArr = object_worker_visuals.toArray(ObjectenWorkerArr);

        for(Object_visual obj: ObjectenArr )
        {
            if (obj.check_within(x,y, offset_x,offset_y ))
            {
                return obj;
            }
        }
        for(Object_visual obj: ObjectenWorkerArr )
        {
            if (obj.check_within(x,y, offset_x,offset_y ))
            {
                return obj;
            }
        }


        return null;
    }


    public  int x_model_to_view(int x)
    {
        return (int)(x*scale_from_model-offset_x);
    }

    public int y_model_to_view(int y)
    {
        return (int) (size_y - (y*scale_from_model-offset_y));
    }

    public int size_model_to_view(int size)
    {
        return (int)(size*scale_from_model);
    }


    public  int x_view_to_model(int x)
    {

        return (int) ((x+offset_x)/scale_from_model);
    }

    public  int y_view_to_model(int y)
    {
        return (int) ((size_y - (y + offset_y))/scale_from_model);
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

    public void add_worker_visual(Object_visual visual) {
        object_worker_visuals.add(visual);
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

    public int getText_size() {
        return text_size;
    }

    public void setText_size(int text_size) {
        this.text_size = text_size;
    }

    public Object_visual getHovering_over_visual() {
        return hovering_over_visual;
    }

    public void setHovering_over_visual(Object_visual hovering_over_visual) {
        this.hovering_over_visual = hovering_over_visual;
    }

    public Object_visual getSelected_visual() {
        return selected_visual;
    }

    public void setSelected_visual(Object_visual selected_visual) {
        this.selected_visual = selected_visual;
    }

    public void delete_visual(Object_visual visual)
    {
        Iterator<Object_visual> iter2 = object_visuals.iterator();
        while (iter2.hasNext()) {
            Object_visual obj = iter2.next();
            if(obj.equals(visual))
            {
                iter2.remove();
            }


        }
    }

    public boolean isDraw_pheromones() {
        return draw_pheromones;
    }

    public void setDraw_pheromones(boolean draw_pheromones) {
        this.draw_pheromones = draw_pheromones;
    }

    public int getOffset_x() {
        return offset_x;
    }

    public void setOffset_x(int offset_x) {
        this.offset_x = offset_x;
        set_offset_to_objects( this.offset_x, this.offset_y);
    }

    public int getOffset_y() {
        return offset_y;

    }

    public void setOffset_y(int offset_y) {
        this.offset_y = offset_y;
        set_offset_to_objects(this.offset_x, this.offset_y);
    }

    public void increase_offset_x()
    {
        setOffset_x(offset_x + offset_delta);
    }
    public void increase_offset_y()
    {
        setOffset_y(offset_y + offset_delta);
    }

    public void decrease_offset_x()
    {
        setOffset_x(offset_x - offset_delta);
    }
    public void decrease_offset_y()
    {
        setOffset_y(offset_y - offset_delta);

    }

    public void set_offset_to_objects(int offset_x, int offset_y)
    {
        for(Object_visual obj: object_visuals)
        {
            obj.set_offset(offset_x,offset_y);
        }
    }

}
