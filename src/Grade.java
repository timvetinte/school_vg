import java.io.Serializable;

public class Grade implements Serializable {

    private String stringGrade;
    private Course course;
    private Student student;

    public Grade(Student student, String stringGrade, Course course){
        this.student=student;
        this.stringGrade=stringGrade;
        this.course=course;
    }

    public String getStringGrade() {
        return stringGrade;
    }

    public void setStringGrade(String stringGrade) {
        this.stringGrade = stringGrade;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Student getStudent() {
        return student;
    }
}
