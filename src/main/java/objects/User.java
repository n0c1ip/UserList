package objects;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;

@Entity
@Table(name="users")
//@NamedQuery(name="User.getAll", query="SELECT c FROM users c")
public class User extends Model{

    private static final long serialVersionUID = 1L;

    private String firstName ="";
    private String lastName ="";
    private String middleName = "";
    @ManyToOne
    @JoinColumn(name="department_id")
    private Department department = new Department("");
    @ManyToOne
    @JoinColumn(name="location_id")
    private Location location = new Location("");

    private String position ="";
    private String login = "";
    private String password = "";
    private String mail = "";
    private boolean isFired = false;

    public User() {
    }

    public User(String firstName, String lastName, String middleName, Department department, Location location,
                String position, String login, String password, String mail) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.department = department;
        this.location = location;
        this.position = position;
        this.login = login;
        this.password = password;
        this.mail = mail;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getFirstName() {
        return firstName;
    }
    public StringProperty getFirstNameProperty(){
        StringProperty fNameProperty = null;
        return fNameProperty = new SimpleStringProperty(this.firstName);
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getLastName() {
        return lastName;
    }
    public StringProperty getLastNameProperty(){
        StringProperty lNameProperty = null;
        return lNameProperty = new SimpleStringProperty(this.lastName);
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    public String getMiddleName() {
        return middleName;
    }
    public StringProperty getMiddleNameProperty(){
        StringProperty mNameProperty = null;
        return mNameProperty = new SimpleStringProperty(this.middleName);
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
    public Department getDepartment() {
        return department;
    }
    public StringProperty getDepartmentProperty(){
        StringProperty dptProperty = null;
        return dptProperty = new SimpleStringProperty(this.department.getName());
    }

    public void setLocation(Location location) {
        this.location = location;
    }
    public Location getLocation() {
        return location;
    }

    public void setPosition(String position) {
        this.position = position;
    }
    public String getPosition() {
        return position;
    }
    public StringProperty getPositionProperty(){
        StringProperty psnProperty = null;
        return psnProperty = new SimpleStringProperty(this.position);
    }

    public void setLogin(String login) {
        this.login = login;
    }
    public String getLogin() {
        return login;
    }
    public StringProperty getLoginProperty(){
        StringProperty lgnProperty = null;
        return lgnProperty = new SimpleStringProperty(this.login);
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }
    public StringProperty getPasswordProperty(){
        StringProperty pswdProperty = null;
        return pswdProperty = new SimpleStringProperty(this.password);
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
    public String getMail() {
        return mail;
    }
    public StringProperty getMailProperty(){
        StringProperty mailProperty = null;
        return mailProperty = new SimpleStringProperty(this.mail);
    }

    public boolean isFired() {
        return isFired;
    }
    public void setIsFired(boolean isFired) {
        this.isFired = isFired;
    }
}
