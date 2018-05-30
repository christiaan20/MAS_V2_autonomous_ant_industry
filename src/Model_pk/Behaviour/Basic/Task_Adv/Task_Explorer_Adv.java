package Model_pk.Behaviour.Basic.Task_Adv;

import Model_pk.Behaviour.Basic.Task.Task_Basic.Task_Explorer_Basic;
import Model_pk.Objects.Worker;

/**
 * Created by christiaan on 29/05/18.
 */
public class Task_Explorer_Adv extends Task_Explorer_Basic
{
    private boolean halve = false;

    public Task_Explorer_Adv()
    {
        super();
        wander_limit = (int) (45 - 0.5*step_distance);
     //   System.out.println("wander limit:  " + wander_limit);
    }

    @Override
    public void select_target(Worker worker)
    {
        halve = false;
        super.select_target(worker);
    }


    @Override
    protected boolean is_at_target_halve(Worker worker)
    {
        if((dist_walked >= step_distance/2) && !halve)
        {
            halve = true;
            return true;
        }
        return  false;
    }
}
