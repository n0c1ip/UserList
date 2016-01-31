package util;


import com.opencsv.CSVReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.Department;
import objects.User;

import java.io.*;
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
        return stringList;

    }

    public static ArrayList<String> getDepartmentsStrings(){
        ArrayList<String> stringList = new ArrayList<>();
        for (String s : departments.keySet()) {
                stringList.add(s);
        }
        return stringList;
    }

    public static void createDepartment(String name){
        departments.put(name, new Department(name));
    }

    public static Department getDepartmentByName(String name){
       return departments.get(name);
    }

    public static void loadUsersFromCSV(String tablename, String csvfilename, char delimiter){

        try {
            CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(csvfilename),"windows-1251"),delimiter);
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null){
                getListByName(tablename).add(new User(nextLine[0],nextLine[1],nextLine[2],
                        nextLine[3],nextLine[4],nextLine[5],nextLine[6],nextLine[7]));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
