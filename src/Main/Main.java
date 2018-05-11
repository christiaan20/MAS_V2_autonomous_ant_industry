package Main;

import View.Window;
import Controller.Controller;
import Main.Main_thread;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by christiaan on 10/05/18.
 */
public class Main extends Frame {
    private Window window;
    private Controller controller;
    private Main_thread main_thread;
    private Thread t;

    private int size_x = 1000;
    private int size_y = 1000;

    public Main() throws HeadlessException {
        window = new Window(size_x,size_y);
        add(window);

        controller = new Controller();
        this.setSize(size_x, size_y);

        main_thread = new Main_thread();
        t = new Thread(main_thread);

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

        main.start_thread();


    }

    public void start_thread()
    {
        t.start();
    }
}
