import java.io.Serializable;
import java.util.ArrayList;

public class Course implements Serializable {

    private String courseName;
    private ArrayList<Student> classList;
    private int courseSize;
    private Teacher teacher;


    public Course(String courseName, int courseSize, Teacher teacher) {
        this.courseName = courseName;
        this.classList = new ArrayList<>();
        this.courseSize=courseSize;
        this.teacher=teacher;

    }

    public boolean addStudentToCourse(Student student){
        if (classList.size()<this.getCourseSize()) {
            classList.add(student);
            return true;
        } else {
        return false;
        }
    }

    public void removeStudent(Student student){
        classList.remove(student);
    }


    public String getCourseName() {
        return courseName;
    }

    public ArrayList<Student> getClassList() {
        return classList;
    }

    public String getTeacherName() {
        return teacher.getFirstName();
    }

    public Teacher getTeacher(){
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public int getCourseSize() {
        return courseSize;
    }
}