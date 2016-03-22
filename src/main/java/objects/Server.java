package objects;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Server extends Model{

    private String name;
    private String ram;
    private String os;
    private String ipAddress;
    private String description;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public StringProperty getNameProperty(){
        return new SimpleStringProperty(this.name);
    }

    public String getRam() {
        return ram;
    }
    public void setRam(String ram) {
        this.ram = ram;
    }
    public StringProperty getRamProperty(){
        return new SimpleStringProperty(this.ram);
    }

    public String getOs() {
        return os;
    }
    public void setOs(String os) {
        this.os = os;
    }
    public StringProperty getOsProperty(){
        return new SimpleStringProperty(this.os);
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


}
