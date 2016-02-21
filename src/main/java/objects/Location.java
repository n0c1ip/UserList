package objects;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

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

    private String city;

    private String address;

    @OneToMany(mappedBy="location")
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
        StringProperty lAddressProperty = null;
        return lAddressProperty = new SimpleStringProperty(this.address);
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
    public StringProperty getNameProperty(){
        StringProperty lNameProperty = null;
        return lNameProperty = new SimpleStringProperty(this.name);
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public StringProperty getCityProperty(){
        StringProperty lCityProperty = null;
        return lCityProperty = new SimpleStringProperty(this.city);
    }

    @Override
    public String toString() {
        return name;
    }
}
