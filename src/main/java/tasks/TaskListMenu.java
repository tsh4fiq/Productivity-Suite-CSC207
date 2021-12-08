package tasks;
import calendar.CalendarController;
import frontend.GUI;
import frontend.MainMenu;
import login.LogIn;
import users.groups.GroupController;
import users.students.StudentController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;

public class TaskListMenu implements GUI {
    private HashMap<JButton, Task> mappings;

    private LogIn loginController;
    private GroupController groupController;
    private CalendarController calendarController;
    private StudentController studentController;
    private String studentUsername;

    private JButton newTaskButton;
    private JFrame frame;
    private JButton returnButton;

    public TaskListMenu(LogIn loginController, GroupController groupController, CalendarController calendarController,
                        StudentController studentController, String studentUsername) {

        this.mappings = new HashMap<JButton, Task>();
        this.frame = new JFrame();
//        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(500, 500);

        setControllers(loginController, groupController, calendarController, studentController);
        this.studentUsername = studentUsername;
        this.frame.setLayout(new GridLayout(6, 6));

        setButtons();
//        setLabelsAndText();
        this.frame.setVisible(true);

    }
//
//        JPanel panel = new JPanel();
//        panel.setSize(400, 400);
//        panel.setLayout(new GridLayout(0,0,5,10));

    @Override
    public void setLabelsAndText() {

    }

    public void setButtons() {
        this.returnButton = new JButton("Return Home");
        this.returnButton.setBounds(0,0,200,200);
        this.returnButton.addActionListener(this);
        this.frame.add(returnButton);

        ArrayList<Task> tasks = this.studentController.getTasks(this.studentUsername);

        for (Task ex : tasks){
            JButton button = new JButton();
            String exString = ex.toString();
            button.setText(exString);
            if (ex.isClosed()) {
                button.setForeground(Color.red);
            }
            button.addActionListener(this);

            this.mappings.put(button, ex);

            this.frame.add(button);

        }




        this.newTaskButton = new JButton("Add a new Task");
        this.newTaskButton.addActionListener(this);
        this.frame.add(newTaskButton);

        frame.setVisible(true);


    }

    @Override
    public void setControllers(LogIn loginController, GroupController groupController,
                               CalendarController calendarController, StudentController studentController) {
        this.loginController = loginController;
        this.groupController = groupController;
        this.calendarController = calendarController;
        this.studentController = studentController;
    }


    /** Makes button to perform based on a choice of a user.
     * @param e
     */

    @Override
    public void actionPerformed(ActionEvent e) {
        for (JButton button: this.mappings.keySet()){
            if (e.getSource() == button){
                this.frame.dispose();
                Task task = this.mappings.get(button);
                new ViewTask(this.loginController, this.groupController, this.calendarController,
                        this.studentController, this.studentUsername, task);

            }
        }

        if(e.getSource()==newTaskButton) {
            this.frame.dispose();
            new NewTaskWindow(this.loginController, this.groupController, this.calendarController,
                    this.studentController, this.studentUsername);
        }
        else if(e.getSource() == returnButton){
            this.frame.dispose();
            new MainMenu(this.loginController, this.groupController, this.calendarController,
                    this.studentController, this.studentUsername);
        }
    }

}