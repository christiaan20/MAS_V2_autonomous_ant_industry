package Model_pk;

import View.Object_visuals.Object_visual;
import View.View;

/**
 * Created by christiaan on 10/05/18.
 */
public abstract class Object {

    private static long ID_count = 0;
    protected long ID;

    protected int x;
    protected int y;
    protected int size;

    protected View view;
    protected Object_visual visual;

    protected boolean expired;

    public Object(int x, int y, int size) {
        this.ID = ID_count;
        this.x = x;
        this.y = y;
        this.size = size;
        this.expired = false;

        this.view = View.getInstance();

        ID_count++;
    }

    public abstract void tick();

    public boolean check_within( int x, int y)
    {
        int top_border = this.y + size/2;
        int bottom_border = this.y - size/2;
        int left_border = this.x - size/2;
        int right_border = this.x + size/2;

        if(     (top_border >= y) &&  (bottom_border <= y) &&
            (right_border >= x) &&  (left_border <= x))
        {
            return true;
        }
        return false;
    }

    public boolean check_object_within(int left_border,int right_border, int bottom_border, int top_border)
    {
        if(     (top_border >= this.y) &&  (bottom_border <= this.y) &&
                (right_border >= this.x) &&  (left_border <= this.x))
        {
            return true;
        }
        return false;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
        visual.update_parameter(x,y,size);
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
        visual.update_parameter(x,y,size);
    }

    public int getSize()
    {

        return size;
    }

    public void setSize(int size)
    {
        this.size = size;
        visual.update_parameter(x,y,size);
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public Object_visual getVisual() {
        return visual;
    }

    public void setVisual(Object_visual visual) {
        this.visual = visual;
        view.add_visual(visual);
    }

    public void delete_visual()
    {

        view.delete_visual(visual);
        this.visual = null;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Object object = o;

        if (getID() != object.getID()) return false;
        if (getX() != object.getX()) return false;
        if (getY() != object.getY()) return false;
        if (getSize() != object.getSize()) return false;
        return isExpired() == object.isExpired();
    }

    @Override
    public int hashCode() {
        int result = (int) (getID() ^ (getID() >>> 32));
        result = 31 * result + getX();
        result = 31 * result + getY();
        result = 31 * result + getSize();
        result = 31 * result + (isExpired() ? 1 : 0);
        return result;
    }
}
