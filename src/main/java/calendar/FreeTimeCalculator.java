package calendar;

import events.CalendarEvent;
import events.EventFactory;
import events.OneOffEvent;
import users.groups.Group;
import users.groups.GroupManager;

import java.util.*;

/**
 * Calculates the free-times for a group of students
 */
public class FreeTimeCalculator {
    /**
     * Calculates the free-times of a group of students
     * @param groupManager The GroupManager containing all created Students
     * @return the Calendar containing all free-times of a week
     */
    public calendar.Calendar getFreeCalendar(GroupManager groupManager, String groupID){
        Group group = groupManager.getGroup(groupID);
        Map<String, List<CalendarEvent>> aggregateRoutine = getAggregateRoutine(group);
        Map<Float, List<OneOffEvent>> aggregateOneOff = getAggregateOneOff(group);
        return calculateFreeTime(aggregateRoutine, aggregateOneOff);
    }

    /**
     * Calculates the free-times of a given aggregateRoutine and returns the Calendar for the free-times
     * @param aggregateRoutine
     * @param aggregateOneOff
     * @return the free-time Calendar
     */
    private calendar.Calendar calculateFreeTime(Map<String, List<CalendarEvent>> aggregateRoutine,
                                                Map<Float, List<OneOffEvent>> aggregateOneOff){
        calendar.Calendar freeCalendar = new calendar.Calendar();
        addFreeRecurringEvents(aggregateRoutine, freeCalendar);
        addFreeOneOffEvents(aggregateOneOff, freeCalendar);
        return freeCalendar;
    }

    private void addFreeOneOffEvents(Map<Float, List<OneOffEvent>> aggregateOneOff, calendar.Calendar freeCalendar) {
        for(Map.Entry<Float, List<OneOffEvent>> entry : aggregateOneOff.entrySet()) {
            Float date = entry.getKey();
            List<OneOffEvent> dateSchedule = entry.getValue();

            //calculate every day's free time as an ArrayList
            Collections.sort(dateSchedule); //need compareTo method from Comparable Interface
            ArrayList<OneOffEvent> dateFreeTime = getDateFreeTime(date, dateSchedule);

            //Convert ArrayList to a value in the Calendar
            for (OneOffEvent event : dateFreeTime){
                freeCalendar.addSingleEvent(date, event);
            }
        }
    }


    private void addFreeRecurringEvents(Map<String, List<CalendarEvent>> aggregateRoutine, calendar.Calendar freeCalendar) {
        //for each day in aggregateRoutine, calculate freeTime
        for(Map.Entry<String, List<CalendarEvent>> entry : aggregateRoutine.entrySet()) {
            String day = entry.getKey();
            List<CalendarEvent> daySchedule = entry.getValue();

            //calculate every day's free time as an ArrayList
            Collections.sort(daySchedule); //need compareTo method from Comparable Interface
            ArrayList<CalendarEvent> dayFreeTime = getDayFreeTime(day, daySchedule);

            //Convert ArrayList to a value in the Calendar
            for (CalendarEvent event : dayFreeTime){
                freeCalendar.addRecurEvent(day, event);
            }
        }
    }

    /**
     * Calculates the aggregate routines of the students in a StudentManager
     * @param group the StudentManager that stores all the students
     * @return the aggregate routines of the students in StudentManager
     */
    private Map<String, List<CalendarEvent>> getAggregateRoutine(Group group){
        ArrayList<calendar.Calendar> calendars  = group.getCalendars();
        Map<String, List<CalendarEvent>> aggregateRoutine= new HashMap<String, List<CalendarEvent>>();
        createAggregateRoutine(aggregateRoutine);
        calendars.forEach((calendar) -> {
            calendar.getRecurring().forEach((day, events) -> aggregateRoutine.put(day,
                    getUnionCalendarEvent(aggregateRoutine.get(day), events)));
        });
        return aggregateRoutine;
    }

    private Map<Float, List<OneOffEvent>> getAggregateOneOff(Group group) {
        ArrayList<Calendar> calendars  = group.getCalendars();
        Map<Float, List<OneOffEvent>> aggregateOneOff = new HashMap<>();
        calendars.forEach((calendar) -> {
            calendar.getSingle().forEach((date, events) -> {
                if(aggregateOneOff.containsKey(date)){
                    aggregateOneOff.put(date, getUnionOneOffEvent(aggregateOneOff.get(date), events));
                }else
                    aggregateOneOff.put(date, events);
            });
        });
        return aggregateOneOff;
    }

    /**
     * Calculate the free-times of a day
     * @param day
     * @param daySchedule
     * @return an ArrayList containing
     */
    private ArrayList<CalendarEvent> getDayFreeTime(String day, List<CalendarEvent> daySchedule) {
        ArrayList<CalendarEvent> dayFreeTime= new ArrayList<CalendarEvent>();
        EventFactory eventC = new EventFactory();
        dayFreeTime.add(eventC.createEvent("Free", 0, (float) 23.59, day));

        for (CalendarEvent event: daySchedule){
            float startTime = event.getStartTime();
            float endTime = event.getEndTime();

            List<CalendarEvent> toRemove = new ArrayList<>();
            List<CalendarEvent> toAdd = addToRemoveAndAddRecurring(dayFreeTime, startTime, endTime, toRemove, day);
            removeAndAddRecurring(dayFreeTime, toRemove, toAdd);
            Collections.sort(dayFreeTime);
        }
        return dayFreeTime;
    }

    private ArrayList<OneOffEvent> getDateFreeTime(Float date, List<OneOffEvent> dateSchedule) {
        ArrayList<OneOffEvent> dateFreeTime= new ArrayList<OneOffEvent>();
        EventFactory eventC = new EventFactory();
        dateFreeTime.add(eventC.createEvent("Free", 0, (float) 23.59, date));

        for (OneOffEvent event: dateSchedule){
            float startTime = event.getStartTime();
            float endTime = event.getEndTime();

            List<OneOffEvent> toRemove = new ArrayList<>();
            List<OneOffEvent> toAdd = addToRemoveAndAddOneOff(dateFreeTime, startTime, endTime, toRemove, date);
            removeAndAddOneOff(dateFreeTime, toRemove, toAdd);
            Collections.sort(dateFreeTime);
        }
        return dateFreeTime;
    }



    private List<OneOffEvent> addToRemoveAndAddOneOff(ArrayList<OneOffEvent> dateFreeTime, float startTime,
                                                      float endTime, List<OneOffEvent> toRemove, Float date) {
        List<OneOffEvent> toAdd = new ArrayList<>();
        for (int i = 0; i < dateFreeTime.size(); i++) {
            EventFactory eventC = new EventFactory();
            OneOffEvent eventToCheck = dateFreeTime.get(i);
            float startFree = eventToCheck.getStartTime();
            float endFree = eventToCheck.getEndTime();


            if (startTime >= startFree && endTime <= endFree) {
                toRemove.add(eventToCheck);
                if (startTime != startFree){
                    toAdd.add(eventC.createEvent("Free", startFree, startTime, date));
                }
                if (endTime != endFree) {
                    toAdd.add(eventC.createEvent("Free", endTime, endFree, date));
                }
            } else if (startTime >= startFree && endTime >= endFree && startTime <= endFree) {
                toRemove.add(eventToCheck);
                toAdd.add(eventC.createEvent("Free", startFree, startTime, date));
            } else if (startTime <= startFree && endTime <= endFree && endTime >= startFree)  {
                toRemove.add(eventToCheck);
                toAdd.add(eventC.createEvent("Free", endTime, endFree, date));
            }

        }

        return toAdd;
    }

    private void removeAndAddOneOff(ArrayList<OneOffEvent> dateFreeTime,
                                    List<OneOffEvent> toRemove, List<OneOffEvent> toAdd) {
        for (OneOffEvent toRemEvent: toRemove){
            dateFreeTime.remove(toRemEvent);
        }
        for (OneOffEvent toAddEvent: toAdd){
            dateFreeTime.add(toAddEvent);
        }
    }

    /**
     * Remove each event in toRemove from dayFreeTime
     * @param dayFreeTime
     * @param toRemove
     */
    private void removeAndAddRecurring(ArrayList<CalendarEvent> dayFreeTime, List<CalendarEvent> toRemove,
                                       List<CalendarEvent> toAdd) {
        for (CalendarEvent toRemEvent: toRemove){
            dayFreeTime.remove(toRemEvent);
        }
        for (CalendarEvent toAddEvent: toAdd){
            dayFreeTime.add(toAddEvent);
        }
    }


    /**
     * @param dayFreeTime
     * @param startTime
     * @param endTime
     * @param toRemove
     * @param day
     * @return
     */
    private List<CalendarEvent> addToRemoveAndAddRecurring(ArrayList<CalendarEvent> dayFreeTime,
                                                           float startTime, float endTime, List<CalendarEvent> toRemove, String day) {
        List<CalendarEvent> toAdd = new ArrayList<>();
        for (int i = 0; i < dayFreeTime.size(); i++) {
            EventFactory eventC = new EventFactory();
            CalendarEvent eventToCheck = dayFreeTime.get(i);
            float startFree = eventToCheck.getStartTime();
            float endFree = eventToCheck.getEndTime();


            if (startTime >= startFree && endTime <= endFree) {
                toRemove.add(eventToCheck);
                if (startTime != startFree){
                    toAdd.add(eventC.createEvent("Free", startFree, startTime, day));
                }
                if (endTime != endFree) {
                    toAdd.add(eventC.createEvent("Free", endTime, endFree, day));
                }
            } else if (startTime >= startFree && endTime >= endFree && startTime <= endFree) {
                toRemove.add(eventToCheck);
                toAdd.add(eventC.createEvent("Free", startFree, startTime, day));
            } else if (startTime <= startFree && endTime <= endFree && endTime >= startFree)  {
                toRemove.add(eventToCheck);
                toAdd.add(eventC.createEvent("Free", endTime, endFree, day));
            }

        }

        return toAdd;
    }


    private void createAggregateRoutine(Map<String, List<CalendarEvent>> aggregateRoutine) {
        aggregateRoutine.put("Monday", new ArrayList<>());
        aggregateRoutine.put("Tuesday", new ArrayList<>());
        aggregateRoutine.put("Wednesday", new ArrayList<>());
        aggregateRoutine.put("Thursday", new ArrayList<>());
        aggregateRoutine.put("Friday", new ArrayList<>());
        aggregateRoutine.put("Saturday", new ArrayList<>());
        aggregateRoutine.put("Sunday", new ArrayList<>());
    }

    /**
     * Calculates the union of two lists
     * @param list1 one of the list to calculate union of
     * @param list2 the other list to calculate the union of
     * @return the union of the two Lists as an ArrayList
     */
    private static List<CalendarEvent> getUnionCalendarEvent(List<CalendarEvent> list1, List<CalendarEvent> list2) {

        Set<CalendarEvent> set = new HashSet<CalendarEvent>();
        set.addAll(list1);
        set.addAll(list2);

        return new ArrayList<CalendarEvent>(set);
    }

    /**
     * Calculates the union of two lists
     * @param list1 one of the list to calculate union of
     * @param list2 the other list to calculate the union of
     * @return the union of the two Lists as an ArrayList
     */
    private static List<OneOffEvent> getUnionOneOffEvent(List<OneOffEvent> list1, List<OneOffEvent> list2) {

        Set<OneOffEvent> set = new HashSet<OneOffEvent>();
        set.addAll(list1);
        set.addAll(list2);

        return new ArrayList<OneOffEvent>(set);
    }
}