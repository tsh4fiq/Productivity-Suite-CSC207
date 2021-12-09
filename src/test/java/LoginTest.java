import java.util.ArrayList;

import login.LogIn;
import org.junit.*;
import users.Person;
import users.students.Student;
import users.students.StudentController;

import static org.junit.Assert.*;

public class LoginTest {
    LogIn l;
    StudentController sc;

    @Before
    public void setUp() {
        sc = new StudentController();
        l = new LogIn(sc);
    }

    @Test
    public void TestCheckUsername() {
        sc.addNewStudent("stu1", "pass");
        sc.addNewStudent("stu2", "pass");
        sc.addNewStudent("stu3", "pass");

        assertTrue(l.checkUsername("stu1"));
        assertTrue(l.checkUsername("stu2"));
        assertTrue(l.checkUsername("stu3"));

        assertFalse(l.checkUsername("stu4"));
    }

    @Test
    public void TestValidateLogIn() {
        sc.addNewStudent("stu1", "pass1");
        sc.addNewStudent("stu2", "pass2");
        sc.addNewStudent("stu3", "pass3");

        assertTrue(l.validateLogIn("stu1", "pass1"));
        assertTrue(l.validateLogIn("stu2", "pass2"));
        assertTrue(l.validateLogIn("stu3", "pass3"));

        assertFalse(l.validateLogIn("stu1", "pass3"));
        assertFalse(l.validateLogIn("stu2", "pass4"));
        assertFalse(l.validateLogIn("stu3", "pass1"));
    }

}
