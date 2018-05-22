package Model_pk.Behaviour.Basic.Task;

import Model_pk.Behaviour.Abstr_Task;
import Model_pk.Behaviour.Task_Enum;
import Model_pk.Enterables.Resource_pool;
import Model_pk.Object;
import Model_pk.Pheromone;
import Model_pk.Worker;
import Model_pk.Worker_State_Enum;

/**
 * Created by christiaan on 16/05/18.
 */
public class Task_Explorer_Basic extends Abstr_Task{

    public Task_Explorer_Basic()
    {
        super.task = Task_Enum.explorer;
    }

    @Override
    public boolean is_obj_relevant_to_task(Worker worker, Object obj)
    {
        if(obj instanceof Pheromone)
        {
            return (((Pheromone) obj).isTask(task));
        }
        return false;
    }

    @Override
    public void select_target(Worker worker)
    {
        wanderWithin(worker);
        worker.setState(Worker_State_Enum.Wandering);
    }


    @Override
    public void move(Worker worker)
    {
        //super.wanderWithin(worker);
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

                if((pool.getType() == worker.getResource_type()) || (worker.getResource_type() == null) )
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