import org.junit.Before;
import org.junit.Test;
import tasks.Task;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class TaskTest {
    Task task1;
    LocalDateTime currDateTime;

    @Before
    public void setup() {
        this.task1 = new Task("Write notes");
        this.currDateTime = LocalDateTime.now();
    }

    @Test
    public void TestToString() {
        assertEquals("Write notes", this.task1.toString());
    }

    @Test
    public void TestGetTitle() {
        assertEquals("Write notes", this.task1.getTitle());
    }

    @Test
    public void TestGetStartDT() {
        assertEquals(currDateTime.getYear(), task1.getStartDT().getYear());
        assertEquals(currDateTime.getMonthValue(), task1.getStartDT().getMonthValue());
        assertEquals(currDateTime.getDayOfMonth(), task1.getStartDT().getDayOfMonth());
        assertEquals(currDateTime.getHour(), task1.getStartDT().getHour());
    }

    @Test
    public void TestGetEndDT() {
        assertNull(this.task1.getEndDT());
    }

    @Test
    public void TestEditStartDT() {
        LocalDateTime newDateTime = this.currDateTime.plusMonths(1);
        this.task1.editStartDT(newDateTime);

        assertEquals(newDateTime.getYear(), task1.getStartDT().getYear());
        assertEquals(newDateTime.getMonthValue(), task1.getStartDT().getMonthValue());
        assertEquals(newDateTime.getDayOfMonth(), task1.getStartDT().getDayOfMonth());
        assertEquals(newDateTime.getHour(), task1.getStartDT().getHour());
    }

    @Test
    public void TestEditEndDT() {
        LocalDateTime newDateTime = this.currDateTime.plusHours(2);
        this.task1.editEndDT(newDateTime);

        assertEquals(newDateTime.getYear(), task1.getEndDT().getYear());
        assertEquals(newDateTime.getMonthValue(), task1.getEndDT().getMonthValue());
        assertEquals(newDateTime.getDayOfMonth(), task1.getEndDT().getDayOfMonth());
        assertEquals(newDateTime.getHour(), task1.getEndDT().getHour());

    }

    @Test
    public void TestEditTitle() {
        this.task1.editTitle("Write notes for math");
        assertEquals("Write notes for math", this.task1.getTitle());
    }

    @Test
    public void TestIsClosed() {
        assertFalse(this.task1.isClosed());

        this.task1.editEndDT(this.currDateTime.plusHours(2));

        assertTrue(this.task1.isClosed());
    }

    @Test
    public void TestCompareTo() {
        Task task2 = new Task("Do Quiz");

        assertEquals(-1, this.task1.compareTo(task2));
        assertEquals(1, task2.compareTo(this.task1));

        LocalDateTime currDateTime = LocalDateTime.now();
        Task task3 = new Task("test1");
        Task task4 = new Task("test2");
        task3.editStartDT(currDateTime);
        task4.editStartDT(currDateTime);

        assertEquals(0, task3.compareTo(task4));
    }



}
