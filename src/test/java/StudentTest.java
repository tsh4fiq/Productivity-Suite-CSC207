import calendar.Calendar;
import events.CalendarEvent;
import events.OneOffEvent;
import org.junit.*;
import tasks.Task;
import tasks.TaskList;
import users.students.Student;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class StudentTest {
    Student s;
    Calendar c;

    @Before
    public void setUp() {
        s = new Student();
        s.setUsername("stu1");
        s.setPassword("pass1");
        c = new Calendar();
        CalendarEvent recur = new CalendarEvent("Lecture", 3.0f, 4.0f, "Tuesday");
        OneOffEvent single = new OneOffEvent("Interview", 9.0f, 10.0f, 12.09f);
        c.addRecurEvent("Tuesday", recur);
        c.addSingleEvent(12.09f, single);
    }

    @Test
    public void TestUserAndPass() {
        assertEquals("stu1", s.getUsername());
        assertEquals("pass1", s.getPassword());

        s.setUsername("stu2");
        s.setPassword("pass2");
        assertEquals("stu2", s.getUsername());
        assertEquals("pass2", s.getPassword());
    }

    @Test
    public void TestRecurringEvents() {
        assertEquals(0, c.getRecurring().get("Wednesday").size());
        assertEquals(1, c.getRecurring().get("Tuesday").size());

        CalendarEvent newRecur = new CalendarEvent("Lunch", 12.0f, 13.0f, "Tuesday");
        c.addRecurEvent("Tuesday", newRecur);
        assertEquals(2, c.getRecurring().get("Tuesday").size());

        c.removeRecurEvent("Tuesday", newRecur);
        assertEquals(1, c.getRecurring().get("Tuesday").size());
    }

    @Test
    public void TestOneOffEvents() {
        assertEquals(1, c.getSingle().size());

        OneOffEvent newSingle = new OneOffEvent("Birthday Party", 12.0f, 13.0f, 6.21f);
        c.addSingleEvent(6.21f, newSingle);
        assertEquals(1, c.getSingle().get(6.21f).size());
        assertEquals(2, c.getSingle().size());

        c.removeSingleEvent(6.21f, newSingle);
        assertEquals(0, c.getSingle().get(6.21f).size());
    }

    @Test
    public void TestCreateTask() {
        s.getTaskList().createTask("Read chapter 1");

        assertEquals(1, s.getTaskList().getTasks().size());
        assertEquals("Read chapter 1", s.getTaskList().getTasks().get(0).getTitle());
    }

    @Test
    public void TestAddTask() {
        Task task1 = new Task("Type notes");
        int taskListSize = s.getTaskList().getTasks().size();
        s.getTaskList().addTask(task1);
        assertEquals(taskListSize+1, s.getTaskList().getTasks().size());
        assertEquals("Type notes",
                s.getTaskList().getTasks().get(s.getTaskList().getTasks().size()-1).getTitle());
    }

    @Test
    public void TestRemoveTask() {
        Task task1 = new Task("Type notes");
        s.getTaskList().addTask(task1);
        int size = s.getTaskList().getTasks().size();
        s.getTaskList().removeTask(task1);
        assertEquals(size-1, s.getTaskList().getTasks().size());
    }

    @Test
    public void TestCloseTask() {
        Task task1 = new Task("Type notes");
        s.getTaskList().addTask(task1);
        assertFalse(s.getTaskList().getTasks().get(0).isClosed());
        s.getTaskList().getTasks().get(0).editEndDT(LocalDateTime.now());
        assertTrue(s.getTaskList().getTasks().get(0).isClosed());
    }

    @Test
    public void TestGetNumTasks() {
        s.setTaskList();
        assertEquals(0, s.getTaskList().getNumTasks());

        Task task1 = new Task("Type notes");
        s.getTaskList().addTask(task1);

        assertEquals(1, s.getTaskList().getNumTasks());
    }

}
