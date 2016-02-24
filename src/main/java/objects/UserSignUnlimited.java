package objects;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="user_sign_unlimited")
@NamedQueries({
        @NamedQuery(name="UserSignUnlimited.getAll",
                    query="SELECT d FROM UserSignUnlimited d"),
        @NamedQuery(name="UserSignUnlimited.getByUser",
                query="SELECT d FROM UserSignUnlimited d WHERE d.user = :user"),
        @NamedQuery(name="UserSignUnlimited.getBySignUnlimited",
                    query="SELECT d FROM UserSignUnlimited d WHERE d.signUnlimited = :signUnlimited"),
})
public class UserSignUnlimited extends Model{

    private String value;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="sign_unlimited_id")
    private SignUnlimited signUnlimited;

    public UserSignUnlimited() {
    }

    public void setOrganization(SignUnlimited signUnlimited) {
        this.signUnlimited = signUnlimited;
    }

    public UserSignUnlimited(String value) {
        this.value = value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
    public StringProperty getValueProperty(){
        StringProperty lValueProperty = null;
        return lValueProperty = new SimpleStringProperty(this.value);
    }
    @Override
    public String toString() {
        return this.value;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public User getUser() {
        return user;
    }

    public void setSignUnlimited(SignUnlimited signUnlimited) {
        this.signUnlimited = signUnlimited;
    }
    public SignUnlimited getSignUnlimited() {
        return signUnlimited;
    }
}
