package Model_pk.Objects.Enterables;


import Model_pk.*;
import Model_pk.Behaviour.Abstr_Task;
import Model_pk.Behaviour.Task_Enum;
import Model_pk.Enums.Resource_Type_Enum;
import Model_pk.Objects.Worker;
import Model_pk.Task_managers.Abstr_Task_manager;
import Model_pk.Testing_Classes.Tester;
import View.Object_visuals.Base_visual;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The base station where the workers get created. The collected resources are brought back to this base.
 * The worker can get a different resource assigned to them at the base.
 */

public class Base extends Abstr_Enterable_object {
    private HashMap<Resource_Type_Enum, Integer> obtained_resources;
    private Abstr_Task_manager task_manager;
    private double chance_of_general_explorer = 0.75;
    private int detect_dist;
    private Model model;


    public Base(int x, int y, int size, int time,Abstr_Task_manager task_manager) {
        super( x, y, size, time);
        this.setVisual(new Base_visual( x, y, size,this));
        this.detect_dist = size*2;
        this.obtained_resources = new HashMap<>();
        init_obtained_resources();

        this.task_manager = task_manager;
        this.model = Model.getInstance();

    }

    public void init_obtained_resources(){

        for(Resource_Type_Enum type: Resource_Type_Enum.values())
        {
            this.obtained_resources.put(type, 0);
        }
    }

    @Override
    public void tick() {

    }

    @Override
    public boolean action(Worker worker)
    {

        Task_Enum old_task = worker.getTask().getTask();

        task_manager.update_task_of(worker);

        set_worker_parameters(worker);

        find_target_phero_for_miner(worker, worker.getTask());

        double random = Model.getInstance().getRandom().nextDouble();
        boolean miner_without_load = worker.get_total_amount_of_load() == 0 && old_task == Task_Enum.miner;
        if(     !worker.isFound_new_target() ||         // if worker didn't find a new target within the detected pheromones
                (random >= 0.95) ||                     // there is a random chance to become a explorer
                miner_without_load) // if the worker was a miner and came back without a load
        {
            worker.setTask(model.getBehaviour().getTask_explorer());
            worker.setResource_type(null);
        }



        drop_resources(worker);

        worker.setBreak_chance(0.01);
        worker.setLast_seen_at_base(model.getTickcount());



       // }

        return true;

    }

    private void set_worker_parameters(Worker worker) {
        worker.setTask(model.getBehaviour().getTask_miner());
        worker.getTask().setFound_resource(false);
        worker.getTask().setReturn_from_resource(false);
        worker.getTask().setOn_base(true);
    }

    private void find_target_phero_for_miner(Worker worker, Abstr_Task worker_task) {
        worker.clear_visited_objects();
        worker.clear_detected_objects();
        worker.setDetected_objects(model.find_objects(getX(),getY(),detect_dist,worker.getResource_type(),worker_task.getTask()));
        worker_task.select_target(worker);
        worker.clear_visited_objects();
    }

    @Override
    public void exit(Worker worker) {

    }


    public HashMap<Resource_Type_Enum, Integer> resource_to_obtain(){

        Tester tester = Model.getInstance().getTester();
        HashMap<Resource_Type_Enum, Integer>  goal = tester.get_current_goal();
        if (goal == null)
            return null;
        HashMap<Resource_Type_Enum, Integer> resources_to_obtain = new HashMap<>();
        int new_amount;

        for(Resource_Type_Enum type: Resource_Type_Enum.values())
        {
            new_amount =  goal.get(type) - obtained_resources.get(type);
            resources_to_obtain.put(type, new_amount);
        }

        return resources_to_obtain;

    }


    public void drop_resources(Worker worker)
    {

        ArrayList<Resource> load =  worker.getLoad();

        for(Resource resource: load) {
            Resource_Type_Enum type = resource.getType();
            int previous_amount = this.obtained_resources.get(type);
            int new_amount = previous_amount + resource.getAmount();

            this.obtained_resources.replace(type, new_amount);
        }

        worker.clear_load();

    }

    public HashMap<Resource_Type_Enum, Integer> get_obtained_resources_resources() {
        return this.obtained_resources;
    }

    public void set_obtained_resources(HashMap<Resource_Type_Enum, Integer> obtained_resources) {
        this.obtained_resources = obtained_resources;
    }

    public Abstr_Task_manager getTask_manager() {
        return task_manager;
    }

    public void setTask_manager(Abstr_Task_manager task_manager) {
        this.task_manager = task_manager;
    }

    public int getDetect_dist() {
        return detect_dist;
    }

    public void setDetect_dist(int detect_dist) {
        this.detect_dist = detect_dist;
    }
}
