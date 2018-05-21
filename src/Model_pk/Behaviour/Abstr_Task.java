package Model_pk.Behaviour;

import Model_pk.Model;
import Model_pk.Worker;
import Model_pk.Object;

import java.util.Random;
import java.text.DecimalFormat;

/**
 * Created by christiaan on 16/05/18.
 */
public abstract class Abstr_Task
{
    protected Model model;
    protected Task_Enum task;

    //protected Worker worker;



    //parameter attaining to the movement of workers
    protected int target_x; //absolute x coordinate where the workers is going to
    protected int target_y; //absolute y coordinate where the workers is going to

    private int worker_speed = 4;                   //the size of the step of a worker at each tick
    private int step_distance = worker_speed *20;   //the distance that is walked during wandering before a new target is chosen
    protected int dist_walked = step_distance;        //the amount of distance walked since the last target choice

    private Random random = new Random(); // Random object to generate directions of travel
    private int big_turn_threshold = 10; //the amount of steps taken before a mayor turn is possible
    private int big_turn_count = 0;

    //parameters attaining to the use of the pheromones
    //the size of pheromone is scaled with its strenght
    int start_phero_size = 4;   //the size at dropping the phero
    int max_phero_size = 10;    //the limit on the size of the pheromone
    int start_phero_strength = 10;
    int max_phero_strength = 50;
    int degrade_time = 50;      //how many ticks it takes to degrade the pheromone by 1

    int phero_drop_dist         = step_distance;   //the distance needed to walk before dropping a pheromone
    int dist_walked_since_drop  = 0;                    //the distance walked since last dropping of pheromone
    boolean drop_enabled        = true;
    int phero_detect_dist       = (int)(step_distance*1.25);



    public Abstr_Task()
    {
        this.model = Model.getInstance();
    }

    public abstract void move(Worker worker);
    public abstract boolean on_reached(Worker worker, Object obj);

  /*  public void setWorker(Worker worker) {
        this.worker = worker;
    }*/

    public  void tick(Worker worker)
    {
        Object reached = model.check_if_reached_an_object(worker.getX(), worker.getY());
        if(reached != null)
        {
            if(on_reached(worker, reached))
            {

            }
            else
            {
                model.find_objects(worker);
                check_dropping_pheromone(worker);
                move(worker);
            }
        }
        else
        {
            model.find_objects(worker);
            check_dropping_pheromone(worker);
            move(worker);
        }
    }


    public boolean checkOutsideOfBorders(int x, int y, int sizeX, int sizeY)
    {
        if(x < 0 || y < 0 || x > sizeX || y > sizeY)
        {
            return true;
        }
        return false;
    }

    protected boolean wanderWithin(Worker worker) {
        Model  model = Model.getInstance();
        int sizeX = model.get_size_x_field();
        int sizeY = model.get_size_y_field();

        boolean output = false; //whether the move_worker has reached a target
        //each step of step_distance long the ant turns -90° to 90° after big_turn_threshold the ant can turn upto 360°
        if( dist_walked >= step_distance || ((target_x == worker.getX() && target_y == worker.getY())))
        {

            double corner = (random.nextDouble()-0.5)*(Math.PI); //make a bend of min -90° and 90°
            double totCorner = worker.getCurrDirection() + corner; //add bend to currecnt direction of the workers

            //generate a target based on the step_distance
            if(big_turn_count >= big_turn_threshold)
            {
                totCorner = (random.nextDouble())*(Math.PI*2); //make a bend of 0° to 360°
                big_turn_count = 0;
            }
            big_turn_count = big_turn_count +1;

            target_x = worker.getX() +  (int) (Math.cos(totCorner)* step_distance);
            target_y = worker.getY() + (int) (Math.sin(totCorner)* step_distance);

            //if the target coordinates are outside of the borders of the map the ant is turned back by decreasing totCorner with 180° (but in radials)
            if(checkOutsideOfBorders(target_x,target_y,sizeX,sizeY))
            {
                totCorner = totCorner - Math.PI;
                target_x = worker.getX() +  (int) (Math.cos(totCorner)* step_distance);
                target_y = worker.getY() + (int) (Math.sin(totCorner)* step_distance);
            }

            dist_walked =0;

            output = true;
        }
        dist_walked = dist_walked + move_worker(worker,target_x,target_y);
        return output;
    }

    public int move_worker(Worker worker, int x_target, int y_target)
    {
        int x = worker.getX();
        int y = worker.getY();
        int Xdist = get_x_dist(x,x_target);
        int Ydist = get_y_dist(y, y_target);

        int dist = (int)Math.abs( Math.sqrt(Math.pow(Xdist,2)+ Math.pow(Ydist,2)));

        int newX = x_target;
        int newY = y_target;

        int distWalked = 0 ;

        double corner = getaTanCorner(Xdist, Ydist);
        worker.setCurrDirection(corner);

        if (dist > worker_speed)
        {
            newX = x +  (int) (Math.cos(corner)* worker_speed);
            newY = y + (int) (Math.sin(corner)* worker_speed);
            distWalked = (int) (Math.sqrt(Math.pow(newX-x,2)+Math.pow(newY-y,2)));
        }

        worker.move(newX,newY);
        dist_walked_since_drop =  dist_walked_since_drop + distWalked;

        return distWalked;
    }

    public void check_dropping_pheromone(Worker worker)
    {
        if(dist_walked_since_drop >= phero_drop_dist)
        {
            model.drop_pheromone( worker.getX(), worker.getY(),start_phero_size ,worker.getTask(),worker.getResource_type() , start_phero_strength,degrade_time);
            dist_walked_since_drop = 0;
        }
    }

    public int get_x_dist(int curr_x, int x_target)
    {
        return x_target - curr_x;
    }

    public int get_y_dist(int curr_y, int y_target)
    {
        return y_target - curr_y;
    }

    public double getaTanCorner(int xdist, int ydist) {
        double corner = 0;
        if(xdist < 0)
            corner = Math.PI+ Math.atan((double) ydist /(double) xdist) ;
        else
        {
            if(ydist < 0)
                corner = 2*Math.PI+ Math.atan((double) ydist /(double) xdist) ;
            else
                corner = Math.atan((double) ydist /(double) xdist) ;
        }
        return corner;
    }

    public int getStep_distance() {
        return step_distance;
    }

    public void setStep_distance(int step_distance) {
        this.step_distance = step_distance;
    }

    public int getBig_turn_threshold() {
        return big_turn_threshold;
    }

    public void setBig_turn_threshold(int big_turn_threshold) {
        this.big_turn_threshold = big_turn_threshold;
    }

    public int getTarget_x() {
        return target_x;
    }


    public int getTarget_y() {
        return target_y;
    }

    public void setTarget(int x_target,int y_target)
    {
        this.target_x = x_target;
        this.target_y = y_target;

        /*
        int x = worker.getX();
        int y = worker.getY();
        int Xdist = get_x_dist(x,x_target);
        int Ydist = get_x_dist(y,y_target);
        double corner = getaTanCorner(Xdist, Ydist);
        worker.setCurrDirection(corner);
        */
    }


    public int getDist_walked() {
        return dist_walked;
    }

    public void setDist_walked(int dist_walked) {
        this.dist_walked = dist_walked;
    }

    public Task_Enum getTask() {
        return task;
    }

    public void setTask(Task_Enum task) {
        this.task = task;
    }

    public void test_tan_function()
    {
        DecimalFormat df2 = new DecimalFormat(".##");

        int amount  = 9;

        double[] angle = new double[amount];
        double[] expected_angle = new double[amount];


        int parts = 4;
        System.out.println("\nchecking the tan corner calculation");
        System.out.println("the first angle column needs to be the same as the last tan_angle column");
        System.out.println("angle  cos  sin  x_dist  Y_dist  tan_angle ");

        for(int i=0;i<amount;i++)
        {
            angle[i] = (Math.PI/parts)*i;
            double cos = Math.cos(angle[i]);
            double sin = Math.sin(angle[i]);
            int x = (int) (cos* 10);
            int y = (int) (sin * 10);

            double tan = getaTanCorner(x,y);

            System.out.println(df2.format(angle[i]) + "  "  + df2.format(cos)  + "  " + df2.format(sin) + "  " + x + "  " +  y + "  " + df2.format(tan));


        }



    }

    public int getStart_phero_size() {
        return start_phero_size;
    }

    public void setStart_phero_size(int start_phero_size) {
        this.start_phero_size = start_phero_size;
    }

    public int getMax_phero_size() {
        return max_phero_size;
    }

    public void setMax_phero_size(int max_phero_size) {
        this.max_phero_size = max_phero_size;
    }

    public int getStart_phero_strength() {
        return start_phero_strength;
    }

    public void setStart_phero_strength(int start_phero_strength) {
        this.start_phero_strength = start_phero_strength;
    }

    public int getMax_phero_strength() {
        return max_phero_strength;
    }

    public void setMax_phero_strength(int max_phero_strength) {
        this.max_phero_strength = max_phero_strength;
    }

    public int getDegrade_time() {
        return degrade_time;
    }

    public void setDegrade_time(int degrade_time) {
        this.degrade_time = degrade_time;
    }

    public int getPhero_drop_dist() {
        return phero_drop_dist;
    }

    public void setPhero_drop_dist(int phero_drop_dist) {
        this.phero_drop_dist = phero_drop_dist;
    }

    public boolean isDrop_enabled() {
        return drop_enabled;
    }

    public void setDrop_enabled(boolean drop_enabled) {
        this.drop_enabled = drop_enabled;
    }

    public int getPhero_detect_dist() {
        return phero_detect_dist;
    }

    public void setPhero_detect_dist(int phero_detect_dist) {
        this.phero_detect_dist = phero_detect_dist;
    }
}
