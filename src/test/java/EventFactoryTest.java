import events.CalendarEvent;
import events.EventFactory;
import events.OneOffEvent;
import org.junit.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class EventFactoryTest {
    EventFactory e;

    @Before
    public void setUp() {
        e = new EventFactory();
    }

    @Test(timeout = 50)
    public void TestEvent() {
        CalendarEvent event = e.createEvent("Lecture", 3.0f, 4.0f, "Tuesday");
        assertEquals(3.0f, event.getStartTime(), 0);
    }


    @Test(timeout = 50)
    public void TestOneOff() {
        OneOffEvent event = e.createEvent("Interview", 9.0f, 10.0f, 12.09f);
        assertEquals("Interview", event.getName());
    }

}
