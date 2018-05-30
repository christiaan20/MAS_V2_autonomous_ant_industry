package View.Object_visuals;

import Model_pk.Behaviour.Task_Enum;
import Model_pk.Enums.Resource_Type_Enum;

import java.awt.*;

/**
 * Created by christiaan on 20/05/18.
 */
public class Colors
{
    private static Colors colors;

    Color background = new Color(194, 60, 42); //red color for the mars ground;
    Color worker = Color.BLACK;
    Color selected = Color.green;

    Color explorer =  Color.blue;
    Color copper = new Color(182, 115, 51); ;
    Color iron = new Color(68, 79, 103);
    Color stone  = Color.GRAY;
    Color coal = new Color(30, 40, 50);;
    Color uranium = Color.green;
    Color bug = new Color(255, 0, 0);



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

    public Color get_resource_pool_color(Resource_Type_Enum type)
    {
        Color out = null;
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


        return out;
    }

    public Color getPheroColor(Task_Enum task, Resource_Type_Enum type)
    {
        Color out = null;
        if(task == Task_Enum.explorer)
        {
            out= explorer;
        }
        else
        {

            if(type == null)
            {
                out = explorer;
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

    public Color getSelected() {
        return selected;
    }

    public void setSelected(Color selected) {
        this.selected = selected;
    }

    public Color getIron() {
        return iron;
    }

    public void setIron(Color iron) {
        this.iron = iron;
    }

    public Color getCoal() {
        return coal;
    }

    public void setCoal(Color coal) {
        this.coal = coal;
    }

    public Color getUranium() {
        return uranium;
    }

    public void setUranium(Color uranium) {
        this.uranium = uranium;
    }
}
