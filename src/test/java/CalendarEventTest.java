import events.CalendarEvent;
import org.junit.*;

import static org.junit.Assert.*;

public class CalendarEventTest {
    CalendarEvent event;

    @Before
    public void setUp() {
        event = new CalendarEvent("Lecture", 3.0f, 4.0f, "Tuesday");
    }

    @Test(timeout = 50)
    public void TestGetDay() {
        assertEquals("Tuesday", event.getDayOrDate());
    }

    @Test(timeout = 50)
    public void TestCompareTo() {
        CalendarEvent tut = new CalendarEvent("Tutorial", 2.0f, 5.0f, "Wednesday");
        assertEquals(1, event.compareTo(tut));
    }

    @Test(timeout = 50)
    public void TestStartTime() {
        event.setStartTime(1.0f);
        assertEquals(1.0f, event.getStartTime(), 0);
        assertEquals(3.0f, event.getDuration(), 0);
    }

    @Test(timeout = 50)
    public void TestEndTime() {
        event.setEndTime(6.0f);
        assertEquals(6.0f, event.getEndTime(), 0);
        assertEquals(3.0f, event.getDuration(), 0);
    }

    @Test(timeout = 50)
    public void TestName() {
        event.setName("Sleep");
        assertEquals("Sleep", event.getName());
    }

}
