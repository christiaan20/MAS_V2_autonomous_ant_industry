package Model_pk;

/**
 * Created by christiaan on 10/05/18.
 */
public class Resource {
    private int amount;
    private Resource_Type type;

    public Resource(int amount, Resource_Type type) {
        this.amount = amount;
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Resource_Type getType() {
        return type;
    }

    public void setType(Resource_Type type) {
        this.type = type;
    }
}
