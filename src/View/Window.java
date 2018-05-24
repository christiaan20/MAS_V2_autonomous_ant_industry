package View;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

import Main.Main_thread;
import Model_pk.Behaviour.Task_Enum;
import Model_pk.Model;
import Model_pk.Resource;
import Model_pk.Resource_Type;
import Controller.Controller;
import javafx.concurrent.Task;

/**
 * Created by christiaan on 10/05/18.
 */
public class Window extends Panel implements ActionListener
{
    private Main_thread main_thread;
    private Controller control;

    private Panel top_panel;
    private Panel bottom_panel_1;
    private Panel bottom_panel_2;
    private Panel bottom_grid_panel;

    //general information labels
    private String  tickcount_text;
    private Label   tickcount_label;
    private Map<Resource_Type,Label> resource_count_labels = new HashMap<>();

    //buttons regarding to simulation speed
    private Button pause_button;
    private Button slowdown_button;
    private Button speedup_button;
    private String ticks_per_second_text;
    private Label  ticks_per_second_label;

    //buttons regarding the drawing of aspects
    private Button draw_pheromones_button;

    //buttons regarding the creation of aspects
    private Button create_pheromones_button;
    private Map<Task_Enum,Button> task_buttons = new HashMap<>();
    private Map<Resource_Type,Button> resource_type_buttons = new HashMap<>();

  ;

    public Window(int size_x, int size_y, Main_thread main_thread)
    {
        this.main_thread = main_thread;
        BuildWindow();


        //the size of the view is dependent on the size of the panels on the top and bottom
        //int view_y_size = size_y - top_panel.getHeight() - bottom_panel.getHeight();

        //View.getInstance().set_size(size_x,view_y_size);
        //View.getInstance().set_y_offset(top_panel.getHeight());
    }

    /**
     * Method that builds the panels on the top and side of the window and includes the middel View Object where the world is displays
     * First buttons, parameter displays and other interactives are created and their non-default parameters are set
     * Second the panels at the edges are created, their parameters are also set
     * The resource counters now created seperately based on the enum valuse of Resource_type
     * Third the elements are added to the panels
     * Finally all elements are added to the window
     *
     */
    public void BuildWindow()
    {
        this.setLayout(new BorderLayout());

        //create top panel labels
        tickcount_text = "Ticks passed: ";
        tickcount_label = new Label (tickcount_text + String.valueOf(10000000));

        //create the buttons
        pause_button = new Button("Pauze");
        pause_button.setBackground(Color.lightGray);
        pause_button.addActionListener(this);

        slowdown_button = new Button("<<");
        slowdown_button.setBackground(Color.lightGray);
        slowdown_button.addActionListener(this);

        speedup_button = new Button(">>");
        speedup_button.setBackground(Color.lightGray);
        speedup_button.addActionListener(this);

        draw_pheromones_button = new Button("Draw Pheromones");
        draw_pheromones_button.setBackground(Color.lightGray);
        draw_pheromones_button.addActionListener(this);

        create_pheromones_button = new Button("create Pheromones");
        create_pheromones_button.setBackground(Color.lightGray);
        create_pheromones_button.addActionListener(this);


        //create labels
        ticks_per_second_text = "Ticks/sec: ";
        ticks_per_second_label = new Label (ticks_per_second_text + String.valueOf(0));
        //ticks_per_second_label.setSize((int)(ticks_per_second_label.getWidth()*1.25),ticks_per_second_label.getHeight());

        //create the panels and add to the elements to them
        top_panel = new Panel();
        top_panel.setLayout(new FlowLayout());
        top_panel.setBackground(Color.gray);

        top_panel.add(tickcount_label);

        add_resource_count_labels(top_panel);

        bottom_grid_panel = new Panel();
        bottom_grid_panel.setLayout(new GridLayout(2,1));

        bottom_panel_1 = new Panel();
        bottom_panel_1.setLayout(new FlowLayout());
        bottom_panel_1.setBackground(Color.gray);

        bottom_panel_2 = new Panel();
        bottom_panel_2.setLayout(new FlowLayout());
        bottom_panel_2.setBackground(Color.gray);

        //bottom_panel_1
        bottom_panel_1.add(draw_pheromones_button);
        bottom_panel_1.add(pause_button);
        bottom_panel_1.add(slowdown_button);
        bottom_panel_1.add(speedup_button);
        bottom_panel_1.add(ticks_per_second_label);

        //bottom_panel_2
        bottom_panel_2.add(create_pheromones_button);
        add_resource_type_buttons(bottom_panel_2);
        add_task_buttons(bottom_panel_2);


        this.add(top_panel,BorderLayout.NORTH);
        bottom_grid_panel.add(bottom_panel_1);
        bottom_grid_panel.add(bottom_panel_2);
        this.add(bottom_grid_panel,BorderLayout.SOUTH);
        this.add(View.getInstance());

    }

    public void add_resource_count_labels(Panel panel)
    {
        for(Resource_Type type: Resource_Type.values())
        {
           // System.out.print(type.toString());
            Label new_resource_count_label = new Label(type.toString() + ": 0" );
            resource_count_labels.put(type,new_resource_count_label);
            panel.add(new_resource_count_label);
        }
    }

    public void add_resource_type_buttons(Panel panel)
    {
        for(Resource_Type type: Resource_Type.values())
        {
            //System.out.print(type.toString());
            Button new_resource_type_button = new Button(type.toString() );
            new_resource_type_button.addActionListener(this);
            resource_type_buttons.put(type,new_resource_type_button);

            panel.add(new_resource_type_button);
        }
    }

    public void add_task_buttons(Panel panel)
    {
        for(Task_Enum task: Task_Enum.values())
        {
           // System.out.print(task.toString());
            Button new_task_button = new Button(task.toString() );
            new_task_button.addActionListener(this);
            task_buttons.put(task,new_task_button);
            panel.add(new_task_button);
        }
    }




    @Override
    public void actionPerformed(ActionEvent e)
    {
        check_pause_button(e);
        check_slowdown_button(e);
        check_speedup_button(e);
        check_draw_pheromones_button(e);
        check_create_pheromones_button(e);
        check_resource_type_button(e);
        check_task_button(e);
    }

    public void check_pause_button(ActionEvent e)
    {
        if( e.getSource() == pause_button )
        {
            if(main_thread.isRunning() == true)
            {
                main_thread.setRunning(false);
                pause_button.setBackground(Color.RED);
            }
            else
            {
                main_thread.setRunning(true);
                pause_button.setBackground(Color.lightGray);
            }

        }
    }

    public void check_draw_pheromones_button(ActionEvent e)
    {
        if( e.getSource() == draw_pheromones_button )
        {
            View view = View.getInstance();
            if(view.isDraw_pheromones() == true)
            {
                view.setDraw_pheromones(false);
                draw_pheromones_button .setBackground(Color.RED);
            }
            else
            {
                view.setDraw_pheromones(true);
                draw_pheromones_button .setBackground(Color.lightGray);
            }

        }
    }

    public void check_task_button(ActionEvent e)
    {
        for(Task_Enum task: Task_Enum.values())
        {
            Button task_button  = task_buttons.get(task);
            task_button.setBackground(Color.lightGray);
            if( e.getSource() == task_button )
            {
               control.setCreate_task(task);

            }
        }
        Task_Enum task = control.getCreate_task();
        if(task != null)
        {
            task_buttons.get(task).setBackground(Color.RED);
        }

    }

    public void check_resource_type_button(ActionEvent e)
    {

        for(Resource_Type res: Resource_Type.values())
        {
            Button resource_type_button  = resource_type_buttons.get(res);
            resource_type_button.setBackground(Color.lightGray);
            if( e.getSource() == resource_type_button )
            {
                control.setCreate_type(res);
            }
        }
        Resource_Type type = control.getCreate_type();
        if(type != null)
        {
            resource_type_buttons.get(type).setBackground(Color.RED);
        }

    }

    public void check_create_pheromones_button(ActionEvent e)
    {
        if( e.getSource() == create_pheromones_button )
        {
            View view = View.getInstance();
            if(control.isCreate_phero() == false)
            {
                control.setCreate_phero(true);
                create_pheromones_button.setBackground(Color.RED);
            }
            else
            {
                control.setCreate_phero(false);
                create_pheromones_button.setBackground(Color.lightGray);
            }

        }
    }

    public void check_slowdown_button(ActionEvent e)
    {
        if (e.getSource() == slowdown_button)
        {
            main_thread.decrease_speed(5);
            update_tick_per_sec_label();
        }
    }


    public void check_speedup_button(ActionEvent e)
    {
        if (e.getSource() == speedup_button)
        {
            main_thread.increase_speed(5);
            update_tick_per_sec_label();
        }
    }

    public void update_tick_per_sec_label()
    {
        ticks_per_second_label.setText( ticks_per_second_text + String.valueOf(main_thread.get_ticks_per_sec()));
    }


    public void update_resource_counters()
    {
        Model model = Model.getInstance();

        HashMap<Resource_Type, Integer> resources = model.getBase().get_obtained_resources_resources();

        for(Resource_Type type: Resource_Type.values())
        {
            int value = resources.get(type);

            resource_count_labels.get(type).setText( type.toString() + ": " + String.valueOf(value)  );

        }

    }

    public void update_tick_counter(int ticks)
    {
        tickcount_label.setText( tickcount_text + ": " + String.valueOf(ticks)  );
        tickcount_label.setSize(150,tickcount_label.getHeight());
    }

    public Controller getControl() {
        return control;
    }

    public void setControl(Controller control) {
        this.control = control;
    }
}
