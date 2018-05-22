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
    private boolean lost_phero = false; //becomes true if the miner cannot find a phero
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
            setDrop_enabled(true);
            set_lost_phero_false();
            go_to_phero(worker,obj);
            worker.setCurr_target_object(obj);
            worker.setState(Worker_State_Enum.to_phero);
            return;
        }
        obj = worker.closest_owned_phero_of_type(worker, Task_Enum.explorer,null);
        if(obj != null)
        {
            setDrop_enabled(true);
            set_lost_phero_false();
            go_to_phero(worker,obj);
            worker.setCurr_target_object(obj);
            worker.setState(Worker_State_Enum.to_phero);
            return;
        }
        obj = worker.closest_phero_of_type(getTask(),worker.getResource_type());
        if(obj != null)
        {
            setDrop_enabled(true);
            set_lost_phero_false();
            go_to_phero(worker,obj);
            worker.setCurr_target_object(obj);
            worker.setState(Worker_State_Enum.to_phero);
            return;
        }
        obj = worker.closest_phero_of_type(Task_Enum.explorer,null);
        if(obj != null)
        {
            setDrop_enabled(true);
            set_lost_phero_false();
            go_to_phero(worker,obj);
            worker.setCurr_target_object(obj);
            worker.setState(Worker_State_Enum.to_phero);
            return;
        }


        worker.setCurr_target_object(null);
        //wanderWithin(worker);
        //worker.setState(Worker_State_Enum.Wandering);

        if(lost_phero)
        {
            setDrop_enabled(false);
            wanderWithin(worker);
            worker.setState(Worker_State_Enum.Wandering);
        }
        else
        {
            set_lost_phero_true();
            walk_straight_for(worker,1);
        }


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


        return false;
    }

    @Override
    public boolean at_base(Worker worker, Base base) {

        if(worker.get_total_amount_of_load() > 0)
        {
            return base.enter(worker);
        }
        return false;
    }

    private void set_lost_phero_true()
    {
        lost_phero = true;

    }

    private void set_lost_phero_false()
    {
        lost_phero = false;
        setDrop_enabled(true);
    }

}
