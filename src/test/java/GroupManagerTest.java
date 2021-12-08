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
        g = m.CreateGroup(students, "G1");
    }

    @Test(timeout = 50)
    public void TestGetGroup() {
        assertEquals("0", m.getID(g));
    }

    @Test(timeout = 50)
    public void TestAddStudent() {
        Student s = new Student("mike", "password1");
        m.addToGroup(s, "0");
        assertEquals("mike", m.getStudentUsername("0").get(0));
    }

}
