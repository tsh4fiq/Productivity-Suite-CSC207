package tasks;

import calendar.CalendarController;
import frontend.GUI;
import login.LogIn;
import users.groups.GroupController;
import users.students.StudentController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ViewTask implements GUI {


    private Task task;
    private LogIn loginController;
    private GroupController groupController;
    private CalendarController calendarController;
    private StudentController studentController;
    private String studentUsername;

    private JFrame frame;
    private JButton removeTask;
    private JButton closeTask;
    private JButton back;

    public ViewTask(LogIn loginController, GroupController groupController,
                    CalendarController calendarController, StudentController studentController,
                    String studentUsername, Task task) {
        setControllers(loginController, groupController, calendarController, studentController);
        this.studentUsername = studentUsername;
        this.task = task;

        this.frame = new JFrame();
        this.frame.setSize(300, 1200);
        this.frame.setLayout(new GridLayout(6, 6));
        setButtons();
        setLabelsAndText();
        this.frame.setVisible(true);

    }

    @Override
    public void setLabelsAndText() {

    }

    @Override
    public void setButtons() {
        this.removeTask = new JButton("Delete the Task");
        this.removeTask.addActionListener(this);
        this.frame.add(removeTask);

        if(this.studentController.isClosedTask(this.studentUsername, this.task)){
            this.closeTask = new JButton("Task is already DONE");
            this.frame.add(closeTask);
        }else{
            this.closeTask = new JButton("Mark task as DONE");
            this.closeTask.addActionListener(this);
            this.frame.add(closeTask);
        }


        this.back = new JButton("Return to Your TODO List");
        this.back.addActionListener(this);
        this.frame.add(back);

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

        if (e.getSource() == this.removeTask){
            this.frame.dispose();
            this.studentController.removeTask(this.studentUsername, this.task);
            new TaskListMenu(this.loginController, this.groupController,
                    this.calendarController, this.studentController, this.studentUsername);
        }

        else if (e.getSource() == this.closeTask){
            this.frame.dispose();
            this.studentController.closeTask(this.studentUsername, this.task);
            new TaskListMenu(this.loginController, this.groupController,
                    this.calendarController, this.studentController, this.studentUsername);
        }
        else if(e.getSource() == this.back){
            this.frame.dispose();
            new TaskListMenu(this.loginController, this.groupController,
                    this.calendarController, this.studentController, this.studentUsername);
        }
    }
}