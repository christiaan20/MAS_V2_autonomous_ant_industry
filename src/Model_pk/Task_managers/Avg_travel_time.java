package Model_pk.Task_managers;

import java.util.ArrayList;

/**
 * Created by Gebruiker on 30/05/2018.
 */
public class Avg_travel_time {

    private ArrayList<Integer> all_travel_times;

    public Avg_travel_time() {
        this.all_travel_times = new ArrayList<>();
    }

    public Avg_travel_time(int travel_time) {
        this.all_travel_times = new ArrayList<>();
        this.all_travel_times.add(travel_time);
    }

    public int get_avg_travel_time() {

        int amount = all_travel_times.size();
        if ( amount == 0)
            return 0;

        int total_travel_time = 0;
        for( int travel_time: all_travel_times){
            total_travel_time += travel_time;
        }
        return total_travel_time / amount ;
    }

    public void update_travel_time(int travel_time) {

        int amount = all_travel_times.size();
        if (amount >= 10 ){
            all_travel_times.remove(0);
        }
        all_travel_times.add(travel_time);

    }
}
