package Model_pk.Behaviour.Basic.Task_Adv;

import Model_pk.Behaviour.Basic.Task.Task_Basic.Task_Miner_Basic;
import Model_pk.Behaviour.Task_Enum;
import Model_pk.Enterables.Resource_pool;
import Model_pk.Object;
import Model_pk.Pheromone;
import Model_pk.Worker;
import Model_pk.Worker_State_Enum;

/**
 * Created by christiaan on 28/05/18.
 */
public class Task_Miner_Adv extends Task_Miner_Basic
{
    private boolean halve = false;
    private boolean return_from_resource;

    @Override
    public boolean is_obj_relevant_to_task(Worker worker, Object obj)
    {
        if(obj instanceof Pheromone)
        {
            Pheromone phero = (Pheromone) obj;

            if(phero.getTask() == Task_Enum.explorer && isFound_resource())
            {
                return true;
            }
            else if(phero.getTask() == getTask() && phero.getType() == worker.getResource_type() && !isFound_resource() )
            {
                return true;
            }
        }

        return false ;
    }


    @Override
    public void select_target(Worker worker)
    {
        //when the unit is returning from having just found the resource it will only follow his own explorer path
        if(found_resource) //only when the resource_pool has been found recently will the miner follow his own explorer path
        {
            Object obj = worker.closest_owned_phero_of_type(worker, Task_Enum.explorer,null);
            if(obj != null)
            {
                setDrop_enabled(true);
                set_lost_phero_false();
                go_to_phero(worker,obj);
                worker.setCurr_target_object(obj);
                worker.setState(Worker_State_Enum.to_phero);
                return;
            }


        }
        else
        {
            halve = false;
            if(return_from_resource)
                setDrop_enabled(true);
            else
                setDrop_enabled(false);

            if(select_target_from_vectors(worker))
            {
                worker.setFound_new_target(true);
                // setFound_resource(true);
                set_lost_phero_false();
                worker.setState(Worker_State_Enum.to_phero);
                return;
            }

        }
        worker.setFound_new_target(false);

        if(isLost_phero())
        {
            setDrop_enabled(false);
            wanderWithin(worker);
            worker.setState(Worker_State_Enum.Wandering);
            worker.clear_visited_objects();
        }
        else
        {
            set_lost_phero_true();
            walk_straight_for(worker,1);
        }


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

    @Override
    public boolean isHalve() {
        return halve;
    }

    @Override
    public void setHalve(boolean halve) {
        this.halve = halve;
    }

    public boolean isReturn_from_resource() {
        return return_from_resource;
    }

    public void setReturn_from_resource(boolean return_from_resource) {
        this.return_from_resource = return_from_resource;
    }
}
