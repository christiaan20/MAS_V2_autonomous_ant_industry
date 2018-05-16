package Model.Enterables;

import Model.Model;
import Model.Object;
import Model.Worker;
import View.Object_visuals.Base_visual;

/**
 * Created by christiaan on 10/05/18.
 */
public abstract class Enterable_object extends Object {
    protected int time; //time in ticks

    public Enterable_object( int x, int y, int size, int time) {
        super(x, y, size);
        this.time = time;

    }

    public abstract void enter(Worker w);
    public abstract void exit(Worker w);
    public abstract void has_reached(int x, int y);

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
