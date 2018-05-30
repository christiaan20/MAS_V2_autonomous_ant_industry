package Model_pk.Enterables;


import Model_pk.*;
import Model_pk.Behaviour.Abstr_Task;
import Model_pk.Behaviour.Basic.Task.Task_Basic.Behaviour_Basic;
import Model_pk.Task_managers.Abstr_Task_manager;
import Model_pk.Task_managers.Task_Manager_Simple;
import Model_pk.Task_managers.Task_manager_extended;
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
    //private Task_Manager_Simple task_managerSimple;
    private Abstr_Task_manager task_manager;
    private double chance_of_general_explorer = 0.75;
    private int detect_dist;


    public Base(int x, int y, int size, int time) {
        super( x, y, size, time);
        this.setVisual(new Base_visual( x, y, size,this));
        this.detect_dist = size*2;
        this.obtained_resources = new HashMap<>();
        init_obtained_resources();

        this.task_manager = new Task_manager_extended(this);

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


    @Override
    public boolean action(Worker worker)
    {

        Model model = Model.getInstance();

            drop_resources(worker);
            task_manager.update_task_of(worker);

            worker.setTask(model.getBehaviour().getTask_miner());
            worker.getTask().setFound_resource(false);
            worker.getTask().setReturn_from_resource(false);

            Abstr_Task worker_task = worker.getTask();

            //if(worker_task.getTask() == Task_Enum.miner)
            //{

            int original_detect_dist = 0;//for basic case
        //hack to increase the detection range of the worker
            if(model.getBehaviour() instanceof Behaviour_Basic)
            {
                original_detect_dist = worker_task.getPhero_detect_dist(); //for basic case
                worker_task.setPhero_detect_dist((int)(size*2.5)); //for basic cas
            }


            worker.clear_visited_objects();
            worker.clear_detected_objects();
            //Model.getInstance().find_objects(worker);
            worker.setDetected_objects(model.find_objects(getX(),getY(),detect_dist,worker.getResource_type(),worker_task.getTask()));
            worker_task.select_target(worker);
            worker.clear_visited_objects();

            if(model.getBehaviour() instanceof Behaviour_Basic)
                worker_task.setPhero_detect_dist(original_detect_dist); //for basic case

            if(!worker.isFound_new_target())
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
        if (goal == null)
            return null;
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

    public Abstr_Task_manager getTask_managerSimple() {
        return task_manager;
    }

    public void setTask_managerSimple(Abstr_Task_manager task_manager) {
        this.task_manager = task_manager;
    }

    public int getDetect_dist() {
        return detect_dist;
    }

    public void setDetect_dist(int detect_dist) {
        this.detect_dist = detect_dist;
    }
}
