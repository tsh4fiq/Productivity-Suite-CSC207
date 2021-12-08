package tasks;

import java.time.LocalDateTime;

public interface TaskInterface extends Comparable<TaskInterface>{
    public String toString();

    public String getTitle();


    public LocalDateTime getStartDT();

    public LocalDateTime getEndDT();

    public void editStartDT(LocalDateTime newStartDT);

    public void editEndDT(LocalDateTime newEndDT);

    public void editTitle(String newTitle);



    public boolean isClosed();
}