package login;

import users.students.Student;
import users.students.StudentController;

import java.util.HashMap;

public class LogIn {

    private HashMap<String, Student> userMap;

    public LogIn(StudentController students) {

        this.userMap = students.getAllStudents();

    }

    public boolean checkUsername(String userName) {
        return userMap.containsKey(userName);
    }

    public boolean validateLogIn(String userName, String password) {
        boolean validUsername = this.checkUsername(userName);
        boolean validPassword = false;
        if (validUsername) {
            validPassword = this.userMap.get(userName).getPassword().equals(password);
        }
        return validPassword;
    }

}
