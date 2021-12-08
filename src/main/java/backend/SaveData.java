package backend;

import users.groups.GroupController;
import users.students.StudentController;

public interface SaveData {

    public void saveData(StudentController studentController, GroupController grpcontroller);
}
