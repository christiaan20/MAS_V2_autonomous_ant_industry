package Model_pk.Behaviour.Basic.Task;

import Model_pk.Behaviour.Abstr_Task;
import Model_pk.Behaviour.Task_Enum;
import Model_pk.Enterables.Base;
import Model_pk.Enterables.Resource_pool;
import Model_pk.Object;
import Model_pk.Worker;

/**
 * Created by christiaan on 16/05/18.
 */
public class Task_Miner_Basic extends Abstr_Task
{
    public Task_Miner_Basic()
    {
        super.task = Task_Enum.miner;
    }

    @Override
    public void move(Worker worker)
    {

        wanderWithin(worker);
    }

    @Override
    public boolean on_reached(Worker worker, Object obj)
    {

        if(obj instanceof Resource_pool)
        {
            if(!worker.is_full())
            {
                Resource_pool pool = (Resource_pool) obj;
                if(pool.getType() == worker.getResource_type())
                {
                    return (pool.enter(worker)) ;
                }
            }

        }
        else if(obj instanceof Base)
        {
            Base base = (Base) obj;
            if(worker.get_total_amount_of_load() > 0)
            {
                return base.enter(worker);
            }
        }

        return false;
    }
}
