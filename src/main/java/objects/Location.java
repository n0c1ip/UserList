package objects;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotBlank;

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
@Audited
public class Location extends Model {

    @NotBlank(message = "Название должно быть заполнено")
    private String name;

    private String city;

    private String address;

    @OneToMany(mappedBy="location", fetch = FetchType.LAZY)
    private Set<User> userSet;

    public Location() {
    }

    public Location(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public StringProperty getAdderssProperty(){
        return new SimpleStringProperty(this.address);
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
    public StringProperty getNameProperty(){
        return new SimpleStringProperty(this.name);
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public StringProperty getCityProperty(){
        return new SimpleStringProperty(this.city);
    }

    @Override
    public String toString() {
        return name;
    }
}
