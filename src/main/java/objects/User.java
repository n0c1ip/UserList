package objects;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer userId;
    private String firstName ="";
    private String lastName ="";
    private String middleName = "1";
    private Department department = new Department("");
    private String position ="";
    private String login = "";
    private String password = "";
    private String mail = "";

    public User() {
    }
    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Integer getUserId() {
        return userId;
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









}
