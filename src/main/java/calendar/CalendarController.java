package calendar;

import constants.VarExceptions;
import events.CalendarEvent;
import events.EventFactory;
import events.EventInterface;
import events.OneOffEvent;
import users.students.Student;
import users.students.StudentController;

import java.util.ArrayList;
import java.util.HashMap;

public class CalendarController {
    private CalendarManager calendarManager;
    private EventFactory eventCreator;
    private StudentController studentController;

    public CalendarController(StudentController studentController) {
        this.calendarManager = new CalendarManager();
        this.eventCreator = new EventFactory();
        this.studentController = studentController;
    }
    public void addRecEvent(String student, ArrayList<CalendarEvent> events) throws VarExceptions {
        for (CalendarEvent e: events) {
            checkAvailableRecurring(this.getStudent(student), e);
        }
        calendarManager.addRecurringEvents(this.getStudent(student), events);
    }

    public void addOneOffEvent(String student, ArrayList<OneOffEvent> events) throws VarExceptions {
        for (OneOffEvent e: events) {
            checkAvailableOneoff(this.getStudent(student), e);
        }
        calendarManager.addSingleEvents(this.getStudent(student), events);
    }

    public CalendarEvent createRecEvent(String name, float start, float end, String day) {
        return eventCreator.createEvent(name, start, end, day);
    }

    public OneOffEvent createOneOffEvent(String name, float start, float end, float date) {
        return eventCreator.createEvent(name, start, end, date);
    }

    public Student getStudent(String student){
        HashMap<String, Student> students = this.studentController.getAllStudents();
        return students.get(student);
    }

    public void checkAvailableRecurring(Student student, CalendarEvent recurringEvent) throws VarExceptions {
        String day = recurringEvent.getDayOrDate();
        ArrayList<CalendarEvent> events = student.getStudentSchedule().getRecurring().get(day);
        float newStartTime = recurringEvent.getStartTime();
        float newEndTime = recurringEvent.getEndTime();
        for (CalendarEvent event: events) {
            checkAvailableLoop(newStartTime, event.getStartTime(), event.getEndTime(), event);
        }
    }

    private void checkAvailableLoop(float newStartTime, float startTime, float endTime, EventInterface event)
            throws VarExceptions {
        float eStartTime = event.getStartTime();
        float eEndTime = event.getEndTime();
        if ((eStartTime < newStartTime && newStartTime < eEndTime) || (eStartTime < newStartTime &&
                newStartTime > eEndTime) || (eStartTime > newStartTime && newStartTime < eEndTime)) {
            throw new VarExceptions("Event exists at time");
        }
    }

    public void checkAvailableOneoff(Student student, OneOffEvent oneOffEvent) throws VarExceptions {
        String day = oneOffEvent.getDayOrDate();
        float date = Float.parseFloat(day);
        if (student.getStudentSchedule().getSingle().containsKey(date)) {
            ArrayList<OneOffEvent> events = student.getStudentSchedule().getSingle().get(date);
            float newStartTime = oneOffEvent.getStartTime();
            float newEndTime = oneOffEvent.getEndTime();
            for (OneOffEvent event: events) {
                checkAvailableLoop(newStartTime, event.getStartTime(), event.getEndTime(), event);
            }
        }
    }

}


}
