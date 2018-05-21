package View.Object_visuals;

import Model_pk.Behaviour.Abstr_Task;
import Model_pk.Behaviour.Task_Enum;
import Model_pk.Resource_Type;

import java.awt.*;

/**
 * Created by christiaan on 20/05/18.
 */
public class Colors
{
    private static Colors colors;

    Color background = new Color(194, 60, 42); //red color for the mars ground;
    Color worker = Color.BLACK;


    Color copper = new Color(182, 115, 51); ;
    Color iron = new Color(224,223,219);
    Color stone  = Color.GRAY;
    Color explorer =  Color.blue;
    Color coal = Color.darkGray;
    Color uranium = Color.green;



    /**
     * Create private constructor for a singleton
     */
    private Colors(){}

    /**
     * Create a static method to get instance of the singleton
     */
    public static Colors getInstance(){
        if(colors == null){
            colors = new Colors();
        }
        return colors;
    }

    public Color getPheroColor(Task_Enum task, Resource_Type type)
    {
        Color out = null;
        if(task == Task_Enum.explorer)
        {
            out= explorer;
        }
        else
        {
            switch(type)
            {
                case Stone:
                    out = stone;
                    break;
                case Copper:
                    out = copper;
                    break;
                case Coal:
                    out = coal;
                    break;
                case Iron:
                    out= iron;
                    break;
                case Uranium:
                    out = uranium;
            }

        }
        return out;
    }


    public Color getBackground() {
        return background;
    }

    public void setBackground(Color background) {
        this.background = background;
    }

    public Color getWorker() {
        return worker;
    }

    public void setWorker(Color worker) {
        this.worker = worker;
    }

    public Color getCopper() {
        return copper;
    }

    public void setCopper(Color copper) {
        this.copper = copper;
    }

    public Color getStone() {
        return stone;
    }

    public void setStone(Color stone) {
        this.stone = stone;
    }

    public Color getExplorer() {
        return explorer;
    }

    public void setExplorer(Color explorer) {
        this.explorer = explorer;
    }
}
