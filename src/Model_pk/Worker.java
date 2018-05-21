package Model_pk;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

import Model_pk.Behaviour.*;
import Model_pk.Enterables.Enterable_object;
import View.Object_visuals.Worker_visual;
import View.View;

/**
 * Created by christiaan on 10/05/18.
 */
public class Worker extends Object {

    private Model model;

    private double  currDirection;

    private ArrayList<Resource> load = new ArrayList<>();
    private int                 max_load;
    private int                 time;   //the amount of ticks that the worker is within a object without receiving an resource

    private Pheromone          currPhermone;
    private ArrayList<CustomStruct> DetectedObjects = new ArrayList<>();
    private ArrayList<Objects> VisitedObjects = new ArrayList<>();

    //private Abstr_Behaviour abstrBehaviour;
    private Abstr_Task      task;
    private Resource_Type resource_type; //only relevant when the task = miner

    public Worker( int x, int y, int size, double currDirection, int max_load, Abstr_Task task)
    {// Abstr_Behaviour abstrBehaviour) {
        super( x, y, size);
        this.currDirection = currDirection;
        this.max_load = max_load;
        this.task = task;
        //this.task.setWorker(this);
        //this.abstrBehaviour = abstrBehaviour;
        //this.abstrBehaviour.setWorker(this);
        this.resource_type = Resource_Type.Stone;

        this.setVisual(new Worker_visual( x, y, size,this));

        this.model = Model.getInstance();

        task.test_tan_function();


    }

    @Override
    public void tick()
    {
        task.tick(this);
    }

    public void move(int x, int y)
    {
        this.x = x;
        this.y = y;
        visual.update_parameter(x,y,size);

    }

    public void setTarget(int x, int y)
    {
        task.setTarget(x,y);
        task.setDist_walked(0);
        task.move_worker(this,x,y);
}


    public double getCurrDirection() {
        return currDirection;
    }

    public void setCurrDirection(double currDirection) {
        this.currDirection = currDirection;
    }

    public int getMax_load() {
        return max_load;
    }

    public void setMax_load(int max_load) {
        this.max_load = max_load;
    }

    public Pheromone getCurrPhermone() {
        return currPhermone;
    }

    public void setCurrPhermone(Pheromone currPhermone) {
        this.currPhermone = currPhermone;
    }

    public Abstr_Task getTask() {
        return task;
    }

    public void setTask(Abstr_Task task) {
        this.task = task;
    }

    public Resource_Type getResource_type() {
        return resource_type;
    }

    public void setResource_type(Resource_Type resource_type) {
        this.resource_type = resource_type;
    }

    public boolean is_full()
    {
        if(max_load <= get_total_amount_of_load())
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    public boolean add_load(Resource resource)
    {
        if(is_full())
        {
            return false;
        }
        else
        {
            Resource res = get_resource_of_type(resource.getType());

            if(res != null)
            {
                res.setAmount(res.getAmount()+resource.getAmount());
            }
            else
            {
                load.add(resource);
            }



            return true;
        }



    }

    public Resource get_resource_of_type(Resource_Type type)
    {
        for(Resource res: load)
        {
            if(res.getType() == type)
            {
                return res;
            }
        }
        return null;
    }


    public int get_total_amount_of_load()
    {
        int total  = 0;
        for(Resource res: load)
        {
            total = total + res.getAmount();
        }
        return total;
    }

    public void clear_load()
    {
        /*Iterator<Resource> iter = load.iterator();
        while (iter.hasNext())
        {
            iter.remove();

        }*/

        load.clear();
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void increase_time()
    {
        this.time= time +1;
    }

    public ArrayList<Resource> getLoad() {
        return load;
    }

    public void setLoad(ArrayList<Resource> load) {
        this.load = load;
    }

    public void addDetectedPheromone(CustomStruct struct)
    {

    }
}
