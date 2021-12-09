import events.CalendarEvent;
import events.EventFactory;
import events.OneOffEvent;
import org.junit.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class EventFactoryTest {
    EventFactory f;

    @Before
    public void setUp() {
        f = new EventFactory();
    }

    @Test(timeout = 50)
    public void TestEvent() {
        CalendarEvent event = f.createEvent("Lecture", 3.0f, 4.0f, "Tuesday");
        assertEquals(3.0f, event.getStartTime(), 0);
    }

    @Test(timeout = 50)
    public void TestEventsCompare() {
        CalendarEvent e1 = f.createEvent("Lecture", 15.0f, 16.0f, "Tuesday");
        OneOffEvent e2 = f.createEvent("Interview", 9.0f, 10.0f, 12.09f);
        assertEquals(1, e1.compareTo(e2));
    }

    @Test(timeout = 50)
    public void TestOneOff() {
        OneOffEvent event = f.createEvent("Interview", 9.0f, 10.0f, 12.09f);
        assertEquals("Interview", event.getName());
    }

    @Test(timeout = 50)
    public void TestEventAlter() {
        CalendarEvent e1 = f.createEvent("Lecture", 15.0f, 16.0f, "Tuesday");
        assertEquals("Lecture", e1.getName());
        e1.setName("Tutorial");
        assertEquals("Tutorial", e1.getName());
        CalendarEvent e2 = f.createEvent("Lecture", 16.0f, 17.0f, "Tuesday");
        assertEquals("Lecture", e2.getName());
    }

    @Test(timeout = 50)
    public void TestOneOffAlter() {
        OneOffEvent event = f.createEvent("Hiking", 13.0f, 15.0f, 12.12f);
        event.setStartTime(12.0f);
        assertEquals(12.0f, event.getStartTime(), 0);
        assertEquals(3.0f, event.getDuration(), 0);
    }

}
