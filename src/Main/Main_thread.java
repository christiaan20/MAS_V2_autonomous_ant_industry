package Main;

import View.View;

/**
 * Created by christiaan on 10/05/18.
 */
public class Main_thread implements Runnable {

    public Main_thread()
    {

    }

    @Override
    public void run()
    {
        while(true)
        {
            View view = View.getInstance();
            view.paint();

            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
}
