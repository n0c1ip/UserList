package objects;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

/**
 * Class Location grouped Users by physically location.
 * Represents shops, offices etc.
 */
@Entity
@Table(name="locations")
public class Location extends Model {

    private String name;

    @OneToMany(mappedBy="location")
    private Set<User> userSet;

    public Location() {
    }

    public Location(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addUserToLocation(User user){
        this.userSet.add(user);
    }
}
