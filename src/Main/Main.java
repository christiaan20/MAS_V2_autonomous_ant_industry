package Main;

import Model_pk.Model;
import View.Second_Window_pk.Second_Frame;
import View.View;
import View.Window;
import Controller.Controller;
import com.sun.org.apache.bcel.internal.generic.LLOAD;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * Created by christiaan on 10/05/18.
 */

public class Main extends Frame {
    private Window window;
    private Model model;
    private View view;
    private Controller controller;
    private Main_thread main_thread;
    private Thread t;
    private boolean big_scenario = false;

    private Second_Frame second_frame;

    private int size_x = 950;
    private int size_y = 750;


    public Main() throws HeadlessException {

        if(this.big_scenario)
        {
            size_x = 1500;
            size_y = 1000;
        }

        main_thread = new Main_thread(this);

        window = new Window(size_x,size_y, main_thread);
        add(window);

        main_thread.set_window(window);

        controller = new Controller();
        window.setControl(controller);

        this.setSize(size_x, size_y);

        //start the drawing thread
        t = new Thread(main_thread);

        model = Model.getInstance();
        view = View.getInstance();

        //start the second window containing settings of the whole thing
        second_frame =  new Second_Frame(controller,main_thread);
        second_frame.setVisible(true);
        second_frame.setResizable(false);

        this.addWindowListener( new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                dispose();
                System.exit(0);
            }
        });

    }

    public static void main(String a[]) throws IOException {
        Main main = new Main();
        main.setTitle("Autonomous Ant colonisation");
        main.setVisible(true);
        main.setResizable(false);

        //view needs to be initialised to set the right size
        main.initialise_view();
        //start the scenario after setting the main visible and initialising the view
   //     main.start_scenario();



        main.start_thread();

    }

    public void start_thread()
    {
        t.start();
    }

    public void start_scenario() throws IOException {
        model.set_scenario_1();
    }

    public void initialise_view()
    {
        view.initialise();
    }
}
