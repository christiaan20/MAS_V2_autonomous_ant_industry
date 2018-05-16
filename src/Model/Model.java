package Model;

import java.util.ArrayList;
import java.util.Random;

import Model.Behaviour.Behaviour_basic;
import Model.Enterables.Enterable_object;
import Model.Enterables.Base;

import Model.Enterables.Resource_pool;
import View.View;

/**
 * Created by christiaan on 10/05/18.
 *
 * A Singleton Model object that is accessable by all other classes
 */
public class Model{
    private static Model  model ;
    private Random random = new Random();

    private final int size_x_field = 500;
    private final int size_y_field = 500;

    private ArrayList<Worker> workers = new ArrayList<>();
    private ArrayList<Enterable_object> enterable_objects = new ArrayList<>();
    private ArrayList<Pheromone> pheromones = new ArrayList<>();

    private boolean pause;


    /**
     * Create private constructor for a singleton
     */
    private Model(){

    }

    /**
     * Create a static method to get instance of the singleton
     */
    public static Model getInstance(){
        if(model == null){
            model = new Model();
        }
        return model;
    }

    public void set_scenario_1()
    {
        View.getInstance().set_field_size(size_x_field, size_y_field);
        int base_x = 200;
        int base_y = 200;
        int object_size= 50;
        int worker_size = 20;
        int phero_size = 7;
        int base_time = 5;
        int resource_time = 20;

        // create the base
        enterable_objects.add(new Base(base_x,base_y,object_size,base_time, new Order()));

        //create the resources
        //enterable_objects.add(new Resource_pool(300,400,object_size,resource_time,Resource_type.Iron));

        //create the workers

        //workers.add(new Worker(base_x, base_y, object_size, random.nextDouble(), 5, new Behaviour_basic()));

    }

    public int getField_size_x() {
        return size_x_field;
    }

    public int getSize_y_field() {
        return size_y_field;
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }


}
