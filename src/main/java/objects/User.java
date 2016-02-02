package objects;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.hibernate.annotations.CollectionId;
import util.ListUtil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User extends Model{

    private static final long serialVersionUID = 1L;


    @Column(name = "firstname")
    private String firstName ="";
    @Column(name = "lastname")
    private String lastName ="";
    @Column(name = "middlename")
    private String middleName = "1";

    private Department department = new Department("");
//    @Column(name = "position")
    private String position ="";
//    @Column(name = "login")
    private String login = "";
//    @Column(name ="password")
    private String password = "";
//    @Column(name = "mail")
    private String mail = "";
//    @Column(name = "isFired")
    private boolean isFired = false;


    public User() {
    }

    public User(String firstName, String lastName, String middleName, String departmentname,
                String position, String login, String password, String mail) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.department = ListUtil.getDepartmentByName(departmentname);
        ListUtil.getDepartmentByName(departmentname).addUserToDepartment(this);
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

    public void setDepartment(String  departmentName) {
        this.department = ListUtil.getDepartmentByName(departmentName);
    }
    public Department getDepartment() {
        return department;
    }
    public StringProperty getDepartmentProperty(){
        StringProperty dptProperty = null;
        return dptProperty = new SimpleStringProperty(this.department.getName());
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
