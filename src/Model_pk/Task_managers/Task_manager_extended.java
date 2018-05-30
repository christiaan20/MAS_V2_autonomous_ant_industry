package Model_pk.Task_managers;

import Model_pk.Objects.Enterables.Base;
import Model_pk.Enums.Resource_Type_Enum;
import Model_pk.Objects.Worker;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Gebruiker on 29/05/2018.
 */
public class Task_manager_extended extends  Abstr_Task_manager{


    private ArrayList<Worker_representation> workers_list;


    public Task_manager_extended()
    {
        this.workers_list = new ArrayList<>();
    }

    public Task_manager_extended(Base base)
    {
        this();
        super.base = base;
    }

    @Override
    public void update_task_of( Worker worker){
        remember_task_of(worker);

        HashMap<Resource_Type_Enum, Integer> diff_in_distribution_of_worker = get_diff_in_distribution_of_worker();
        HashMap<Resource_Type_Enum, Integer> distribution_of_workers =  get_distribution_of_workers();

        Resource_Type_Enum worker_type = worker.getResource_type();
        if( worker_type == null ){
            Resource_Type_Enum new_type = get_max_diff_type();
            worker.setResource_type(new_type);
            return;
        }

        int current_amount = distribution_of_workers.get(worker_type);
        if (current_amount <= 1)
            return;

        int diff = diff_in_distribution_of_worker.get(worker_type);
        if(diff < 0 ){
            Resource_Type_Enum new_type = get_max_diff_type();
            worker.setResource_type(new_type);
        }

        worker.setTravel_time(0);
        remember_task_of(worker);
    }

    private Resource_Type_Enum get_max_diff_type(){

        HashMap<Resource_Type_Enum, Integer> diff_in_distribution_of_worker = get_diff_in_distribution_of_worker();
        int max_diff = 0;
        Resource_Type_Enum max_type = null;
        for(Resource_Type_Enum type: Resource_Type_Enum.values()){
            int diff = diff_in_distribution_of_worker.get(type);
            if( diff >= max_diff)
                max_type = type;
        }
        return max_type;
    }


    private HashMap<Resource_Type_Enum, Integer> get_diff_in_distribution_of_worker(){

        HashMap<Resource_Type_Enum, Integer> distribution_of_workers =  get_distribution_of_workers();
        HashMap<Resource_Type_Enum, Integer> new_distribution_of_workers = get_new_distribution_of_worker();
        HashMap<Resource_Type_Enum, Integer> diff_in_distribution_of_worker = new HashMap<>();

        for(Resource_Type_Enum type: Resource_Type_Enum.values()){
            int diff = new_distribution_of_workers.get(type) - distribution_of_workers.get(type);
            diff_in_distribution_of_worker.put(type, diff);
        }
        return diff_in_distribution_of_worker;
    }


    private HashMap<Resource_Type_Enum, Integer> get_new_distribution_of_worker(){

        HashMap<Resource_Type_Enum, Integer> avg_travel_time_to_resource = get_avg_travel_time_to_resource();
        HashMap<Resource_Type_Enum, Integer> resources_to_obtain = base.resource_to_obtain();
        HashMap<Resource_Type_Enum, Integer> distribution_ratio = new HashMap<>();

        int resources_needed;
        int avg_travel_time;
        int distribution;
        int total_distribution = 0;

        for(Resource_Type_Enum type: Resource_Type_Enum.values()){

            avg_travel_time = avg_travel_time_to_resource.get(type);
            resources_needed = resources_to_obtain.get(type);
            if(resources_needed < 0 )
                resources_needed = 0;

            distribution = avg_travel_time * resources_needed;
            distribution_ratio.put(type, distribution);

            total_distribution += distribution;

        }

        HashMap<Resource_Type_Enum, Integer> distribution_of_workers =  get_distribution_of_workers();
        double distr_ratio;
        int worker_amount;
        int new_worker_amount;

        for(Resource_Type_Enum type: Resource_Type_Enum.values()){

            distribution = distribution_ratio.get(type);
            worker_amount = distribution_of_workers.get(type);

            if( total_distribution == 0){
                new_worker_amount = 0;
            }
            else{
                distr_ratio = distribution / total_distribution;
                new_worker_amount = (int) Math.round(distr_ratio * worker_amount);
            }
            distribution_ratio.replace(type, new_worker_amount);
        }

        return distribution_ratio;

    }

    private void remember_task_of(Worker worker){

        Worker_representation worker_repr = convert_to_representation(worker);
        if (! workers_list.contains(worker_repr))
            workers_list.add(worker_repr);

        else{
            workers_list.remove(worker_repr);
            workers_list.add(worker_repr);
        }

    }

    private Worker_representation convert_to_representation(Worker worker){

        return new Worker_representation(worker.getID(), worker.getTask(),worker.getResource_type(), worker.getTravel_time());

    }

    private HashMap<Resource_Type_Enum, Integer> get_avg_travel_time_to_resource() {

        HashMap<Resource_Type_Enum, Integer> distribution_of_workers = get_distribution_of_workers();
        HashMap<Resource_Type_Enum, Integer> total_travel_time_to_resource = get_total_travel_time_to_resource();
        HashMap<Resource_Type_Enum, Integer> avg_travel_time_to_resource = new HashMap<>();
        int avg_travel_time;

        for(Resource_Type_Enum type: Resource_Type_Enum.values()){

            Integer total_travel_time = total_travel_time_to_resource.get(type);
            Integer worker_amount = distribution_of_workers.get(type);
            if(total_travel_time == null || worker_amount == null){
                avg_travel_time_to_resource.put(type, 0);
            }
            else if( worker_amount == 0 ){
                avg_travel_time_to_resource.put(type, 0);
            }
            else {
                avg_travel_time = total_travel_time / worker_amount;
                avg_travel_time_to_resource.put(type, avg_travel_time);
            }
        }
        return avg_travel_time_to_resource;
    }

    private HashMap<Resource_Type_Enum, Integer> get_total_travel_time_to_resource() {

        HashMap<Resource_Type_Enum, Integer> total_travel_time_to_resource = new HashMap<>();

        for (Worker_representation worker : workers_list) {

            Resource_Type_Enum type = worker.getType();
            int travel_time = worker.getTravel_time();
            Integer total_travel_time = total_travel_time_to_resource.get(type);

            if(total_travel_time == null) {
                total_travel_time_to_resource.put(type, travel_time);
            }
            else {
                total_travel_time_to_resource.put(type, total_travel_time + travel_time);
            }

        }

        return total_travel_time_to_resource;

    }

    private  HashMap<Resource_Type_Enum, Integer> get_distribution_of_workers() {

        HashMap<Resource_Type_Enum, Integer> distribution_of_workers = new HashMap<>();

        for(Resource_Type_Enum type: Resource_Type_Enum.values()){
            distribution_of_workers.put(type, 0);
        }

        for (Worker_representation worker : workers_list) {

            Resource_Type_Enum type = worker.getType();
            if (type == null){
                continue;
            }
            int value = distribution_of_workers.get(type);
            distribution_of_workers.replace(type, value + 1);

        }

        return distribution_of_workers;
    }



}
