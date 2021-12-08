package constants;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * This class holds all the exception messages in the program
 */
public class Exceptions {
    public static final String USERNAME_ALREADY_EXISTS = "Username already exists";
    public static final String STU_ALREADY_IN_GROUP = "The student is already in the group";
    public static final String EVENT_EXISTS_AT_TIME = "Another event exists at this time";

    // Returns a list of all exceptions
    public static ArrayList<String> getErrors() {
        ArrayList<String> errorMessages = new ArrayList<String>();
        Field[] fields = Exceptions.class.getFields();
        for (Field field : fields) {
            try {
                String errorMessage =  (String) field.get(Exceptions.class);
                errorMessages.add(errorMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return errorMessages;
    }
}
