package Model_pk.Task_managers;

import Model_pk.Objects.Enterables.Base;
import Model_pk.Objects.Worker;

/**
 * Created by Gebruiker on 29/05/2018.
 */
public class Abstr_Task_manager {

    protected Base base;

    public void update_task_of(Worker worker){

    }

    public Base getBase() {
        return base;
    }

    public void setBase(Base base) {
        this.base = base;
    }

}
