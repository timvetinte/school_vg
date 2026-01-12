import java.io.Serializable;

public class Teacher extends Person implements Serializable {

    private String firstName;
    private String lastName;
    private String emailAddress;
    private String password;
    private Boolean admin;

    public Teacher(String firstName, String lastName, String emailAddress, String password, Boolean admin){
        this.firstName=firstName;
        this.lastName=lastName;
        this.emailAddress=emailAddress;
        this.password=password;
        this.admin=admin;
    }

    public static Teacher createTeacher (String firstName, String lastName, String emailAddress, Boolean admin){
        return new Teacher(firstName, lastName, emailAddress, "Password", false);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public Boolean isAdmin() {
        return admin;
    }
}
