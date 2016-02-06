package objects;
//Created by mva on 28.01.2016.

import java.util.HashSet;

public class Department extends Model{


    private String name;
    private HashSet<User> userSet = new HashSet<>();

    public Department() {
    }

    public Department(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public HashSet<User> getUserList(){
        return this.userSet;
    }

    public void addUserToDepartment(User user){
        userSet.add(user);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
