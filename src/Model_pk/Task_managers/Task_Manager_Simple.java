package Model_pk.Task_managers;

import Model_pk.Model;
import Model_pk.Objects.Enterables.Base;
import Model_pk.Enums.Resource_Type_Enum;
import Model_pk.Objects.Worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by Gebruiker on 23/05/2018.
 */
public class Task_Manager_Simple extends Abstr_Task_manager {

    private ArrayList<Worker_representation> workers_list;
    private int worker_broken_threshold;
    private Model model;
    private int base_rate;
    private Random  rand;


    public Task_Manager_Simple()
    {
        base_rate = 10;
        rand = new Random();

        model = Model.getInstance();
        this.workers_list = new ArrayList<>();
        worker_broken_threshold = 4000;
    }

    public Task_Manager_Simple(Base base) {
        this();
        super.base = base;
    }

    @Override
    public void update_task_of( Worker worker){

        //if(Task_Enum.miner == worker.getTask().getTask()){
            Resource_Type_Enum new_type = get_new_resource_type();
            worker.setResource_type(new_type);

            remember_task_of(worker);
            broken_worker_manager();

            //worker.setResource_type(Resource_Type_Enum.Coal); // for testing

        //}

        //Resource_Type_Enum type = worker.getResource_type();
        //Abstr_Task task = worker.getTask();
        //worker.setTask(task);
    }

    private Resource_Type_Enum get_new_resource_type(){

        int  random = rand.nextInt(100) ;
        int rate;

        HashMap<Resource_Type_Enum, Integer> resource_rates = get_resource_acumm_percentage_rates();

        for(Resource_Type_Enum type: Resource_Type_Enum.values())
        {
            rate = resource_rates.get(type);

            if( random <= rate)
                return type;

        }

        return null;

    }

    public void broken_worker_manager(){

        ArrayList<Worker_representation> broken_workers = new ArrayList<>();

        for( Worker_representation worker: workers_list){
            if( worker.get_ticks_since_last_seen_at_base() > worker_broken_threshold)
                broken_workers.add(worker);
        }
        for( Worker_representation worker: broken_workers){
            workers_list.remove(worker);
        }

        for( Worker_representation worker: broken_workers){
            Worker new_worker = model.add_worker();
            new_worker.setLast_seen_at_base(model.getTickcount());
            update_task_of(new_worker);
        }

    }

    public HashMap<Resource_Type_Enum, Integer> get_resource_acumm_percentage_rates(){

        HashMap<Resource_Type_Enum, Integer> resources_to_obtain = base.resource_to_obtain();
        if (resources_to_obtain == null)
            return null;
        HashMap<Resource_Type_Enum, Integer> resource_rates = new HashMap<>();
        int total_amount = 0;
        int new_amount;

        for(Resource_Type_Enum type: Resource_Type_Enum.values())
        {
            new_amount = resources_to_obtain.get(type) + base_rate;

            if( new_amount < 0 )
                new_amount = 0;

            total_amount += new_amount;

            resource_rates.put(type, new_amount);
        }

        int accum = 0;

        for(Resource_Type_Enum type: Resource_Type_Enum.values())
        {
            if(total_amount == 0)
                new_amount = 0;
            else
                new_amount = resource_rates.get(type) * 100 / total_amount;
            new_amount += accum;
            accum = new_amount;
            resource_rates.replace(type, new_amount);

        }

        return resource_rates;

    }

    private void remember_task_of(Worker worker){

        Worker_representation worker_repr = convert_to_representation(worker);
        if (! workers_list.contains(worker_repr))
            workers_list.add(worker_repr);

        else{
            workers_list.remove(worker_repr);
            workers_list.add(worker_repr);
        }

        //update_avg_travel_time_to_resource(worker_repr);

    }


}
