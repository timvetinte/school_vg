public class View {


    public View(Model model) {

    }

    private int index = 1;
    boolean firstIntro = true;

    public void printAllStudents(Model model) {
        if (model.getStudentList() != null) {
            for (Student s : model.getStudentList()) {
                System.out.println(index + ". " + s.getFirstName() + " " + s.getLastName());
                index++;
            }
            System.out.println(index + ". Exit");
            index = 1;
        } else {
            System.out.println("No students exist.");
        }
    }

    public void printAllCourses(Model model) {
        for (Course c : model.getCourses()) {
            System.out.println(index + ". " + c.getCourseName());
            index++;
        }
        System.out.println(index + ". New Course");
        index++;
        System.out.println(index + ". Exit");
        index = 1;
    }

    public void printAllTeachers(Model model) {
        if (model.getTeacherList() != null) {
            for (Teacher t : model.getTeacherList()) {
                System.out.println(index + ". " + t.getFirstName() + " " + t.getLastName());
                index++;
            }
            System.out.println(index + ". Exit");
            index = 1;
        } else {
            System.out.println("No teachers exist.");
        }
    }

    public String gradeToString(int grade) {
        switch (grade) {
            case 0 -> {
                return "-";
            }
            case 1 -> {
                return "A";
            }
            case 2 -> {
                return "B";
            }
            case 3 -> {
                return "C";
            }
            case 4 -> {
                return "D";
            }
            case 5 -> {
                return "E";
            }
            case 6 -> {
                return "F";
            }
            default -> {
                return "?";
            }
        }
    }
    public int getCourseAverage(Course course){
        int courseAverage = 0;
        int peopleWithGrades = 0;
        boolean divideOk = false;
        for(Student s: course.getClassList()){
            if(s.getGrade(course) != 0){
                courseAverage = courseAverage + s.getGrade(course);
                peopleWithGrades++;
                divideOk = true;
            }
        }
        if(divideOk) {
            courseAverage = courseAverage / peopleWithGrades;
        }
        return courseAverage;
    }

    public int getStudentAverage(Student student){
        int studentAverage = 0;
        int gradedCourses = 0;
        boolean divideOk = false;
        for(Grade g : student.getGrades()){
            if(g.getGrade()!=0 && student.getGrades()!=null){
                studentAverage = studentAverage + g.getGrade();
                gradedCourses++;
                divideOk = true;
            }
        }
        if(divideOk) {
            studentAverage = studentAverage / gradedCourses;
        }
        return studentAverage;
    }

    public void printCourseInfo(Course course) {
        System.out.println(course.getCourseName());
        if (course.getTeacher() != null) {
            System.out.println("Teacher: " + course.getTeacher().getFirstName() + " " + course.getTeacher().getLastName());
        } else {
            System.out.println("No teacher assigned");
        }
        if (!course.getClassList().isEmpty()) {
            for (Student s : course.getClassList()) {
                System.out.println(index + ". " + s.getFirstName() + " " + s.getLastName());
                index++;
            }
            System.out.println("--------------------");
            if(getCourseAverage(course)!=0) {
                System.out.println("Course average grade: (" + gradeToString(getCourseAverage(course)) +")");
            }
            index = 1;
        } else {
            System.out.println("Course is empty, add students.");
        }
    }

    public void printGradeAlternatives(){
        System.out.println("1. A \n2. B \n3. C\n4. D\n5. E\n6. F\n7. To exit.");
    }

    public void printCourseStudents(Course course){
        if (!course.getClassList().isEmpty()) {
            for (Student s : course.getClassList()) {
                System.out.println(index + ". " + s.getFirstName() + " " + s.getLastName() + " (" + gradeToString(s.getGrade(course)) + ")");
                index++;
            }
            index = 1;
            if(getCourseAverage(course)!=0) {
                System.out.println("Course average grade: (" + gradeToString(getCourseAverage(course)) +")");
            }
        } else {
            System.out.println("Course is empty, add students.");
        }
    }

    public void printStudentInfo(Student student) {
        System.out.println("Selected student: " + student.getFirstName() + " " + student.getLastName());
        System.out.println("Student ID: " + student.getStudentID());
        System.out.println("Student email: " + student.getEmailAddress());
        if(getStudentAverage(student)!=0){
            System.out.println("Average grade: " + gradeToString(getStudentAverage(student)));
        }
    }

    public void printTeacherInfo(Teacher teacher) {
        System.out.println("Selected Teacher: " + teacher.getFirstName() + " " + teacher.getLastName());
        System.out.println("Teacher's email: " + teacher.getEmailAddress());

    }

    public void printMessage(String messageText) {
        System.out.println(messageText);

    }

    public void printOnOneLine(String messageText){
        System.out.print(messageText);
    }

    public void printIntro() throws InterruptedException {

        if (firstIntro) {
            int sleepAmount = 650;
            System.out.println("\n\n\n");
            System.out.println("▄▖  ▌     ▜              ");
            Thread.sleep(sleepAmount);
            System.out.println("▚ ▛▘▛▌▛▌▛▌▐              ");
            Thread.sleep(sleepAmount);
            System.out.println("▄▌▙▖▌▌▙▌▙▌▐▖             ");
            System.out.println("                         ");
            Thread.sleep(sleepAmount);
            System.out.println("▄▖ ▌   ▘    ▄▖    ▗      ");
            Thread.sleep(sleepAmount);
            System.out.println("▌▌▛▌▛▛▌▌▛▌▄▖▚ ▌▌▛▘▜▘█▌▛▛▌");
            Thread.sleep(sleepAmount);
            System.out.println("▛▌▙▌▌▌▌▌▌▌  ▄▌▙▌▄▌▐▖▙▖▌▌▌");
            Thread.sleep(sleepAmount);
            System.out.println("              ▄▌         ");
            Thread.sleep(sleepAmount);
            System.out.println();
            firstIntro=false;
        } else {
            System.out.println("\n\n\n");
            System.out.println("▄▖  ▌     ▜              ");
            System.out.println("▚ ▛▘▛▌▛▌▛▌▐              ");
            System.out.println("▄▌▙▖▌▌▙▌▙▌▐▖             ");
            System.out.println("                         ");
            System.out.println("▄▖ ▌   ▘    ▄▖    ▗      ");
            System.out.println("▌▌▛▌▛▛▌▌▛▌▄▖▚ ▌▌▛▘▜▘█▌▛▛▌");
            System.out.println("▛▌▙▌▌▌▌▌▌▌  ▄▌▙▌▄▌▐▖▙▖▌▌▌");
            System.out.println("              ▄▌         ");
            System.out.println();
            Thread.sleep(500);
        }

    }

    public void studentFindCourses(Student student, Model model) {
        int index = 1;
        boolean studentHasCourse = false;
        for (Course c : model.courses) {
            if (c.getClassList().contains(student)) {
                studentHasCourse = true;
            }
        }
        if (studentHasCourse) {
            System.out.println(student.getFirstName() + " is enrolled in: ");
            for (Course c : model.courses) {
                if (c.getClassList().contains(student)) {
                    System.out.println(index + ". " + c.getCourseName() + " " + gradeToString(student.getGrade(c)));
                    index++;
                }
            }
            System.out.println("-------------");
        } else {
            System.out.println("Student has no courses.\n");
        }
    }
}
