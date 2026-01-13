import java.io.Serial;
import java.io.Serializable;

public abstract class Person implements Serializable {

    String firstName;
    String lastName;
    String emailAddress;

    public Person(String firstName, String lastName, String emailAddress){
        this.firstName=firstName;
        this.lastName=lastName;
        this.emailAddress=emailAddress;
    }

    public Person(){

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
}
