package Model_pk.Behaviour;

import Model_pk.Objects.Enterables.Abstr_Enterable_object;

/**
 * Basic parameters and settings available to the behaviours of the workers.
 */
public abstract class Abstr_Behaviour
{
    protected Class<?> task_explorer;
    protected Class<?> task_miner;
    protected Class<?> task_transporter;


    // parameters for the movement of the worker
    protected int worker_speed = 3;                   //the size of the step of a worker at each tick
    protected int step_distance = worker_speed *20;   //the distance that is walked during wandering before a new target is chosen
    protected int dist_walked = step_distance;        //the amount of distance walked since the last target choice

    //parameters for the wander move of the worker
    protected int big_turn_threshold = 10; //the amount of steps taken before a mayor turn is possible
    protected int big_turn_count = 0;

    // parameters attaining to the use of the pheromones
    // the size of pheromone is scaled with its strenght
    protected int start_phero_size = 5;   //the size at dropping the phero
    protected int max_phero_size = 15;    //the limit on the size of the pheromone
    protected int start_phero_strength = 10;
    protected int max_phero_strength = 50;
    protected int phero_strength_load_factor = 1; //the factor of infleunce of the load of the worker on the start strength of the pheromone
    protected int degrade_time = 150;      //how many ticks it takes to degrade the pheromone by 1

    // parameters fot the dropping of pheromones
    protected int phero_drop_dist         = step_distance;   //the distance needed to walk before dropping a pheromone [NOT USED?]
    protected int dist_walked_since_drop  = 0;                    //the distance walked since last dropping of pheromone
    protected boolean drop_enabled        = true;

    protected int phero_detect_dist       = (int)(step_distance*1);  // for the advanced case

    //parameters for the return mechanism of the workers
    protected int ticks_before_return     = (int) (degrade_time*start_phero_strength*0.5); // the number of ticks before the worker has to return if it wants to find it's way home
    protected int ticks_since_enter       = 0;    // the counter for
    protected int wander_limit = 15;
    protected Abstr_Enterable_object last_entered_object = null;

    //parameters for the behavior advanced
    protected  double ignore_chance;

    //parameters for the behavior basic
    protected boolean incalculate_strength = false;  // whether the workers decide on following the strongest pheromone or only distance
    protected boolean incalculate_direction = false; // whether the workers decide on giving more infleunce to the pheromones straight in front of him
    protected double  strength_influence  = 1 ;   // how much infleunce the strength has when taken into account

    protected boolean limited_visited_memory = false;
    protected int visited_objects_size = 5;

    protected boolean turn_arround_at_wander= false;



    public Abstr_Task getTask_explorer() {

        try {
            Abstr_Task task = (Abstr_Task)  task_explorer.newInstance();
            task.setWander_limit(this.wander_limit);
            return task;

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Abstr_Task getTask_explorer(int wanderlimit) {

        try {
            Abstr_Task task = (Abstr_Task)  task_explorer.newInstance();
            task.setWander_limit(wanderlimit);
            return task;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Abstr_Task getTask_miner() {
        try {
            return (Abstr_Task) task_miner.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Abstr_Task getTask_transporter() {
        try {
            return (Abstr_Task) task_transporter.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getWorker_speed() {
        return worker_speed;
    }

    public int getStep_distance() {
        return step_distance;
    }

    public int getDist_walked() {
        return dist_walked;
    }

    public int getBig_turn_threshold() {
        return big_turn_threshold;
    }

    public int getBig_turn_count() {
        return big_turn_count;
    }

    public int getStart_phero_size() {
        return start_phero_size;
    }

    public int getMax_phero_size() {
        return max_phero_size;
    }

    public int getStart_phero_strength() {
        return start_phero_strength;
    }

    public int getMax_phero_strength() {
        return max_phero_strength;
    }

    public int getPhero_strength_load_factor() {
        return phero_strength_load_factor;
    }

    public int getDegrade_time() {
        return degrade_time;
    }

    public int getPhero_drop_dist() {
        return phero_drop_dist;
    }

    public int getDist_walked_since_drop() {
        return dist_walked_since_drop;
    }

    public boolean isDrop_enabled() {
        return drop_enabled;
    }

    public int getPhero_detect_dist() {
        return phero_detect_dist;
    }

    public int getTicks_before_return() {
        return ticks_before_return;
    }

    public int getTicks_since_enter() {
        return ticks_since_enter;
    }

    public Abstr_Enterable_object getLast_entered_object() {
        return last_entered_object;
    }

    public double getIgnore_chance() {
        return ignore_chance;
    }

    public boolean isIncalculate_strength() {
        return incalculate_strength;
    }

    public double getStrength_influence() {
        return strength_influence;
    }

    public boolean isLimited_visited_memory() {
        return limited_visited_memory;
    }

    public int getVisited_objects_size() {
        return visited_objects_size;
    }
    public boolean isTurn_arround_at_wander() {
        return turn_arround_at_wander;
    }

    public boolean isIncalculate_direction() {
        return incalculate_direction;
    }

    public void setPhero_detect_dist(int phero_detect_dist) {
        this.phero_detect_dist = phero_detect_dist;
    }

    public void setDegrade_time(int degrade_time) {
        this.degrade_time = degrade_time;
    }

    public int getWander_limit() {
        return wander_limit;
    }

    public void setWander_limit(int wander_limit) {
        this.wander_limit = wander_limit;
    }
}
