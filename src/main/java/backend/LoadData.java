package backend;

import users.groups.GroupController;
import users.students.StudentController;

public interface LoadData {

    public void loadData(StudentController studentController, GroupController grpController);
}
