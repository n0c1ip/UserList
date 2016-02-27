package objects;

import javax.persistence.*;

@Entity
@Table(name="user_classification")
@NamedQueries({
        @NamedQuery(name="UserClassification.getAll",
                query="SELECT u FROM UserClassification u"),
        @NamedQuery(name="UserClassification.getByUser",
                query="SELECT u FROM UserClassification u WHERE u.user = :user")
})
public class UserClassification extends Model{

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="classification_id")
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
