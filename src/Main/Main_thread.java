package Main;

import Model_pk.Tester;
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
    private int refresh_time = 15;     // refresh_time of ticks and refreshes in ms
    private int tickcount = 0;



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

        while(true)
        {
            if(running)
            {
                tickcount++;

                model.tick(tickcount);

                window.update_resource_counters();
                window.update_tick_counter(tickcount);
                window.update_resource_probs();

            }

                view.paint();

                try {
                    Thread.sleep(refresh_time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }



        }


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
            this.refresh_time = 5;
        }

    }

    public double get_ticks_per_sec()
    {
        return (double)(1000/refresh_time);
    }

}
