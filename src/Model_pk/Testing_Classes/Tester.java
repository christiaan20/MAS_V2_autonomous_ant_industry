package Model_pk.Testing_Classes;

import Model_pk.Enums.Resource_Type_Enum;
import Model_pk.Model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * A tester class that accounts for setting up the goals that need to be fulfilled in a sequential way.
 * It will write the results of the test to an external .txt file when the goals are reached.
 */
public class Tester {

    private Model model;
    private ArrayList<HashMap<Resource_Type_Enum, Integer>> goals;
    private ArrayList<Integer> tick_counts_goals;
    private ArrayList<String> surplus_goals;

    private ArrayList<HashMap<Resource_Type_Enum, Integer>> surplus_maps;
    private ArrayList<Integer> dead_workers;
    private ArrayList<Integer> living_workers;

    private int simulate_count;
    private int total_simulate_count;


    public Tester() throws IOException {

        this.model = Model.getInstance();
        this.goals = new ArrayList<>();
        this.tick_counts_goals = new ArrayList<>();
        this.surplus_goals = new ArrayList<>();
        init_goal();
        init_log_file();

        this.surplus_maps   = new ArrayList<>();
        this.dead_workers   = new ArrayList<>();;
        this.living_workers = new ArrayList<>();;
    }


    public void init_goal(){


       HashMap<Resource_Type_Enum, Integer> first_goal = fill_resources_with(500,100,20,250,100);
        HashMap<Resource_Type_Enum, Integer> second_goal = fill_all_resources_with(250);
        HashMap<Resource_Type_Enum, Integer> third_goal = fill_resources_with(0,50,600,500,160);

        goals.add(first_goal);
        goals.add(second_goal);
        goals.add(third_goal);


       //goals.add(fill_all_resources_with(20));

    }

    public HashMap<Resource_Type_Enum, Integer> fill_all_resources_with(int amount){

        HashMap<Resource_Type_Enum, Integer> goal = new HashMap<>();

        for(Resource_Type_Enum type: Resource_Type_Enum.values())
        {
            goal.put(type, amount);
        }
        return goal;
    }

    public HashMap<Resource_Type_Enum, Integer> fill_resources_with(int stone,int iron, int coal, int copper, int uranium){

        HashMap<Resource_Type_Enum, Integer> goal = new HashMap<>();

        for(Resource_Type_Enum type: Resource_Type_Enum.values())
        {
            switch (type)
            {

                case Stone:
                    goal.put(type, stone);
                    break;
                case Iron:
                    goal.put(type, iron);
                    break;
                case Coal:
                    goal.put(type, coal);
                    break;
                case Copper:
                    goal.put(type, copper);
                    break;
                case Uranium:
                    goal.put(type, uranium);
                    break;
            }
        }
        return goal;
    }


    private void init_log_file()throws IOException {

        write_to_log_file("");
        write_to_log_file("Tested on " + get_current_date());
        write_to_log_file("Goals to reach:");
        write_goals_to_log();
        write_to_log_file("");
    }


    private void write_goals_to_log() throws IOException {

        for( HashMap<Resource_Type_Enum, Integer> goal: goals){
            write_to_log_file( goal.toString());
        }

    }

    public boolean all_goals_reached(){

        if( goals.size() == 0)
            return true;

        return false;

    }

    public void write_results_to_file(){

        try
        {
            write_to_log_file("Amount of ticks to reach goals");

            for( int ticks: tick_counts_goals){
                write_to_log_file( "" + ticks );
            }

            write_to_log_file( "");
            write_to_log_file("Amount of surplus when goal was reached");

            for( String surplus: surplus_goals){
                write_to_log_file(surplus);
            }
            write_to_log_file( "");
            write_to_log_file( "Amount of workers alive: " + model.count_living_workers());

            write_to_log_file( "");
            write_to_log_file( "Amount of dead workers: " + model.count_dead_worker());

            write_to_log_file( "");
            write_to_log_file("Ended on " + get_current_date());

            write_results_to_excel_file();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void init_excel_file()throws IOException
    {
     write_to_excel_file("tot_sim_nr; sim_nr; ID; goal_nr; ticks; sur_Stone; sur_Iron; sur_Coal; sur_Copper; sur_Uranium; living_workers; dead_workers; tot_ticks");
    }

    public void write_results_to_excel_file()
    {
        String log = "";

        if(tick_counts_goals.size() == 0)
        {
            log = log.concat(this.total_simulate_count + " ;");
            log = log.concat(this.simulate_count + " ;");
            log = log.concat(Test_Settings.getInstance().getAutomatic_step_counter() + " ;");
            log = log.concat(0 + " ;");
            log = log.concat(0 + " ;");
            for(Resource_Type_Enum type: Resource_Type_Enum.values())
            {
                log = log.concat(0 + " ;");
            }
            log = log.concat(model.count_living_workers() + " ;");
            log = log.concat(model.count_dead_worker() + " ;");
            log = log.concat(model.getTickcount() + " ;");
            try {
                write_to_excel_file(log);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
            for( int i = 0;i < tick_counts_goals.size();i++)
            {
                log = "";
                log = log.concat(this.total_simulate_count + " ;");
                log = log.concat(this.simulate_count + " ;");
                log = log.concat(Test_Settings.getInstance().getAutomatic_step_counter() + " ;");
                log = log.concat(i + " ;");
                log = log.concat(tick_counts_goals.get(i).toString() + " ;");
                for(Resource_Type_Enum type: Resource_Type_Enum.values())
                {
                    log = log.concat(surplus_maps.get(i).get(type) + " ;");
                }
                log = log.concat(dead_workers.get(i) + " ;");
                log = log.concat(living_workers.get(i) + " ;");
                log = log.concat(model.getTickcount() + " ;");
                try {
                    write_to_excel_file(log);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    public boolean is_goal_reached(int tickcount)  {

        if( all_goals_reached() ){
            return true;
        }

        HashMap<Resource_Type_Enum, Integer> current_resources = get_current_resources();
        int to_reach_amount;
        int current_amount;

        HashMap<Resource_Type_Enum, Integer> goal = goals.get(0);

        if (goal == null) {
            System.out.println("no goal");
            return false;
        }

        for(Resource_Type_Enum type: Resource_Type_Enum.values())
        {

            to_reach_amount = goal.get(type);
            current_amount = current_resources.get(type);

            if( current_amount < to_reach_amount )
                return false;

        }

        System.out.println("Goal reached");
        goal_is_reached(tickcount);
        return true;

    }

    public void goal_is_reached(int tickcount)
    {

        HashMap<Resource_Type_Enum, Integer> goal = goals.get(0);
        HashMap<Resource_Type_Enum, Integer> resources = get_current_resources();
        tick_counts_goals.add(tickcount);

        int new_amount;

        HashMap<Resource_Type_Enum, Integer> new_surplus = new HashMap<>(); //chris

        for(Resource_Type_Enum type: Resource_Type_Enum.values())
        {
            new_amount = resources.get(type) - goal.get(type);
            resources.replace(type, new_amount);

            new_surplus.put(type,new_amount);//chris
        }

        surplus_goals.add(resources.toString());

        surplus_maps.add(new_surplus);//chris
        dead_workers.add(model.count_dead_worker()); //chris
        living_workers.add(model.count_living_workers());//chris

        goals.remove(0);

    }

    public String get_current_date(){

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(cal.getTime()) ;

    }


    public void write_to_log_file(String info) throws IOException {

        FileWriter fileWriter = new FileWriter("Simulation_output.txt", true);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println(info);
        printWriter.close();
    }

    public void write_to_excel_file(String info) throws IOException {

        FileWriter fileWriter = new FileWriter("excel_output.txt", true);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println(info);
        printWriter.close();
    }


    private HashMap<Resource_Type_Enum, Integer> get_current_resources()
    {
        return model.getBase().get_obtained_resources_resources();
    }

    private void set_current_resources(HashMap<Resource_Type_Enum, Integer> new_resources)
    {
        model.getBase().set_obtained_resources(new_resources);
    }


    public HashMap<Resource_Type_Enum, Integer> get_current_goal(){

        if( all_goals_reached())
            return fill_all_resources_with(0);

        return goals.get(0);

    }


    public void setSimulate_count(int simulate_count) {
        this.simulate_count = simulate_count;
    }


    public void setTotal_simulate_count(int total_simulate_count) {
        this.total_simulate_count = total_simulate_count;
    }
}
