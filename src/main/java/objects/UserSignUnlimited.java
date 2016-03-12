package objects;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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

    @NotBlank(message = "Значение должно быть заполнено")
    private String value;

    @ManyToOne
    @JoinColumn(name="user_id")
    @NotNull(message = "Пользователь должен быть указан")
    private User user;

    @ManyToOne
    @JoinColumn(name="sign_unlimited_id")
    @NotNull(message = "Признак должен быть указан")
    private SignUnlimited signUnlimited;

    public UserSignUnlimited() {
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
        return new SimpleStringProperty(this.value);
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
