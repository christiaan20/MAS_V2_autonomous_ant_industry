package Model_pk.Behaviour.Basic.Task;

import Model_pk.Behaviour.Abstr_Task;
import Model_pk.Behaviour.Task_Enum;
import Model_pk.Enterables.Base;
import Model_pk.Object;
import Model_pk.Worker;
import Model_pk.Worker_State_Enum;

/**
 * Created by christiaan on 16/05/18.
 */
public class Task_Transporter_Basic extends Abstr_Task
{

    public Task_Transporter_Basic()
    {
        super.task = Task_Enum.transporter;
    }

    @Override
    public boolean is_obj_relevant_to_task(Worker worker, Object obj) {
        return false;
    }

    @Override
    public void select_target(Worker worker)
    {
        wanderWithin(worker);
        worker.setState(Worker_State_Enum.Wandering);
    }

    @Override
    public void move(Worker worker) {

    }

    @Override
    public boolean on_reached(Worker worker, Object obj)
    {
        return false;
    }

    @Override
    public boolean at_base(Worker worker, Base base) {
        return false;
    }
}
