package Model_pk;

import Model_pk.Behaviour.Task_Enum;
import View.Object_visuals.Pheromone_visual;
import View.View;

/**
 * Created by christiaan on 10/05/18.
 */
public class Pheromone extends Object
{
    private Task_Enum task;
    private Resource_Type type;

    private Worker owner;

    private int strength;
    private int degrade_time;
    private int degrade_counter;
    private double actual_size;
    private double size_per_strength;


    public Pheromone(int x, int y, int size,Task_Enum task,Resource_Type type,int strength, int degrade_time)
    {
        super(x, y, size);
        this.task = task;
        this.type = type;
        this.degrade_time = degrade_time;
        this.actual_size = (double) size;
        this.size_per_strength = ((double)(size-2))/strength;

        this.visual = new Pheromone_visual(x,y,size,this);
        View.getInstance().add_visual(visual);

        this.setStrength(strength);
    }

    @Override
    public void tick()
    {
        increase_expire_counter();
        if(degrade_counter >= degrade_time)
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

    public Resource_Type getType() {
        return type;
    }

    public void setType(Resource_Type type) {
        this.type = type;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength)
    {
        this.strength   = strength;
        actual_size = (2 + strength*size_per_strength);
        this.setSize((int)actual_size);
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

    public Worker getOwner() {
        return owner;
    }

    public void setOwner(Worker owner) {
        this.owner = owner;
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
