package Model_pk.Enterables;


import Model_pk.*;
import Model_pk.Behaviour.Abstr_Task;
import Model_pk.Behaviour.Task_Enum;
import View.Object_visuals.Base_visual;
import View.View;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by christiaan on 10/05/18.
 */
public class Base extends Enterable_object {
    private Order order;
    private HashMap<Resource_Type, Integer> obtained_resources;
    private Task_Manager task_manager;
    private double chance_of_general_explorer = 0.75;


    public Base(int x, int y, int size, int time, Order order) {
        super( x, y, size, time);
        this.order = order;
        this.setVisual(new Base_visual( x, y, size,this));

        this.obtained_resources = new HashMap<>();
        init_obtained_resources();

        this.task_manager = new Task_Manager(this);

    }

    public void init_obtained_resources(){

        for(Resource_Type type: Resource_Type.values())
        {
            this.obtained_resources.put(type, 0);
        }
    }

    @Override
    public void tick() {

    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public boolean action(Worker worker)
    {

        Model model = Model.getInstance();

            drop_resources(worker);
            task_manager.update_task_of(worker);
            worker.setTask(model.getBehaviour().getTask_miner());

            Abstr_Task worker_task = worker.getTask();
            //if(worker_task.getTask() == Task_Enum.miner)
            //{
            int original_detect_dist = worker_task.getPhero_detect_dist();
            //hack to increase the detection range of the worker
            worker_task.setPhero_detect_dist((int)(size*2.5));
            worker.clear_detected_objects();
            Model.getInstance().find_objects(worker);
            worker_task.select_target(worker);
            worker_task.setPhero_detect_dist(original_detect_dist);

            if(worker.getCurr_target_object() == null)
            {
                double random = Model.getInstance().getRandom().nextDouble();
                worker.setTask(model.getBehaviour().getTask_explorer());
                if(random > chance_of_general_explorer)
                {
                    worker.setResource_type(null);
                }
            }



       // }

        return true;

    }

    @Override
    public void exit(Worker worker) {

    }


    public HashMap<Resource_Type, Integer> resource_to_obtain(){

        Tester tester = Model.getInstance().getTest_setting();
        HashMap<Resource_Type, Integer>  goal = tester.get_current_goal();
        HashMap<Resource_Type, Integer> resources_to_obtain = new HashMap<>();
        int new_amount;

        for(Resource_Type type: Resource_Type.values())
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
            Resource_Type type = resource.getType();
            int previous_amount = this.obtained_resources.get(type);
            int new_amount = previous_amount + resource.getAmount();

            this.obtained_resources.replace(type, new_amount);
        }

        worker.clear_load();

    }


    public HashMap<Resource_Type, Integer> get_obtained_resources_resources() {
        return this.obtained_resources;
    }

    public void set_obtained_resources(HashMap<Resource_Type, Integer> obtained_resources) {
        this.obtained_resources = obtained_resources;
    }

    public Task_Manager getTask_manager() {
        return task_manager;
    }

    public void setTask_manager(Task_Manager task_manager) {
        this.task_manager = task_manager;
    }
}
