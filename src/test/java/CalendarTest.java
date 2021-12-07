import calendar.Calendar;
import events.CalendarEvent;
import events.OneOffEvent;
import org.junit.*;

import static org.junit.Assert.*;

public class CalendarTest {
    Calendar c;

    @Before
    public void setUp() {
        c = new Calendar();
        CalendarEvent recur = new CalendarEvent("Lecture", 3.0f, 4.0f, "Tuesday");
        OneOffEvent single = new OneOffEvent("Interview", 9.0f, 10.0f, 12.09f);
        c.addRecurEvent("Tuesday", recur);
        c.addSingleEvent(12.09f, single);
    }

    @Test(timeout = 50)
    public void TestRecurring() {
        assertEquals(0, c.getRecurring().get("Wednesday").size());
        assertEquals(1, c.getRecurring().get("Tuesday").size());

        CalendarEvent newRecur = new CalendarEvent("Lunch", 12.0f, 13.0f, "Tuesday");
        c.addRecurEvent("Tuesday", newRecur);
        assertEquals(2, c.getRecurring().get("Tuesday").size());

        c.removeRecurEvent("Tuesday", newRecur);
        assertEquals(1, c.getRecurring().get("Tuesday").size());
    }

    @Test(timeout = 50)
    public void TestSingle() {
        assertEquals(1, c.getSingle().size());

        OneOffEvent newSingle = new OneOffEvent("Birthday Party", 12.0f, 13.0f, 6.21f);
        c.addSingleEvent(6.21f, newSingle);
        assertEquals(1, c.getSingle().get(6.21f).size());
        assertEquals(2, c.getSingle().size());

        c.removeSingleEvent(6.21f, newSingle);
        assertEquals(0, c.getSingle().get(6.21f).size());
    }

}
