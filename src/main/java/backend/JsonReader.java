package backend;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.google.gson.reflect.TypeToken;
import users.Person;
import users.groups.Group;
import users.groups.GroupController;
import users.students.Student;
import users.students.StudentController;

public class JsonReader implements LoadData {
    private String studentJsonLoc;
    private String groupJsonLoc;
    private String subGroupJsonLoc;

    public JsonReader() {
        this.studentJsonLoc = "src/main/java/students.json";
        this.groupJsonLoc = "src/main/java/groups.json";
        this.subGroupJsonLoc = "src/main/java/subgroups.json";
    }

    public Student[] readStudentJson() throws IOException {

        String filePath = this.studentJsonLoc;

        GsonBuilder gbuild = new GsonBuilder();
        gbuild.serializeNulls();
        Gson gson = gbuild.setPrettyPrinting().create();

        Reader reader = new FileReader(filePath);
        Student[] studentArray = gson.fromJson(reader, Student[].class);

        reader.close();

        return studentArray;

    }

    public HashMap<String, ArrayList<String>> readStudentGroupJson() throws IOException {
        String grpfilePath = this.groupJsonLoc;

        GsonBuilder gbuild = new GsonBuilder();
        gbuild.serializeNulls();
        Gson gson = gbuild.setPrettyPrinting().create();

        Reader reader = new FileReader(grpfilePath);
        Type type = new TypeToken<HashMap<String, ArrayList<String>>>(){}.getType();
        HashMap<String, ArrayList<String>> groupHash = gson.fromJson(reader, type);

        reader.close();

        return groupHash;
    }

    public HashMap<String, ArrayList<String>> readSubGroupJson() throws IOException {
        String subGrpfilePath = this.subGroupJsonLoc;

        GsonBuilder gbuild = new GsonBuilder();
        gbuild.serializeNulls();
        Gson gson = gbuild.setPrettyPrinting().create();

        Reader reader = new FileReader(subGrpfilePath);
        Type type = new TypeToken<HashMap<String, ArrayList<String>>>(){}.getType();
        HashMap<String, ArrayList<String>> subgroupHash = gson.fromJson(reader, type);

        reader.close();

        return subgroupHash;
    }

    public boolean savedInfoStudents() {
        String filePath = this.studentJsonLoc;
        File studentJson = new File(filePath);
        return studentJson.exists();

    }

    public boolean savedInfoGroups() {

        String grpfilePath = this.groupJsonLoc;

        File groupJson = new File(grpfilePath);
        return groupJson.exists();

    }

    public boolean savedInfoSubGroups() {
        String subgrpfilepath = this.subGroupJsonLoc;

        File groupJson = new File(subgrpfilepath);
        return groupJson.exists();
    }

    public void loadData(StudentController studentController, GroupController grpController) {
        try {
            if (savedInfoStudents()) {
                for (Student student : readStudentJson()) {
                    studentController.addStudent(student);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (savedInfoGroups()) {
                HashMap<String, ArrayList<String>> groupHash = readStudentGroupJson();
                for (Map.Entry<String, ArrayList<String>> entry: groupHash.entrySet()) {
                    ArrayList<Person>  students = new ArrayList<>();
                    for (String username: entry.getValue()) {
                        students.add(studentController.getAllStudents().get(username));
                    }
                    grpController.createGroup(students, entry.getKey());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (savedInfoSubGroups()) {
                HashMap<String, ArrayList<String>> subGroupHash = readSubGroupJson();
                for (Map.Entry<String, ArrayList<String>> entry: subGroupHash.entrySet()) {
                    for (String subgroup: entry.getValue()) {
                        Person subgrp = grpController.getGroup(subgroup);
                        grpController.addToGroup(subgrp, entry.getKey());
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
