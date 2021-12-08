package tasks;

import calendar.CalendarController;
import frontend.GUI;
import login.LogIn;
import users.groups.GroupController;
import users.students.StudentController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class NewTaskWindow implements GUI {
    private LogIn loginController;
    private GroupController groupController;
    private CalendarController calendarController;
    private StudentController studentController;
    private String studentUsername;

    private JButton back;
    private JFrame frame;
    private JTextField taskName;
    private JButton cancelButton;
    private JButton submitButton;


    public NewTaskWindow(LogIn loginController, GroupController groupController, CalendarController calendarController,
                         StudentController studentController, String studentUsername){

        setControllers(loginController, groupController, calendarController, studentController);
        this.studentUsername = studentUsername;

        this.frame = new JFrame();
        this.frame.setSize(500,500);
        this.frame.setLayout(new GridLayout(6, 6));

        setLabelsAndText();
        setButtons();

        this.frame.setVisible(true);


    }

    @Override
    public void setLabelsAndText() {
        JLabel taskNameLabel = new JLabel("TODO: ");
        taskNameLabel.setBounds(110,50,115, 30);
        this.frame.add(taskNameLabel);

        this.taskName = new JTextField();
        this.taskName.setBounds(210,50,165,30);
        this.frame.add(this.taskName);
    }

    @Override
    public void setButtons() {
        this.submitButton = new JButton("Submit");
        this.submitButton.setBounds(100,100,100,30);
        this.submitButton.addActionListener(this);
        this.frame.add(this.submitButton);

        this.cancelButton = new JButton("Cancel");
        this.cancelButton.setBounds(240,100,100,30);
        this.cancelButton.addActionListener(this);
        this.frame.add(this.cancelButton);
    }

    @Override
    public void setControllers(LogIn loginController, GroupController groupController, CalendarController calendarController, StudentController studentController) {
        this.loginController = loginController;
        this.groupController = groupController;
        this.calendarController = calendarController;
        this.studentController = studentController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.cancelButton) {
            this.frame.dispose();
            new TaskListMenu(this.loginController, this.groupController, this.calendarController,
                    this.studentController, this.studentUsername);
        }
        else if(e.getSource() == this.submitButton){
            String name = this.taskName.getText();
            this.studentController.addTask(this.studentUsername, name);

            this.frame.dispose();
            new TaskListMenu(this.loginController, this.groupController, this.calendarController,
                    this.studentController, this.studentUsername);


        }
    }
}