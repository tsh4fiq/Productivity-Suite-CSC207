import calendar.CalendarManager;
import events.CalendarEvent;
import events.OneOffEvent;
import org.junit.*;
import users.students.Student;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class CalendarManagerTest {
    CalendarManager m;
    Student s;
    ArrayList<CalendarEvent> recurArr;
    ArrayList<OneOffEvent> singleArr;

    @Before
    public void setUp() {
        m = new CalendarManager();
        s = new Student("mike", "password1");

        CalendarEvent recur1 = new CalendarEvent("Lecture", 3.0f, 4.0f, "Tuesday");
        CalendarEvent recur2 = new CalendarEvent("Tutorial", 5.0f, 7.0f, "Tuesday");
        recurArr = new ArrayList<CalendarEvent>();
        recurArr.add(recur1);
        recurArr.add(recur2);

        OneOffEvent single = new OneOffEvent("Interview", 9.0f, 10.0f, 12.09f);
        singleArr = new ArrayList<OneOffEvent>();
        singleArr.add(single);
    }

    @Test(timeout = 50)
    public void TestRecurring() {
        m.addRecurringEvents(s, recurArr);
        assertEquals(2, s.getStudentSchedule().getRecurring().get("Tuesday").size());

        m.removeRecurringEvents(s, recurArr);
        assertEquals(0, s.getStudentSchedule().getRecurring().get("Tuesday").size());

    }

    @Test(timeout = 50)
    public void TestSingle() {
        m.addSingleEvents(s, singleArr);
        assertEquals(1, s.getStudentSchedule().getSingle().get(12.09f).size());

        m.removeSingleEvents(s, singleArr);
        assertEquals(0, s.getStudentSchedule().getSingle().get(12.09f).size());

    }

}
