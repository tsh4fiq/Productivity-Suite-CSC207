import events.OneOffEvent;
import org.junit.*;

import static org.junit.Assert.*;

public class OneOffEventTest {
    OneOffEvent event;

    @Before
    public void setUp() {
        event = new OneOffEvent("Lecture", 3.0f, 4.0f, 12.09f);
    }

    @Test(timeout = 50)
    public void TestGetDate() {
        assertEquals("12.09", event.getDayOrDate());
    }

    @Test(timeout = 50)
    public void TestEarlier() {
        OneOffEvent tut = new OneOffEvent("Tutorial", 2.0f, 5.0f, 12.12f);
        assertEquals(-1, event.compareTo(tut));
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

    @Test(timeout = 50)
    public void TestLater() {
        OneOffEvent e2 = new OneOffEvent("Competition", 10.0f, 13.0f, 12.03f);
        assertEquals(1, event.compareTo(e2));
    }

    @Test(timeout = 50)
    public void TestSetDate() {
        event.setDayOrDate("6.21");
        assertEquals("6.21", event.getDayOrDate());
    }

    @Test(timeout = 50)
    public void TestDuration() {
        assertEquals(1.0f, event.getDuration(), 0);
        event.setStartTime(10.0f);
        event.setEndTime(12.0f);
        assertEquals(2,0f, event.getDuration());
    }

}
