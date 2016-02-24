package objects;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

import javax.persistence.*;

@Entity
@Table(name="SignUnlimited")
@NamedQueries({
        @NamedQuery(name="SignUnlimited.getAll",
                query="SELECT s FROM SignUnlimited s"),
        @NamedQuery(name="SignUnlimited.getByName",
                query="SELECT s FROM SignUnlimited s WHERE s.name = :name")
})
public class SignUnlimited extends Model{

    private String name;

   /* @OneToOne(mappedBy="SignUnlimited")
    private User user; */

    public SignUnlimited() {
    }

    public SignUnlimited(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public ObservableValue<String> getNameProperty() {
        StringProperty lNameProperty = null;
        return lNameProperty = new SimpleStringProperty(this.name);
    }

    @Override
    public String toString() {
        return name;
    }


}