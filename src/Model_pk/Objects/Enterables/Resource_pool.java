package Model_pk.Objects.Enterables;

import Model_pk.Resource;
import Model_pk.Enums.Resource_Type_Enum;
import Model_pk.Objects.Worker;
import View.Object_visuals.Resource_pool_visual;

/**
 * Represents a resource pool, a pickup site, at unknown location for the workers that need to be collected from.
 */
public class Resource_pool extends Abstr_Enterable_object {
    private Resource_Type_Enum type;
    private int capacity;

    public Resource_pool(int x, int y, int size, int time, Resource_Type_Enum type, int capacity) {
        super(x, y, size, time);
        this.type = type;
        this.capacity = capacity;
        this.setVisual(new Resource_pool_visual( x, y, size,this,type));
    }

    @Override
    public void tick() {

    }

    public Resource_Type_Enum getType() {
        return type;
    }

    public void setType(Resource_Type_Enum type) {
        this.type = type;
    }


    @Override
    public boolean action(Worker worker)
    {
        if(capacity > 0)
        {
            worker.getTask().setReturn_from_resource(true);
            return worker.add_load(give_unit_resource());
        }
        else
        {
            return false;
        }

    }

    @Override
    public void exit(Worker worker) {

    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
        check_expire();
    }

    public Resource give_unit_resource()
    {
        capacity--;
        check_expire();
        return new Resource(1, type);
    }

    public void check_expire()
    {
        if(capacity <= 0 )
        {
            setExpired(true);
        }
    }
}



