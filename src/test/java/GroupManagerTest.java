import events.CalendarEvent;
import events.EventFactory;
import org.junit.*;
import users.Person;
import users.groups.Group;
import users.groups.GroupManager;
import users.students.Student;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GroupManagerTest {
    GroupManager m;
    Group g;

    @Before
    public void setUp() {
        m = new GroupManager();
        ArrayList<Person> students = new ArrayList<Person>();
        g = m.createGroup(students, "G1");
    }

    @Test(timeout = 50)
    public void TestGetGroup() {
        assertEquals("0", m.getID(g));
    }

    @Test(timeout = 50)
    public void TestAddStudent() {
        Student s = new Student("mike", "password1");
        m.addToGroup(s, "0");
        assertEquals(true, m.getGroup("0").checkStudent(s));
    }

    @Test(timeout = 50)
    public void TestStudentUsernames() {
        Student s = new Student("mike", "password1");
        m.addToGroup(s, "0");
        assertEquals("mike", m.getStudentUsername("0").get(0));
    }

    @Test
    public void TestRemoveStudent() {
        Student s = new Student("stu1", "pass");
        m.addToGroup(s, "0");
        assertEquals("stu1", m.getStudentUsername("0").get(0));
        m.removeGroupMember(s, g.getgID());
        assertEquals(false, m.getGroup(g.getgID()).checkStudent(s));
    }

    @Test
    public void TestCreateGroup() {
        Person s1 = new Student("stu1", "pass");
        Person s2 = new Student("stu2", "pass");
        Person s3 = new Student("stu3", "pass");
        ArrayList<Person> students = new ArrayList<Person>();
        students.add(s1);
        students.add(s2);
        students.add(s3);
        assertEquals(students, m.createGroup(students, "variable").getMembers());
    }
}
