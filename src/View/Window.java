package View;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import Model.Model;
import Model.Resource_type;

/**
 * Created by christiaan on 10/05/18.
 */
public class Window extends Panel implements ActionListener
{
    private Panel top_panel;
    private Panel bottom_panel;

    private Button pause_button;

    private ArrayList<Label> resource_count_labels = new ArrayList<>();

    public Window(int size_x, int size_y)
    {
        BuildWindow();


        //the size of the view is dependent on the size of the panels on the top and bottom
        int view_y_size = size_y - top_panel.getHeight() - bottom_panel.getHeight();

        //View.getInstance().set_size(size_x,view_y_size);
        //View.getInstance().set_y_offset(top_panel.getHeight());
    }

    public void BuildWindow()
    {
        this.setLayout(new BorderLayout());

        //create the buttons
        pause_button = new Button("Pauze");
        pause_button.setBackground(Color.lightGray);
        pause_button.addActionListener(this);

        //create the panels and add to the fl
        top_panel = new Panel();
        top_panel.setLayout(new FlowLayout());
        top_panel.setBackground(Color.gray);

        bottom_panel = new Panel();
        bottom_panel.setLayout(new FlowLayout());
        bottom_panel.setBackground(Color.gray);

        bottom_panel.add(pause_button);

        add_resource_count_labels();

        this.add(top_panel,BorderLayout.NORTH);
        this.add(bottom_panel,BorderLayout.SOUTH);
        this.add(View.getInstance());

    }

    public void add_resource_count_labels()
    {
        for(Resource_type type: Resource_type.values())
        {
            System.out.print(type.toString());
            Label new_resource_count_label = new Label(type.toString() + ": 0" );
            resource_count_labels.add(new_resource_count_label);
            top_panel.add(new_resource_count_label);
        }
    }


    @Override
    public void actionPerformed(ActionEvent e)
    {
        Model model = Model.getInstance();

        if( e.getSource() == pause_button )
        {
            if(model.isPause()== false)
            {
              model.setPause(true);
              pause_button.setBackground(Color.RED);
            }
            else
            {
               model.setPause(false);
               pause_button.setBackground(Color.lightGray);
            }

        }

    }
}
