package objects;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "virtual_server")
@NamedQueries({
        @NamedQuery(name="VirtualServer.getAll",
                query="SELECT v FROM VirtualServer v"),
        @NamedQuery(name="VirtualServer.getByName",
                query="SELECT v FROM VirtualServer v WHERE v.name = :name")
})
@Audited
public class VirtualServer extends Server{

    private String hdd;
    private String scsiHost;
    private String scsiTarget;
    private String scsiHdd;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private PhysicalServer pServer;

    public VirtualServer() {
    }

    public String getHdd() {
        return hdd;
    }
    public void setHdd(String hdd) {
        this.hdd = hdd;
    }
    public StringProperty getHddProperty(){
        return new SimpleStringProperty(this.hdd);
    }

    public String getScsiHost() {
        return scsiHost;
    }
    public void setScsiHost(String scsiHost) {
        this.scsiHost = scsiHost;
    }
    public StringProperty getScsiHostProperty(){
        return new SimpleStringProperty(this.scsiHost);
    }

    public String getScsiTarget() {
        return scsiTarget;
    }
    public void setScsiTarget(String scsiTarget) {
        this.scsiTarget = scsiTarget;
    }
    public StringProperty getScsiTargetProperty(){
        return new SimpleStringProperty(this.scsiTarget);
    }

    public String getScsiHdd() {
        return scsiHdd;
    }
    public void setScsiHdd(String scsiHdd) {
        this.scsiHdd = scsiHdd;
    }
    public StringProperty getScsiHddProperty(){
        return new SimpleStringProperty(this.scsiHdd);
    }

    public PhysicalServer getpServer() {
        return pServer;
    }
    public void setpServer(PhysicalServer pServer) {
        this.pServer = pServer;
    }
    public StringProperty getPhysicalServerProperty(){
        return new SimpleStringProperty(this.pServer.toString());
    }
}
