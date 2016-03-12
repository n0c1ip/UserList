package objects;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="sign_unlimited")
@NamedQueries({
        @NamedQuery(name="SignUnlimited.getAll",
                query="SELECT s FROM SignUnlimited s"),
        @NamedQuery(name="SignUnlimited.getByName",
                query="SELECT s FROM SignUnlimited s WHERE s.name = :name")
})
public class SignUnlimited extends Model{
    @NotBlank(message = "Название должно быть не пустым")
    private String name;

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
        return new SimpleStringProperty(this.name);
    }

    @Override
    public String toString() {
        return name;
    }


}
