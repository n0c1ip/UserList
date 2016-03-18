package objects;

import crudDB.ExtendedRevisionListener;
import org.hibernate.envers.DefaultRevisionEntity;
import javax.persistence.Entity;

@Entity
@org.hibernate.envers.RevisionEntity(ExtendedRevisionListener.class)
public class ExtendedRevisionEntity extends DefaultRevisionEntity {
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
