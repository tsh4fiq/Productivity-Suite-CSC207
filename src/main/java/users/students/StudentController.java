package users.students;

import tasks.Task;
import events.CalendarEvent;
import events.OneOffEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class StudentController {

    private StudentManager studentManager;

    public StudentController(){
        this.studentManager = new StudentManager();
    }

    public boolean addStudent(Student student) {
        // returns true if student has been added to hashmap, false if student already exists
        if (studentManager.checkValidStudent(student)) {
            return false;
        } else {
            studentManager.addStudent(student);
            return true;
        }
    }
    public void addNewStudent(String username, String password){
        this.studentManager.addNewStudent(username,password);
    }

    public HashMap<String, Student> getAllStudents(){
        return this.studentManager.getAllStudents();
    }


    public HashMap<String, ArrayList<CalendarEvent>> getCalendarRecurring(String username) {

        return studentManager.getCalendarRecurring(username);


    }

    public HashMap<Float, ArrayList<OneOffEvent>> getCalendarOneOff(String username) {

        return studentManager.getCalendarOneOff(username);


    }


    public  ArrayList<ArrayList<Object>> getTimes(String username, Float date, String day) {

        return studentManager.getTimes(username, date, day);
    }



    public ArrayList<Task> getTasks(String user){
        return this.studentManager.getTasks(user);
    }

    public void addTask(String user, String title) {
        this.studentManager.addTask(user, title);
    }

    public void removeTask(String user, Task task) {
        this.studentManager.removeTask(user, task);
    }

    public void closeTask(String user, Task task) {
        this.studentManager.closeTask(user, task);;
    }
    public boolean isClosedTask(String user, Task task){
        return this.studentManager.isClosedTask(user, task);
    }

}
