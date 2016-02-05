package objects;

import java.util.HashSet;

/**
 * Class Location grouped Users by physically location.
 * Represents shops, offices etc.
 */
public class Location extends Model {

    private String name;
    private HashSet<User> userSet = new HashSet<>();

    public Location() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void userAdd(User user){
        this.userSet.add(user);
    }
}
