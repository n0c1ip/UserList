package objects;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;

@Entity
@Table(name="users")
@NamedQueries({
        @NamedQuery(name="User.getAll",
                    query="SELECT u FROM User u"),
        @NamedQuery(name="User.getUsersByLocation",
                    query="SELECT u FROM User u WHERE u.location = :location"),
        @NamedQuery(name="User.getUsersByDepartment",
                    query = "SELECT u FROM User u WHERE u.department = :department")
})
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

    public void setPc(Pc pc) {
        this.pc = pc;
    }
    public Pc getPc() {
        return pc;
    }
    public StringProperty getPcProperty(){
        StringProperty pcProperty = null;
        if (this.pc != null) {
            pcProperty = new SimpleStringProperty(this.pc.getName());
        } else {
            pcProperty = new SimpleStringProperty("");
        }
        return pcProperty;
    }

}
