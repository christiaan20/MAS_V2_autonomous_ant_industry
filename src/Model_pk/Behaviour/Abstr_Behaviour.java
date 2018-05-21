package Model_pk.Behaviour;

import Model_pk.Behaviour.Basic.Task.Task_Explorer_Basic;
import Model_pk.Behaviour.Basic.Task.Task_Miner_Basic;
import Model_pk.Behaviour.Basic.Task.Task_Transporter_Basic;

/**
 * Created by christiaan on 10/05/18.
 */
public abstract class Abstr_Behaviour
{
    protected Class<?> task_explorer;
    protected Class<?> task_miner;
    protected Class<?> task_transporter;

    public Abstr_Task getTask_explorer() {

        try {
            return (Abstr_Task) task_explorer.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Abstr_Task getTask_miner() {
        try {
            return (Abstr_Task) task_miner.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Abstr_Task getTask_transporter() {
        try {
            return (Abstr_Task) task_transporter.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
