package Model_pk.Behaviour.Basic;

import Model_pk.Behaviour.Abstr_Behaviour;
import Model_pk.Behaviour.Basic.Task.*;
import Model_pk.Object;

/**
 * Created by christiaan on 10/05/18.
 */
public class Behaviour_Basic extends Abstr_Behaviour {


    public Behaviour_Basic()  {

        try {
            super.task_explorer     = Class.forName("Model_pk.Behaviour.Basic.Task.Task_Explorer_Basic");
            super.task_miner        = Class.forName("Model_pk.Behaviour.Basic.Task.Task_Miner_Basic");
            super.task_transporter  = Class.forName("Model_pk.Behaviour.Basic.Task.Task_Transporter_Basic");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }


}
