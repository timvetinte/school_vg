import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Student implements Serializable {

    private String firstName;
    private String lastName;
    private String emailAddress;
    private int studentID;
    private List<Grade> grades;

    public Student(String firstName, String lastName, String emailAddress, int studentID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.studentID = studentID;
        this.grades = new ArrayList<>();
    }

    public static Student createStudent(String firstName, String lastName, String emailAddress, int studentID) {
        return new Student(firstName, lastName, emailAddress, studentID);
    }

    public static void createManyStudents(ArrayList studentList){
        studentList.add(createStudent("Melvin", "Davis", "Melvin.davis@skola.se", 4357));
        studentList.add(createStudent("Anna", "Larsson", "anna.larsson@skola.se", 58214));
        studentList.add(createStudent("Erik", "Johansson", "erik.johansson@skola.se", 73920));
        studentList.add(createStudent("Sara", "Nilsson", "sara.nilsson@skola.se", 46195));
        studentList.add(createStudent("Lucas", "Berg", "lucas.berg@skola.se", 80431));
        studentList.add(createStudent("Maja", "Andersson", "maja.andersson@skola.se", 29574));
        studentList.add(createStudent("Oliver", "Karlsson", "oliver.karlsson@skola.se", 67382));
        studentList.add(createStudent("Elin", "Svensson", "elin.svensson@skola.se", 91846));
        studentList.add(createStudent("Noah", "Pettersson", "noah.pettersson@skola.se", 34759));
        studentList.add(createStudent("Emma", "Lind", "emma.lind@skola.se", 56028));
        studentList.add(createStudent("William", "Holm", "william.holm@skola.se", 78214));
        studentList.add(createStudent("Ida", "Nyström", "ida.nystrom@skola.se", 42680));
        studentList.add(createStudent("Leo", "Axelsson", "leo.axelsson@skola.se", 63971));
        studentList.add(createStudent("Alva", "Ekström", "alva.ekstrom@skola.se", 85062));
        studentList.add(createStudent("Elias", "Hansen", "elias.hansen@skola.se", 19475));
        studentList.add(createStudent("Frida", "Björk", "frida.bjork@skola.se", 70593));
        studentList.add(createStudent("Anton", "Wallin", "anton.wallin@skola.se", 36841));
        studentList.add(createStudent("Nora", "Forsberg", "nora.forsberg@skola.se", 92457));
        studentList.add(createStudent("Isak", "Lund", "isak.lund@skola.se", 58734));
        studentList.add(createStudent("Tilda", "Sandberg", "tilda.sandberg@skola.se", 24186));
        studentList.add(createStudent("Hugo", "Engström", "hugo.engstrom@skola.se", 66902));

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

    public String getEmailAddress() {
        return emailAddress;
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

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }
}
