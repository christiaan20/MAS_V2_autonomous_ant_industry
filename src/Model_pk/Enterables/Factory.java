package Model_pk.Enterables;

import java.util.ArrayList;


import Model_pk.Resource_Type;
import Model_pk.Worker;
import View.Object_visuals.Base_visual;
import View.View;


/**
 * Created by christiaan on 10/05/18.
 */
public class Factory extends Enterable_object {
    private ArrayList<Resource_Type> needed_resources = new ArrayList<Resource_Type>();
    private ArrayList<Resource_Type> current_resources = new ArrayList<Resource_Type>();
    private ArrayList<Resource_Type> produced_resources = new ArrayList<Resource_Type>();

    public Factory( int x, int y, int size, int time) {
        super(x, y, size, time);
        this.setVisual(new Base_visual( x, y, size,this));

    }

    @Override
    public void tick() {

    }

    @Override
    public void exit(Worker worker) {

    }

    @Override
    public boolean action(Worker worker) {
        return false;
    }


}
