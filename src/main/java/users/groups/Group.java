package users.groups;

import calendar.Calendar;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import tasks.Task;
import tasks.TaskList;
import users.Person;
import users.students.Student;

import java.io.Serializable;
import java.util.ArrayList;

public class Group implements Person, Serializable {

    private ArrayList<Person> members;
    private String name;
    private String gID;

    public Group() {
        this.members = new ArrayList<Person>();
        this.name = null;
        this.gID = null;
    }

    @JsonCreator
    public Group(@JsonProperty("name") String name) {
        this.members = new ArrayList<Person>();
        this.name = name;
        this.gID = null;
    }

    public ArrayList<Person> getMembers() {
        return this.members;
    }

    public ArrayList<Person> getStudentsInGroup() {
        ArrayList<Person> students = new ArrayList<>();
        for (Person member: this.members) {
            if (member instanceof Student) {
                students.add(member);
            } else {
                students.addAll(((Group) member).getStudentsInGroup());
            }
        }

        return students;
    }

    public ArrayList<Student> getDirectStudentsInGroup() {
        // By direct students we mean students that are not a part of any subgroups
        ArrayList<Student> directStudents = new ArrayList<>();
        for (Person member: this.members) {
            if (member instanceof Student) {
                directStudents.add((Student) member);
            }
        }
        return directStudents;
    }

    public ArrayList<String> getDirectStudentsString() {
        ArrayList<Student> directStudents = getDirectStudentsInGroup();
        ArrayList<String> directStudentsString = new ArrayList<>();

        for (Student student: directStudents) {
            directStudentsString.add(student.getUsername());
        }
        return directStudentsString;
    }

    public ArrayList<Group> getDirectSubGroupsInGroup() {
        // By direct subgroups we mean subgroups that are directly under the group, so further nested subgroups do not
        // count
        ArrayList<Group> directSubGroups = new ArrayList<>();
        for (Person member: this.members) {
            if (member instanceof Group) {
                directSubGroups.add((Group) member);
            }
        }
        return directSubGroups;
    }

    public ArrayList<String> getDirectSubGroupsString() {
        ArrayList<Group> directGroups = getDirectSubGroupsInGroup();
        ArrayList<String> directGroupsString = new ArrayList<>();

        for (Group group: directGroups) {
            directGroupsString.add(group.getgID());
        }
        return directGroupsString;

    }

    public ArrayList<String> getStudentsUsername() {

        ArrayList<String> list = new ArrayList<>();

        ArrayList<Person> students = this.getStudentsInGroup();

        for (Person student: students) {
            list.add(((Student) student).getUsername());

        }

        return list;

    }

    public void setStudentsInGroup(ArrayList<Person> students) {
        this.members = students;
    }

    public String getGroupName() {
        return this.name;
    }

    public void setGroupName(String name) {
        this.name = name;
    }

    public String getgID() {
        return gID;
    }

    public void setgID(String gID) {
        this.gID = gID;
    }

    @JsonIgnore
    public ArrayList<Calendar> getCalendars() {

        ArrayList<Calendar> calendars = new ArrayList<Calendar>();
        ArrayList<Person> students = this.getStudentsInGroup();

        for (Person student : students) {
            Calendar stuCal = ((Student) student).getStudentSchedule();
            calendars.add(stuCal);
        }
        return calendars;
    }

    public Boolean checkStudent(Person student) {

        if (this.members.contains(student)) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public TaskList getTaskList() {
        TaskList studentTasks = new TaskList();
        ArrayList<Task> listOfTasks = new ArrayList<>();
        for (Person member: this.members) {
            listOfTasks.addAll(member.getTaskList().getTasks());

        }
        for (Task task: listOfTasks) {
            studentTasks.addTask(task);
        }
        return studentTasks;
    }

    @Override
    public ArrayList<Task> OpenTasks() {
        ArrayList<Task> openTasks = new ArrayList<>();
        for (Person member: this.members) {
            openTasks.addAll(member.OpenTasks());

        }
        return openTasks;

    }

    @JsonIgnore
    @Override
    public ArrayList<Task> ClosedTasks() {
        ArrayList<Task> closedTasks = new ArrayList<>();
        for (Person member: this.members) {
            closedTasks.addAll(member.ClosedTasks());
        }
        return closedTasks;
    }

    @Override
    public int NumTasks() {
        int totalTasks = 0;
        for (Person member: this.members) {
            totalTasks += member.NumTasks();
        }
        return totalTasks;
    }

    @Override
    public float totalMinutesCloseTasks() {
        float totalMinutes = (float) 0;
        for (Person member: this.members) {
            totalMinutes += member.totalMinutesCloseTasks();
        }
        return totalMinutes;
    }

    @Override
    public float avgTaskCloseTimeDays() {
        float totalMinutes = this.totalMinutesCloseTasks();
        int totalTasks = this.NumTasks();
        float avgMinutes = totalMinutes / totalTasks;
        return avgMinutes / 1440;

    }

    @Override
    public float avgTaskCloseTimeHours() {
        float totalMinutes = this.totalMinutesCloseTasks();
        int totalTasks = this.NumTasks();
        float avgMinutes = totalMinutes / totalTasks;
        return avgMinutes / 60;

    }
}