package Model_pk;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

import Model_pk.Behaviour.*;
import Model_pk.Enterables.Base;
import Model_pk.Enterables.Resource_pool;
import View.Object_visuals.Worker_visual;

/**
 * Created by christiaan on 10/05/18.
 */
public class Worker extends Object {

    private Model model;

    private double  currDirection;      //The current direction the worker is moving
    private Worker_State_Enum state;    //the worker is in

    private ArrayList<Resource> load = new ArrayList<>();   //the resources it is carrying
    private int                 max_load;                   //the maximum amount of resources the worker can carry
    private int                 time;                       //the amount of ticks that the worker is within a object without receiving an resource

    private Object curr_target_object;      //the current object the worker is moving towards



    private ArrayList<CustomStruct> detected_objects = new ArrayList<>();   //The list of objects it can see now
    private ArrayList<Object> visited_objects = new ArrayList<>();          //The list of objects it has already passed

    //private Abstr_Behaviour abstrBehaviour;
    private Abstr_Task      task;           //The Task it is currently doing
    private Resource_Type resource_type;    //The resource it will mine, transport for or look for

    public Worker(int x, int y, int size, double currDirection, int max_load, Abstr_Task task, Base base)
    {
        super( x, y, size);
        this.currDirection = currDirection;
        this.max_load = max_load;
        this.task = task;
        this.task.setLast_entered_object(base);
        this.resource_type = null;
        this.setState(Worker_State_Enum.inside);

        this.setVisual(new Worker_visual( x, y, size,this));

        this.model = Model.getInstance();

        task.test_tan_function();
    }

    public Worker( int x, int y, int size, double currDirection, int max_load, Abstr_Task task, Resource_Type type, Base base)
    {
//        super( x, y, size);
//        this.currDirection = currDirection;
//        this.max_load = max_load;
//        this.task = task;
//
//
//        this.setVisual(new Worker_visual( x, y, size,this));
//
//        this.model = Model.getInstance();
//
//        task.test_tan_function();
        this(x,y,size,currDirection,max_load,task,base);
        this.resource_type = type;

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
        task.setTarget(this,x,y);
    }

    public double get_corner_relative_to(int target_x,int target_y)
    {
        int Xdist = task.get_x_dist(this.x,target_x);
        int Ydist = task.get_y_dist(this.y, target_y);

        if(x == target_x || y == target_y )
            return model.getRandom().nextDouble();
        else
            return task.getaTanCorner(Xdist, Ydist);
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

    public Object getCurr_target_object() {
        return curr_target_object;
    }

    public void setCurr_target_object(Object curr_target_object) {
        this.curr_target_object = curr_target_object;
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
        return(max_load <= get_total_amount_of_load());
    }

    public boolean is_empty()
    {
        return (0 == get_total_amount_of_load());
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

    public void add_visited_pheromone(Object obj)
    {
        remove_obj_from_detected_pheromones(obj);
        visited_objects.add(obj);
    }

    public void remove_obj_from_visited_pheromones(Object obj)
    {
        Iterator<Object> iter = visited_objects.iterator();
        while (iter.hasNext()) {
            Object object = iter.next();
            if(object.equals(obj) )
                iter.remove();

        }
    }

    public void add_detected_pheromone(CustomStruct struct) {
        if (!isVisited(struct.getObject()))
            detected_objects.add(struct);
    }

    public void remove_obj_from_detected_pheromones(Object obj)
    {
        Iterator<CustomStruct> iter = detected_objects.iterator();
        while (iter.hasNext()) {
            CustomStruct struct = iter.next();
            if(struct.getObject().equals(obj) )
                iter.remove();

        }
    }


    public Pheromone closest_phero()
    {
        CustomStruct closest_phero = new CustomStruct(null,10000000);


        for(CustomStruct struct: detected_objects)
        {
            Object obj = struct.getObject();
            if(obj instanceof Pheromone)
            {
                Pheromone phero = (Pheromone) obj;

                    if(closest_phero.is_farther_than(struct))
                    {
                        closest_phero = struct;
                    }
            }
        }

        return (Pheromone) closest_phero.getObject();
    }

    public Pheromone closest_phero_of_type(Task_Enum task,Resource_Type type)
    {
        CustomStruct closest_phero = new CustomStruct(null,10000000);


        for(CustomStruct struct: detected_objects)
        {
            Object obj = struct.getObject();
            if(obj instanceof Pheromone)
            {
                Pheromone phero = (Pheromone) obj;

                if(phero.getType() == type && phero.getTask() == task)
                {
                    if(closest_phero.is_farther_than(struct))
                    {
                        closest_phero = struct;
                    }
                }
            }
        }

        return (Pheromone) closest_phero.getObject();
    }

    public Pheromone closest_owned_phero_of_type(Worker owner,Task_Enum task,Resource_Type type)
    {
        CustomStruct closest_phero = new CustomStruct(null,10000000);

        for(CustomStruct struct: detected_objects)
        {
            Object obj = struct.getObject();
            if(obj instanceof Pheromone)
            {
                Pheromone phero = (Pheromone) obj;

                if((phero.isType(type) || type == null)&& phero.isTask(task) && phero.isOwner(owner))
                {
                    if(closest_phero.is_farther_than(struct))
                    {
                        closest_phero = struct;
                    }
                }
            }
        }

        return (Pheromone) closest_phero.getObject();
    }


    public boolean isVisited(Object obj)
    {
        return visited_objects.contains(obj);
    }

    public void clear_visited_objects()
    {
        visited_objects.clear();
    }

    public void clear_detected_objects()
    {
        detected_objects.clear();
    }

    public Worker_State_Enum getState() {
        return state;
    }

    public void setState(Worker_State_Enum state) {
        this.state = state;
    }

    public Object pop_last_visited_phero()
    {
        if(visited_objects.size() >  0)
        {
            Object obj = visited_objects.get(visited_objects.size()-1);
            remove_obj_from_visited_pheromones(obj);
            return obj;
        }
        return null;

    }

    public ArrayList<CustomStruct> getDetected_objects() {
        return detected_objects;
    }

    public void setDetected_objects(ArrayList<CustomStruct> detected_objects) {
        this.detected_objects = detected_objects;
    }

    public ArrayList<Object> getVisited_objects() {
        return visited_objects;
    }

    public void setVisited_objects(ArrayList<Object> visited_objects) {
        this.visited_objects = visited_objects;
    }
}
