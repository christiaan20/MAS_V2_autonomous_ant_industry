package Model_pk;

/**
 * Created by christiaan on 20/05/18.
 */
public class CustomStruct
{
    private int distance;
    private Object object;

    public CustomStruct(Object obj) {
        this.distance = 10000000;
        this.object= obj;
    }

    public CustomStruct(Object obj, int distance) {
        this.distance = distance;
        this.object = obj;
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
}
