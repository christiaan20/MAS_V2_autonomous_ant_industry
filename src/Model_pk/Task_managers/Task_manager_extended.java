package Model_pk.Task_managers;

import Model_pk.Model;
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
    private HashMap<Resource_Type_Enum, Avg_travel_time> avg_travel_time_per_resource;
    private Model model;
    private int worker_broken_threshold;

    public Task_manager_extended() {
        this.model = Model.getInstance();
        this.workers_list = new ArrayList<>();
        this.avg_travel_time_per_resource = new HashMap<>();
        init_avg_travel_time_per_resource();
        worker_broken_threshold = 4000;

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
            Resource_Type_Enum new_type = get_max_diff_type(diff_in_distribution_of_worker);
            worker.setResource_type(new_type);
            return;
        }

        int current_amount = distribution_of_workers.get(worker_type);
        if (current_amount <= 1)
            return;

        int diff = diff_in_distribution_of_worker.get(worker_type);
        if(diff < 0 ){
            Resource_Type_Enum new_type = get_max_diff_type(diff_in_distribution_of_worker);
            worker.setResource_type(new_type);
        }

        worker.setTravel_time(0);
        remember_task_of(worker);

        broken_worker_manager();

        worker.setTravel_time(model.getTickcount());
    }

    public void broken_worker_manager(){

        int current_time = model.getTickcount();
        ArrayList<Worker_representation> broken_workers = new ArrayList<>();

        for( Worker_representation worker: workers_list){
            if( current_time - worker.getLast_seen() > worker_broken_threshold)
                broken_workers.add(worker);
        }
        for( Worker_representation worker: broken_workers){
            workers_list.remove(worker);
        }

        int amount_of_new_workers = broken_workers.size();
        if( amount_of_new_workers > 0)
            model.add_worker(amount_of_new_workers);

    }

    private void init_avg_travel_time_per_resource(){

        for(Resource_Type_Enum  type: Resource_Type_Enum.values()){
            Avg_travel_time avg_travel_time = new Avg_travel_time();
            avg_travel_time_per_resource.put(type, avg_travel_time);
        }
    }

    private Resource_Type_Enum get_max_diff_type(HashMap<Resource_Type_Enum, Integer> diff_in_distribution_of_worker){

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


    private int get_max_travel_time(HashMap<Resource_Type_Enum, Avg_travel_time> avg_travel_time_to_resource){

        int max_travel_time = 0;
        for(Resource_Type_Enum type: Resource_Type_Enum.values()){

            int travel_time = avg_travel_time_to_resource.get(type).get_avg_travel_time();
            if (max_travel_time < travel_time)
                max_travel_time = travel_time;
        }
        return max_travel_time;
    }

    public HashMap<Resource_Type_Enum, Integer> get_measure_of_worker(){

        HashMap<Resource_Type_Enum, Avg_travel_time> avg_travel_time_to_resource = get_avg_travel_time_to_resource();
        HashMap<Resource_Type_Enum, Integer> resources_to_obtain = base.resource_to_obtain();
        HashMap<Resource_Type_Enum, Integer> distribution_ratio = new HashMap<>();

        int resources_needed;
        Avg_travel_time avg_travel_time_object;
        int distribution;
        int total_distribution = 0;

        for(Resource_Type_Enum type: Resource_Type_Enum.values()){

            avg_travel_time_object = avg_travel_time_to_resource.get(type);
            resources_needed = resources_to_obtain.get(type);
            if(resources_needed < 0 )
                resources_needed = 0;
            int travel_time = avg_travel_time_object.get_avg_travel_time();
            if ( travel_time == 0){
                travel_time = get_max_travel_time(avg_travel_time_to_resource);
                travel_time += travel_time / 2;
            }
            distribution = travel_time * resources_needed;
            distribution_ratio.put(type, distribution);

            total_distribution += distribution;

        }
        return distribution_ratio;

    }

    private int get_sum_of_map(HashMap<Resource_Type_Enum, Integer> map){

        int sum = 0;
        for(Resource_Type_Enum type: Resource_Type_Enum.values()){
            sum = sum + map.get(type);
        }
        return sum;
    }

    public HashMap<Resource_Type_Enum, Integer> get_new_distribution_of_worker(){

        HashMap<Resource_Type_Enum, Integer> measure_of_worker = get_measure_of_worker();
        HashMap<Resource_Type_Enum, Integer> new_distribution_of_worker = new HashMap<>();
        int total_measure = get_sum_of_map(measure_of_worker);

        double distr_ratio;
        int new_worker_amount;
        int measure;
        double total_worker_amount = get_worker_amount();

        for(Resource_Type_Enum type: Resource_Type_Enum.values()){

            measure = measure_of_worker.get(type);

            if( total_measure == 0){
                new_worker_amount = 0;
            }
            else{
                distr_ratio = ((double) measure )/ total_measure;
                //new_worker_amount = (int) (distr_ratio * 100);
                new_worker_amount = (int) Math.round(distr_ratio * total_worker_amount);
            }
            new_distribution_of_worker.put(type, new_worker_amount);
        }

        //measure_of_worker.replace(Resource_Type_Enum.Uranium, total_measure);
        return new_distribution_of_worker;

    }

    public double get_worker_amount(){

        return (double) workers_list.size();

    }

    private void remember_task_of(Worker worker){

        Worker_representation worker_repr = convert_to_representation(worker);
        if (! workers_list.contains(worker_repr))
            workers_list.add(worker_repr);

        else{
            workers_list.remove(worker_repr);
            workers_list.add(worker_repr);
        }

        update_avg_travel_time_to_resource(worker_repr);

    }

    private Worker_representation convert_to_representation(Worker worker){

        return new Worker_representation(worker.getID(), worker.getTask(),worker.getResource_type(), worker.getTravel_time(), worker.getLast_seen_at_base());

    }

    private void update_avg_travel_time_to_resource(Worker_representation worker){

        Resource_Type_Enum type = worker.getType();
        if( type == null)
            return;

        Avg_travel_time avg_travel = avg_travel_time_per_resource.get(type);
        if ( avg_travel == null ){
            avg_travel = new Avg_travel_time(worker.getTravel_time());
            avg_travel_time_per_resource.put(type, avg_travel);
        }
        else{
            avg_travel.update_travel_time(worker.getTravel_time());
            avg_travel_time_per_resource.replace(type, avg_travel);
        }

    }

    public HashMap<Resource_Type_Enum, Avg_travel_time> get_avg_travel_time_to_resource() {
        return avg_travel_time_per_resource;
    }

    public void set_avg_travel_time_to_resource(HashMap<Resource_Type_Enum, Avg_travel_time> avg_travel_time_per_resource) {
        this.avg_travel_time_per_resource = avg_travel_time_per_resource;
    }

    public HashMap<Resource_Type_Enum, Integer> get_distribution_of_workers() {

        HashMap<Resource_Type_Enum, Integer> distribution_of_workers = new HashMap<>();

        for(Resource_Type_Enum type: Resource_Type_Enum.values()){
            distribution_of_workers.put(type, 0);
        }
        distribution_of_workers.put(null, 0);

        for (Worker_representation worker : workers_list) {

            Resource_Type_Enum type = worker.getType();
//            if (type == null){
//                continue;
//            }
            int value = distribution_of_workers.get(type);
            distribution_of_workers.replace(type, value + 1);

        }

        return distribution_of_workers;
    }


//    public HashMap<Resource_Type, Integer> get_avg_travel_time_to_resource() {
//
//        HashMap<Resource_Type, Integer> distribution_of_workers = get_distribution_of_workers();
//        HashMap<Resource_Type, Integer> total_travel_time_to_resource = get_total_travel_time_to_resource();
//        HashMap<Resource_Type, Integer> avg_travel_time_to_resource = new HashMap<>();
//        int Avg_travel_time;
//
//        for(Resource_Type type: Resource_Type.values()){
//
//            Integer total_travel_time = total_travel_time_to_resource.get(type);
//            Integer worker_amount = distribution_of_workers.get(type);
//            if(total_travel_time == null || worker_amount == null){
//                avg_travel_time_to_resource.put(type, 0);
//            }
//            else if( worker_amount == 0 ){
//                avg_travel_time_to_resource.put(type, total_travel_time);
//            }
//            else {
//                Avg_travel_time = total_travel_time / worker_amount;
//                avg_travel_time_to_resource.put(type, Avg_travel_time);
//            }
//        }
//        return avg_travel_time_to_resource;
//    }
//
//    private HashMap<Resource_Type, Integer> get_total_travel_time_to_resource() {
//
//        HashMap<Resource_Type, Integer> total_travel_time_to_resource = new HashMap<>();
//
//        for (Worker_representation worker : workers_list) {
//
//            Resource_Type type = worker.getType();
//            int travel_time = worker.getTravel_time();
//            Integer total_travel_time = total_travel_time_to_resource.get(type);
//
//            if(total_travel_time == null) {
//                total_travel_time_to_resource.put(type, travel_time);
//            }
//            else {
//                total_travel_time_to_resource.put(type, total_travel_time + travel_time);
//            }
//
//        }
//
//        return total_travel_time_to_resource;
//
//    }



}
