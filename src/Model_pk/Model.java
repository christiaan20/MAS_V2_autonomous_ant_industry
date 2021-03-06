package Model_pk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import Main.Main;
import Model_pk.Behaviour.Abstr_Behaviour;
import Model_pk.Behaviour.Task_Enum;
import Model_pk.Enums.Resource_Type_Enum;
import Model_pk.Objects.Enterables.Abstr_Enterable_object;
import Model_pk.Objects.Enterables.Base;

import Model_pk.Objects.Enterables.Resource_pool;
import Model_pk.Objects.Abstr_Object;
import Model_pk.Objects.Pheromone;
import Model_pk.Objects.Worker;
import Model_pk.Task_managers.Abstr_Task_manager;
import Model_pk.Testing_Classes.Test_Settings;
import Model_pk.Testing_Classes.Tester;
import Model_pk.Testing_Classes.parameter_setting;
import View.View;

/**
 *
 * A Singleton Model_pk object that is accessible by all other classes
 * The class represents the model of the world and initializes the scenario.
 *
 */
public class Model{
    private static Model  model ;

    private Abstr_Behaviour behaviour;
    private Abstr_Task_manager task_manager;
    private Random random = new Random();

    private int size_x_field;
    private int size_y_field;
    private int tile_size;      //the size of tile where each pheromone is collected in the middle

    private Base base;
    private ArrayList<Worker> workers = new ArrayList<>();
    private ArrayList<Abstr_Enterable_object> enterable_objects = new ArrayList<>();
    private ArrayList<Abstr_Enterable_object> impassable_objects = new ArrayList<>(); //NOT YET IMPLEMENTED
    private ArrayList<Pheromone> pheromones = new ArrayList<>();

    private Tester tester;

    private boolean pause;

    private int base_x;
    private int base_y;
    private int worker_size;
    private int max_worker_load;
    private int work_force_size;
    private int object_size;
    private int resource_time;
    private int resource_pool_capacity;
    private int base_time;

    private int max_wander_steps;

    private int tickcount;

    private ArrayList<Worker> workers_to_add;


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

    public void init_model(Main main) throws IOException {
        Test_Settings settings = Test_Settings.getInstance();
        this.work_force_size = settings.getStarting_workers_value();
        this.behaviour = settings.getBehaviour();
        this.task_manager = settings.getTask_manager();
        this.behaviour.setPhero_detect_dist(settings.getPheromone_dist_value());

        parameter_setting scenario_setting = settings.getScenario();


        if(scenario_setting == parameter_setting.big)
        {
            this.size_x_field    = 1500;
            this.size_y_field    = 900;
            main.setSize(1650,1050);
            set_scenario_2();
        }
        else
        {
            this.size_x_field    = 800;
            this.size_y_field    = 600;
            main.setSize(950,750);
            set_scenario_1();
        }

    }


    public void set_scenario_1()  throws IOException
    {
        String scenario_name = "scenario_1_small_size";

        this.workers_to_add = new ArrayList<>();
        this.tickcount = 0;
        //this.behaviour = new Behaviour_Basic();
        //this.task_manager = new Task_manager_extended();

        //this.size_x_field    = 800;
        //this.size_y_field    = 600;
        this.max_wander_steps = 15;
        behaviour.setWander_limit(max_wander_steps);


        View.getInstance().set_field_size(size_x_field, size_y_field);
        View.getInstance().set_size(size_x_field+150, size_y_field);
        this.base_x      = 450;
        this.base_y      = 150;
        this.object_size  = 50;
        this.worker_size = 20;

        //this.work_force_size = 15;

        this.base_time       = 50;
        this.resource_time   = 50;

        this.resource_pool_capacity  = 500;
        this.max_worker_load        = 5;

        tile_size = object_size/5;

        boolean test = false;
        boolean test_direction = false;

        if(test)
        {
            test_resources_pools();
            work_force_size = 1;

        }
        else if(test_direction)
        {

            test_direction();
            work_force_size = 0;

        }
        else
        {
            this.base = new Base(base_x,base_y,object_size,base_time,this.task_manager);
            enterable_objects.add(this.base);

            create_resource_pools();
        }

        //give the task manager a reference to the created base
        this.task_manager.setBase(this.base);

        //create the workers
        for(int i = 0 ; i < work_force_size;i++)
        {
            workers.add(new Worker(base_x, base_y, worker_size, random.nextDouble()*Math.PI*2, max_worker_load, behaviour.getTask_explorer(),base));
        }

        this.tester = new Tester();

        //tester.write_to_log_file(scenario_name);

    }

    public void set_scenario_2()  throws IOException
    {
        this.behaviour.setDegrade_time(175);

        String scenario_name = "scenario_1_small_size";

        this.workers_to_add = new ArrayList<>();
        this.tickcount = 0;

        //this.behaviour = new Behaviour_Basic();
        //this.task_manager = new Task_manager_extended();

        //this.size_x_field    = 1500;
        //this.size_y_field    = 1000;
        this.max_wander_steps = 30;
        behaviour.setWander_limit(max_wander_steps);

        View.getInstance().set_field_size(size_x_field, size_y_field);
        View.getInstance().set_size(size_x_field+150, size_y_field);
        this.base_x      = 750;
        this.base_y      = 500;
        this.object_size = 50;
        this.worker_size = 20;

        //int work_force_size = 15;

        this.base_time       = 50;
        this.resource_time   = 50;

        this.resource_pool_capacity  = 500;
        this.max_worker_load         = 5;

        tile_size = object_size/5;

        boolean test = false;
        boolean test_direction = false;

        if(test)
        {
            test_resources_pools();
            work_force_size = 1;

        }
        else if(test_direction)
        {
            test_direction();
            work_force_size = 0;

        }
        else
        {
            base = new Base(base_x,base_y,object_size,base_time,task_manager);
            enterable_objects.add(base);

            create_big_resource_pools();
        }

        //give the task manager a reference to the created base
        task_manager.setBase(base);

        //create the workers
        for(int i = 0 ; i < work_force_size;i++)
        {
            workers.add(new Worker(base_x, base_y, worker_size, random.nextDouble()*Math.PI*2, max_worker_load, behaviour.getTask_explorer(),base));
        }

        this.tester = new Tester();
       // tester.write_to_log_file(scenario_name);


    }

    private void test_resources_pools(){

        base = new Base(base_x,base_y,object_size,base_time,task_manager);
        enterable_objects.add(base);

        for(int i = 0 ; i<5;i++)
        {
            enterable_objects.add(new Resource_pool(base_x - 125 +object_size*i ,base_y - 125 +object_size*0 ,object_size,resource_time, Resource_Type_Enum.Coal,resource_pool_capacity));
            enterable_objects.add(new Resource_pool(base_x - 125 +object_size*0 ,base_y - 125 +object_size*i ,object_size,resource_time, Resource_Type_Enum.Coal,resource_pool_capacity));
            enterable_objects.add(new Resource_pool(base_x - 125 +object_size*5 ,base_y - 125 +object_size*i ,object_size,resource_time, Resource_Type_Enum.Coal,resource_pool_capacity));
            enterable_objects.add(new Resource_pool(base_x - 125 +object_size*i ,base_y - 125 +object_size*5 ,object_size,resource_time, Resource_Type_Enum.Coal,resource_pool_capacity));
            enterable_objects.add(new Resource_pool(base_x - 125 +object_size*i ,base_y - 125 +object_size*5 ,object_size,resource_time, Resource_Type_Enum.Coal,resource_pool_capacity));
        }

    }

    private void test_direction(){

        base = new Base(base_x+200,base_y+200,object_size,base_time,task_manager);
        enterable_objects.add(base);

        workers.add(new Worker(base_x, base_y, worker_size, random.nextDouble()*Math.PI*2, max_worker_load, behaviour.getTask_miner(), Resource_Type_Enum.Coal,null));
        pheromones.add(new Pheromone(null, base_x+30, base_y+30, 5, Task_Enum.miner, Resource_Type_Enum.Coal, 20, 10000, 15));
        pheromones.add(new Pheromone(null, base_x-30, base_y+30, 5, Task_Enum.miner, Resource_Type_Enum.Coal, 10, 10000, 15));

    }

    private void create_resource_pools(){

        //create the resources
        enterable_objects.add(new Resource_pool(300,400,object_size,resource_time, Resource_Type_Enum.Stone,2000));
        enterable_objects.add(new Resource_pool(75,325,object_size,resource_time, Resource_Type_Enum.Coal, resource_pool_capacity));
        enterable_objects.add(new Resource_pool(550,325,object_size,resource_time, Resource_Type_Enum.Copper, resource_pool_capacity));
        enterable_objects.add(new Resource_pool(250,100,object_size,resource_time, Resource_Type_Enum.Iron, resource_pool_capacity));
        enterable_objects.add(new Resource_pool(600,250,object_size,resource_time, Resource_Type_Enum.Uranium, resource_pool_capacity));
        enterable_objects.add(new Resource_pool(750,50,object_size,resource_time, Resource_Type_Enum.Uranium, 800));
        enterable_objects.add(new Resource_pool(100,500,object_size,resource_time, Resource_Type_Enum.Iron, 300));
        enterable_objects.add(new Resource_pool(200,450,object_size,resource_time, Resource_Type_Enum.Iron, 300));
        enterable_objects.add(new Resource_pool(750,125,object_size,resource_time, Resource_Type_Enum.Copper, 1000));
        enterable_objects.add(new Resource_pool(75,275,object_size,resource_time, Resource_Type_Enum.Coal, resource_pool_capacity));


    }

    private void create_big_resource_pools(){

        //create the resources
        enterable_objects.add(new Resource_pool(300*2,400*2,object_size,resource_time, Resource_Type_Enum.Stone,2000));
        enterable_objects.add(new Resource_pool(75*2,325*2,object_size,resource_time, Resource_Type_Enum.Coal, resource_pool_capacity));
        enterable_objects.add(new Resource_pool(550*2, 325*2,object_size,resource_time, Resource_Type_Enum.Copper, resource_pool_capacity));
        enterable_objects.add(new Resource_pool(250*2,100,object_size,resource_time, Resource_Type_Enum.Iron, resource_pool_capacity));
        enterable_objects.add(new Resource_pool(600*2,250,object_size,resource_time, Resource_Type_Enum.Uranium, resource_pool_capacity));
        enterable_objects.add(new Resource_pool(700*2,50*2,object_size,resource_time, Resource_Type_Enum.Uranium, 800));
        enterable_objects.add(new Resource_pool(100*2,500*2,object_size,resource_time, Resource_Type_Enum.Iron, 300));
        enterable_objects.add(new Resource_pool(200*2,400*2,object_size,resource_time, Resource_Type_Enum.Iron, 300));
        enterable_objects.add(new Resource_pool(700*2,125*2,object_size,resource_time, Resource_Type_Enum.Copper, 1000));
        enterable_objects.add(new Resource_pool(75*2,275*2,object_size,resource_time, Resource_Type_Enum.Coal, resource_pool_capacity));
        enterable_objects.add(new Resource_pool(100*2,300*2,object_size,resource_time, Resource_Type_Enum.Coal, resource_pool_capacity));

        enterable_objects.add(new Resource_pool(275,15,object_size,resource_time, Resource_Type_Enum.Uranium, 1000));
        enterable_objects.add(new Resource_pool(400,300,object_size,resource_time, Resource_Type_Enum.Iron, 500));
        enterable_objects.add(new Resource_pool(400,250,object_size,resource_time, Resource_Type_Enum.Iron, 500));
        enterable_objects.add(new Resource_pool(475,275,object_size,resource_time, Resource_Type_Enum.Iron, 800));
        enterable_objects.add(new Resource_pool(800,50,object_size,resource_time, Resource_Type_Enum.Copper, 1000));
        enterable_objects.add(new Resource_pool(1000,200,object_size,resource_time, Resource_Type_Enum.Stone, resource_pool_capacity));
        enterable_objects.add(new Resource_pool(1100,500,object_size,resource_time, Resource_Type_Enum.Coal, resource_pool_capacity));
    }

    public Worker add_worker(){
        Worker worker = new Worker(base_x, base_y, worker_size, random.nextDouble()*Math.PI*2, max_worker_load, behaviour.getTask_explorer(),base);
        workers_to_add.add(worker);
        return worker;
        }



    public void tick(int tick_count){

        tickcount = tick_count;
        tick_workers();
        tick_pheromone();
        delete_expired_objects();

        workers.addAll(workers_to_add);
        workers_to_add = new ArrayList<>();


            tester.is_goal_reached(tick_count);

    }

    public boolean goals_are_reached(){
        return tester.all_goals_reached();
    }


    public void restart()
    {
        behaviour =null;

        base = null;
        workers = new ArrayList<>();
        enterable_objects = new ArrayList<>();
        pheromones = new ArrayList<>();

        tester = null;


    }


    public void tick_workers()
    {
        for(Worker w: workers)
        {
            if ( !w.isBroken() && getTickcount() % 500 == 0)
                w.chance_to_break();

            if( !w.isBroken())
                w.tick();
        }
    }

    public void tick_enterable_objects()
    {
        for(Abstr_Enterable_object o: enterable_objects)
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

    public Abstr_Object check_if_reached_an_object(int x, int y)
    {
        for(Abstr_Enterable_object o: enterable_objects)
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
        Abstr_Object obj= struct.getObject();
        if(worker.getTask().is_obj_relevant_to_task(worker,obj))
        {
            int x_w =  worker.getX();
            int y_w =  worker.getY();
            int x_obj =  obj.getX();
            int y_obj =  obj.getY();

            int Xdist = x_obj - x_w;
            int Ydist = y_obj - y_w;
            int dist = (int) Math.abs( Math.sqrt(Math.pow(Xdist,2)+ Math.pow(Ydist,2)));
            double angle_from_worker_direction = worker.getCurrDirection()- worker.get_corner_relative_to(x_obj,y_obj);


            if(dist<= worker.getTask().getPhero_detect_dist())
            {

                    struct.setDistance(dist);
                    struct.setX_vector(Xdist);
                    struct.setY_vector(Ydist);
                    struct.setAngle_from_worker_direction(angle_from_worker_direction);

                    return true;
            }
        }

        return false;
    }

    public void find_objects(Worker worker)
    {

        for(Abstr_Object o:pheromones)
        {
            CustomStruct struct = new CustomStruct(o);
            if(detected_object(worker, struct))
            {
                worker.add_detected_pheromone(struct);
            }

        }
        for(Abstr_Object o:enterable_objects)
        {
            CustomStruct struct = new CustomStruct(o);
            if(detected_object(worker, struct)) //fills the struct with a the distance from the worker
            {
                worker.add_detected_pheromone(struct);
            }

        }

    }

    public ArrayList<CustomStruct> find_objects(int x, int y, int detect_dist, Resource_Type_Enum type, Task_Enum task)
    {
        ArrayList<CustomStruct> objects = new ArrayList<>();
        CustomStruct struct = null;
        for(Abstr_Object o:pheromones)
        {
            struct = new CustomStruct(o);
            if(detected_object(x,y,detect_dist,type,task, struct))
            {
                objects.add(struct);
            }

        }
        for(Abstr_Object o:enterable_objects)
        {
            struct = new CustomStruct(o);
            if(detected_object(x,y,detect_dist,type,task, struct)) //fills the struct with a the distance from the worker
            {
               objects.add(struct);
            }

        }

        return objects;

    }

    public boolean detected_object(int x, int y, int detect_dist, Resource_Type_Enum type, Task_Enum task, CustomStruct struct)
    {
        Abstr_Object obj= struct.getObject();
        if(obj instanceof Pheromone)
        {
            Pheromone phero = (Pheromone) obj;
            if(phero.isTask(task) && phero.isType(type))
            {
                int x_w =  x;
                int y_w =  y;
                int x_obj =  obj.getX();
                int y_obj =  obj.getY();

                int Xdist = x_obj - x_w;
                int Ydist = y_obj - y_w;
                int dist = (int) Math.abs( Math.sqrt(Math.pow(Xdist,2)+ Math.pow(Ydist,2)));


                if(dist<= detect_dist)
                {

                    struct.setDistance(dist);
                    struct.setX_vector(Xdist);
                    struct.setY_vector(Ydist);
                    return true;
                }
            }
        }

        return false;
    }

    public int distBetween(int x1, int y1,int x2, int y2)
    {
        int Xdist = x2 - x1;
        int Ydist = y2 - y1;
        return (int) Math.abs( Math.sqrt(Math.pow(Xdist,2)+ Math.pow(Ydist,2)));
    }

    public void drop_pheromone(Worker worker, int x, int y, int size , Task_Enum task, Resource_Type_Enum type, int strength, int degrade_time, int max_size)
    {
        int left_border = (int) Math.floor(x/tile_size)* tile_size;
        int right_border  = left_border +tile_size;
        int bottom_border = (int) Math.floor(y/tile_size)* tile_size;
        int top_border = bottom_border+ tile_size;


        for(Pheromone phero: pheromones)
        {
            if(phero.check_object_within(left_border,right_border,bottom_border,top_border))
            {
                if(phero.getTask() == task)
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
        Pheromone new_phero = (new Pheromone(worker,(left_border + tile_size/2),(bottom_border + tile_size/2),size,task,type,strength,degrade_time,max_size));
        worker.add_visited_pheromone(new_phero);
        pheromones.add(new_phero);

    }

    public void drop_seperate_pheromone(Worker worker, int x, int y, int size , Task_Enum task, Resource_Type_Enum type, int strength, int degrade_time, int max_size)
    {
        int left_border = (int) Math.floor(x/tile_size)* tile_size;
        int right_border  = left_border +tile_size;
        int bottom_border = (int) Math.floor(y/tile_size)* tile_size;
        int top_border = bottom_border+ tile_size;


        for(Pheromone phero: pheromones)
        {
            if(phero.check_object_within(left_border,right_border,bottom_border,top_border))
            {
                if(phero.getTask() == task)
                {
                    if(phero.isTask(Task_Enum.explorer))
                    {
                        phero.increase_strength(strength);
                        phero.addOwner(worker);
                        return;
                    }
                    else
                    {
                        if(phero.isType(type))
                        {
                            phero.increase_strength(strength);
                            phero.addOwner(worker);
                            return;
                        }
                    }
                }
                ;
            }
        }
        Pheromone new_phero = (new Pheromone(worker,(left_border + tile_size/2),(bottom_border + tile_size/2),size,task,type,strength,degrade_time,max_size));
        pheromones.add(new_phero);

    }


    public void delete_expired_objects()
    {
        Iterator<Abstr_Enterable_object> iter1 = enterable_objects.iterator();
        while (iter1.hasNext()) {
            Abstr_Object obj = iter1.next();
            if(obj.isExpired())
            {
                obj.delete_visual();
                iter1.remove();
            }
        }

        Iterator<Pheromone> iter2 = pheromones.iterator();
        while (iter2.hasNext()) {
            Abstr_Object obj = iter2.next();
            if(obj.isExpired())
            {
                obj.delete_visual();
                iter2.remove();
            }
        }
    }


    public Base getBase() {
        return base;
    }

    public Random getRandom() {
        return random;
    }

    public Tester getTester() {
        return tester;
    }

    public int getTickcount() {
        return tickcount;
    }

    public int count_dead_worker()
    {
        int count = 0;
        for(Worker worker: workers)
        {
            if(worker.isBroken())
            {
                count++;
            }
        }
        return count;
    }

    public int count_living_workers()
    {
        int count = 0;
        for(Worker worker: workers)
        {
            if(!worker.isBroken())
            {
                count++;
            }
        }
        return count;
    }
}
