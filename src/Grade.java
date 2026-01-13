import java.io.Serializable;

public class Grade implements Serializable {

    private Course course;
    private int grade;

    public Grade(Course course, int grade){
        this.course=course;
        this.grade=grade;
    }

    public Course getCourse() {
        return course;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
