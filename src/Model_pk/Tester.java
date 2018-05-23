package Model_pk;

import Model_pk.Enterables.Base;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * Created by Gebruiker on 22/05/2018.
 */
public class Tester {

    private Model model;
    private HashMap<Resource_Type, Integer> goal;

    public Tester(){

        this.model = Model.getInstance();
        this.goal = new HashMap<>();
        init_goal();

    }

    public void init_goal(){


        for(Resource_Type type: Resource_Type.values())
        {
            if( type != Resource_Type.Iron)
                this.goal.put(type, 20);
        }

    }

    public boolean is_goal_reached(){

        HashMap<Resource_Type, Integer> current_resources = get_current_resources();
        int to_reach_amount;
        int current_amount;

        for(Resource_Type type: Resource_Type.values())
        {
            if (goal == null) {
                System.out.println("no goal");
            }
            if(type == Resource_Type.Iron)
            {

            }
            else
            {
                to_reach_amount = this.goal.get(type);
                current_amount = current_resources.get(type);

                if( current_amount < to_reach_amount )
                    return false;
            }

        }

        return true;

    }

    public void write_to_file(String info) throws IOException {

        FileWriter fileWriter = new FileWriter("Test_output", true);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.print(info);
        //printWriter.printf("Product name is %s and its price is %d $", "iPhone", 1000);
        printWriter.close();
    }


    public HashMap<Resource_Type, Integer> get_current_resources()
    {
        return model.getBase().get_obtained_resources_resources();
    }

}
