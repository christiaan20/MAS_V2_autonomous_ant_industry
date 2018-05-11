package Model;

import View.View;

/**
 * Created by christiaan on 10/05/18.
 *
 * A Singleton Model object that is accessable by all other classes
 */
public class Model{
    private static Model  model ;

    private boolean pause;


    /**
     * Create private constructor for a singleton
     */
    private Model(){

    }
    /**
     * Create a static method to get instance of the singleton
     */
    public static Model getInstance(){
        if(model == null){
            model = new Model();
        }
        return model;
    }



    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }


}
