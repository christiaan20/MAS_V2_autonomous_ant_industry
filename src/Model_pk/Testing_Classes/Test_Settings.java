package Model_pk.Testing_Classes;

import Model_pk.Behaviour.Abstr_Behaviour;
import Model_pk.Behaviour.Basic.Task.Task_Basic.Behaviour_Basic;
import Model_pk.Behaviour.Basic.Task_Adv.Behaviour_Adv;
import Model_pk.Model;
import Model_pk.Task_managers.Abstr_Task_manager;
import Model_pk.Task_managers.Task_Manager_Simple;
import Model_pk.Task_managers.Task_manager_extended;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by christiaan on 15/06/18.
 */
public class Test_Settings
{
    static Test_Settings test_setting;

    parameter_setting pheromone_dist;
    parameter_setting starting_workers;
    pheromone_enum pheromone_setting;
    parameter_setting scenario;
    task_distribution_enum task_distribution;

    private Map<parameter_setting,Integer>  phero_dist_settings_basic = new HashMap<>();
    private Map<parameter_setting,Integer>  phero_dist_settings_adv = new HashMap<>();
    private Map<parameter_setting,Integer>  starting_workers_settings = new HashMap<>();

    private int automatic_step_counter; //the counter for keeping at which setting the simulation is.
    private int start_automatic_step_counter;
    private ArrayList<Parameter_Struct> all_settings = new ArrayList<>();


    public Test_Settings()
    {
        this.pheromone_dist = parameter_setting.medium;
        this.starting_workers = parameter_setting.medium;
        this.pheromone_setting = pheromone_enum.basic_strength_direction;
        this.scenario = parameter_setting.big;
        this.task_distribution = task_distribution_enum.advanced;

        phero_dist_settings_basic .put(parameter_setting.small,60);
        phero_dist_settings_basic .put(parameter_setting.medium,90);
        phero_dist_settings_basic .put(parameter_setting.big,120);

        phero_dist_settings_adv.put(parameter_setting.small,30);
        phero_dist_settings_adv.put(parameter_setting.medium,60);
        phero_dist_settings_adv.put(parameter_setting.big,90);

        //starting_workers_settings.put(parameter_setting.small,5);
        //starting_workers_settings.put(parameter_setting.medium,15);
        //starting_workers_settings.put(parameter_setting.big,25);

        starting_workers_settings.put(parameter_setting.small,10);
        starting_workers_settings.put(parameter_setting.medium,20);
        starting_workers_settings.put(parameter_setting.big,40);

        automatic_step_counter = 0;
        start_automatic_step_counter =  automatic_step_counter;

        //generate all possible test_setting objects
        for(parameter_setting scene : parameter_setting.values())
        {
            if(scene != parameter_setting.medium)
            {
                for (pheromone_enum policy : pheromone_enum.values())
                {
                    //for (parameter_setting phero_dist : parameter_setting.values())
                    //{
                        parameter_setting phero_dist = parameter_setting.medium;
                        for (parameter_setting workers : parameter_setting.values())
                        {
                            for ( task_distribution_enum tasks :  task_distribution_enum.values())
                            {
                                all_settings.add(new Parameter_Struct(phero_dist,workers,policy,scene,tasks));
                            }
                        }
                    //}
                }
            }
        }



    }

    public static Test_Settings getInstance(){
        if(test_setting == null){
            test_setting = new Test_Settings();
        }
        return test_setting;
    }

    public parameter_setting getPheromone_dist() {
        return pheromone_dist;
    }

    public int getPheromone_dist_value() {
       /* if(pheromone_setting == pheromone_enum.advanced)
            return phero_dist_settings_adv.get(pheromone_dist);
        else*/
            return phero_dist_settings_basic.get(pheromone_dist);
    }

    public void setPheromone_dist(parameter_setting pheromone_dist) {
        this.pheromone_dist = pheromone_dist;
    }

    public parameter_setting getStarting_workers() {
        return starting_workers;
    }

    public int getStarting_workers_value() {
        return starting_workers_settings.get(starting_workers);
    }

    public void setStarting_workers(parameter_setting starting_workers) {
        this.starting_workers = starting_workers;
    }

    public pheromone_enum getPheromone_setting() {
        return pheromone_setting;
    }

    public void setPheromone_setting(pheromone_enum pheromone_setting) {
        this.pheromone_setting = pheromone_setting;
    }

    public parameter_setting getScenario() {
        return scenario;
    }

    public void setScenario(parameter_setting scenario) {
        this.scenario = scenario;
    }

    public task_distribution_enum getTask_distribution() {
        return task_distribution;
    }

    public void setTask_distribution(task_distribution_enum task_distribution) {
        this.task_distribution = task_distribution;
    }

    public Abstr_Behaviour getBehaviour()
    {
        boolean standard_turn_arround_at_wander = true;
        switch(pheromone_setting)
        {
           /* case advanced:
                return new Behaviour_Adv();*/
            case basic_normal:
                return new Behaviour_Basic();
            case basic_strength:
                return new Behaviour_Basic(true, false,false ,standard_turn_arround_at_wander);
            case basic_direction:
                return new Behaviour_Basic(false, true,false ,standard_turn_arround_at_wander);
            case basic_strength_direction:
                return new Behaviour_Basic(true, true,false ,standard_turn_arround_at_wander);

        }
        return null;
    }

    public Abstr_Task_manager getTask_manager()
    {
        if(task_distribution == task_distribution_enum.advanced)
        {
            return new Task_manager_extended();
        }
        else
        {
            return new Task_Manager_Simple();
        }
    }

    public boolean next_step()
    {
        automatic_step_counter++;
        if(automatic_step_counter <= start_automatic_step_counter+10)
        //if(automatic_step_counter < all_settings.size())
        {
            copy_struct(all_settings.get(automatic_step_counter));
            return true;
        }
        return false;
    }

    public boolean copy_current_step()
    {
        copy_struct(all_settings.get(automatic_step_counter));
        return true;
    }

    public void copy_struct(Parameter_Struct settings)
    {
        this.pheromone_dist = settings.getPheromone_dist();
        this.starting_workers = settings.getStarting_workers();
        this.pheromone_setting = settings.getPheromone_setting();
        this.task_distribution = settings.getTask_distribution();
        this.scenario = settings.getScenario();
    }

    public void write_settings()
    {
        Model model = Model.getInstance();
        Tester tester =  model.getTester();
        try {
            tester.write_to_log_file("");
            tester.write_to_log_file("--- SIMULATION SETTINGS ---");
            tester.write_to_log_file("set number                    : " + automatic_step_counter);
            tester.write_to_log_file("Pheromone detection distance  : " + (getPheromone_dist().toString()) +  "  " + getPheromone_dist_value());
            tester.write_to_log_file("Starting workers amount       : " + (getStarting_workers().toString()) +  "  "  + getStarting_workers_value());
            tester.write_to_log_file("Pheromone processing behavior : " + getPheromone_setting().toString());
            tester.write_to_log_file("Task manager version          : " + getTask_distribution());
            tester.write_to_log_file("scenario version              : " + getScenario());
            tester.write_to_log_file("");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getAutomatic_step_counter() {
        return automatic_step_counter;
    }
}

