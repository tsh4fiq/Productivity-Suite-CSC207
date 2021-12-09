package frontend;

import backend.JsonWriter;
import calendar.CalendarController;
import calendar.CalendarFrame;
import constants.VarExceptions;
import events.*;
import login.LogIn;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;

import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.CalendarComponent;

import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.DtEnd;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.RRule;
import tasks.TaskListMenu;
import users.groups.GroupController;
import users.groups.GroupMenu;
import users.students.StudentController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.GregorianCalendar;


/** When User is successfully logged in, MainMenu window pops with 4 options to continue*/
public class MainMenu implements GUI {
    private JFrame frame;
    private JButton addRecurButton;
    private JButton addOneOffButton;
    private JButton groupsButton;
    private JButton viewCalendarButton;
    private JButton returnButton;
    private JButton todoButton;
    
    private LogIn loginController;
    private GroupController groupController;
    private String studentUsername;
    private CalendarController calendarController;
    private StudentController studentController;
    private JButton uploadFile;
    private JLabel uploadLabel;

    /**
     * constructor StartMenu with 5 parameters
     *
     * @param loginController
     * @param groupController
     * @param calendarController
     * @param studentController
     * @param studentUsername
     */

    public MainMenu(LogIn loginController, GroupController groupController, CalendarController calendarController,
                    StudentController studentController, String studentUsername) {

        this.studentUsername = studentUsername;
        setControllers(loginController, groupController, calendarController, studentController);
        this.frame = new JFrame();
        JPanel panel = new JPanel();
        this.frame.setLayout(null);
        this.frame.setVisible(true);
        setLabelsAndText();
        setButtons();
        this.frame.setSize(500, 500);
    }

    @Override
    public void setLabelsAndText() {

    }

    @Override
    public void setButtons() {
        this.addRecurButton = new JButton("Create Recurring Events");
        this.addOneOffButton = new JButton("Create OneOff Events");
        this.groupsButton = new JButton("Groups");
        this.viewCalendarButton = new JButton("View Your Calendar");
        this.returnButton = new JButton("Logout");
        this.uploadFile = new JButton("Upload Ical File");
        this.todoButton = new JButton("Your TODO List");


        this.addRecurButton.setBounds(50, 110, 170, 40);
        this.groupsButton.setBounds(50, 220, 170, 40);
        this.addOneOffButton.setBounds(250, 110, 170, 40);
        this.viewCalendarButton.setBounds(250, 220, 170, 40);
        this.returnButton.setBounds(0, 0, 100, 20);

        this.uploadFile.setBounds(50, 330, 170, 40);
        this.todoButton.setBounds(250, 330,170,40);
        


        this.addRecurButton.addActionListener(this);
        this.addOneOffButton.addActionListener(this);
        this.groupsButton.addActionListener(this);
        this.viewCalendarButton.addActionListener(this);
        this.returnButton.addActionListener(this);
        this.uploadFile.addActionListener(this);
        this.todoButton.addActionListener(this);

        this.frame.add(addRecurButton);
        this.frame.add(addOneOffButton);
        this.frame.add(groupsButton);
        this.frame.add(viewCalendarButton);
        this.frame.add(returnButton);
        this.frame.add(uploadFile);
        this.frame.add(todoButton);

    }

    @Override
    public void setControllers(LogIn loginController, GroupController groupController, CalendarController calendarController, StudentController studentController) {
        this.loginController = loginController;
        this.groupController = groupController;
        this.calendarController = calendarController;
        this.studentController = studentController;
    }

    /**
     * Makes button to perform based on a choice of a user.
     *
     * @param e
     */

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.addRecurButton) {
            this.frame.dispose();
            RecurringMenu recurringMenu = new RecurringMenu(this.loginController, this.groupController,
                    this.calendarController, this.studentController, this.studentUsername);
        } else if (e.getSource() == this.addOneOffButton) {
            this.frame.dispose();
            OneOffMenu oneOffMenu = new OneOffMenu(this.loginController, this.groupController,
                    this.calendarController, this.studentController, this.studentUsername);

        } else if (e.getSource() == this.groupsButton) {
            this.frame.dispose();
            GroupMenu groupMenu = new GroupMenu(this.loginController, this.groupController,
                    this.calendarController, this.studentController, this.studentUsername);

        } else if (e.getSource() == this.viewCalendarButton) {
            GregorianCalendar cal = new GregorianCalendar();
            int realMonth = cal.get(GregorianCalendar.MONTH);
            int realYear = cal.get(GregorianCalendar.YEAR);
            CalendarFrame calendarFrame = new CalendarFrame(this.loginController, this.groupController,
                    this.calendarController, this.studentController, this.studentUsername, realMonth, realYear);

        } else if (e.getSource() == this.returnButton) {
            JsonWriter jsonWriter = new JsonWriter();
            jsonWriter.saveData(this.studentController, this.groupController);
            this.frame.dispose();
            StartMenu startMenu = new StartMenu(this.loginController, this.groupController,
                    this.calendarController, this.studentController);

        } else if (e.getSource() == this.uploadFile) {

            JFileChooser fileChooser = new JFileChooser();
            int response = fileChooser.showOpenDialog(null);

            if (response == JFileChooser.APPROVE_OPTION) {
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                try {
                    this.useFile(file);
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (ParserException ex) {
                    ex.printStackTrace();
                } catch (VarExceptions varExceptions) {
                    varExceptions.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error: Overlapping Events Found");
                }
                JOptionPane.showMessageDialog(null, "File Succesfully Uploaded");
            }

        }else if (e.getSource() == this.todoButton){
            this.frame.dispose();
            new TaskListMenu(this.loginController, this.groupController,
                    this.calendarController, this.studentController, this.studentUsername);
        }
    }


    public void useFile(File file) throws IOException, ParserException, VarExceptions {


        FileInputStream fin = new FileInputStream(file);
        CalendarBuilder builder = new CalendarBuilder();
        Calendar calendar = builder.build(fin);
        ComponentList<CalendarComponent> list = calendar.getComponents();
        ArrayList<CalendarEvent> recurring = new ArrayList<CalendarEvent>();
        ArrayList<OneOffEvent> singles = new ArrayList<OneOffEvent>();


        for (CalendarComponent i : list) {

            if (i instanceof VEvent) {

                RRule rrule = ((VEvent) i).getProperty(Property.RRULE);

                if (rrule == null) {

                    String nameEvent = ((VEvent) i).getSummary().getValue();
                    DtStart startDate = ((VEvent) i).getStartDate();
                    DtEnd endDate = ((VEvent) i).getEndDate();
                    long beginTime = startDate.getDate().getTime();
                    long endTime =endDate.getDate().getTime();
                    ZonedDateTime dateTimeBegin = Instant.ofEpochMilli(beginTime)
                            .atZone(ZoneId.of("Canada/Eastern"));
                    String formattedBegin = dateTimeBegin.format(DateTimeFormatter.ofPattern("MM.dd HH.mm"));
                    ZonedDateTime dateTimeEnd = Instant.ofEpochMilli(endTime)
                            .atZone(ZoneId.of("Canada/Eastern"));
                    String formattedEnd = dateTimeEnd.format(DateTimeFormatter.ofPattern("HH.mm"));
                    String[] arrOfStr = formattedBegin.split(" ");

                    singles.add(this.calendarController.createOneOffEvent(nameEvent,
                            Float.parseFloat(arrOfStr[1]),
                            Float.parseFloat(formattedEnd), Float.parseFloat(arrOfStr[0])));
                } else {

                    String rule = rrule.getValue();
                    if (rule.contains("WEEKLY")) {

                        String nameEvent = ((VEvent) i).getSummary().getValue();
                        DtStart startDate = ((VEvent) i).getStartDate();
                        DtEnd endDate = ((VEvent) i).getEndDate();
                        long beginTime = startDate.getDate().getTime();
                        long endTime =endDate.getDate().getTime();
                        ZonedDateTime dateTimeBegin = Instant.ofEpochMilli(beginTime)
                                .atZone(ZoneId.of("Canada/Eastern"));
                        String formattedBegin = dateTimeBegin.format(DateTimeFormatter.ofPattern("MM.dd HH.mm"));
                        ZonedDateTime dateTimeEnd = Instant.ofEpochMilli(endTime)
                                .atZone(ZoneId.of("Canada/Eastern"));
                        String formattedEnd = dateTimeEnd.format(DateTimeFormatter.ofPattern("HH.mm"));
                        String[] arrOfStr = formattedBegin.split(" ");

                        // Get Day of the week
                        GregorianCalendar personalCalendar =  new GregorianCalendar();
                        Date date = new Date(beginTime);
                        personalCalendar.setTime(date);
                        personalCalendar.setTimeZone(TimeZone.getTimeZone("EST"));
                        int numb = personalCalendar.get(GregorianCalendar.DAY_OF_WEEK);
                        String str;
                        if (numb ==1) {
                            str = "Monday";
                        } else if (numb == 2) {
                            str = "Tuesday";
                        } else if (numb == 3) {
                            str = "Wednesday";
                        }else if (numb == 4) {
                            str = "Thursday";
                        }else if (numb == 5) {
                            str = "Friday";
                        }else if (numb == 6) {
                            str = "Saturday";
                        } else {
                            str = "Sunday";
                        }
                        recurring.add(this.calendarController.createRecEvent(nameEvent,
                                Float.parseFloat(arrOfStr[1]), Float.parseFloat(formattedEnd), str));
                    }
                }

            }


        }

        this.calendarController.addOneOffEvent(this.studentUsername, singles);
        this.calendarController.addRecEvent(this.studentUsername, recurring);




    }
}


