package Model_pk;

import Model_pk.Enterables.Base;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Gebruiker on 22/05/2018.
 */
public class Tester {

    private Model model;
    private ArrayList<HashMap<Resource_Type, Integer>> goals;
    private boolean results_written_to_file;
    private ArrayList<Integer> tick_counts_goals;
    private ArrayList<String> surplus_goals;


    public Tester() throws IOException {

        this.model = Model.getInstance();
        this.goals = new ArrayList<>();
        this.tick_counts_goals = new ArrayList<>();
        this.surplus_goals = new ArrayList<>();
        results_written_to_file = false;
        init_goal();
        init_log_file();

    }

    public void init_goal(){

        HashMap<Resource_Type, Integer> first_goal = fill_all_resources_with(10);

        goals.add(first_goal);
        goals.add(first_goal);

    }

    public HashMap<Resource_Type, Integer> fill_all_resources_with(int amount){

        HashMap<Resource_Type, Integer> goal = new HashMap<>();

        for(Resource_Type type: Resource_Type.values())
        {
            goal.put(type, amount);
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

        for( HashMap<Resource_Type, Integer> goal: goals){
            write_to_log_file( goal.toString());
        }

    }

    public boolean all_goals_reached(){

        if( goals.size() == 0)
            return true;

        return false;

    }

    private void wrtie_results_to_file()throws IOException {

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
        results_written_to_file = true;

    }

    public boolean is_goal_reached(int tickcount) throws IOException {

        if( all_goals_reached() ){

            if(results_written_to_file){
                return true;
            }

            wrtie_results_to_file();
            return true;
        }

        HashMap<Resource_Type, Integer> current_resources = get_current_resources();
        int to_reach_amount;
        int current_amount;

        HashMap<Resource_Type, Integer> goal = goals.get(0);

        if (goal == null) {
            System.out.println("no goal");
            return false;
        }

        for(Resource_Type type: Resource_Type.values())
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

    public void goal_is_reached(int tickcount) throws IOException {

        HashMap<Resource_Type, Integer> goal = goals.get(0);
        HashMap<Resource_Type, Integer> resources = get_current_resources();
        tick_counts_goals.add(tickcount);

        int new_amount;

        for(Resource_Type type: Resource_Type.values())
        {
            new_amount = resources.get(type) - goal.get(type);
            resources.replace(type, new_amount);
        }

        surplus_goals.add(resources.toString());

        goals.remove(0);

    }

    public String get_current_date(){

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(cal.getTime()) ;

    }


    public void write_to_log_file(String info) throws IOException {

        FileWriter fileWriter = new FileWriter("Test_output.txt", true);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println(info);
        //printWriter.printf("Product name is %s and its price is %d $", "iPhone", 1000);
        printWriter.close();
    }


    private HashMap<Resource_Type, Integer> get_current_resources()
    {
        return model.getBase().get_obtained_resources_resources();
    }

    private void set_current_resources(HashMap<Resource_Type, Integer> new_resources)
    {
        model.getBase().set_obtained_resources(new_resources);
    }


    public HashMap<Resource_Type, Integer> get_current_goal(){

        if( all_goals_reached())
            return fill_all_resources_with(0);

        return goals.get(0);

    }



}
