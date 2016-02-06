package objects;
//Created by mva on 28.01.2016.

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="departments")
@NamedQueries({
        @NamedQuery(name="Department.getAll",
                    query="SELECT d FROM Department d"),
        @NamedQuery(name="Department.getByName",
                    query="SELECT d FROM Department d WHERE d.name = :name")
})
public class Department extends Model{

    private String name;

    @OneToMany(mappedBy="department")
    private Set<User> userSet;

    public Department() {
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
    @Override
    public String toString() {
        return this.name;
    }
}
