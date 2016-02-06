package objects;
//Created by mva on 28.01.2016.

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name="departments")
//@NamedQuery(name="Department.findAll", query="SELECT c FROM departments c")
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
