package View.Second_Window_pk;

import Main.Main_thread;
import Controller.Controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by christiaan on 15/06/18.
 */
public class Second_Frame extends Frame implements ActionListener
{
    private Controller controller;
    private Main_thread main_thread;
    //the main panel gridlayout in rows
    private Panel main_panel;

    //panels for each of the settings
    private Panel scenario_panel;

    private Label scenario_label;
    private Button small_scenario_button;
    private Button big_scenario_button;

    public Second_Frame(Controller controller, Main_thread main_thread) throws HeadlessException {
        super("Program settings");

        this.controller = controller;
        this.main_thread  = main_thread;

        build_second_window();

        this.setSize(500,700);

        this.addWindowListener( new WindowAdapter()
        {

            public void windowClosing(WindowEvent e)
            {
                dispose();
                System.exit(0);
            }
        });
    }

    private void build_second_window()
    {
        //the main panel where all the other panels are added to
        this.main_panel = new Panel(new GridLayout(6,1));

        //create buttons and labels
        this.scenario_label = new Label("scenario: ");;
        this.small_scenario_button= init_button("small");
        this.big_scenario_button = init_button("big");

        //create panels and add elements
        this.scenario_panel = new Panel(new FlowLayout());
        this.scenario_panel.setBackground(Color.gray);
        this.scenario_panel.add(scenario_label);
        this.scenario_panel.add(small_scenario_button);
        this.scenario_panel.add(big_scenario_button);

        //add panels to the main panel
        this.main_panel.add(this.scenario_panel);

        add(main_panel);


    }

    private Button init_button(String text)
    {
        Button button = new Button(text);
        button.setBackground(Color.lightGray);
        button.addActionListener(this);

        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
