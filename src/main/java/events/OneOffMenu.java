package events;

import calendar.CalendarController;
import constants.VarExceptions;
import frontend.MainMenu;
import login.LogIn;
import users.groups.GroupController;
import users.students.StudentController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/** OneOffMenu window is made to create one event,without any recurrence
 */
public class OneOffMenu implements GUIEventMenu {
    private JTextField eventNameText;
    private JFrame frame;
    private JButton returnButton;
    private JButton confirmButton;
    private JComboBox month;
    private JComboBox day;
    private JComboBox startHours;
    private JComboBox startMinutes;
    private JComboBox endHours;
    private JComboBox endMinutes;
    private String eventName;
    private float dateMonth;
    private float dateDay;
    private float startHour;
    private float startMinute;
    private float endHour;
    private float endMinute;
    private LogIn loginController;
    private GroupController groupController;
    private CalendarController calendarController;
    private StudentController studentController;
    private String studentUsername;

    /** constructor OneOffMenu with 5 parameters
     * @param loginController
     * @param groupController
     * @param calendarController
     * @param studentController
     * @param studentUsername
     */

    public OneOffMenu(LogIn loginController, GroupController groupController, CalendarController calendarController,
                      StudentController studentController, String studentUsername) {
        setControllers(loginController, groupController, calendarController, studentController);
        this.studentUsername = studentUsername;
        this.dateMonth = 0;
        this.dateDay = 0;
        this.startHour = 0;
        this.startMinute = 0;
        this.endHour = 0;
        this.endMinute = 0;
        this.eventName = "";
        this.frame = new JFrame();
        this.frame.setLayout(null);
        this.frame.setSize(500,500);
        setButtons();
        setEventDropDown();
        setLabelsAndText();
        this.frame.setVisible(true);
    }


    @Override
    public void setLabelsAndText() {
        JLabel userLabel = new JLabel("Event Name");
        userLabel.setBounds(110,50,110,30);
        this.frame.add(userLabel);
    }

    @Override
    public void setButtons() {
        this.returnButton = new JButton("Return");
        this.returnButton.setBounds(0,0,100,20);
        this.returnButton.addActionListener(this);
        this.frame.add(this.returnButton);

        this.confirmButton = new JButton("Confirm");
        this.confirmButton.setBounds(300,200,100,30);
        this.confirmButton.addActionListener(this);
        this.frame.add(this.confirmButton);

        this.eventNameText = new JTextField();
        this.eventNameText.setBounds(200,50,150,30);
        this.frame.add(this.eventNameText);
    }

    @Override
    public void setControllers(LogIn loginController, GroupController groupController, CalendarController calendarController,
                                      StudentController studentController) {
        this.loginController = loginController;
        this.groupController = groupController;
        this.calendarController = calendarController;
        this.studentController = studentController;
    }

    @Override
    public void setEventDropDown() {
        String[] months = new String[12];
        String[] days = new String[31];
        for(int i = 0; i != 12; i++){
            months[i] = (String.valueOf(i + 1));
        }
        for(int i = 0; i != 31; i++){
            days[i] = (String.valueOf(i + 1));
        }
        String[] hours = new String[24];
        for(int i = 0; i != 24; i++){
            hours[i] = (String.valueOf(i));
        }
        String[] minutes = {"00", "30"};

        this.month = new JComboBox(months);
        this.month.setBounds(10,120,70,30);
        this.month.addActionListener(this);
        this.frame.add(this.month);

        this.day = new JComboBox(days);
        this.day.setBounds(75,120,70,30);
        this.day.addActionListener(this);
        this.frame.add(this.day);

        this.startHours = new JComboBox(hours);
        this.startHours.setBounds(170,120,70,30);
        this.startHours.addActionListener(this);
        this.frame.add(this.startHours);

        this.endHours = new JComboBox(hours);
        this.endHours.setBounds(320,120,70,30);
        this.endHours.addActionListener(this);
        this.frame.add(this.endHours);

        this.startMinutes = new JComboBox(minutes);
        this.startMinutes.setBounds(235,120,70,30);
        this.startMinutes.addActionListener(this);
        this.frame.add(this.startMinutes);

        this.endMinutes = new JComboBox(minutes);
        this.endMinutes.setBounds(385,120,70,30);
        this.endMinutes.addActionListener(this);
        this.frame.add(this.endMinutes);
    }

    /** Makes button to perform based on a choice of a user.
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e)  {
        if(e.getSource() == this.returnButton){
            this.frame.dispose();
            MainMenu mainMenu = new MainMenu(this.loginController, this.groupController,
                    this.calendarController, this.studentController, this.studentUsername);
        }else if(e.getSource() == this.month){
            this.dateMonth = Float.parseFloat((String) month.getSelectedItem()) ;

        }else if(e.getSource() == this.day){
            this.dateDay =  Float.parseFloat((String) day.getSelectedItem())  ;

        }else if(e.getSource() == this.startMinutes){
            this.startMinute =  Float.parseFloat((String) startMinutes.getSelectedItem());

        }else if(e.getSource() == this.startHours){
            this.startHour =  Float.parseFloat((String) startHours.getSelectedItem());

        }else if(e.getSource() == this.endMinutes){
            this.endMinute =  Float.parseFloat((String) endMinutes.getSelectedItem());

        }else if(e.getSource() == this.endHours){
            this.endHour =  Float.parseFloat((String) endHours.getSelectedItem());

        }else if(e.getSource() == this.confirmButton){
            this.frame.dispose();
            MainMenu mainMenu = new MainMenu(this.loginController, this.groupController,
                    this.calendarController, this.studentController, this.studentUsername);
            this.eventName = this.eventNameText.getText();

            ArrayList<OneOffEvent> events = new ArrayList();
            events.add(this.calendarController.createOneOffEvent(this.eventName, this.startHour + (this.startMinute / 100),
                    this.endHour + (this.endMinute/100), this.dateMonth + (this.dateDay/100)));
            try {
                this.calendarController.addOneOffEvent(this.studentUsername, events);
            } catch (VarExceptions ex) {

                JOptionPane.showMessageDialog(null, "Error: you added an overlapping Event");

            }

        }

    }

}
