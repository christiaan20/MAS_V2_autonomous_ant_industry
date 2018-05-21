package Model_pk.Behaviour.Basic.Task;

import Model_pk.Behaviour.Abstr_Task;
import Model_pk.Behaviour.Task_Enum;
import Model_pk.Object;
import Model_pk.Worker;

/**
 * Created by christiaan on 16/05/18.
 */
public class Task_Transporter_Basic extends Abstr_Task
{

    public Task_Transporter_Basic()
    {
        super.task = Task_Enum.transporter;
    }

    @Override
    public void move(Worker worker) {

    }

    @Override
    public boolean on_reached(Worker worker, Object obj)
    {
        return false;
    }
}
