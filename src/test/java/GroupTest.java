import org.junit.*;
import users.groups.Group;
import users.students.Student;

import java.util.ArrayList;
import java.util.Calendar;

import static org.junit.Assert.*;

public class GroupTest {
    Group g1;
    Group g2;
    Group g3;
    Student s1;
    Student s2;
    Student s3;

    @Before
    public void setUp() {
        g1 = new Group("G1");
        g2 = new Group("G2");
        g3 = new Group("G3");
        g1.setgID("1");
        g2.setgID("2");
        g3.setgID("3");

        s1 = new Student("stu1", "pass1");
        s2 = new Student("stu2", "pass2");
        s3 = new Student("stu3", "pass3");
    }

    @Test(timeout = 50)
    public void TestGetGroupName() {
        assertEquals("G1", g1.getGroupName());
        assertEquals("G2", g2.getGroupName());
        assertEquals("G3", g3.getGroupName());
        g1.setGroupName("G4");
        assertEquals("G4", g1.getGroupName());
        g1.setGroupName("G1");
    }

    @Test
    public void TestGetMembers() {
        ArrayList<Student> students = new ArrayList<Student>();
        students.add(s1);
        students.add(s2);
        students.add(s3);

        g1.getMembers().add(s1);
        g1.getMembers().add(s2);
        g1.getMembers().add(s3);

        assertEquals(students, g1.getMembers());
    }

    @Test
    public void TestGetStudentsUsername() {
        ArrayList<String> users = new ArrayList<String>();
        users.add(s1.getUsername());
        users.add(s2.getUsername());
        users.add(s3.getUsername());

        g1.getMembers().add(s1);
        g1.getMembers().add(s2);
        g1.getMembers().add(s3);

        assertEquals(users, g1.getStudentsUsername());
    }

    @Test
    public void TestCheckStudent() {
        g1.getMembers().add(s1);

        assertEquals(true, g1.checkStudent(s1));
    }

    @Test
    public void TestGetCalendars() {
        calendar.Calendar c1 = new calendar.Calendar();
        calendar.Calendar c2 = new calendar.Calendar();
        calendar.Calendar c3 = new calendar.Calendar();
        ArrayList<calendar.Calendar> calendars = new ArrayList<calendar.Calendar>();
        calendars.add(c1);
        calendars.add(c2);
        calendars.add(c3);

        s1.setPopulatedSchedule(c1);
        s2.setPopulatedSchedule(c2);
        s3.setPopulatedSchedule(c3);

        g1.getMembers().add(s1);
        g1.getMembers().add(s2);
        g1.getMembers().add(s3);

        assertEquals(calendars, g1.getCalendars());
    }

}
