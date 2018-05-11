package View;

import Model.Model;

import java.awt.*;

/**
 * Created by christiaan on 10/05/18.
 */
public class View extends Canvas {
    private static View  view ;

    private double scale_from_model; //the scale that the view objects have relative to the sizes in the model
    private int size_x;
    private int size_y;
    private int y_offset; //offset from top to compensate for the top_panel

    private Image buffer;

    /**
     * Create private constructor for a singleton
     */
    private View()
    {


    }


    /**
     * Create a static method to get instance of the singleton
     */
    public static View getInstance(){
        if(view == null){
            view = new View();
        }
        return view;
    }

    public void paint()
    {
        if (buffer == null)
        {
            buffer = createImage(getWidth(), getHeight());
        }
        Graphics g = buffer.getGraphics();
        g.clearRect(0,0,size_x, size_y);
        draw_background(g);
        getGraphics().drawImage(buffer,0,y_offset,this);
    }

    public void draw_background(Graphics g)
    {
        g.setColor(new Color(194,60,42 )); //red color for the mars ground
        g.fillRect(getX(),getY(),getWidth(), getHeight());

        g.setColor(Color.black);
        //red color for the mars ground
        g.drawRect(450,450,50,50);
    }

    public void setSize(int size_x, int size_y)
    {
        this.size_x = size_x;
        this.size_y = size_y;
    }

    public void set_y_offset(int y_offset)
    {
        this.y_offset = y_offset;
    }
}
