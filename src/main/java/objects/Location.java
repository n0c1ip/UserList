package objects;

import javax.persistence.*;
import java.util.Set;

/**
 * Class Location grouped Users by physically location.
 * Represents shops, offices etc.
 */
@Entity
@Table(name="locations")
@NamedQueries({
        @NamedQuery(name="Location.getAll",
                query="SELECT l FROM Location l"),
        @NamedQuery(name="Location.getByName",
                query="SELECT l FROM Location l WHERE l.name = :name")
})
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
