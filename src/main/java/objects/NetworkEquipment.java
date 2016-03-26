package objects;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="network_equipment")
@NamedQuery(name="NetworkEquipment.getAll", query="SELECT n FROM NetworkEquipment n")
@Audited
public class NetworkEquipment extends Model{

    @NotBlank(message = "Название должно быть заполнено")
    private String name;
    private String model;
    private String type;
    private String ipAddress;
    private String description;
    private String location;

    public NetworkEquipment() {
    }
    public NetworkEquipment(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public StringProperty getNameProperty(){
        return new SimpleStringProperty(this.name);
    }

    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public StringProperty getModelProperty(){
        return new SimpleStringProperty(this.model);
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public StringProperty getTypeProperty(){
        return new SimpleStringProperty(this.type);
    }

    public String getIpAddress() {
        return ipAddress;
    }
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    public StringProperty getIpAddressProperty(){
        return new SimpleStringProperty(this.ipAddress);
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

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public StringProperty getLocationProperty() {
        return new SimpleStringProperty(this.location);
    }
}
