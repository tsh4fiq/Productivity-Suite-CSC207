package users.groups;

import backend.*;
import calendar.Calendar;
import calendar.FreeTimeCalculator;
import events.CalendarEvent;
import events.OneOffEvent;
import users.Person;
import users.students.Student;

import java.util.HashMap;
import java.util.ArrayList;

public class GroupManager {

    private HashMap<String, Group> groupMap;

    public GroupManager() {

        this.groupMap = new HashMap<String, Group>();

    }

    public ArrayList<String> getStudentUsername(String gID) {

        Group curr = this.groupMap.get(gID);
        return curr.getStudentsUsername();



    }


    public Group createGroup(ArrayList<Person> groupMembers, String groupName) {

        Group newGroup = new Group(groupName);
        newGroup.setStudentsInGroup(groupMembers);
        Integer groupSize = ((this.groupMap).size());
        newGroup.setgID(groupSize.toString());
        (this.groupMap).put((newGroup.getgID()).toString(), newGroup);

        return newGroup;
    }

    public boolean addToGroup(Person student, String GroupID) {

        if ((this.groupMap).containsKey(GroupID)) {

            this.groupMap.get(GroupID).getMembers().add(student);

            return true;


        } else {

            return false;
        }


    }

    public boolean removeGroupMember(Person student, String GroupID) {

        if ((this.groupMap).containsKey(GroupID)) {

            if (this.groupMap.get(GroupID).getMembers().contains(student)) {
                this.groupMap.get(GroupID).getMembers().remove(student);
                return true;
            }
            return false;

        } else {

            return false;
        }

    }

    public Group getGroup(String gID) { return this.groupMap.get(gID); }

    public String getID(Group group) {

        return group.getgID();
    }


    public ArrayList<ArrayList<Object>> getStudentGroups(Student currStudent) {
        ArrayList<ArrayList<Object>> groups = new ArrayList<>();

        for (Group group: this.groupMap.values()){
            ArrayList<Object> newList = new ArrayList<Object>();
            if (group.checkStudent(currStudent)) {
                newList.add(group);
                newList.add(group.getGroupName());
                newList.add(group.getgID());
            }
            groups.add(newList);
        }
        return groups;
    }

    public ArrayList<ArrayList<Object>> getFreeTimes(String gID, Float date, String day) {

        if (groupMap.containsKey(gID)) {
            FreeTimeCalculator freeTimeCalculator = new FreeTimeCalculator();
            Calendar freeSchedule = freeTimeCalculator.getFreeCalendar(this, gID);

            ArrayList<ArrayList<Object>> eventTimes = new ArrayList<>();
            ArrayList<CalendarEvent> calendarEvents = freeSchedule.getRecurring().get(day);

            if (freeSchedule.getSingle().containsKey(date)) {
                ArrayList<OneOffEvent> oneOffs = freeSchedule.getSingle().get(date);
                int length1 = oneOffs.size();

                for (int i = 0; i < length1; i++) {
                    ArrayList<Object> temp = new ArrayList<>();
                    temp.add(oneOffs.get(i).getStartTime());
                    temp.add(oneOffs.get(i).getEndTime());
                    temp.add(oneOffs.get(i).getName());
                    eventTimes.add(temp);
                }
            }
            int length2 = calendarEvents.size();

            ArrayList<ArrayList<Object>> toRemove = new ArrayList<>();
            ArrayList<ArrayList<Object>> toAdd = new ArrayList<>();
            addAndRemove(eventTimes, calendarEvents, length2, toRemove, toAdd);
            for (int i = 0; i < toAdd.size(); i++){
                eventTimes.add(toAdd.get(i));
            }
            for (int i = 0; i < toRemove.size(); i++){
                eventTimes.remove(toRemove.get(i));
            }

            return eventTimes;

        } else {

            return new ArrayList<ArrayList<Object>>();
        }
    }

    private void addAndRemove(ArrayList<ArrayList<Object>> eventTimes, ArrayList<CalendarEvent> calendarEvents,
                              int length2, ArrayList<ArrayList<Object>> toRemove, ArrayList<ArrayList<Object>> toAdd) {
        for (int i = 0; i < length2; i++) {
            float startTime = calendarEvents.get(i).getStartTime();
            float endTime = calendarEvents.get(i).getEndTime();
            String name = calendarEvents.get(i).getName();
            if(!eventTimes.isEmpty()){
                for (int j = 0; j < eventTimes.size(); j++){
                    float startCompare = (float) eventTimes.get(j).get(0);
                    float endCompare = (float) eventTimes.get(j).get(1);
                    ArrayList<Object> temp = new ArrayList<>();
                    if(startTime >= startCompare && endTime <= endCompare){
                        toRemove.add(eventTimes.get(j));
                        temp.add(startTime);
                        temp.add(endTime);
                        temp.add(name);
                        toAdd.add(temp);
                    }else if(startTime >= startCompare && startTime <= endCompare && endTime >= endCompare){
                        toRemove.add(eventTimes.get(j));
                        temp.add(startTime);
                        temp.add(endCompare);
                        temp.add(name);
                        toAdd.add(temp);
                    }else if(startTime <= startCompare && endTime >= startCompare && endTime <= endCompare){
                        toRemove.add(eventTimes.get(j));
                        temp.add(startCompare);
                        temp.add(endTime);
                        temp.add(name);
                        toAdd.add(temp);
                    }
                }
            }else{
                ArrayList<Object> temp = new ArrayList<>();
                temp.add(startTime);
                temp.add(endTime);
                temp.add(name);
                toAdd.add(temp);
            }
        }
    }

    public HashMap<String, Group> getGroups() {
        return this.groupMap;
    }

    public void addTask(String gID, Task task) {
        this.groupMap.get(gID).getTaskList().addTask(task);

    }

    public void removeTask(String gID, Task task) {
        this.groupMap.get(gID).getTaskList().removeTask(task);
    }

    public void closeTask(String gID, Task task) {
        this.groupMap.get(gID).getTaskList().closeTask(task);
    }
}


