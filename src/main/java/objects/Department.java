package objects;
//Created by mva on 28.01.2016.

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="departments")
@NamedQueries({
        @NamedQuery(name="Department.getAll",
                    query="SELECT d FROM Department d"),
        @NamedQuery(name="Department.getByName",
                    query="SELECT d FROM Department d WHERE d.name = :name"),
        @NamedQuery(name="Department.getByOrganization",
        query="SELECT d FROM Department d WHERE d.organization = :organization")
})
public class Department extends Model{

    private String name;

    @OneToMany(mappedBy="department")
    private Set<User> userSet;

    @ManyToOne
    @JoinColumn(name="organization_id")
    private Organization organization;

    public Department() {
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Department(String name) {
        this.name = name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public StringProperty getNameProperty(){
        return new SimpleStringProperty(this.name);
    }
    @Override
    public String toString() {
        return this.name;
    }
}
