package Model_pk;

import Model_pk.Objects.Abstr_Object;
import Model_pk.Objects.Pheromone;

/**
 * Created by christiaan on 20/05/18.
 */
public class CustomStruct
{
    private int distance;   //the distance from the worker to the object
    private Abstr_Object object;  // the object that is stored
    private int x_vector;   // the x coordinate of the vector from the worker to the object
    private int y_vector;   // the y coordinate of the vector form the worker to the object

    public CustomStruct(int distance, Abstr_Object object, int x_vector, int y_vector)
    {
        this.distance = distance;
        this.object = object;
        this.x_vector = x_vector;
        this.y_vector = y_vector;
    }

    public CustomStruct(Abstr_Object obj) {
        this(10000000, obj,0,0);

    }

    public CustomStruct(Abstr_Object obj, int distance) {
        this(distance, obj,0,0);
    }

    public boolean is_farther_than(CustomStruct struct)
    {
        return this.getDistance() >= struct.getDistance();
    }

    public boolean is_closer_than(CustomStruct struct)
    {
        return this.getDistance() < struct.getDistance();
    }

    public boolean is_more_attractive_than(CustomStruct struct)
    {
        double strength_factor = Model.getInstance().getBehaviour().getStrength_influence();

        Pheromone phero_this = (Pheromone) object;
        Pheromone phero_given = (Pheromone) struct.getObject();

        double attraction_this = phero_this.getStrength()/Math.pow(this.distance,strength_factor);
        double attraction_given = phero_given.getStrength()/Math.pow(this.distance,strength_factor);

        return attraction_this > attraction_given;
    }

    public boolean is_less_attractive_than(CustomStruct struct)
    {

        double attraction_this = 0; //the attraction of the pheromone in this struct

        //calculate the attraction strength of the phero in this struct
        if(object != null)
        {
            Pheromone phero_this = (Pheromone) this.object;
            attraction_this = calc_attraction(phero_this);
        }

        //calculate the attraction strength of the phero in the give struct
        Pheromone phero_given = (Pheromone) struct.getObject();
        double attraction_given = calc_attraction(phero_given);

        return attraction_this < attraction_given;
    }



    private double calc_attraction(Pheromone phero){

        double strength_factor = Model.getInstance().getBehaviour().getStrength_influence();
        return phero.getStrength()/Math.pow(this.distance,strength_factor);

    }



    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public Abstr_Object getObject() {
        return object;
    }

    public void setObject(Abstr_Object object) {
        this.object = object;
    }

    public int getX_vector() {
        return x_vector;
    }

    public void setX_vector(int x_vector) {
        this.x_vector = x_vector;
    }

    public int getY_vector() {
        return y_vector;
    }

    public void setY_vector(int y_vector) {
        this.y_vector = y_vector;
    }
}
