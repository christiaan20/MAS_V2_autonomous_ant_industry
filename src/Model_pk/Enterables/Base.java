package Model_pk.Enterables;


import Model_pk.*;
import Model_pk.Behaviour.Abstr_Task;
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
        drop_resources(worker);
        task_manager.update_task_of(worker);
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
}
