package util;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.Department;
import objects.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListUtil {

    private static Map<String,ObservableList> maplist = new HashMap<>();
    private static Map<String, Department> departments= new HashMap<>();


    public static ObservableList createUserList (String name){
        ObservableList<User> templist = FXCollections.observableArrayList();
        maplist.put(name,templist);
        return templist;
    }

    public static ObservableList getListByName(String name){
        return maplist.get(name);
    }

    public static Map<String, ObservableList> getMaplist() {
        return maplist;
    }

    public static ArrayList<String> getMapStrings(){
        ArrayList<String> stringList = new ArrayList<>();
        for (String s : maplist.keySet()) {
                stringList.add(s);
        }
        stringList.sort(String::compareTo);
        return stringList;
    }

    public static ArrayList<String> getDepartmentsStrings(){
        ArrayList<String> stringList = new ArrayList<>();
        for (String s : departments.keySet()) {
                stringList.add(s);
        }
        stringList.sort(String::compareTo);
        return stringList;
    }

    public static void createDepartment(String name){
        departments.put(name, new Department(name));
    }

    public static Department getDepartmentByName(String name){
       return departments.get(name);
    }

}
