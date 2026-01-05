import java.io.Serializable;
import java.lang.annotation.Target;

public class Teacher implements Serializable {

    String firstName;
    String lastName;
    String emailAdress;
    String Password;

    public Teacher(String firstName, String lastName, String emailAdress){
        this.firstName=firstName;
        this.lastName=lastName;
        this.emailAdress=emailAdress;

    }

    public Teacher(String firstName, String lastName, String emailAdress, String Password){
        this.firstName=firstName;
        this.lastName=lastName;
        this.emailAdress=emailAdress;
        this.Password=Password;

    }


    public static Teacher createTeacher (String firstName, String lastName, String emailAdress){
        return new Teacher(firstName, lastName, emailAdress);
    }

    public Teacher(){

    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmailAdress() {
        return emailAdress;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmailAdress(String emailAdress) {
        this.emailAdress = emailAdress;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPassword() {
        return Password;
    }
}
