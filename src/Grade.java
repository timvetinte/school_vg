import java.io.Serializable;

public class Grade implements Serializable {

    private Course course;
    private String grade;

    public Grade(Course course, String grade){
        this.course=course;
        this.grade=grade;
    }

    public Course getCourse() {
        return course;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
