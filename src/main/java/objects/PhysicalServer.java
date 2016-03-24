package objects;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "physical_server")
@NamedQueries({
        @NamedQuery(name="PhysicalServer.getAll",
                query="SELECT p FROM PhysicalServer p"),
        @NamedQuery(name="PhysicalServer.getByName",
                query="SELECT p FROM PhysicalServer p WHERE p.name = :name")
})
@Audited
public class PhysicalServer extends Server{

    private String model;
    private TYPE type;
    private boolean isVirtualHost;

    @OneToMany(mappedBy="pServer", fetch = FetchType.LAZY)
    private Set<VirtualServer> virtualServers;

    public enum TYPE {RACK, TOWER}

    public PhysicalServer() {
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


    public TYPE getType() {
        return type;
    }
    public void setType(TYPE type) {
        this.type = type;
    }
    public StringProperty getTypeProperty(){
        return new SimpleStringProperty(this.type.toString());
    }


    public boolean isVirtualHost() {
        return isVirtualHost;
    }
    public void setVirtualHost(boolean virtualHost) {
        isVirtualHost = virtualHost;
    }
}
