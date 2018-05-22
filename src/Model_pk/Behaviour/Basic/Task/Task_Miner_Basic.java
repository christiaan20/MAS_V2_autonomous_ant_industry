package Model_pk.Behaviour.Basic.Task;

import Model_pk.*;
import Model_pk.Behaviour.Abstr_Task;
import Model_pk.Behaviour.Task_Enum;
import Model_pk.Enterables.Base;
import Model_pk.Enterables.Resource_pool;
import Model_pk.Object;

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
    public boolean is_obj_relevant_to_task(Worker worker, Object obj) {
        if(obj instanceof Pheromone)
        {
            Pheromone phero = (Pheromone) obj;

            if(phero.getTask() == Task_Enum.explorer)
            {
                return true;
            }
            else if(phero.getTask() == getTask() && phero.getType() == worker.getResource_type() )
            {
                return true;
            }
        }

        return false ;
    }

    @Override
    public void select_target(Worker worker)
    {

        Object obj =  worker.closest_owned_phero_of_type(worker,getTask(),worker.getResource_type());
        if(obj != null)
        {
            go_to_phero(worker,obj);
            worker.setState(Worker_State_Enum.to_phero);
            return;
        }
        obj = worker.closest_owned_phero_of_type(worker, Task_Enum.explorer,null);
        if(obj != null)
        {
            go_to_phero(worker,obj);
            worker.setState(Worker_State_Enum.to_phero);
            return;
        }
        obj = worker.closest_phero_of_type(getTask(),worker.getResource_type());
        if(obj != null)
        {
            go_to_phero(worker,obj);
            worker.setState(Worker_State_Enum.to_phero);
            return;
        }
        obj = worker.closest_phero_of_type(Task_Enum.explorer,null);
        if(obj != null)
        {
            go_to_phero(worker,obj);
            worker.setState(Worker_State_Enum.to_phero);
            return;
        }

        worker.setCurr_target_object(null);
        wanderWithin(worker);
        worker.setState(Worker_State_Enum.Wandering);

    }

    @Override
    public void move(Worker worker)
    {

        move_worker(worker);
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
