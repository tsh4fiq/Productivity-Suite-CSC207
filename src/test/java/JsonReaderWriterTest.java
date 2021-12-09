import backend.JsonReader;
import backend.JsonWriter;
import org.junit.Before;
import org.junit.Test;
import users.Person;
import users.groups.Group;
import users.groups.GroupController;
import users.students.Student;
import users.students.StudentController;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class JsonReaderWriterTest {
    Student s1;
    Student s2;
    Student s3;
    Student s4;
    Student s5;
    Student s6;
    Student s7;
    Student s8;
    GroupController gc;
    StudentController sc;

    @Before
    public void setUp() {
        s1 = new Student("Vergil", "pass");
        s2 = new Student("Tom", "pass");
        s3 = new Student("Mike", "pass");
        s4 = new Student("Adam", "pass");
        s5 = new Student("Fred", "pass");
        s6 = new Student("Louis", "pass");
        s7 = new Student("Mark", "pass");
        s8 = new Student("Cat", "pass");
        gc = new GroupController();
        sc = new StudentController();
        sc.addStudent(s1);
        sc.addStudent(s2);
        sc.addStudent(s3);
        sc.addStudent(s4);
        sc.addStudent(s5);
        sc.addStudent(s6);
        sc.addStudent(s7);
        sc.addStudent(s8);


    }

    @Test
    public void TestJsonReaderWriterNonNestedGroups() {
        ArrayList<Person> g0 = new ArrayList<>();
        ArrayList<Person> g1 = new ArrayList<>();
        ArrayList<Person> g2 = new ArrayList<>();
        ArrayList<Person> g3 = new ArrayList<>();

        g0.add(s1);
        g0.add(s2);
        g0.add(s3);
        g1.add(s4);
        g1.add(s5);
        g2.add(s6);
        g1.add(s7);
        g3.add(s8);

        gc.createGroup(g0, "G0");
        gc.createGroup(g1, "G1");
        gc.createGroup(g2, "G2");
        gc.createGroup(g3, "G3");

        JsonWriter writer = new JsonWriter();
        writer.saveData(sc, gc);

        StudentController newSC = new StudentController();
        GroupController newGC = new GroupController();

        JsonReader reader = new JsonReader();
        reader.loadData(newSC, newGC);

        assertTrue(newGC.getGroups().containsKey("0"));
        assertTrue(newGC.getGroups().containsKey("1"));
        assertTrue(newGC.getGroups().containsKey("2"));
        assertTrue(newGC.getGroups().containsKey("3"));


    }

    @Test
    public void TestJsonReaderWriterStudents() {
        ArrayList<Person> g0 = new ArrayList<>();
        ArrayList<Person> g1 = new ArrayList<>();
        ArrayList<Person> g2 = new ArrayList<>();
        ArrayList<Person> g3 = new ArrayList<>();

        g0.add(s1);
        g0.add(s2);
        g0.add(s3);
        g1.add(s4);
        g1.add(s5);
        g2.add(s6);
        g1.add(s7);
        g3.add(s8);

        gc.createGroup(g0, "G0");
        gc.createGroup(g1, "G1");
        gc.createGroup(g2, "G2");
        gc.createGroup(g3, "G3");

        JsonWriter writer = new JsonWriter();
        writer.saveData(sc, gc);

        StudentController newSC = new StudentController();
        GroupController newGC = new GroupController();

        JsonReader reader = new JsonReader();
        reader.loadData(newSC, newGC);

        assertTrue(newSC.getAllStudents().containsKey("Vergil"));
        assertTrue(newSC.getAllStudents().containsKey("Tom"));
        assertTrue(newSC.getAllStudents().containsKey("Mike"));
        assertTrue(newSC.getAllStudents().containsKey("Adam"));
        assertTrue(newSC.getAllStudents().containsKey("Fred"));
        assertTrue(newSC.getAllStudents().containsKey("Louis"));
        assertTrue(newSC.getAllStudents().containsKey("Mark"));
        assertTrue(newSC.getAllStudents().containsKey("Cat"));


    }

    @Test
    public void TestJsonReaderWriterNonNestedGroupsStudentsInGroups() {
        ArrayList<Person> g0 = new ArrayList<>();
        ArrayList<Person> g1 = new ArrayList<>();
        ArrayList<Person> g2 = new ArrayList<>();
        ArrayList<Person> g3 = new ArrayList<>();

        g0.add(s1);
        g0.add(s2);
        g0.add(s3);
        g1.add(s4);
        g1.add(s5);
        g2.add(s6);
        g1.add(s7);
        g3.add(s8);

        gc.createGroup(g0, "G0");
        gc.createGroup(g1, "G1");
        gc.createGroup(g2, "G2");
        gc.createGroup(g3, "G3");

        JsonWriter writer = new JsonWriter();
        writer.saveData(sc, gc);

        StudentController newSC = new StudentController();
        GroupController newGC = new GroupController();

        JsonReader reader = new JsonReader();
        reader.loadData(newSC, newGC);

        assertTrue(gc.getGroup("0").checkStudent(sc.getAllStudents().get("Vergil")));
        assertTrue(gc.getGroup("0").checkStudent(sc.getAllStudents().get("Tom")));
        assertTrue(gc.getGroup("0").checkStudent(sc.getAllStudents().get("Mike")));
        assertTrue(gc.getGroup("1").checkStudent(sc.getAllStudents().get("Adam")));
        assertTrue(gc.getGroup("1").checkStudent(sc.getAllStudents().get("Fred")));
        assertTrue(gc.getGroup("2").checkStudent(sc.getAllStudents().get("Louis")));
        assertTrue(gc.getGroup("1").checkStudent(sc.getAllStudents().get("Mark")));
        assertTrue(gc.getGroup("3").checkStudent(sc.getAllStudents().get("Cat")));



    }

    @Test
    public void TestJsonReaderWriterNestedGroupsStudents() {
        ArrayList<Person> g0 = new ArrayList<>();
        ArrayList<Person> g1 = new ArrayList<>();
        ArrayList<Person> g2 = new ArrayList<>();
        ArrayList<Person> g3 = new ArrayList<>();

        g0.add(s1);
        g0.add(s2);
        g0.add(s3);
        g1.add(s4);
        g1.add(s5);
        g2.add(s6);
        g1.add(s7);
        g3.add(s8);

        gc.createGroup(g0, "G0");
        gc.createGroup(g1, "G1");
        gc.createGroup(g2, "G2");
        gc.createGroup(g3, "G3");

        gc.addToGroup(gc.getGroup("2"), "1");
        gc.addToGroup(gc.getGroup("1"), "0");
        gc.addToGroup(gc.getGroup("3"), "0");

        JsonWriter writer = new JsonWriter();
        writer.saveData(sc, gc);

        StudentController newSC = new StudentController();
        GroupController newGC = new GroupController();

        JsonReader reader = new JsonReader();
        reader.loadData(newSC, newGC);

        assertTrue(gc.getGroup("0").checkStudent(sc.getAllStudents().get("Vergil")));
        assertTrue(gc.getGroup("0").checkStudent(sc.getAllStudents().get("Tom")));
        assertTrue(gc.getGroup("0").checkStudent(sc.getAllStudents().get("Mike")));
        assertTrue(gc.getGroup("1").checkStudent(sc.getAllStudents().get("Adam")));
        assertTrue(gc.getGroup("1").checkStudent(sc.getAllStudents().get("Fred")));
        assertTrue(gc.getGroup("2").checkStudent(sc.getAllStudents().get("Louis")));
        assertTrue(gc.getGroup("1").checkStudent(sc.getAllStudents().get("Mark")));
        assertTrue(gc.getGroup("3").checkStudent(sc.getAllStudents().get("Cat")));
    }

    @Test
    public void TestJsonReaderWriterNestedGroupsGroups() {
        ArrayList<Person> g0 = new ArrayList<>();
        ArrayList<Person> g1 = new ArrayList<>();
        ArrayList<Person> g2 = new ArrayList<>();
        ArrayList<Person> g3 = new ArrayList<>();

        g0.add(s1);
        g0.add(s2);
        g0.add(s3);
        g1.add(s4);
        g1.add(s5);
        g2.add(s6);
        g1.add(s7);
        g3.add(s8);

        gc.createGroup(g0, "G0");
        gc.createGroup(g1, "G1");
        gc.createGroup(g2, "G2");
        gc.createGroup(g3, "G3");

        gc.addToGroup(gc.getGroup("2"), "1");
        gc.addToGroup(gc.getGroup("1"), "0");
        gc.addToGroup(gc.getGroup("3"), "0");

        JsonWriter writer = new JsonWriter();
        writer.saveData(sc, gc);

        StudentController newSC = new StudentController();
        GroupController newGC = new GroupController();

        JsonReader reader = new JsonReader();
        reader.loadData(newSC, newGC);

        assertTrue(newGC.getGroups().containsKey("0"));
        assertTrue(newGC.getGroups().containsKey("1"));
        assertTrue(newGC.getGroups().containsKey("2"));
        assertTrue(newGC.getGroups().containsKey("3"));

    }

    @Test
    public void TestJsonReaderWriterNestedGroupsSubGroups() {
        ArrayList<Person> g0 = new ArrayList<>();
        ArrayList<Person> g1 = new ArrayList<>();
        ArrayList<Person> g2 = new ArrayList<>();
        ArrayList<Person> g3 = new ArrayList<>();

        g0.add(s1);
        g0.add(s2);
        g0.add(s3);
        g1.add(s4);
        g1.add(s5);
        g2.add(s6);
        g1.add(s7);
        g3.add(s8);

        gc.createGroup(g0, "G0");
        gc.createGroup(g1, "G1");
        gc.createGroup(g2, "G2");
        gc.createGroup(g3, "G3");

        gc.addToGroup(gc.getGroup("2"), "1");
        gc.addToGroup(gc.getGroup("1"), "0");
        gc.addToGroup(gc.getGroup("3"), "0");

        JsonWriter writer = new JsonWriter();
        writer.saveData(sc, gc);

        StudentController newSC = new StudentController();
        GroupController newGC = new GroupController();

        JsonReader reader = new JsonReader();
        reader.loadData(newSC, newGC);

        assertTrue(newGC.getGroup("0").getDirectSubGroupsString().contains("1"));
        assertTrue(newGC.getGroup("0").getDirectSubGroupsString().contains("3"));
        assertTrue(newGC.getGroup("1").getDirectSubGroupsString().contains("2"));


    }
}
