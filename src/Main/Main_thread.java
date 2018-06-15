package Main;

import Model_pk.Testing_Classes.Test_Settings;
import View.View;
import Model_pk.Model;
import View.Window;

import java.io.IOException;

/**
 * The main thread that runs the simulation.
 */
public class Main_thread implements Runnable {
    private Main main;
    private View view;
    private Model model;
    private Window window;

    private boolean running = true; // whether the mainthread is running or not
    private boolean automatic_experiments = true;
    private boolean restart_activated = true; // whether the mainthread has to restart_activated the simulation or not
    private boolean paint_graphics = true;
    private int refresh_time = 1;     // refresh_time of ticks and refreshes in ms
    private int tickcount = 0;
    private int max_tickcount = 300000; //the maximum amount of ticks 1 simulation is allowed to run before it is forced to restart
    private int simulate_times = 1;    //the amount of times the simulation is run before stopping the program
    private int simulate_count = 0;     //the counter of times the simulation has run



    public Main_thread(Main main){
        this.main = main;
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

        if(automatic_experiments)
        {
            Test_Settings.getInstance().next_step();
        }
        //write_simulation_count();
        while(true)
        {


            if(restart_activated || tickcount >= max_tickcount)
            {
                if(tickcount >= max_tickcount)
                {
                    model.getTester().write_results_to_file();
                    write_reached_max_tick_count();
                }
                simulate_count++;
                restart();
                setRestart_activated(false);
            }

            if(running)
            {
                if(simulate_count > simulate_times)
                {
                    if(automatic_experiments)
                    {
                        simulate_count = 0;
                        if(!Test_Settings.getInstance().next_step())
                        {
                            break;
                        }
                        setRestart_activated(true);
                    }
                    else
                    {
                        view.setGAME_OVER(true);
                        view.paint();
                        continue;
                    }

                }

                tickcount++;

                model.tick(tickcount);

                if (model.getTester().all_goals_reached())
                {

                    model.getTester().write_results_to_file();
                    //simulate_count++;
                    setRestart_activated(true);
                    //restart();
                }

                window.update_resource_counters();
                window.update_tick_counter(tickcount);
                //window.update_resource_probs();
                window.update_resource_goals();

            }
            if(paint_graphics)
            {
                view.paint();
            }

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
            model.getTester().write_to_log_file("---------  Simulation: " + simulate_count + "-----------");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void write_reached_max_tick_count()
    {

        try {
            model.getTester().write_to_log_file("REACHED max tickcount: " + tickcount);
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
            model.init_model(main);

        } catch (IOException e) {
            e.printStackTrace();
        }
        write_simulation_count();
        Test_Settings.getInstance().write_settings();

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

    public boolean isPaint_graphics() {
        return paint_graphics;
    }

    public void setPaint_graphics(boolean paint_graphics) {
        this.paint_graphics = paint_graphics;
    }
}
