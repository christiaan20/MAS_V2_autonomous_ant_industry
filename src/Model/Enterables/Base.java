package Model.Enterables;


import java.util.ArrayList;

import Model.Model;
import Model.Enterables.Enterable_object;
import Model.Worker;
import Model.Order;
import View.Object_visuals.Base_visual;
import View.View;

/**
 * Created by christiaan on 10/05/18.
 */
public class Base extends Enterable_object {
    private Order order;

    public Base(int x, int y, int size, int time, Order order) {
        super( x, y, size, time);
        this.order = order;
        this.visual = new Base_visual( x, y, size,this);
        View.getInstance().add_visual(this.visual);
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public void enter(Worker w) {

    }

    @Override
    public void exit(Worker w) {

    }

    @Override
    public void has_reached(int x, int y) {

    }
}
