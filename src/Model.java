import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

public class Model {

    ArrayList<Student> studentList = new ArrayList();
    ArrayList<Teacher> teacherList = new ArrayList<>();
    ArrayList<Course> courses = new ArrayList<>();


    File file = new File("src/list.ser");

    public ArrayList<Student> getStudentList() {
        return studentList;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void addStudent(Student student_name){
        studentList.add(student_name);
    }

    public void addTeacher(Teacher teacher_name){
        teacherList.add(teacher_name);
    }

    public void removeTeacher(Teacher teacher_name){
        teacherList.remove(teacher_name);
    }

    public void removeStudent(Student student_name){
        studentList.remove(student_name);
    }

    public void sortStudentList(){
        studentList.sort(Comparator.comparing(Student::getFirstName));
    }

    public void sortTeacherList(){
        teacherList.sort(Comparator.comparing(Teacher::getFirstName));
    }

    public void sortCourses(){

        for(Course c : courses){
            c.getClassList().sort(Comparator.comparing(Student::getFirstName));
        }
    }



    public void saveList() {

        sortStudentList();
        sortTeacherList();
        sortCourses();

        try { ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));

            out.writeObject(studentList);
            out.writeObject(teacherList);
            out.writeObject(courses);

        } catch (FileNotFoundException e) {
            System.out.println("FILE DOES NOT EXIST");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadList() throws IOException {

        if(!file.exists()) {
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(file))) {
            studentList = (ArrayList<Student>) ois.readObject();
            teacherList = (ArrayList<Teacher>) ois.readObject();
            courses = (ArrayList<Course>) ois.readObject();

        } catch (FileNotFoundException e) {
            System.out.println("NO SAVEFILE LOADED");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Teacher> getTeacherList() {
        return teacherList;
    }

    public void removeStudentFromAllCourses(Student student) {

        for (Course c : courses) {
            if (c.getClassList().contains(student)) {
                c.getClassList().remove(student);
            }
        }
    }

    public Model(){

        courses.add(new Course("Math", 20, null));
        courses.add(new Course("Java", 20, null));
        courses.add(new Course("Biology", 20, null));
        courses.add(new Course("Design-patterns", 20, null));
        teacherList.add(new Teacher("Admin", "Login", "Admin.login@skola.se", "Password", true));




        try {
            loadList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

