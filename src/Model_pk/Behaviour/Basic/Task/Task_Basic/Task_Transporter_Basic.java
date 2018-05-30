package Model_pk.Behaviour.Basic.Task.Task_Basic;

import Model_pk.Objects.Abstr_Object;
import Model_pk.Behaviour.Abstr_Task;
import Model_pk.Behaviour.Task_Enum;
import Model_pk.Objects.Enterables.Base;
import Model_pk.Objects.Worker;
import Model_pk.Enums.Worker_State_Enum;

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
    public boolean is_obj_relevant_to_task(Worker worker, Abstr_Object obj) {
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
    public boolean on_reached(Worker worker, Abstr_Object obj)
    {
        return false;
    }

    @Override
    public boolean at_base(Worker worker, Base base) {
        return false;
    }

    @Override
    public boolean out_of_base(Worker worker, Abstr_Object base) {
        return false;
    }
}
