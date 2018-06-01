package Model_pk;

import Model_pk.Objects.Abstr_Object;
import Model_pk.Objects.Pheromone;

/**
 *
 */
public class CustomStruct
{
    private int distance;   //the distance from the worker to the object
    private Abstr_Object object;  // the object that is stored
    private int x_vector;   // the x coordinate of the vector from the worker to the object
    private int y_vector;   // the y coordinate of the vector form the worker to the object
    private double angle_from_worker_direction;

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
            attraction_this = calc_attraction_strength(phero_this);
        }

        //calculate the attraction strength of the phero in the give struct
        Pheromone phero_given = (Pheromone) struct.getObject();
        double attraction_given = calc_attraction_strength(phero_given);

        return attraction_this < attraction_given;
    }

    public boolean is_less_attractive_strength_direction_than(CustomStruct struct)
    {

        double attraction_this = 0; //the attraction of the pheromone in this struct

        //calculate the attraction strength of the phero in this struct
        if(object != null)
        {
            Pheromone phero_this = (Pheromone) this.object;
            attraction_this = calc_attraction_strength_direction(phero_this,this.angle_from_worker_direction);
        }

        //calculate the attraction strength of the phero in the give struct
        Pheromone phero_given = (Pheromone) struct.getObject();
        double attraction_given = calc_attraction_strength_direction(phero_given,struct.getAngle_from_worker_direction());

        return attraction_this < attraction_given;
    }

    public boolean is_less_attractive_direction_than(CustomStruct struct)
    {

        double attraction_this = 0; //the attraction of the pheromone in this struct

        //calculate the attraction strength of the phero in this struct
        if(object != null)
        {
            attraction_this = calc_attraction_direction(this.getDistance(),this.angle_from_worker_direction);
        }

        //calculate the attraction strength of the phero in the give struct
        double attraction_given = calc_attraction_direction(struct.getDistance(),struct.getAngle_from_worker_direction());

        return attraction_this < attraction_given;
    }


    private double calc_attraction_strength(Pheromone phero)
    {

    double strength_factor = Model.getInstance().getBehaviour().getStrength_influence();
    return phero.getStrength()/Math.pow(this.distance,strength_factor);

    }

    private double calc_attraction_strength_direction(Pheromone phero, double angle_from_worker_direction)
    {

        double strength_factor = Model.getInstance().getBehaviour().getStrength_influence();
        double strength_attraction = phero.getStrength()/Math.pow(this.distance,strength_factor);


        double full_attraction = calc_attraction_direction(strength_attraction, angle_from_worker_direction);

        return full_attraction;

    }

    public double calc_attraction_direction(double dist, double angle_to_worker_direction)
    {
        double sigma = Math.PI*2/3;
        double sqrt = Math.sqrt(2*Math.PI);
        double mean = 0;

        double factor = (1/(sigma*sqrt))*Math.exp(-(Math.pow((angle_to_worker_direction-mean),2)/(2*Math.pow(sigma,2))));

        return dist*factor;

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

    public double getAngle_from_worker_direction() {
        return angle_from_worker_direction;
    }

    public void setAngle_from_worker_direction(double angle_from_worker_direction) {
        this.angle_from_worker_direction = angle_from_worker_direction;
    }
}
