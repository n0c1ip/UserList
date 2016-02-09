package objects;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="organization")
@NamedQueries({
        @NamedQuery(name="Organization.getAll",
                query="SELECT o FROM Organization o"),
        @NamedQuery(name="Organization.getByName",
                query="SELECT o FROM Department o WHERE o.name = :name")
})
public class Organization extends Model {

    private String name;

    @OneToMany(mappedBy="organization")
    private Set<Department> departmentSet;

    public Organization(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Set<Department> getDepartmentSet() {
        return departmentSet;
    }
    public void setDepartmentSet(Set<Department> departmentSet) {
        this.departmentSet = departmentSet;
    }
}
