package Model_pk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import Model_pk.Behaviour.Abstr_Behaviour;
import Model_pk.Behaviour.Abstr_Task;
import Model_pk.Behaviour.Basic.Behaviour_Basic;
import Model_pk.Behaviour.Task_Enum;
import Model_pk.Enterables.Enterable_object;
import Model_pk.Enterables.Base;

import Model_pk.Enterables.Resource_pool;
import View.View;

/**
 * Created by christiaan on 10/05/18.
 *
 * A Singleton Model_pk object that is accessable by all other classes
 */
public class Model{
    private static Model  model ;

    private Abstr_Behaviour behaviour;
    private Random random = new Random();

    private int size_x_field;
    private int size_y_field;
    private int tile_size;      //the size of tile where each pheromone is collected in the middle

    private Base base;
    private ArrayList<Worker> workers = new ArrayList<>();
    private ArrayList<Enterable_object> enterable_objects = new ArrayList<>();
    private ArrayList<Pheromone> pheromones = new ArrayList<>();

    private Tester test_setting;

    private boolean pause;


    /**
     * Create private constructor for a singleton
     */
    private Model(){}

    /**
     * Create a static method to get instance of the singleton
     */
    public static Model getInstance(){
        if(model == null){
            model = new Model();
        }
        return model;
    }

    public void set_scenario_1()  throws IOException
    {
        behaviour = new Behaviour_Basic();

        size_x_field    = 800;
        size_y_field    = 800;

        View.getInstance().set_field_size(size_x_field, size_y_field);
        int base_x      = 450;
        int base_y      = 450;
        int object_size = 50;
        int worker_size = 20;

        int work_force_size = 15;

        int base_time       = 50;
        int resource_time   = 50;

        int resource_pool_capacity  = 60;
        int max_worker_load         = 5;

        tile_size = object_size/5;

        // create the base
        base = new Base(base_x,base_y,object_size,base_time, new Order());
        enterable_objects.add(base);

        //create the resources
        enterable_objects.add(new Resource_pool(300,400,object_size,resource_time, Resource_Type.Stone,resource_pool_capacity));
        enterable_objects.add(new Resource_pool(75,325,object_size,resource_time, Resource_Type.Coal, resource_pool_capacity));
        enterable_objects.add(new Resource_pool(700,325,object_size,resource_time, Resource_Type.Copper, resource_pool_capacity));
        enterable_objects.add(new Resource_pool(700,500,object_size,resource_time, Resource_Type.Iron, resource_pool_capacity));
       enterable_objects.add(new Resource_pool(600,250,object_size,resource_time, Resource_Type.Uranium, resource_pool_capacity));

        //create the workers
        for(int i = 0 ; i < work_force_size;i++)
        {
            workers.add(new Worker(base_x, base_y, worker_size, random.nextDouble()*Math.PI*2, max_worker_load, behaviour.getTask_explorer(),base));
        }

        //workers.add(new Worker(base_x, base_y, worker_size, random.nextDouble(), max_worker_load, behaviour.getTask_explorer(),Resource_Type.Coal,base));
        //workers.add(new Worker(base_x, base_y, worker_size, random.nextDouble(), max_worker_load, behaviour.getTask_explorer(),Resource_Type.Stone,base));

        this.test_setting = new Tester();

    }


    public void tick(int tick_count){

        tick_workers();
        tick_pheromone();
        delete_expired_objects();

        try {

            test_setting.is_goal_reached(tick_count);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void tick_workers()
    {
        for(Worker w: workers)
        {
            w.tick();
        }
    }

    public void tick_enterable_objects()
    {
        for(Enterable_object o: enterable_objects)
        {
            o.tick();
        }
    }

    public void tick_pheromone()
    {
        for(Pheromone phero: pheromones)
        {
            phero.tick();
        }
    }

    public Object check_if_reached_an_object(int x, int y)
    {
        for(Enterable_object o: enterable_objects)
        {
            if(o.has_reached(x,y))
            {
                return o;
            }
        }

        return null;
    }

    public void set_target_all_workers(int x, int y)
    {
        for(Worker worker: workers)
        {
            worker.getTask().setTarget(worker,x,y);
        }
    }

    public int get_size_x_field() {
        return size_x_field;
    }

    public int get_size_y_field() {
        return size_y_field;
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public Abstr_Behaviour getBehaviour() {
        return behaviour;
    }

    public void setBehaviour(Abstr_Behaviour behaviour) {
        this.behaviour = behaviour;
    }


    public boolean  detected_object(Worker worker, CustomStruct struct)
    {
        Object obj= struct.getObject();
        if(worker.getTask().is_obj_relevant_to_task(worker,obj))
        {
            int dist = distBetween(worker.getX(),worker.getY(),obj.getX(),obj.getY());

            if(dist<= worker.getTask().getPhero_detect_dist())
            {

                    struct.setDistance(dist);
                    return true;
            }
        }

        return false;
    }

    public void find_objects(Worker worker)
    {

        for(Object o:pheromones)
        {
            CustomStruct struct = new CustomStruct(o);
            if(detected_object(worker, struct))
            {
                worker.add_detected_pheromone(struct);
            }

        }
        for(Object o:enterable_objects)
        {
            CustomStruct struct = new CustomStruct(o);
            if(detected_object(worker, struct)) //fills the struct with a the distance from the worker
            {
                worker.add_detected_pheromone(struct);
            }

        }

    }

    public int distBetween(int x1, int y1,int x2, int y2)
    {
        int Xdist = x1 - x2;
        int Ydist = y1 - y2;
        return (int) Math.abs( Math.sqrt(Math.pow(Xdist,2)+ Math.pow(Ydist,2)));
    }

    public void drop_pheromone(Worker worker,int x, int y,int size , Abstr_Task task, Resource_Type type, int strength, int degrade_time, int max_size)
    {
        int left_border = (int) Math.floor(x/tile_size)* tile_size;
        int right_border  = left_border +tile_size;
        int bottom_border = (int) Math.floor(y/tile_size)* tile_size;
        int top_border = bottom_border+ tile_size;


        for(Pheromone phero: pheromones)
        {
            if(phero.check_object_within(left_border,right_border,bottom_border,top_border))
            {
                if(phero.getTask() == task.getTask())
                {
                    if(phero.isTask(Task_Enum.explorer))
                    {
                        phero.increase_strength(strength);
                        phero.addOwner(worker);
                        worker.add_visited_pheromone(phero);
                        return;
                    }
                    else
                    {
                        if(phero.isType(type))
                        {
                            phero.increase_strength(strength);
                            phero.addOwner(worker);
                            worker.add_visited_pheromone(phero);
                            return;
                        }
                    }
                }
                ;
            }
        }
        Pheromone new_phero = (new Pheromone(worker,(left_border + tile_size/2),(bottom_border + tile_size/2),size,task.getTask(),type,strength,degrade_time,max_size));
        worker.add_visited_pheromone(new_phero);
        pheromones.add(new_phero);

    }

    public void delete_expired_objects()
    {

        Iterator<Enterable_object> iter1 = enterable_objects.iterator();
        while (iter1.hasNext()) {
            Object obj = iter1.next();
            if(obj.isExpired())
            {
                obj.delete_visual();
                iter1.remove();
            }


        }

        Iterator<Pheromone> iter2 = pheromones.iterator();
        while (iter2.hasNext()) {
            Object obj = iter2.next();
            if(obj.isExpired())
            {
                obj.delete_visual();
                iter2.remove();
            }


        }

    }

    public void delete_enterable_object(Object o)
    {
        Iterator<Enterable_object> iter = enterable_objects.iterator();
        while (iter.hasNext()) {
            Object obj = iter.next();
            if(obj.equals(o))
            {
                iter.remove();
            }

        }
    }

    public void delete_pheromone(Object o)
    {
        Iterator<Pheromone> iter = pheromones.iterator();
        while (iter.hasNext()) {
            Object obj = iter.next();
            if(obj.equals(o))
            {
                iter.remove();
            }

        }
    }

    public Base getBase() {
        return base;
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public Tester getTest_setting() {
        return test_setting;
    }
}
