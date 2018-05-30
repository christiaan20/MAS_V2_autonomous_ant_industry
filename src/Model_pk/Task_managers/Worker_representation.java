package Model_pk.Task_managers;

import Model_pk.Behaviour.Abstr_Task;
import Model_pk.Resource_Type;
import Model_pk.Worker_State_Enum;

/**
 * Created by Gebruiker on 29/05/2018.
 */
public class Worker_representation {

    private long ID;
    private Abstr_Task task;                //The Task it is currently doing
    private Resource_Type type;    //The resource it will mine, transport for or look for
    private int travel_time;

    public Worker_representation(long ID, Abstr_Task task, Resource_Type type, int travel_time) {
        this.ID = ID;
        this.task = task;
        this.type = type;
        this.travel_time = travel_time;
    }

    public long getID() {
        return ID;
    }

    public Abstr_Task getTask() {
        return task;
    }

    public void setTask(Abstr_Task task) {
        this.task = task;
    }

    public Resource_Type getType() {
        return type;
    }

    public void setType(Resource_Type type) {
        this.type = type;
    }

    public int getTravel_time() {
        return travel_time;
    }

    @Override
    public boolean equals(Object other){

        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Worker_representation)) return false;

        Worker_representation other_worker = (Worker_representation)other;
        if (other_worker.getID() == this.getID()) return true;

        return false;

    }

}
