package Model_pk.Task_managers;

import Model_pk.Behaviour.Abstr_Task;
import Model_pk.Enums.Resource_Type_Enum;
import Model_pk.Model;

/**
 * Created by Gebruiker on 29/05/2018.
 */
public class Worker_representation {

    private long ID;
    private Abstr_Task task;                //The Task it is currently doing
    private Resource_Type_Enum type;    //The resource it will mine, transport for or look for
    private int last_seen;

    public Worker_representation(long ID, Abstr_Task task, Resource_Type_Enum type, int last_seen) {
        this.ID = ID;
        this.task = task;
        this.type = type;
        this.last_seen = last_seen;
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

    public Resource_Type_Enum getType() {
        return type;
    }

    public void setType(Resource_Type_Enum type) {
        this.type = type;
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

    public int get_ticks_since_last_seen_at_base() {
        return Model.getInstance().getTickcount() - last_seen;
    }

}
