package Model_pk;

import Model_pk.Behaviour.Abstr_Task;
import Model_pk.Behaviour.Task_Enum;
import Model_pk.Enterables.Base;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by Gebruiker on 23/05/2018.
 */
public class Task_Manager {

    private Base base;
    private int base_rate;
    private Random  rand;

    public Task_Manager(Base base) {
        this.base = base;
        base_rate = 50;
        rand = new Random();
    }

    public void update_task_of( Worker worker){

        //if(Task_Enum.miner == worker.getTask().getTask()){
            Resource_Type new_type = get_new_resource_type();
            worker.setResource_type(new_type);

        //}

        //Resource_Type type = worker.getResource_type();
        //Abstr_Task task = worker.getTask();
        //worker.setTask(task);
    }

    private Resource_Type get_new_resource_type(){

        int  random = rand.nextInt(100) ;
        int rate;

        HashMap<Resource_Type, Integer> resource_rates = get_resource_acumm_percentage_rates();

        for(Resource_Type type: Resource_Type.values())
        {
            rate = resource_rates.get(type);

            if( random <= rate)
                return type;

        }

        return null;

    }

    public HashMap<Resource_Type, Integer> get_resource_acumm_percentage_rates(){

        HashMap<Resource_Type, Integer> resources_to_obtain = base.resource_to_obtain();
        HashMap<Resource_Type, Integer> resource_rates = new HashMap<>();
        int total_amount = 0;
        int new_amount;

        for(Resource_Type type: Resource_Type.values())
        {
            new_amount = resources_to_obtain.get(type) + base_rate;

            if( new_amount < 0 )
                new_amount = 0;

            total_amount += new_amount;

            resource_rates.put(type, new_amount);
        }

        int accum = 0;

        for(Resource_Type type: Resource_Type.values())
        {
            new_amount = resource_rates.get(type) * 100 / total_amount;
            new_amount += accum;
            accum = new_amount;
            resource_rates.replace(type, new_amount);

        }

        return resource_rates;

    }


}
