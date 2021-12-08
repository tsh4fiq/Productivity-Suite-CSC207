package users.groups;

import calendar.CalendarController;
import frontend.GUI;
import login.LogIn;
import users.*;
import users.students.Student;
import users.students.StudentController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;

/** CreateGroup window is made to create a new group.*/

public class CreateGroup implements GUI {

    private JFrame frame;
    private JTextField groupName;
    private JButton submitButton;
    private JButton cancelButton;

    private LogIn loginController;
    private GroupController groupController;
    private CalendarController calendarController;
    private StudentController studentController;
    private String studentUsername;

    /** This constructor is used for creating a new Group, has 5 parameters
     * @param loginController
     * @param groupController
     * @param calendarController
     * @param studentController
     * @param studentUsername
     * */

    public CreateGroup(LogIn loginController, GroupController groupController, CalendarController calendarController,
                       StudentController studentController, String studentUsername){

        setControllers(loginController, groupController, calendarController, studentController);
        this.studentUsername = studentUsername;

        this.frame = new JFrame();
        this.frame.setLayout(null);
        this.frame.setSize(500,500);
        setButtons();
        setLabelsAndText();
        this.frame.setVisible(true);
    }

    @Override
    public void setLabelsAndText() {
        JLabel groupNameLabel = new JLabel("Group Name: ");
        groupNameLabel.setBounds(110,50,115, 30);
        this.frame.add(groupNameLabel);

        this.groupName = new JTextField();
        this.groupName.setBounds(210,50,165,30);
        this.frame.add(this.groupName);
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

    /** Makes button to perform based on a choice of a user.
     * @param e
     */

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == this.submitButton){

            HashMap<String, Student> studentHashMap =  this.studentController.getAllStudents();
            Student curr =  studentHashMap.get(this.studentUsername);
            ArrayList<Person> newGroup = new ArrayList<Person>();
            newGroup.add(curr);

            String name = this.groupName.getText();
            Group group = this.groupController.createGroup(newGroup, name);
            String id = this.groupController.getID(group);

            this.frame.dispose();
            new CreateGroupPopUp(this.loginController, this.groupController, this.calendarController,
                        this.studentController, this.studentUsername,id);
        }
        else if(e.getSource() == this.cancelButton){
            this.frame.dispose();
            GroupMenu menu = new GroupMenu(this.loginController, this.groupController, this.calendarController,
                    this.studentController, this.studentUsername);
        }
    }
}
