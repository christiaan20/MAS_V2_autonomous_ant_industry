package Model.Enterables;

import java.lang.reflect.Array;
import java.util.ArrayList;


import Model.Model;
import Model.Enterables.Enterable_object;
import Model.Resource_type;
import Model.Worker;
import View.Object_visuals.Base_visual;
import View.View;


/**
 * Created by christiaan on 10/05/18.
 */
public class Factory extends Enterable_object {
    private ArrayList<Resource_type> needed_resources = new ArrayList<Resource_type>();
    private ArrayList<Resource_type> current_resources = new ArrayList<Resource_type>();
    private ArrayList<Resource_type> produced_resources = new ArrayList<Resource_type>();

    public Factory( int x, int y, int size, int time) {
        super(x, y, size, time);
        this.visual = new Base_visual( x, y, size,this);
        View.getInstance().add_visual(this.visual);
    }

    @Override
    public void has_reached(int x, int y) {

    }

    @Override
    public void exit(Worker w) {

    }

    @Override
    public void enter(Worker w) {

    }
}
