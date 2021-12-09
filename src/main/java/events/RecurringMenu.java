package events;

import calendar.CalendarController;
import constants.VarExceptions;
import frontend.MainMenu;
import login.LogIn;
import users.groups.GroupController;
import users.students.StudentController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

/** RecurringMenu window is made to create recurring events
 */
public class RecurringMenu implements GUIEventMenu {
    private JTextField eventNameText;
    private JFrame frame;
    private JButton returnButton;
    private JButton confirmButton;
    private JComboBox<String> daysOfWeek;
    private JComboBox<String> startHours;
    private JComboBox<String> startMinutes;
    private JComboBox<String> endHours;
    private JComboBox<String> endMinutes;
    private String eventName;
    private String day;
    private float startHour;
    private float startMinute;
    private float endHour;
    private float endMinute;
    private LogIn loginController;
    private GroupController groupController;
    private CalendarController calendarController;
    private StudentController studentController;
    private String studentUsername;

    /** constructor RecurringMenu with 5 parameters
     * @param loginController
     * @param groupController
     * @param calendarController
     * @param studentController
     * @param studentUsername
     */

    public RecurringMenu(LogIn loginController, GroupController groupController, CalendarController calendarController,
                         StudentController studentController, String studentUsername) {
        setControllers(loginController, groupController, calendarController, studentController);
        this.studentUsername = studentUsername;

        this.day = "";
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

        this.eventNameText = new JTextField();
        this.eventNameText.setBounds(200,50,150,30);
        this.frame.add(this.eventNameText);
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
    }

    @Override
    public void setControllers(LogIn loginController, GroupController groupController, CalendarController calendarController, StudentController studentController) {
        this.loginController = loginController;
        this.groupController = groupController;
        this.calendarController = calendarController;
        this.studentController = studentController;
    }

    @Override
    public void setEventDropDown() {
        String[] days = {"Monday", "Tuesday", "Wednesday",
                "Thursday", "Friday", "Saturday", "Sunday"};
        String[] hours = new String[24];
        for(int i = 0; i != 24; i++){
            hours[i] = String.valueOf(i);
        }
        String[] minutes = {"00", "30"};

        this.daysOfWeek = new JComboBox<>(days);
        this.daysOfWeek.setBounds(25,120,100,30);
        this.daysOfWeek.addActionListener(this);
        this.frame.add(this.daysOfWeek);

        this.startHours = new JComboBox<>(hours);
        this.startHours.setBounds(160,120,70,30);
        this.startHours.addActionListener(this);
        this.frame.add(this.startHours);

        this.endHours = new JComboBox<>(hours);
        this.endHours.setBounds(315,120,70,30);
        this.endHours.addActionListener(this);
        this.frame.add(this.endHours);

        this.startMinutes = new JComboBox<>(minutes);
        this.startMinutes.setBounds(230,120,70,30);
        this.startMinutes.addActionListener(this);
        this.frame.add(this.startMinutes);

        this.endMinutes = new JComboBox<>(minutes);
        this.endMinutes.setBounds(385,120,70,30);
        this.endMinutes.addActionListener(this);
        this.frame.add(this.endMinutes);
    }

    /** Makes button to perform based on a choice of a user.
     * @param e
     */

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.returnButton){
            this.frame.dispose();
            MainMenu mainMenu = new MainMenu(this.loginController, this.groupController,
                    this.calendarController, this.studentController, this.studentUsername);
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
            this.day = (String) daysOfWeek.getSelectedItem();

            ArrayList<CalendarEvent> events = new ArrayList<>();
            events.add(this.calendarController.createRecEvent(this.eventName, this.startHour + (this.startMinute / 100),
                    this.endHour + (this.endMinute/100), this.day));
            try {
                this.calendarController.addRecEvent(this.studentUsername, events);
            } catch (VarExceptions ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Overlapping Event Added");
            }
        }

    }


}
