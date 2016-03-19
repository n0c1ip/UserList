package objects;

import crudDB.UserClassificationService;
import crudDB.UserSignUnlimitedService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name="users")
@NamedQueries({
        @NamedQuery(name="User.getAll",
                    query="SELECT u FROM User u"),
        @NamedQuery(name="User.getUsersByLocation",
                    query="SELECT u FROM User u WHERE u.location = :location"),
        @NamedQuery(name="User.getUsersByDepartment",
                    query = "SELECT u FROM User u WHERE u.department = :department"),
        @NamedQuery(name="User.getUsersByClassification",
                query="SELECT u FROM UserClassification uc JOIN uc.user u WHERE uc.classification = :classification")
})
@Audited
public class User extends Model{

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Имя должно быть заполнено")
    private String firstName ="";

    @NotBlank(message = "Фамилия должна быть заполнена")
    private String lastName ="";

    private String middleName = "";

    @ManyToOne
    @JoinColumn(name="department_id")
    @NotNull(message = "Подразделение должно быть указано")
    private Department department;

    @ManyToOne
    @JoinColumn(name="location_id")
    private Location location = new Location("");

    @NotBlank(message = "Должность должна быть заполнена")
    private String position ="";

    @NotBlank(message = "Логин должен быть заполнен")
    private String login = "";

    @NotBlank(message = "Пароль должен быть заполнен")
    private String password = "";

    @NotBlank(message = "Почта должно быть заполнена")
    @Email(message = "Эл. почта не соответсвует формату")
    private String mail = "";

    @OneToOne
    @JoinColumn(name="pc_id")
    private Pc pc;

    public User() {
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getFirstName() {
        return firstName;
    }
    public StringProperty getFirstNameProperty(){
        return new SimpleStringProperty(this.firstName);
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getLastName() {
        return lastName;
    }
    public StringProperty getLastNameProperty(){
        return new SimpleStringProperty(this.lastName);
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    public String getMiddleName() {
        return middleName;
    }
    public StringProperty getMiddleNameProperty(){

        return new SimpleStringProperty(this.middleName);
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
    public Department getDepartment() {
        return department;
    }
    public StringProperty getDepartmentProperty(){
        return new SimpleStringProperty(this.department.getName());
    }

    public void setLocation(Location location) {
        this.location = location;
    }
    public Location getLocation() {
        return location;
    }
    public StringProperty getLocationProperty(){
        return new SimpleStringProperty(this.location.getName());
    }

    public void setPosition(String position) {
        this.position = position;
    }
    public String getPosition() {
        return position;
    }
    public StringProperty getPositionProperty(){
        return new SimpleStringProperty(this.position);
    }

    public void setLogin(String login) {
        this.login = login;
    }
    public String getLogin() {
        return login;
    }
    public StringProperty getLoginProperty(){
        return new SimpleStringProperty(this.login);
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }
    public StringProperty getPasswordProperty(){
        return new SimpleStringProperty(this.password);
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
    public String getMail() {
        return mail;
    }
    public StringProperty getMailProperty(){
        return new SimpleStringProperty(this.mail);
    }

    public void setPc(Pc pc) {
        this.pc = pc;
    }
    public Pc getPc() {
        return pc;
    }
    public StringProperty getPcProperty(){
        StringProperty pcProperty;
        if (this.pc != null) {
            pcProperty = new SimpleStringProperty(this.pc.getName());
        } else {
            pcProperty = new SimpleStringProperty("");
        }
        return pcProperty;
    }

    public String getFullName(){
        return this.lastName + " " + this.firstName  + " " + this.middleName;
    }
    public StringProperty getFullNameProperty(){
        return new SimpleStringProperty(this.lastName + " " + this.firstName  + " " + this.middleName);
    }

    @Override
    public String toString() {
        String userInfo = lastName + " "
                + firstName + " "
                + middleName + " "
                + department + " "
                + pc + " "
                + login + " "
                + password + " "
                + mail + " ";

        List<UserSignUnlimited> signs = UserSignUnlimitedService.getByUser(this);
        for (UserSignUnlimited sign : signs) {
            userInfo += sign.getValue() + " ";
        }

        List<UserClassification> classifications = UserClassificationService.getByUser(this);
        for (UserClassification classification : classifications) {
            userInfo += classification.getClassification().getName() + " ";
        }

        return userInfo.replaceAll("\\s{2,}", " ");
    }
}
