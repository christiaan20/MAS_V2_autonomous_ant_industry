package Model_pk.Enterables;


import Model_pk.Resource;
import Model_pk.Resource_Type;
import Model_pk.Worker;
import Model_pk.Order;
import View.Object_visuals.Base_visual;
import View.View;

import java.util.ArrayList;

/**
 * Created by christiaan on 10/05/18.
 */
public class Base extends Enterable_object {
    private Order order;
    private ArrayList<Resource> gathered_resources = new ArrayList<>();

    public Base(int x, int y, int size, int time, Order order) {
        super( x, y, size, time);
        this.order = order;
        this.setVisual(new Base_visual( x, y, size,this));

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
        return true;

    }

    @Override
    public void exit(Worker worker) {

    }

    public Resource get_resource_of_type(Resource_Type type)
    {

        for(Resource res: gathered_resources)
        {
            if(res.getType() == type)
            {
                return res;
            }
        }
        return null;
    }

    public void drop_resources(Worker worker)
    {

        ArrayList<Resource> load =  worker.getLoad();
        for(Resource resource: load)
        {
            Resource res = get_resource_of_type(resource.getType());

            if (res != null) {
                res.setAmount(res.getAmount() + resource.getAmount());
            } else {
                gathered_resources.add(resource);
            }
        }

        worker.clear_load();

    }

    public ArrayList<Resource> getGathered_resources() {
        return gathered_resources;
    }
}
