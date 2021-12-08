package users;

import calendar.Calendar;

public interface StudentBuilderInterface {

    public void setUsername(String username);

    public void setPassword(String password);

    public void setEmptySchedule();

    public void setPopulatedSchedule(Calendar calendar);

    public void setTaskList();

}
