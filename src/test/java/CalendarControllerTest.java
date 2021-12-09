import calendar.CalendarController;
import constants.VarExceptions;
import events.CalendarEvent;
import events.OneOffEvent;
import org.junit.*;
import users.students.StudentController;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class CalendarControllerTest {
    CalendarController c;
    StudentController s;

    ArrayList<CalendarEvent> recurArr;
    ArrayList<OneOffEvent> singleArr;

    @Before
    public void setUp() {
        s = new StudentController();
        s.addNewStudent("mike", "password1");
        c = new CalendarController(s);

        recurArr = new ArrayList<CalendarEvent>();
        singleArr = new ArrayList<OneOffEvent>();
    }

    @Test(timeout = 50)
    public void TestRecurring() throws VarExceptions {
        CalendarEvent recur1 = c.createRecEvent("Lecture", 3.0f, 4.0f, "Tuesday");
        CalendarEvent recur2 = c.createRecEvent("Tutorial", 5.0f, 7.0f, "Tuesday");
        recurArr.add(recur1);
        recurArr.add(recur2);

        c.addRecEvent("mike", recurArr);
        assertEquals(2, c.getStudent("mike").getStudentSchedule().getRecurring().get("Tuesday").size());
    }


    @Test(timeout = 50)
    public void TestSingle() throws VarExceptions {
        OneOffEvent single = c.createOneOffEvent("Interview", 9.0f, 10.0f, 12.09f);
        singleArr.add(single);
        c.addOneOffEvent("mike", singleArr);
        assertEquals(1, c.getStudent("mike").getStudentSchedule().getSingle().get(12.09f).size());


    }

}
