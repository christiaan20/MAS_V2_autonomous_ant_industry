package Model.Enterables;

import Model.Model;
import Model.Resource_type;
import Model.Enterables.Enterable_object;
import Model.Worker;
import View.Object_visuals.Base_visual;
import View.View;

/**
 * Created by christiaan on 10/05/18.
 */
public class Resource_pool extends Enterable_object {
    private Resource_type type;

    public Resource_pool( int x, int y, int size, int time, Resource_type type) {
        super(x, y, size, time);
        this.type = type;
        this.visual = new Base_visual( x, y, size,this);
        View.getInstance().add_visual(this.visual);
    }

    public Resource_type getType() {
        return type;
    }

    public void setType(Resource_type type) {
        this.type = type;
    }


    @Override
    public void enter(Worker w) {

    }

    @Override
    public void exit(Worker w) {

    }

    @Override
    public void has_reached(int x, int y) {

    }
}
