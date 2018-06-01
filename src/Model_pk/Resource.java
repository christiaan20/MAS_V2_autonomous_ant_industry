package Model_pk;

import Model_pk.Enums.Resource_Type_Enum;

/**
 * A resource object that represents the good and mount that need be collected in the setting.
 */
public class Resource {
    private int amount;
    private Resource_Type_Enum type;

    public Resource(int amount, Resource_Type_Enum type) {
        this.amount = amount;
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Resource_Type_Enum getType() {
        return type;
    }

    public void setType(Resource_Type_Enum type) {
        this.type = type;
    }
}
