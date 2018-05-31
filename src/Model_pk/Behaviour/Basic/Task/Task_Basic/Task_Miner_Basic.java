package Model_pk.Behaviour.Basic.Task.Task_Basic;

import Model_pk.Behaviour.Abstr_Task;
import Model_pk.Behaviour.Task_Enum;
import Model_pk.Enums.Resource_Type_Enum;
import Model_pk.Enums.Worker_State_Enum;
import Model_pk.Objects.Enterables.Base;
import Model_pk.Objects.Enterables.Resource_pool;
import Model_pk.Objects.Abstr_Object;
import Model_pk.Objects.Pheromone;
import Model_pk.Objects.Worker;

/**
 * Created by christiaan on 16/05/18.
 */
public class Task_Miner_Basic extends Abstr_Task
{
    protected boolean lost_phero = false;     //becomes true if the miner cannot find a phero
    protected boolean found_resource = false; //is true if the miner was previously an explorer and a resource_pool was found

    public Task_Miner_Basic()
    {
        super.task = Task_Enum.miner;
    }

    @Override
    public boolean is_obj_relevant_to_task(Worker worker, Abstr_Object obj) {
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

        Abstr_Object obj = null;
        if(found_resource) //only when the resource_pool has been found recently will the miner follow his own explorer path
        {

            //obj = worker.closest_owned_phero_of_type(worker, Task_Enum.explorer,null);
            obj = get_owned_phero_of_type(worker,Task_Enum.explorer,null);

            if(obj != null)
            {
                select_obj_as_new_target(worker, obj);
                return;
            }
        }

        //obj =  worker.closest_owned_phero_of_type(worker,getTask(),worker.getResource_type());
        obj = get_owned_phero_of_type(worker,getTask(),worker.getResource_type());
        if(obj != null)
        {
            select_obj_as_new_target(worker, obj);
            return;
        }


        //obj = worker.closest_phero_of_type(getTask(),worker.getResource_type());
        obj = get_phero_of_type(worker,getTask(),worker.getResource_type());
        if(obj != null)
        {
            select_obj_as_new_target(worker, obj);
            return;
        }
       /*
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
        */


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

    private void select_obj_as_new_target(Worker worker, Abstr_Object obj) {
        setDrop_enabled(true);
        set_lost_phero_false();
        go_to_phero(worker,obj);
        worker.setCurr_target_object(obj);
        worker.setState(Worker_State_Enum.to_phero);
    }

    @Override
    public void move(Worker worker)
    {

        move_worker(worker);
    }

    @Override
    public boolean on_reached(Worker worker, Abstr_Object obj)
    {

        if(obj instanceof Resource_pool)
        {
            if(!worker.is_full())
            {

                Resource_pool pool = (Resource_pool) obj;
                if(pool.getType() == worker.getResource_type())
                {
                    if (pool.enter(worker)){
                        worker.setTravel_time(model.getTickcount());
                        return true;
                    }
                    //return (pool.enter(worker)) ;
                    return false;
                }
            }

        }
        return false;
    }

    @Override
    public boolean at_base(Worker worker, Base base)
    {
        found_resource = false;
        if(worker.get_total_amount_of_load() > 0)
        {
            return base.enter(worker);
        }
        return false;
    }

    @Override
    public boolean out_of_base(Worker worker, Abstr_Object base)
    {

        return false;
    }


    public boolean isLost_phero() {
        return lost_phero;
    }

    public void setLost_phero(boolean lost_phero) {
        this.lost_phero = lost_phero;
    }

    protected void set_lost_phero_true()
    {
        lost_phero = true;

    }

    protected void set_lost_phero_false()
    {
        lost_phero = false;
        setDrop_enabled(true);
    }

    public boolean isFound_resource() {
        return found_resource;
    }

    public void setFound_resource(boolean found_resource) {
        this.found_resource = found_resource;
    }

    protected Abstr_Object get_owned_phero_of_type(Worker worker, Task_Enum task, Resource_Type_Enum type)
    {
        boolean incalculate_strength = model.getBehaviour().isIncalculate_strength();

        if(incalculate_strength)
            return worker.most_attractive_owned_phero_of_type(worker, task,type);
        else
            return worker.closest_owned_phero_of_type(worker, task,type);

    }

    protected Abstr_Object get_phero_of_type(Worker worker, Task_Enum task, Resource_Type_Enum type)
    {
        boolean incalculate_strength = model.getBehaviour().isIncalculate_strength();

        if(incalculate_strength)
            return worker.most_attractive_phero_of_type(task,type);
        else
            return worker.closest_phero_of_type(task,type);

    }


}
