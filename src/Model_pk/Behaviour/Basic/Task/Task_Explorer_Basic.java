package Model_pk.Behaviour.Basic.Task;

import Model_pk.Behaviour.Abstr_Task;
import Model_pk.Behaviour.Task_Enum;
import Model_pk.Enterables.Resource_pool;
import Model_pk.Object;
import Model_pk.Worker;

/**
 * Created by christiaan on 16/05/18.
 */
public class Task_Explorer_Basic extends Abstr_Task{

    public Task_Explorer_Basic()
    {
        super.task = Task_Enum.explorer;
    }


    @Override
    public void move(Worker worker)
    {
        //super.wanderWithin(worker);
        super.wanderWithin(worker);
    }


    @Override
    public boolean on_reached(Worker worker, Object obj)
    {
        if(obj instanceof Resource_pool)
        {
            if(!worker.is_full())
            {
                Resource_pool pool = (Resource_pool) obj;
                if(pool.enter(worker))
                {
                    worker.setResource_type(pool.getType());

                    worker.setTask(model.getBehaviour().getTask_miner());

                    return true;
                }
            }

        }

        return false;
    }

}
