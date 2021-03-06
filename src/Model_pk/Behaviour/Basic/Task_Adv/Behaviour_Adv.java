package Model_pk.Behaviour.Basic.Task_Adv;

import Model_pk.Behaviour.Abstr_Behaviour;
import Model_pk.Objects.Enterables.Abstr_Enterable_object;

/**
 * Created by christiaan on 28/05/18.
 */
public class Behaviour_Adv extends Abstr_Behaviour
{






    public Behaviour_Adv()
    {
        try {
            super.task_explorer     = Class.forName("Model_pk.Behaviour.Basic.Task_Adv.Task_Explorer_Adv");
            super.task_miner        = Class.forName("Model_pk.Behaviour.Basic.Task_Adv.Task_Miner_Adv");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
    }

        // parameters for the movement of the worker
        worker_speed = 3;                   //the size of the step of a worker at each tick
        step_distance = worker_speed *15;   //the distance that is walked during wandering before a new target is chosen
        dist_walked = step_distance;        //the amount of distance walked since the last target choice

        //parameters for the wander move of the worker
        big_turn_threshold = 10; //the amount of steps taken before a mayor turn is possible
        big_turn_count = 0;

        // parameters attaining to the use of the pheromones
        // the size of pheromone is scaled with its strenght
        start_phero_size = 5;   //the size at dropping the phero
        max_phero_size = 15;    //the limit on the size of the pheromone
        start_phero_strength = 10;
        max_phero_strength = 50;
        phero_strength_load_factor = 2; //the factor of infleunce of the load of the worker on the start strength of the pheromone
        degrade_time = 150;      //how many ticks it takes to degrade the pheromone by 1

        // parameters fot the dropping of pheromones
        phero_drop_dist         = step_distance*2;   //the distance needed to walk before dropping a pheromone
        dist_walked_since_drop  = 0;                    //the distance walked since last dropping of pheromone
        drop_enabled        = true;


        phero_detect_dist       = (int)(step_distance*1); // DIFFERENT FROM BASIC


        //parameters for the return mechanism of the workers
       // ticks_before_return     = (int) (degrade_time*start_phero_strength*0.5); // the number of ticks before the worker has to return if it wants to find it's way home
        ticks_since_enter       = 0;    // the counter for
        Abstr_Enterable_object last_entered_object = null;

        ignore_chance = 0.5;


    }
}
