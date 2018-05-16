package Model;

import java.util.ArrayList;

import Model.Behaviour.Behaviour;
import Model.Resource;
import Model.Pheromone;
import View.Object_visuals.Base_visual;

/**
 * Created by christiaan on 10/05/18.
 */
public class Worker extends Object {

    private double currDirection;

    private ArrayList<Resource> load = new ArrayList<>();
    private int max_load;

    private Pheromone currPhermone;
    private ArrayList<Pheromone> DetectedPhermones = new ArrayList<>();
    private ArrayList<Pheromone> VisetedPhermone = new ArrayList<>();

    private Behaviour behaviour;

    public Worker( int x, int y, int size, double currDirection, int max_load, Behaviour behaviour) {
        super( x, y, size);
        this.currDirection = currDirection;
        this.max_load = max_load;
        this.behaviour = behaviour;
        this.visual = new Base_visual( x, y, size,this);
    }
}
