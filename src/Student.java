import java.io.Serializable;
import java.util.ArrayList;

public class Student implements Serializable {

    String firstName;
    String lastName;
    String emailAdress;
    int studentID;
    private ArrayList<Grade> gradeList;

    public Student(String firstName, String lastName, String emailAdress, int studentID){
        this.firstName=firstName;
        this.lastName=lastName;
        this.emailAdress=emailAdress;
        this.studentID=studentID;
    }

    public static Student createStudent (String firstName, String lastName, String emailAdress, int studentID){
        return new Student(firstName, lastName, emailAdress, studentID);
    }

    public Student(){

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

    public int getStudentID() {
        return studentID;
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

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public ArrayList<Grade> getGradeList() {
        return gradeList;
    }
}
