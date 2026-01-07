import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Student implements Serializable {

    private String firstName;
    private String lastName;
    private String emailAdress;
    private int studentID;
    private List<Grade> grades;

    public Student(String firstName, String lastName, String emailAdress, int studentID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAdress = emailAdress;
        this.studentID = studentID;
        this.grades = new ArrayList<>();
    }

    public static Student createStudent(String firstName, String lastName, String emailAdress, int studentID) {
        return new Student(firstName, lastName, emailAdress, studentID);
    }

    public void addGrade(Course course, String grade) {
        for (Grade g : grades) {
            if (g.getCourse().equals(course)) {
                g.setGrade(grade);
                return;
            }
        }
        Grade newGrade = new Grade(course, grade);
        grades.add(newGrade);
    }

    public String getGrade(Course course) {
        for (Grade grade : grades) {
            if (grade.getCourse().equals(course)) {
                return "(" + grade.getGrade() + ")";
            }
        }
        return "(-)";
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
}
