package Model_pk.Behaviour.Basic.Task;

import Model_pk.Behaviour.Abstr_Task;
import Model_pk.Behaviour.Task_Enum;
import Model_pk.Enterables.Base;
import Model_pk.Enterables.Resource_pool;
import Model_pk.Object;
import Model_pk.Pheromone;
import Model_pk.Worker;
import Model_pk.Worker_State_Enum;

/**
 * Created by christiaan on 16/05/18.
 */
public class Task_Explorer_Basic extends Abstr_Task{
    public int wander_limit = 15;
    public int wander_limit_count = 0;

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
        if(reached_wander_limit())
        {
            return_to_base(worker);
        }
        else
        {
            worker.setState(Worker_State_Enum.Wandering);
            wanderWithin(worker);

        }

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
                        worker.getTask().setFound_resource(true);

                        return true;
                    }
            }

        }

        return false;
    }

    @Override
    public boolean at_base(Worker worker, Base base)
    {
        return false;
    }

    public boolean reached_wander_limit()
    {
        wander_limit_count++;
        return(wander_limit_count >= wander_limit);


    }

    public void return_to_base(Worker worker)
    {
        Pheromone phero =  (Pheromone) worker.pop_last_visited_phero();
        worker.setCurr_target_object(null);

        if(phero == null)
        {
            setDrop_enabled(true);
            wander_limit_count =0;
            worker.setState(Worker_State_Enum.Wandering);
        }
        else
        {
            setDrop_enabled(false);

            go_to_phero(worker,phero);
            worker.setState(Worker_State_Enum.returning);
        }
    }

}
