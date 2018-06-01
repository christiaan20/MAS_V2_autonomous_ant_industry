package View;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

import Main.Main_thread;
import Model_pk.Behaviour.Task_Enum;
import Model_pk.Model;
import Model_pk.Enums.Resource_Type_Enum;
import Controller.Controller;
import Model_pk.Objects.Abstr_Object;
import Model_pk.Task_managers.Abstr_Task_manager;
import Model_pk.Task_managers.Avg_travel_time;
import Model_pk.Task_managers.Task_Manager_Simple;
import Model_pk.Task_managers.Task_manager_extended;
import View.Object_visuals.Object_visual;

/**
 * A window with panels where the user can interact with the program and see the goals.
 */
public class Window extends Panel implements ActionListener
{
    private Main_thread main_thread;
    private Controller control;

    private Panel top_panel_1;
    private Panel top_panel_2;
    private Panel top_panel_3;
    private Panel top_grid_panel;
    private Panel bottom_panel_1;
    private Panel bottom_panel_2;
    private Panel bottom_grid_panel;

    //general information labels
    private String  tickcount_text;
    private Label   tickcount_label;
    private String  resource_goal_text;
    private Label   resource_goal_label;
    private Map<Resource_Type_Enum,Label> resource_goal_labels = new HashMap<>();
    private String  resource_count_text;
    private Label   resource_count_label;
    private Map<Resource_Type_Enum,Label> resource_count_labels = new HashMap<>();
    private String  resource_prob_text;
    private Label   resource_prob_label;
    private Map<Resource_Type_Enum,Label> resource_prob_labels  = new HashMap<>();

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
    private Map<Resource_Type_Enum,Button> resource_type_buttons = new HashMap<>();

    //button to restart the simulation
    private Button restart_button;

    //button to break the currently selected worker
    private Button break_worker_button;

  ;

    public Window(int size_x, int size_y, Main_thread main_thread)
    {
        this.main_thread = main_thread;
        BuildWindow();

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

        resource_goal_text =    "GOALS         : ";
        resource_goal_label = new Label(resource_goal_text);;
        resource_count_text =   "RESOURCE COUNT: ";
        resource_count_label = new Label(resource_count_text);
        resource_prob_text =    "PROBABILITIES : ";
        resource_prob_label = new Label(resource_prob_text);

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

        restart_button = new Button("Restart");
        restart_button.setBackground(Color.lightGray);
        restart_button.addActionListener(this);

        break_worker_button = new Button("Break worker");
        break_worker_button.setBackground(Color.lightGray);
        break_worker_button.addActionListener(this);

        //create labels
        ticks_per_second_text = "Ticks/sec: ";
        ticks_per_second_label = new Label (ticks_per_second_text + String.valueOf(0));

        //create the panels and add to the elements to them
        top_grid_panel = new Panel();
        top_grid_panel.setLayout(new GridLayout(2,1));

        top_panel_1 = new Panel();
        top_panel_1.setLayout(new FlowLayout());
        top_panel_1.setBackground(new Color(119, 167, 101));
        top_panel_2 = new Panel();
        top_panel_2.setLayout(new FlowLayout());
        top_panel_2.setBackground(new Color(167, 124, 95));
        top_panel_3 = new Panel();
        top_panel_3.setLayout(new FlowLayout());
        top_panel_3.setBackground(new Color(161, 167, 166));

        top_panel_1.add(resource_goal_label);
        add_labels_template(top_panel_1);

        top_panel_2.add(resource_count_label);
        add_labels_template(top_panel_2);

        top_panel_3.add(resource_prob_label);
        add_resource_prob_labels(top_panel_3);

        bottom_grid_panel = new Panel();
        bottom_grid_panel.setLayout(new GridLayout(1,1));

        bottom_panel_1 = new Panel();
        bottom_panel_1.setLayout(new FlowLayout());
        bottom_panel_1.setBackground(Color.gray);

        bottom_panel_2 = new Panel();
        bottom_panel_2.setLayout(new FlowLayout());
        bottom_panel_2.setBackground(Color.gray);

        //bottom_panel_1

        bottom_panel_1.add(break_worker_button);
        bottom_panel_1.add(draw_pheromones_button);
        bottom_panel_1.add(restart_button);
        bottom_panel_1.add(pause_button);
        bottom_panel_1.add(slowdown_button);
        bottom_panel_1.add(speedup_button);
        bottom_panel_1.add(ticks_per_second_label);
        bottom_panel_1.add(tickcount_label);

        //bottom_panel_2
        bottom_panel_2.add(create_pheromones_button);
        add_resource_type_buttons(bottom_panel_2);
        add_task_buttons(bottom_panel_2);


        top_grid_panel.add(top_panel_1);
        top_grid_panel.add(top_panel_2);
        //top_grid_panel.add(top_panel_3);
        this.add(top_grid_panel,BorderLayout.NORTH);
        bottom_grid_panel.add(bottom_panel_1);
        //bottom_grid_panel.add(bottom_panel_2);
        this.add(bottom_grid_panel,BorderLayout.SOUTH);
        this.add(View.getInstance());

    }

    public void add_labels_template(Panel panel)
    {
        for(Resource_Type_Enum type: Resource_Type_Enum.values())
        {
            Label new_resource_count_label = new Label(type.toString() + ": 0000 " );
            new_resource_count_label.setSize(125,50);
            resource_goal_labels.put(type,new_resource_count_label);
            panel.add(new_resource_count_label);
        }
    }


    public void add_resource_prob_labels(Panel panel)
    {
        for(Resource_Type_Enum type: Resource_Type_Enum.values())
        {
            Label new_resource_count_label = new Label(type.toString() + ": 00 " );
            resource_prob_labels.put(type,new_resource_count_label);
            panel.add(new_resource_count_label);
        }
    }

    public void add_resource_type_buttons(Panel panel)
    {
        for(Resource_Type_Enum type: Resource_Type_Enum.values())
        {
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
            Button new_task_button = new Button(task.toString() );
            new_task_button.addActionListener(this);
            task_buttons.put(task,new_task_button);
            panel.add(new_task_button);
        }
    }

    public void update_tick_per_sec_label()
    {
        ticks_per_second_label.setText( ticks_per_second_text + String.valueOf(main_thread.get_ticks_per_sec()));
    }

    public void update_resource_goals()
    {
        Model model = Model.getInstance();

        HashMap<Resource_Type_Enum, Integer> goal = model.getTest_setting().get_current_goal();

        for(Resource_Type_Enum type: Resource_Type_Enum.values())
        {
            int value = goal.get(type);

            Label selected = resource_goal_labels.get(type);
            selected.setText( type.toString() + ": " + String.valueOf(value)  );

        }

    }

    public void update_resource_counters()
    {
        Model model = Model.getInstance();

        HashMap<Resource_Type_Enum, Integer> resources = model.getBase().get_obtained_resources_resources();

        for(Resource_Type_Enum type: Resource_Type_Enum.values())
        {
            int value = resources.get(type);
            Label selected = resource_count_labels.get(type);

            selected.setText( type.toString() + ": " + String.valueOf(value)  );
        }

    }

    public void update_resource_probs()
    {
        Model model = Model.getInstance();
        int last_value = 0;

        Abstr_Task_manager task_manager = model.getBase().getTask_manager();

        if ( task_manager instanceof Task_manager_extended){
            Task_manager_extended task_manager_extended = (Task_manager_extended)task_manager;
            HashMap<Resource_Type_Enum, Integer> distribution_of_worker = task_manager_extended.get_new_distribution_of_worker();

            for(Resource_Type_Enum type: Resource_Type_Enum.values())
            {
                int value = distribution_of_worker.get(type);
                resource_prob_labels.get(type).setText( type.toString() + ": " + String.valueOf(value)  );
            }

        }
        else if( task_manager instanceof Task_Manager_Simple){
            Task_Manager_Simple task_Manager_Simple = (Task_Manager_Simple)task_manager;
            HashMap<Resource_Type_Enum, Integer> resources = task_Manager_Simple.get_resource_acumm_percentage_rates();
            if (resources == null)
                return;
            for(Resource_Type_Enum type: Resource_Type_Enum.values())
            {
                int value = resources.get(type);
                int display_value = value - last_value;
                last_value = value;

                resource_prob_labels.get(type).setText( type.toString() + ": " + String.valueOf(display_value)  );

            }

        }

    }

    public void update_tick_counter(int ticks)
    {
        tickcount_label.setText( tickcount_text + ": " + String.valueOf(ticks)  );
        tickcount_label.setSize(150,tickcount_label.getHeight());
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
        check_restart_button(e);
        check_break_worker_button(e);
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

    public void check_break_worker_button(ActionEvent e)
    {
        if( e.getSource() == break_worker_button )
        {
            Abstr_Object obj =  View.getInstance().getSelected_visual().getModel_object();
           if(obj!=null)
           {
               obj.setBroken(true);
           }
        }
    }

    public void check_restart_button(ActionEvent e)
    {
        if( e.getSource() == restart_button )
        {
           main_thread.setRestart_activated(true);

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

        for(Resource_Type_Enum res: Resource_Type_Enum.values())
        {
            Button resource_type_button  = resource_type_buttons.get(res);
            resource_type_button.setBackground(Color.lightGray);
            if( e.getSource() == resource_type_button )
            {
                control.setCreate_type(res);
            }
        }
        Resource_Type_Enum type = control.getCreate_type();
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



    public Controller getControl() {
        return control;
    }

    public void setControl(Controller control) {
        this.control = control;
    }
}
