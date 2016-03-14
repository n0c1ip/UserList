package objects;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

@Entity
@Table(name="pc")
@NamedQueries({
        @NamedQuery(name="Pc.getAll",
                query="SELECT p FROM Pc p"),
        @NamedQuery(name="Pc.getByName",
                query="SELECT p FROM Pc p WHERE p.name = :name")
})
public class Pc extends Model{

    @NotBlank(message = "Название компьютера должно быть заполнено")
    private String name;

    private String ipAddress;

    private String vlan;

    private boolean dhcp;

    @OneToOne(mappedBy="pc")
    private User user;

    public Pc() {
    }

    public Pc(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public StringProperty getNameProperty(){
        return new SimpleStringProperty(this.name);
    }

    public User getUser() {
        return this.user;
    }
    public void setUser(User user) {
        this.user = user;
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

    public String getVlan() {
        return vlan;
    }
    public void setVlan(String vlan) {
        this.vlan = vlan;
    }
    public StringProperty getVlanProperty(){
        return new SimpleStringProperty(this.vlan);
    }

    public boolean isDhcp() {
        return dhcp;
    }
    public void setDhcp(boolean dhcp) {
        this.dhcp = dhcp;
    }

    @Override
    public String toString() {
        return name;
    }
}
