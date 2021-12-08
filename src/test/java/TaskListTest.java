import org.junit.Before;
import org.junit.Test;
import tasks.Task;
import tasks.TaskList;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class TaskListTest {
    TaskList tasklist;

    @Before
    public void setup() {
        this.tasklist = new TaskList();
    }

    @Test
    public void TestCreateTask() {
        this.tasklist.createTask("Read chapter 1");

        assertEquals(1, this.tasklist.getTasks().size());
        assertEquals("Read chapter 1", this.tasklist.getTasks().get(0).getTitle());
    }

    @Test
    public void TestAddTask() {
        Task task1 = new Task("Type notes");
        int taskListSize = this.tasklist.getTasks().size();
        this.tasklist.addTask(task1);
        assertEquals(taskListSize+1, this.tasklist.getTasks().size());
        assertEquals("Type notes",
                this.tasklist.getTasks().get(this.tasklist.getTasks().size()-1).getTitle());
    }

    @Test
    public void TestRemoveTask() {
        Task task1 = new Task("Type notes");
        this.tasklist.addTask(task1);
        int size = this.tasklist.getTasks().size();
        this.tasklist.removeTask(task1);
        assertEquals(size-1, this.tasklist.getTasks().size());
    }

    @Test
    public void TestCloseTask() {
        Task task1 = new Task("Type notes");
        this.tasklist.addTask(task1);
        assertFalse(this.tasklist.getTasks().get(0).isClosed());
        this.tasklist.getTasks().get(0).editEndDT(LocalDateTime.now());
        assertTrue(this.tasklist.getTasks().get(0).isClosed());
    }

    @Test
    public void TestgetNumTasks() {
        TaskList tl = new TaskList();
        assertEquals(0, tl.getNumTasks());

        Task task1 = new Task("Type notes");
        tl.addTask(task1);

        assertEquals(1, tl.getNumTasks());
    }

    @Test
    public void TestGetTasks() {
        TaskList tl = new TaskList();
        ArrayList<Task> tlTest = new ArrayList<>();

        assertEquals(tl.getTasks(), tlTest);

        Task task1 = new Task("Type notes");
        tl.addTask(task1);

        tlTest.add(task1);
        assertEquals(tl.getTasks(), tlTest);
    }

    @Test
    public void TestSortList() {
        TaskList tl = new TaskList();
        Task task1 = new Task("Type notes");
        Task task2 = new Task("Read chapter 1");
        Task task3 = new Task("Memorize formulas");

        LocalDateTime currDateTime = LocalDateTime.now();
        task1.editStartDT(currDateTime);
        task2.editStartDT(currDateTime.minusDays(1));
        task3.editStartDT(currDateTime.plusDays(2));

        tl.addTask(task1);
        tl.addTask(task2);
        tl.addTask(task3);

        ArrayList<Task> tlTestStart = new ArrayList<>();
        tlTestStart.add(task1);
        tlTestStart.add(task2);
        tlTestStart.add(task3);

        ArrayList<Task> tlTest = new ArrayList<>();
        tlTest.add(task2);
        tlTest.add(task1);
        tlTest.add(task3);

        assertEquals(tlTestStart, tl.getTasks());

        tl.sortList();

        assertEquals(tlTest, tl.getTasks());
    }

    @Test
    public void TestFilterByTime() {
        TaskList tl = new TaskList();
        Task task1 = new Task("Type notes");
        Task task2 = new Task("Read chapter 1");
        Task task3 = new Task("Memorize formulas");

        LocalDateTime currDateTime = LocalDateTime.now();
        task1.editStartDT(currDateTime);
        task2.editStartDT(currDateTime.minusYears(1));
        task3.editStartDT(currDateTime.plusYears(2));

        tl.addTask(task1);
        tl.addTask(task2);
        tl.addTask(task3);

        ArrayList<Task> tlTest = new ArrayList<>();
        tlTest.add(task1);

        assertEquals(tl.filterByTime(currDateTime.minusDays(1), currDateTime.plusYears(1)),
                tlTest);

    }


}
