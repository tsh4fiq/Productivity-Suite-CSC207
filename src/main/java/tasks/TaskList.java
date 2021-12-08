package tasks;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

public class TaskList implements Serializable {
    private ArrayList<Task> tasks;

    @JsonCreator
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public void createTask(String title) {
        Task task = new Task(title);
        this.tasks.add(task);
    };

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public void removeTask(Task task) {
        this.tasks.remove(task);
    }

    public void closeTask(Task task) {
        task.editEndDT(LocalDateTime.now());
    }

    public boolean isClosedTask(Task task){
        if(this.tasks.contains(task)){
            return task.isClosed();
        }
        return false;
    }

    public int getNumTasks() {
        return this.tasks.size();
    }

    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    public ArrayList<Task> getTaskList() {
        return this.tasks;
    }

    public void sortList() {
        Collections.sort(tasks);
    }

    public ArrayList<Task> filterByTime(LocalDateTime t1, LocalDateTime t2){
        ArrayList<Task> tasksInRange = new ArrayList<>();

        for (Task task : tasks) {
            if (task.getStartDT().isAfter(t1) && task.getStartDT().isBefore(t2)){
                tasksInRange.add(task);
            }
        }

        return tasksInRange;

    }

}
