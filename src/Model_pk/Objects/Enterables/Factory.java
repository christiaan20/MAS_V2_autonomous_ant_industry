package Model_pk.Objects.Enterables;

import java.util.ArrayList;


import Model_pk.Enums.Resource_Type_Enum;
import Model_pk.Objects.Worker;
import View.Object_visuals.Base_visual;


/**
 * Created by christiaan on 10/05/18.
 */
public class Factory extends Abstr_Enterable_object {
    private ArrayList<Resource_Type_Enum> needed_resources = new ArrayList<Resource_Type_Enum>();
    private ArrayList<Resource_Type_Enum> current_resources = new ArrayList<Resource_Type_Enum>();
    private ArrayList<Resource_Type_Enum> produced_resources = new ArrayList<Resource_Type_Enum>();

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
