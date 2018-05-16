package Model;

import java.util.ArrayList;


/**
 * Created by christiaan on 10/05/18.
 */
public class Order {
    private ArrayList<Resource> ordered_resources = new ArrayList<>();

    public Order() {
    }

    public Order(ArrayList<Resource> ordered_resources) {
        this.ordered_resources = ordered_resources;
    }

    public ArrayList<Resource> getOrdered_resources() {
        return ordered_resources;
    }

    public void addResource(Resource resource)
    {
        ordered_resources.add(resource);
    }

}
