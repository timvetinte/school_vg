import java.io.Serializable;

public class Teacher extends Person implements Serializable {

    private String password;
    private Boolean admin;

    public Teacher(String firstName, String lastName, String emailAddress, String password, Boolean admin){
        super(firstName, lastName, emailAddress);
        this.password=password;
        this.admin=admin;
    }

    public static Teacher createTeacher (String firstName, String lastName, String emailAddress, Boolean admin){
        return new Teacher(firstName, lastName, emailAddress, "Password", false);
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
