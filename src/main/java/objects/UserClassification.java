package objects;

import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="user_classification")
@NamedQueries({
        @NamedQuery(name="UserClassification.getAll",
                query="SELECT u FROM UserClassification u"),
        @NamedQuery(name="UserClassification.getByUser",
                query="SELECT u FROM UserClassification u WHERE u.user = :user"),
        @NamedQuery(name="UserClassification.getByUserAndClassification",
        query="SELECT u FROM UserClassification u WHERE u.user = :user AND u.classification = :classification")
})
@Audited
public class UserClassification extends Model{

    @ManyToOne
    @JoinColumn(name="user_id")
    @NotNull(message = "Пользователь должен быть указан")
    private User user;

    @ManyToOne
    @JoinColumn(name="classification_id")
    @NotNull(message = "Классификатор должен быть указан")
    private Classification classification;

    public UserClassification() {
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public Classification getClassification() {
        return classification;
    }
    public void setClassification(Classification classification) {
        this.classification = classification;
    }

}
