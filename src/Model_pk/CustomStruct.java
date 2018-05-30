package Model_pk;

/**
 * Created by christiaan on 20/05/18.
 */
public class CustomStruct
{
    private int distance;   //the distance from the worker to the object
    private Object object;  // the object that is stored
    private int x_vector;   // the x coordinate of the vector from the worker to the object
    private int y_vector;   // the y coordinate of the vector form the worker to the object

    public CustomStruct(int distance, Object object, int x_vector, int y_vector)
    {
        this.distance = distance;
        this.object = object;
        this.x_vector = x_vector;
        this.y_vector = y_vector;
    }

    public CustomStruct(Object obj) {
        this(10000000, obj,0,0);

    }

    public CustomStruct(Object obj, int distance) {
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

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
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
