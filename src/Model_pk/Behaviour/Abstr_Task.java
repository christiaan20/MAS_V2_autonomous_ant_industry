package Model_pk.Behaviour;

import Model_pk.*;
import Model_pk.Enums.Worker_State_Enum;
import Model_pk.Objects.Enterables.Base;
import Model_pk.Objects.Enterables.Abstr_Enterable_object;
import Model_pk.Objects.Abstr_Object;
import Model_pk.Objects.Pheromone;
import Model_pk.Objects.Worker;
import View.Object_visuals.Worker_visual;

import java.util.Random;
import java.text.DecimalFormat;

/**
 * Template for all tasks. Basic parameters and settings available to all tasks.
 */
public abstract class Abstr_Task
{
    protected Model model;
    protected Task_Enum task;

    //parameter attaining to the movement of workers
    protected int target_x; //absolute x coordinate where the workers is going to
    protected int target_y; //absolute y coordinate where the workers is going to
    protected boolean refresh_target = false; //whether a new target needs to be chosen
    protected boolean enter_base = false;

    // parameters for the movement of the worker
    private int worker_speed;                   //the size of the step of a worker at each tick
    protected int step_distance;   //the distance that is walked during wandering before a new target is chosen
    protected int dist_walked;        //the amount of distance walked since the last target choice

    //parameters for the wander move of the worker
    private Random random = new Random(); // Random object to generate directions of travel
    private int big_turn_threshold; //the amount of steps taken before a mayor turn is possible
    private int big_turn_count;

    // parameters attaining to the use of the pheromones
    // the size of pheromone is scaled with its strenght
    private int start_phero_size;   //the size at dropping the phero
    private int max_phero_size;    //the limit on the size of the pheromone
    private int start_phero_strength;
    private int max_phero_strength;
    private int phero_strength_load_factor; //the factor of infleunce of the load of the worker on the start strength of the pheromone
    private int degrade_time;      //how many ticks it takes to degrade the pheromone by 1

    // parameters fot the dropping of pheromones
    private int phero_drop_dist;   //the distance needed to walk before dropping a pheromone
    private int dist_walked_since_drop;                    //the distance walked since last dropping of pheromone
    private boolean drop_enabled;


    //private int phero_detect_dist       = (int)(step_distance*1.5); // for the basic case
    private int phero_detect_dist;  // for the advanced case

    //parameters for the return mechanism of the workers
    private int ticks_before_return ; // the number of ticks before the worker has to return if it wants to find it's way home
    private int ticks_since_enter     ;    // the counter for
    private Abstr_Enterable_object last_entered_object ;

    //parameter for  the vector based path finding
    private double vector_ignore_chance;    //the chance that a vector during vector calculation is ignored
    private double factor;                  //the factor



    public Abstr_Task()
    {
        this.model = Model.getInstance();
        Abstr_Behaviour behaviour = model.getBehaviour();

        // parameters for the movement of the worker
        worker_speed = behaviour.getWorker_speed();                   //the size of the step of a worker at each tick
        step_distance = behaviour.getStep_distance();   //the distance that is walked during wandering before a new target is chosen
        dist_walked = behaviour.getDist_walked();        //the amount of distance walked since the last target choice

        //parameters for the wander move of the worker
        big_turn_threshold = behaviour.getBig_turn_threshold(); //the amount of steps taken before a mayor turn is possible
        big_turn_count = behaviour.getBig_turn_count();

        // parameters attaining to the use of the pheromones
        // the size of pheromone is scaled with its strenght
        start_phero_size = behaviour.getStart_phero_size();   //the size at dropping the phero
        max_phero_size = behaviour.getMax_phero_size();    //the limit on the size of the pheromone
        start_phero_strength = behaviour.getStart_phero_strength();
        max_phero_strength = behaviour.getMax_phero_strength();
        phero_strength_load_factor = behaviour.getPhero_strength_load_factor(); //the factor of infleunce of the load of the worker on the start strength of the pheromone
        degrade_time = behaviour.getDegrade_time();      //how many ticks it takes to degrade the pheromone by 1

        // parameters fot the dropping of pheromones
        phero_drop_dist         = behaviour.getPhero_drop_dist();   //the distance needed to walk before dropping a pheromone
        dist_walked_since_drop  = behaviour.getDist_walked_since_drop();                    //the distance walked since last dropping of pheromone
        drop_enabled        = behaviour.isDrop_enabled();


        phero_detect_dist       = behaviour.getPhero_detect_dist(); // for the basic case


        //parameters for the return mechanism of the workers
        ticks_before_return     = behaviour.getTicks_before_return(); // the number of ticks before the worker has to return if it wants to find it's way home
        ticks_since_enter       = behaviour.getTicks_since_enter();    // the counter for
        Abstr_Enterable_object last_entered_object = behaviour.getLast_entered_object();

        vector_ignore_chance = behaviour.getIgnore_chance();


    }
    public abstract boolean is_obj_relevant_to_task(Worker worker, Abstr_Object obj);
    public abstract void select_target(Worker worker);
    public abstract void move(Worker worker);
    public abstract boolean on_reached(Worker worker, Abstr_Object obj);
    public abstract boolean at_base(Worker worker, Base base);
    public abstract boolean out_of_base(Worker worker, Abstr_Object base);

    public void tick(Worker worker)
    {
        ticks_since_enter++;

        //check if the worker has reached an object
        Abstr_Object reached = model.check_if_reached_an_object(worker.getX(), worker.getY());

        //if the reached object is the one he entered last, the return mechanism counter is reset
        if(will_enter_reached(worker, reached))   //returns true if the worker goes into the object
        {
            //executed when the worker enters an object
            if(worker.getState() != Worker_State_Enum.inside) //when the workers wasn't yet inside the object
            {
                drop_pheromone(worker);
                worker.setState(Worker_State_Enum.inside);
            }

        }
        else
        {
            //executed when the worker is on the move
            if(worker.getState() == Worker_State_Enum.inside)   //if the worker was previously inside an object
            {
                worker.clear_visited_objects();
                drop_pheromone(worker);
                setRefresh_target(true);
                worker.setCurr_target_object(null);
            }

            if(is_at_target_halve(worker)) //only used for advanced tasks
            {
                drop_pheromone(worker);
            }

            if (is_at_target(worker))   // if the worker is at/beyond the current target or just set to refresh the target
            {
                drop_pheromone(worker);
                worker.clear_detected_objects();
                model.find_objects(worker);

                //add the current targetted object to the visited objects
                Abstr_Object curr_target = worker.getCurr_target_object();
                if(curr_target != null )
                {
                    worker.add_visited_pheromone(curr_target );
                }

                setHalve(false); //only for the adv setting

                select_target(worker);
            }

            move(worker);
        }

    }

    protected boolean is_at_target_halve(Worker worker)
    {
        return false;
    }

    private void select_return_target(Worker worker)
    {
        if(worker.getState() != Worker_State_Enum.returning)
        {
            worker.clear_visited_objects();
        }

        worker.setState(Worker_State_Enum.returning);
        Abstr_Object obj =  worker.closest_owned_phero_of_type(worker,getTask(),worker.getResource_type());
        if(obj != null)
        {
            go_to_phero(worker,obj);
            return;
        }

        wanderWithin(worker);

    }

    public boolean will_enter_reached(Worker worker, Abstr_Object reached)
    {
        if(reached == null)
        {
            return false;
        }
        else
        {
            if(reached instanceof Base)
                return at_base(worker,(Base) reached);
            else
                return on_reached(worker,reached);
        }
    }


    public boolean checkOutsideOfBorders(int x, int y, int sizeX, int sizeY)
    {
        if(x < 0 || y < 0 || x > sizeX || y > sizeY)
        {
            return true;
        }
        return false;
    }


    protected void wanderWithin(Worker worker)
    {
        //each step of step_distance long the ant turns -90° to 90° after big_turn_threshold the ant can turn upto 360°

        double corner = (random.nextDouble()-0.5)*(Math.PI*0.5); //make a bend of min -90° and 90°
        double totCorner = worker.getCurrDirection() + corner; //add bend to currecnt direction of the workers

        //generate a target based on the step_distance
        if(big_turn_count >= big_turn_threshold)
        {
            totCorner = (random.nextDouble())*(Math.PI); //make a bend of 0° to 360°
            big_turn_count = 0;
        }
        big_turn_count = big_turn_count +1;

        int new_x = worker.getX() +  (int) (Math.cos(totCorner)* step_distance);
        int new_y = worker.getY() + (int) (Math.sin(totCorner)* step_distance);

        worker.setCurr_target_object(null);
        setTarget(worker,new_x,new_y);

    }

    public void walk_straight_for(Worker worker, int steps)
    {
        int new_x = worker.getX() +  (int) (Math.cos(worker.getCurrDirection())* step_distance*steps);
        int new_y = worker.getY() + (int) (Math.sin(worker.getCurrDirection())* step_distance*steps);

        setTarget(worker, new_x,new_y);
    }

    public boolean select_target_from_vectors(Worker worker)
    {
        int w_x = worker.getX();
        int w_y = worker.getY();
        double x_tot_vector = 0;
        double y_tot_vector = 0;

        if(worker.getDetected_objects().size() != 0 )
        {
            int count = 0;
            for(CustomStruct str : worker.getDetected_objects())
            {

                if(random.nextDouble() > vector_ignore_chance || count < 3)
                {
                    double dist = (double) str.getDistance();
                    if(dist != 0)
                    {
                        count++;
                        double x_unit =  (str.getX_vector()/dist);
                        double y_unit =  (str.getY_vector()/dist);
                        int pheroStrength = ((Pheromone) str.getObject()).getStrength();

                        int attract_strength = 1+ (int)(pheroStrength*1000/dist);

                        x_tot_vector = x_tot_vector + x_unit*attract_strength;
                        y_tot_vector = y_tot_vector + y_unit*attract_strength;
                    }
               }
            }

            double angle = worker.get_corner_relative_to((int)(w_x + x_tot_vector*100),(int) (w_y+y_tot_vector*100 ));
            worker.setCurrDirection(angle);
            walk_straight_for(worker,1);
            ((Worker_visual) worker.getVisual()).update_vectors(worker);
            worker.add_detected_phermones_within_distance_to_visited(step_distance);
            System.out.println("new angle = " + angle);

            return true;
        }
        return false;

    }

    public int move_worker(Worker worker)
    {
        int x = worker.getX();
        int y = worker.getY();
        int Xdist = get_x_dist(x,target_x);
        int Ydist = get_y_dist(y,target_y);

        int dist = (int)Math.abs( Math.sqrt(Math.pow(Xdist,2)+ Math.pow(Ydist,2)));

        int newX = target_x;
        int newY = target_y;

        int distWalked = 0 ;

        double corner = worker.get_corner_relative_to(target_x,target_y);
        worker.setCurrDirection(corner);

        if (dist > worker_speed)
        {
            newX = x +  (int) (Math.cos(corner)* worker_speed);
            newY = y + (int) (Math.sin(corner)* worker_speed);
            distWalked = (int) (Math.sqrt(Math.pow(newX-x,2)+Math.pow(newY-y,2)));
        }

        worker.move(newX,newY);
        dist_walked =  dist_walked + distWalked;
        dist_walked_since_drop =  dist_walked_since_drop + distWalked;

        return distWalked;
    }

    public void check_dropping_pheromone(Worker worker)
    {
        if(dist_walked_since_drop >= phero_drop_dist)
        {
            drop_pheromone(worker);
        }
    }

    public void drop_pheromone(Worker worker)
    {
        if(drop_enabled)
        {
            int drop_strength  = get_drop_strength(worker.get_total_amount_of_load());
            model.drop_pheromone(worker,worker.getX(), worker.getY(),start_phero_size ,worker.getTask().getTask(),worker.getResource_type() , drop_strength,degrade_time,max_phero_size);
        }

        dist_walked_since_drop = 0;
    }

    private int get_drop_strength(int load)
    {
        double factor = (1+(load/(double)phero_strength_load_factor));
        return (int) (start_phero_strength*factor);
    }


    public boolean is_at_target(Worker worker)
    {


        return (dist_walked >= step_distance ||
                ((target_x == worker.getX() && target_y == worker.getY())) ||
                isRefresh_target() ) ;

    }

    public boolean needs_to_return()
    {
        return(ticks_before_return <= ticks_since_enter);
    }


    public void go_to_phero(Worker worker, Abstr_Object obj)
    {
        setTarget(worker,obj.getX(),obj.getY());
    }

    public int get_x_dist(int curr_x, int x_target)
    {
        return x_target - curr_x;
    }

    public int get_y_dist(int curr_y, int y_target)
    {
        return y_target - curr_y;
    }

    public double getaTanCorner(int xdist, int ydist) {
        double corner = 0;
        if(xdist < 0)
            corner = Math.PI+ Math.atan((double) ydist /(double) xdist) ;
        else
        {
            if(ydist < 0)
                corner = 2*Math.PI+ Math.atan((double) ydist /(double) xdist) ;
            else
                corner = Math.atan((double) ydist /(double) xdist) ;
        }
        return corner;
    }


    public int getTarget_x() {
        return target_x;
    }


    public int getTarget_y() {
        return target_y;
    }

    public void setTarget(Worker worker, int target_x, int target_y)
    {
        int sizeX = model.get_size_x_field();
        int sizeY = model.get_size_y_field();

        int new_x = target_x;
        int new_y = target_y;

        double corner = worker.get_corner_relative_to(target_x, target_y);
        worker.setCurrDirection(corner);


        if(checkOutsideOfBorders(target_x, target_y,sizeX,sizeY))
        {
            corner = corner - Math.PI;
            new_x  = worker.getX() +  (int) (Math.cos(corner)* step_distance);
            new_y = worker.getY() + (int) (Math.sin(corner)* step_distance);
        }


        this.target_x = new_x;
        this.target_y = new_y;
        setDist_walked(0);

    }


    public int getDist_walked() {
        return dist_walked;
    }

    public void setDist_walked(int dist_walked) {
        this.dist_walked = dist_walked;
    }

    public Task_Enum getTask() {
        return task;
    }

    public void setTask(Task_Enum task) {
        this.task = task;
    }

    public void test_tan_function()
    {
        DecimalFormat df2 = new DecimalFormat(".##");

        int amount  = 9;

        double[] angle = new double[amount];
        double[] expected_angle = new double[amount];


        int parts = 4;
        System.out.println("\nchecking the tan corner calculation");
        System.out.println("the first angle column needs to be the same as the last tan_angle column");
        System.out.println("angle  cos  sin  x_dist  Y_dist  tan_angle ");

        for(int i=0;i<amount;i++)
        {
            angle[i] = (Math.PI/parts)*i;
            double cos = Math.cos(angle[i]);
            double sin = Math.sin(angle[i]);
            int x = (int) (cos* 10);
            int y = (int) (sin * 10);

            double tan = getaTanCorner(x,y);

            System.out.println(df2.format(angle[i]) + "  "  + df2.format(cos)  + "  " + df2.format(sin) + "  " + x + "  " +  y + "  " + df2.format(tan));

        }

    }

    public boolean isDrop_enabled() {
        return drop_enabled;
    }

    public void setDrop_enabled(boolean drop_enabled) {
        this.drop_enabled = drop_enabled;
    }

    public int getPhero_detect_dist() {
        return phero_detect_dist;
    }

    public void setPhero_detect_dist(int phero_detect_dist) {
        this.phero_detect_dist = phero_detect_dist;
    }

    public boolean isRefresh_target() {
        boolean state = refresh_target;
        refresh_target =false;
        return state;
    }

    public void setRefresh_target(boolean refresh_target) {
        this.refresh_target = refresh_target;
    }

    public int getTicks_since_enter() {
        return ticks_since_enter;
    }

    public void setTicks_since_enter(int ticks_since_enter) {
        this.ticks_since_enter = ticks_since_enter;
    }

    public Abstr_Enterable_object getLast_entered_object() {
        return last_entered_object;
    }

    public boolean isLast_entered_object(Abstr_Enterable_object obj)
    {
        if(obj != null && last_entered_object != null)
        {
            return last_entered_object.equals(obj);
        }
        else
        {
            return false;
        }

    }

    public void setLast_entered_object(Abstr_Enterable_object last_entered_object)
    {
        this.last_entered_object = last_entered_object;
        setTicks_since_enter(0);
    }

    public boolean isFound_resource() {
        return false;
    }

    public void setFound_resource(boolean found_resource) {
    }


    public boolean isHalve() {
        return false;
    }

    public void setHalve(boolean halve) {

    }

    public boolean isReturn_from_resource() {
        return false;
    }

    public void setReturn_from_resource(boolean return_from_resource) {
    }

    public boolean isEnter_base() {
        return enter_base;
    }

    public void setEnter_base(boolean enter_base) {
        this.enter_base = enter_base;
    }
}
