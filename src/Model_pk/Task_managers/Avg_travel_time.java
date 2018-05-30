package Model_pk.Task_managers;

/**
 * Created by Gebruiker on 30/05/2018.
 */
public class Avg_travel_time {

    private int amount;
    private int total_travel_time;

    public Avg_travel_time() {
        amount = 0;
        this.total_travel_time = 0;
    }

    public Avg_travel_time(int travel_time) {
        amount = 1;
        this.total_travel_time = travel_time;
    }

    public int get_avg_travel_time() {

        if (amount == 0)
            return 0;
        return total_travel_time / amount;
    }

    public void update_travel_time(int travel_time) {
        amount += 1;
        this.total_travel_time += travel_time;

    }
}
