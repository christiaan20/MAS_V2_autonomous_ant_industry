package Model;

import View.Object_visuals.Object_visual;

/**
 * Created by christiaan on 10/05/18.
 */
public abstract class Object {

    private static int ID_count = 0;
    protected int ID;

    protected int x;
    protected int y;
    protected int size;

    protected Object_visual visual;

    public Object(int x, int y, int size) {
        this.ID = ID_count;
        this.x = x;
        this.y = y;
        this.size = size;

        ID_count++;
    }

    public void tick()
    {

    }


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
