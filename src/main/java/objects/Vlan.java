package objects;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
@Table(name="vlan")
@NamedQuery(name="Vlan.getAll",   query="SELECT v FROM Vlan v")
@Audited
public class Vlan extends Model {

    @Pattern(regexp = "(4096)|[1-9]\\d?", message = "Введите чилсло от 1 до 4096")
    @NotEmpty(message = "Номер VLAN не может быть пустым")
    private String number;

    @OneToOne
    @JoinColumn(name="network_id")
    private Network network;

    private String description;

    public Vlan() {
    }

    public Vlan(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public SimpleStringProperty getNumberProperty(){
        return new SimpleStringProperty(this.number);
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public StringProperty getDescriptionProperty(){
        return new SimpleStringProperty(this.description);
    }


    public Network getNetwork() {
        return network;
    }
    public void setNetwork(Network network) {
        this.network = network;
    }
    public StringProperty getNetworkProperty(){
        return new SimpleStringProperty(this.network.getNetwork());
    }

    @Override
    public String toString() {
        return number + " [" + network + "]";
    }
}
