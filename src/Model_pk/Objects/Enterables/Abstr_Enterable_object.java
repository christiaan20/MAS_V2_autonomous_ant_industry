package Model_pk.Objects.Enterables;

import Model_pk.Objects.Abstr_Object;
import Model_pk.Objects.Worker;

/**
 * An abstract object that is enterable in its nature.
 */
public abstract class Abstr_Enterable_object extends Abstr_Object {
    protected int time; //time in ticks

    public Abstr_Enterable_object(int x, int y, int size, int time) {
        super(x, y, size);
        this.time = time;

    }

    public abstract boolean action(Worker worker);
    public abstract void exit(Worker worker);

    public boolean enter(Worker worker)
    {
        if(worker.getTime() >= time)
        {
            worker.setTime(0);
            worker.getTask().setEnter_base(false);
            return action(worker);
        }
        else
        {
            worker.increase_time();
            return true;
        }
    }


    public boolean has_reached(int x, int y)
    {
        return check_within(x,y);
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
