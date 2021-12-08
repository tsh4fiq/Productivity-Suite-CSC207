package tasks;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Task implements TaskInterface, Serializable {
    private String title;
    private LocalDateTime startDT;
    private LocalDateTime endDT;
    private boolean finished;

    @JsonCreator
    public Task(@JsonProperty("title") String title) {
        this.title = title;
        this.startDT = LocalDateTime.now();
        this.endDT = null;
        this.finished = false;
    }

    @Override
    public String toString() {
        return this.title;
    }

    public String getTitle() {
        return this.title;
    }

    public LocalDateTime getStartDT() {
        return this.startDT;
    }

    public LocalDateTime getEndDT() {
        return this.endDT;
    }

    public void editStartDT(LocalDateTime newStartDT) {
        this.startDT = newStartDT;
    }

    public void editEndDT(LocalDateTime newEndDT) {
        this.endDT = newEndDT;
        this.finished = true;
    }

    public void editTitle(String newTitle) {
        this.title = newTitle;
    }

    public boolean isClosed() {
        return this.finished;
    }

    @Override
    public int compareTo(TaskInterface other){
        /* Compares 2 tasks based on their start times
        Returns -1 if current task starts earlier than other task
                1 if current task starts later than other task
                0 if 2 tasks have the same start time */
        if (this.getStartDT().isBefore(other.getStartDT())){
            return -1;
        }
        else if (this.getStartDT().isAfter(other.getStartDT())){
            return 1;
        }
        else{
            return 0;
        }
    }

}