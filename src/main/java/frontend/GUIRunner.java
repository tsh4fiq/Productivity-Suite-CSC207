package frontend;

import backend.*;
import calendar.CalendarController;
import login.LogIn;
import users.groups.GroupController;
import users.Person;
import users.students.Student;
import users.students.StudentController;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GUIRunner {
    public static void main(String[] args) {

        StudentController studentController = new StudentController();
        LogIn login = new LogIn(studentController);
        GroupController groupController = new GroupController();
        CalendarController calendarController = new CalendarController(studentController);

        JsonReader jsonReader = new JsonReader();
        jsonReader.loadData(studentController, groupController);

        new StartMenu(login, groupController, calendarController, studentController);

        ImageIcon logo = new ImageIcon("Images/Variable logo.png");

    }
}
