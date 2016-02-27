package objects;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="classification")
@NamedQueries({
        @NamedQuery(name="Classification.getAll",
                query="SELECT c FROM Classification c"),
        @NamedQuery(name="Classification.getByName",
                query="SELECT c FROM Classification c WHERE c.name = :name")
})

public class Classification extends Model {

    private String name;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public StringProperty getNameProperty(){
        StringProperty nameProperty = null;
        return nameProperty = new SimpleStringProperty(this.name);
    }

    @Override
    public String toString() {
        return name;
    }
}
