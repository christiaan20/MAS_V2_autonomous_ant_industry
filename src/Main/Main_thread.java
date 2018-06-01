package Main;

import View.View;
import Model_pk.Model;
import View.Window;

import java.io.IOException;

/**
 * Created by christiaan on 10/05/18.
 */
public class Main_thread implements Runnable {
    private View view;
    private Model model;
    private Window window;

    private boolean running = true; // whether the mainthread is running or not
    private boolean restart_activated = false; // whether the mainthread has to restart_activated the simulation or not
    private int refresh_time = 1;     // refresh_time of ticks and refreshes in ms
    private int tickcount = 0;
    private int simulate_times = 10;    //the amount of times the simulation is run before stopping the program
    private int simulate_count = 0;     //the counter of times the simulation has run



    public Main_thread(){
        view = View.getInstance();
        model = Model.getInstance();
        this.window = window;

    }

    public void set_window(Window window)
    {
        this.window = window;
    }

    @Override
    public void run()
    {
        write_simulation_count();
        while(true)
        {
            if(restart_activated)
            {
                restart();
                setRestart_activated(false);
            }

            if(running)
            {
                if(simulate_count >= simulate_times)
                {
                    view.setGAME_OVER(true);
                    view.paint();
                    continue;
                }


                tickcount++;

                model.tick(tickcount);


                if (model.getTest_setting().all_goals_reached())
                {
                    write_simulation_count();
                    model.getTest_setting().write_results_to_file();
                    simulate_count++;
                    restart();
                }


                window.update_resource_counters();
                window.update_tick_counter(tickcount);
                window.update_resource_probs();
                window.update_resource_goals();

            }

                view.paint();

                try {
                    Thread.sleep(refresh_time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }



        }


    }

    private void write_simulation_count()
    {

        try {
            model.getTest_setting().write_to_log_file("---------  Simulation: " + simulate_count + "-----------");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void restart()
    {
        tickcount =0;
        model.restart();
        view.restart();

        try {
            model.set_scenario_1();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isRestart_activated() {
        return restart_activated;
    }

    public void setRestart_activated(boolean restart_activated) {
        this.restart_activated = restart_activated;
    }

    public int getTickcount() {
        return tickcount;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public int getRefresh_time() {
        return refresh_time;
    }

    public void setRefresh_time(int refresh_time) {
        this.refresh_time = refresh_time;
    }

    public void decrease_speed(int delta)
    {
        this.refresh_time = refresh_time +  delta;
    }

    public void increase_speed(int delta)
    {
        int changed = refresh_time - delta;
        if(changed >= 5)
        {
            this.refresh_time = changed;
        }
        else
        {
            this.refresh_time = 1;
        }

    }

    public double get_ticks_per_sec()
    {
        return (double)(1000/refresh_time);
    }

}
