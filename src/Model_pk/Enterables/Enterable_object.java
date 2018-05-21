package Model_pk.Enterables;

import Model_pk.Object;
import Model_pk.Resource;
import Model_pk.Worker;

/**
 * Created by christiaan on 10/05/18.
 */
public abstract class Enterable_object extends Object {
    protected int time; //time in ticks

    public Enterable_object( int x, int y, int size, int time) {
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
