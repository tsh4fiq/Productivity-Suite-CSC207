package users.students;

import calendar.Calendar;
import events.CalendarEvent;
import events.OneOffEvent;
import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class StudentManager {

    private HashMap<String, Student> students;

    public StudentManager() {
        this.students = new HashMap<String, Student>();
    }

    public StudentManager(HashMap<String, Student> students) {
        this.students = students;
    }

    public void addStudent(Student student) {
        this.students.put(student.getUsername(), student);
    }

    public HashMap<String, Student> getAllStudents() {
        return students;
    }

    public void setAllStudents(HashMap<String, Student> students) {
        this.students = students;
    }

    public Set<String> getAllStudentUsers() { return this.students.keySet(); }

    public void addNewStudent(String username, String password){
        students.put(username, new Student(username, password));
    }


    public ArrayList<Task> getTasks(String user){
        return  this.students.get(user).getTaskList().getTasks();
    }

    public void addTask(String user, String title) {
        this.students.get(user).getTaskList().createTask(title);
    }

    public void removeTask(String user, Task task) {
        this.students.get(user).getTaskList().removeTask(task);
    }

    public void closeTask(String user, Task task) {
        this.students.get(user).getTaskList().closeTask(task);
    }

    public boolean isClosedTask(String user, Task task){return this.students.get(user).getTaskList().isClosedTask(task);}




    public boolean checkValidStudent(Student student) {
        // returns true if student is already within hashmap, false otherwise
        String user = student.getUsername();
        return this.students.containsKey(user);
    }

    public HashMap<String, ArrayList<CalendarEvent>> getCalendarRecurring(String username) {

        if (students.containsKey(username)) {

            Student curr  = students.get(username);
            Calendar studentScehdule = curr.getStudentSchedule();
            return  studentScehdule.getRecurring();

        } else {
            return new HashMap<String, ArrayList<CalendarEvent>>();
        }
    }

    public HashMap<Float, ArrayList<OneOffEvent>> getCalendarOneOff(String username) {

        if (students.containsKey(username)) {

            Student curr = students.get(username);
            Calendar studentScehdule = curr.getStudentSchedule();
            return studentScehdule.getSingle();
        } else {
            return new HashMap<Float, ArrayList<OneOffEvent>>();
        }
    }


    public ArrayList<ArrayList<Object>> getTimes(String username, Float date, String day) {

        if (students.containsKey(username)) {

            Student curr = students.get(username);
            Calendar studentScehdule = curr.getStudentSchedule();

            ArrayList<ArrayList<Object>> eventTimes = new ArrayList<>();
            ArrayList<CalendarEvent> calendarEvents = studentScehdule.getRecurring().get(day);

            if (studentScehdule.getSingle().containsKey(date)) {
                ArrayList<OneOffEvent> oneOffs = studentScehdule.getSingle().get(date);
                int length1 = oneOffs.size();

                for (int i = 0; i < length1; i++) {
                    ArrayList<Object> temp = new ArrayList<>();
                    temp.add(oneOffs.get(i).getStartTime());
                    temp.add(oneOffs.get(i).getEndTime());
                    temp.add(oneOffs.get(i).getName());
                    eventTimes.add(temp);
                }
            }

            int length2 = calendarEvents.size();

            for (int i = 0; i < length2; i++) {
                ArrayList<Object> temp = new ArrayList<>();
                temp.add(calendarEvents.get(i).getStartTime());
                temp.add(calendarEvents.get(i).getEndTime());
                temp.add(calendarEvents.get(i).getName());
                eventTimes.add(temp);
            }
            return eventTimes;

        } else {

            return new ArrayList<ArrayList<Object>>();
        }


    }

}
