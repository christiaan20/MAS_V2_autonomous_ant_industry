package Model_pk.Objects;

import Model_pk.Behaviour.Task_Enum;
import Model_pk.Enums.Resource_Type_Enum;
import View.Object_visuals.Pheromone_visual;
import View.View;

import java.util.ArrayList;

/**
 * Created by christiaan on 10/05/18.
 */
public class Pheromone extends Abstr_Object
{
    private Task_Enum task;
    private Resource_Type_Enum type;

    private ArrayList<Worker> owners = new ArrayList<>();

    private int strength;
    private int degrade_time;
    private int degrade_counter;
    private double actual_size;
    private double size_per_strength;
    private int max_size;

    public Pheromone() {
    }

    public Pheromone(Worker owner, int x, int y, int size, Task_Enum task, Resource_Type_Enum type, int strength, int degrade_time, int max_size)
    {
        super(x, y, size);
        this.task = task;
        this.type = type;
        this.degrade_time = degrade_time;
        this.actual_size = (double) size;
        this.size_per_strength = ((double)(size-2))/strength;
        this.max_size = max_size;

        this.owners.add(owner);

        this.visual = new Pheromone_visual(x,y,size,this);
        View.getInstance().add_visual(visual);

        this.setStrength(strength);
    }

    @Override
    public void tick()
    {
        increase_expire_counter();
        int threshold = ((int)(degrade_time/(strength/10.0)));
        if(threshold > degrade_time )
            threshold = degrade_time;

        if(degrade_counter >= threshold)
        {
            decrease_strength(1);
            setDegrade_counter(0);
        }
        if(strength == 0)
        {
            setExpired(true);
        }
    }

    public Task_Enum getTask() {
        return task;
    }

    public void setTask(Task_Enum task) {
        this.task = task;
    }

    public boolean isTask(Task_Enum task)
    {
        return this.task == task;
    }

    public Resource_Type_Enum getType() {
        return type;
    }

    public boolean isType(Resource_Type_Enum type)
    {
        return this.type == type;
    }

    public void setType(Resource_Type_Enum type) {
        this.type = type;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength)
    {
        this.strength   = strength;
        actual_size = (2 + strength*size_per_strength);
        if(actual_size < max_size)
        {
            this.setSize((int)actual_size);
        }
        else
        {
            this.setSize((int)max_size);
        }

    }

    public void increase_strength(int delta)
    {
        setStrength(this.strength +  delta);

    }

    public void decrease_strength(int delta)
    {
        setStrength( this.strength - delta);

    }

    public int getExpire_time() {
        return degrade_time;
    }

    public void setExpire_time(int expire_time) {
        this.degrade_time = expire_time;
    }

    public int getDegrade_counter() {
        return degrade_counter;
    }

    public void setDegrade_counter(int degrade_counter) {
        this.degrade_counter = degrade_counter;
    }

    public void increase_expire_counter()
    {
        degrade_counter++;
    }

    public ArrayList<Worker> getOwners() {
        return owners;
    }

    public boolean isOwner(Worker owner)
    {
        return owners.contains(owner);
    }

    public void addOwner(Worker owner) {
        if(!isOwner(owner))
        {
            this.owners.add(owner);
        }
    }

    public int getDegrade_time() {
        return degrade_time;
    }

    public void setDegrade_time(int degrade_time) {
        this.degrade_time = degrade_time;
    }

    public double getActual_size() {
        return actual_size;
    }

    public void setActual_size(double actual_size) {
        this.actual_size = actual_size;
    }

    public double getSize_per_strength() {
        return size_per_strength;
    }

    public void setSize_per_strength(double size_per_strength) {
        this.size_per_strength = size_per_strength;
    }


}
