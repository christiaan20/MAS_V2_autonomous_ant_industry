package Main;

import Model_pk.Model;
import View.View;
import View.Window;
import Controller.Controller;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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

    private int size_x = 900;
    private int size_y = 900;

    public Main() throws HeadlessException
    {

        main_thread = new Main_thread();

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



        this.addWindowListener( new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                dispose();
                System.exit(0);
            }
        });

    }

    public static void main(String a[])
    {
        Main main = new Main();
        main.setTitle("Autonomous Ant colonisation");
        main.setVisible(true);
        main.setResizable(false);

        //view needs to be initialised to set the right size
        main.initialise_view();
        //start the scenario after setting the main visible and initialising the view
        main.start_scenario();

        main.start_thread();


    }

    public void start_thread()
    {
        t.start();
    }

    public void start_scenario()
    {
        model.set_scenario_1();
    }

    public void initialise_view()
    {
        view.initialise();
    }
}
