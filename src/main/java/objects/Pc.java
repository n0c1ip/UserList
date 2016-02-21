package objects;

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

    private String name;

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

    @Override
    public String toString() {
        return name;
    }
}
