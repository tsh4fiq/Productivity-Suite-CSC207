package users;

import tasks.Task;
import tasks.TaskList;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.ArrayList;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@JsonTypeInfo(use = NAME, include = PROPERTY)
public interface Person {
    TaskList getTaskList();
    ArrayList<Task> OpenTasks();
    ArrayList<Task> ClosedTasks();
    int NumTasks();
    float totalMinutesCloseTasks();
    float avgTaskCloseTimeDays();
    float avgTaskCloseTimeHours();
}