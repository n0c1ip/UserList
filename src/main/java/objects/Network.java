package objects;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="network")
@NamedQuery(name="Network.getAll",   query="SELECT n FROM Network n")
@Audited
public class Network extends Model{

    @NotBlank(message = "Название должно быть заполнено")
    private String network;

    private String description;

    public Network() {
    }

    public Network(String network) {
        this.network = network;
    }

    public String getNetwork() {
        return network;
    }
    public void setNetwork(String network) {
        this.network = network;
    }
    public StringProperty getNetworkProperty(){
        return new SimpleStringProperty(this.network);
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

    @Override
    public String toString() {
        return network;
    }
}
