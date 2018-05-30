package Model_pk.Behaviour;

import Model_pk.Enterables.Enterable_object;

import java.util.Random;

/**
 * Created by christiaan on 10/05/18.
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
    protected int phero_strength_load_factor = 2; //the factor of infleunce of the load of the worker on the start strength of the pheromone
    protected int degrade_time = 150;      //how many ticks it takes to degrade the pheromone by 1

    // parameters fot the dropping of pheromones
    protected int phero_drop_dist         = step_distance;   //the distance needed to walk before dropping a pheromone
    protected int dist_walked_since_drop  = 0;                    //the distance walked since last dropping of pheromone
    protected boolean drop_enabled        = true;


    //private int phero_detect_dist       = (int)(step_distance*1.5); // for the basic case
    protected int phero_detect_dist        = (int)(step_distance*1);  // for the advanced case

    //parameters for the return mechanism of the workers
    protected int ticks_before_return     = (int) (degrade_time*start_phero_strength*0.5); // the number of ticks before the worker has to return if it wants to find it's way home
    protected int ticks_since_enter       = 0;    // the counter for
    protected Enterable_object last_entered_object = null;

    protected  double ignore_chance;



    public Abstr_Task getTask_explorer() {

        try {
            return (Abstr_Task) task_explorer.newInstance();
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

    public Enterable_object getLast_entered_object() {
        return last_entered_object;
    }

    public double getIgnore_chance() {
        return ignore_chance;
    }
}
