import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Controller {

    private Student currentStudent;
    private Teacher currentTeacher;
    private Teacher currentLogin;
    private Course currentCourse;
    private Model model;
    private View view;
    private ArrayList<Grade> gradeList;

    private enum state {
        LOGIN,
        MAIN_MENU,
        COURSES,
        ASSIGN_TEACHER_TO_COURSE,
        ASSIGN_COURSE_TO_TEACHER,
        STUDENTS,
        TEACHERS,
        ADDING_TEACHER,
        SELECT_TEACHER,
        EDITING_TEACHER,
        SEARCHING_STUDENT,
        ADDING_STUDENT,
        EDITING_STUDENT
    }

    private state currentState = state.LOGIN;

    Scanner scanner = new Scanner(System.in);
    Scanner scannerString = new Scanner(System.in);

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    public void run() throws IOException {
        while (true) {
            switch (currentState) {
                case LOGIN -> login();
                case MAIN_MENU -> mainMenu();
                case STUDENTS -> students();
                case COURSES -> courses();
                case ASSIGN_TEACHER_TO_COURSE -> assignTeacherToCourse();
                case ASSIGN_COURSE_TO_TEACHER -> assignCourseToTeacher();
                case TEACHERS -> teachers();
                case ADDING_TEACHER -> createTeacher();
                case SELECT_TEACHER -> selectTeacher();
                case EDITING_TEACHER -> editTeacher(currentTeacher);
                case SEARCHING_STUDENT -> searchStudent();
                case EDITING_STUDENT -> editStudent(currentStudent);
                case ADDING_STUDENT -> createStudent();
            }
        }
    }

    public int pseudoScanner(){
        int selection;
        try{
            selection = scanner.nextInt();
        } catch (InputMismatchException e){
            scanner.nextLine();
            return 0;
        }
        return selection;
    }

    public void login(){
        view.printMessage("ADMINISTRATIVE SCHOOL SYSTEM");
        view.printMessage("Enter email adress: ");
        String loginEmail = scanner.nextLine();
        for(Teacher t : model.teacherList){
            if(loginEmail.equalsIgnoreCase(t.getEmailAdress())){
                currentLogin = t;
                while(true) {
                    view.printMessage("Enter password: ");
                    String loginPass = scanner.nextLine();
                    if (currentLogin.getPassword().equals(loginPass)) {
                        view.printMessage("Successful login!");
                        view.printMessage("Welcome " + currentLogin.getFirstName());
                        currentState = state.MAIN_MENU;
                        return;
                    } else {
                        view.printMessage("Wrong password, try again.");
                    }
                }

            } else {
                view.printMessage("Email not found.");
            }
        }
    }

    public void mainMenu() {
        view.printMessage("Select one of the numbers below");
        view.printMessage("1. Students 2. Teachers 3. Courses 4. Logout");

        int selection = pseudoScanner();

        switch (selection) {
            case 1 -> currentState = state.STUDENTS;
            case 2 -> currentState = state.TEACHERS;
            case 3 -> currentState = state.COURSES;
            case 4 -> {
                currentLogin = null;
                currentState = state.LOGIN;
            }
            default -> view.printMessage("Not a valid option");
        }

    }


    public void courses() {
        view.printAllCourses(model);
        view.printMessage("Input a number matching a course or " + (model.courses.size() + 1) + " to exit: ");
        int selection;

        while (true) {
                selection = pseudoScanner();

            if (selection <= model.courses.size() && selection > 0) {
                break;
            } else if (selection == (model.courses.size() + 1)) {
                currentState = state.MAIN_MENU;
                return;
            }
            view.printMessage("Not a valid number, try again");
            view.printAllCourses(model);
            view.printMessage("Input a number matching a course or " + (model.courses.size() + 1) + " to exit: ");
        }

        currentCourse = model.courses.get(selection - 1);
        view.printCourseInfo(currentCourse);
        view.printMessage("1. Add student to course 2. Assign teacher 3. Grade student 4. Back");
        int selection2;
        while (true) {
            selection = pseudoScanner();
            switch (selection) {
                case 0 -> {
                    view.printCourseInfo(currentCourse);
                    view.printMessage("1. Add student to course 2. Assign teacher 3. Back");
                }
                case 1 -> {
                    view.printAllStudents(model);
                    view.printMessage("Input a number matching a student or " + (model.studentList.size() + 1) + " to exit: ");

                    while (true) {
                        selection2 = pseudoScanner();
                        if (selection2 <= model.studentList.size() && selection2 > 0) {
                            break;
                        } else if (selection2 == (model.studentList.size() + 1)) {
                            view.printMessage("Exiting to main menu.");
                            currentState = state.MAIN_MENU;
                            return;
                        }
                        view.printMessage("Not a valid number, try again");

                    }
                    currentStudent = model.studentList.get(selection2 - 1);
                    if (!currentCourse.getClassList().contains(currentStudent)) {
                        if (!currentCourse.addStudentToCourse(currentStudent)) {
                            view.printMessage("Class is full.");
                        }
                    } else {
                        view.printMessage("Student already enrolled in course.");
                    }
                    model.saveList();
                    view.printMessage(currentStudent.getFirstName() + " was added to the " + currentCourse.getCourseName() + " course.");
                    return;
                }
                case 2 -> {
                    currentState = state.ASSIGN_COURSE_TO_TEACHER;
                    return;
                }

                case 3 -> {
                    if (currentCourse.getClassList().isEmpty()) {
                        view.printCourseStudents(currentCourse);
                    } else {
                        view.printCourseStudents(currentCourse);
                        while(true) {
                            view.printMessage("Input a number matching a student.");
                            int selection3 = pseudoScanner();
                            if (selection3 > currentCourse.getClassList().size() && selection3 > 0) {
                                currentStudent = currentCourse.getClassList().get(selection3 - 1);
                                view.printMessage("Enter " + currentStudent.getFirstName() + "'s grade.");
                                String grade = scanner.nextLine();
                                model.gradeList.add(new Grade(currentStudent, grade, currentCourse));
                            } else {
                                view.printMessage("Not a valid option.");
                            }
                        }
                    }
                }

                case 4 -> {
                    System.out.println("Exiting to main menu.");
                    currentState = state.MAIN_MENU;
                    return;
                }
                default -> {
                }


            }
        }
    }

    public void students() {
        view.printMessage("Select one of the numbers below");
        view.printMessage("1. Search for student 2. Add a new student 3. Back");

        int selection = pseudoScanner();

        switch (selection) {
            case 0 -> {
                view.printMessage("Select one of the numbers below");
                view.printMessage("1. Search for student 2. Add a new student 3. Back");
            }
            case 1 -> {
                if (!model.studentList.isEmpty()) {
                    currentState = state.SEARCHING_STUDENT;
                } else {
                    view.printMessage("No students exist.");
                    currentState = state.MAIN_MENU;
                }
            }
            case 2 -> currentState = state.ADDING_STUDENT;
            case 3 -> {
                view.printMessage("Exiting to main menu.");
                currentState = state.MAIN_MENU;
            }
            default -> view.printMessage("Not a valid option");
        }
    }

    public void teachers() {
        view.printMessage("Select one of the numbers below");
        view.printMessage("1. Add a new teacher 2. Select teacher 3. Back");

        int selection = pseudoScanner();

        switch (selection) {
            case 0 -> {
                view.printMessage("Select one of the numbers below");
                view.printMessage("1. Add a new teacher 2. Select teacher 3. Back");
            }
            case 1 -> currentState = state.ADDING_TEACHER;
            case 2 -> {
                if (!model.teacherList.isEmpty()) {
                    currentState = state.SELECT_TEACHER;
                } else {
                    view.printMessage("No teachers exist.");
                }
            }
            case 3 -> {
                view.printMessage("Exiting to main menu.");
                currentState = state.MAIN_MENU;
            }
            default -> view.printMessage("Not a valid option");
        }
    }

    public void editStudent(Student currentStudent) {
        view.printMessage("1. Edit first name 2. Edit last name 3. Edit email adress 4. Back");
        int selection = pseudoScanner();
        switch (selection) {
            case 0 -> view.printMessage("1. Edit first name 2. Edit last name 3. Edit email adress 4. Back");
            case 1 -> {
                String previousName = currentStudent.getFirstName();
                view.printMessage("Enter a new first name.");
                currentStudent.setFirstName(scannerString.nextLine());
                view.printMessage(previousName + " " + currentStudent.getLastName() + " changed name to: " + currentStudent.getFirstName() + " " + currentStudent.getLastName());
                model.saveList();
            }
            case 2 -> {
                String previousName = currentStudent.getLastName();
                view.printMessage("Enter a new last name.");
                currentStudent.setLastName(scannerString.nextLine());
                view.printMessage(currentStudent.getFirstName() + " " + previousName + " changed name to: " + currentStudent.getFirstName() + " " + currentStudent.getLastName());
                model.saveList();
            }
            case 3 -> {
                String email = currentStudent.getEmailAdress();
                view.printMessage("Enter a new email.");
                currentStudent.setEmailAdress(scannerString.nextLine());
                view.printMessage(email + " changed their email to: " + currentStudent.getEmailAdress());
                model.saveList();
            }
            case 4 -> {
                view.printMessage("Exiting to main menu.");
                currentState = state.MAIN_MENU;
            }
        }
    }

    public void searchStudent() throws IOException {
        if (!model.studentList.isEmpty()) {
            view.printAllStudents(model);
            view.printMessage("Input a number matching a student or " + (model.studentList.size() + 1) + " to exit: ");
            int selection;

            while (true) {
                selection = pseudoScanner();
                if (selection <= model.studentList.size() && selection > 0) {
                    break;
                } else if (selection == (model.studentList.size() + 1)) {
                    currentState = state.MAIN_MENU;
                    return;
                }
                view.printMessage("Not a valid number, try again");
                view.printAllStudents(model);
                view.printMessage("Input a number matching a student or " + (model.studentList.size() + 1) + " to exit: ");
            }

            currentStudent = model.studentList.get(selection - 1);
            view.printStudentInfo(currentStudent);
            view.printMessage("1. Edit student info 2. Remove student 3. Back");
            selection = pseudoScanner();
            switch (selection) {
                case 0 -> {
                    view.printStudentInfo(currentStudent);
                    view.printMessage("1. Edit student info 2. Remove student 3. Back");
                }
                case 1 -> currentState = state.EDITING_STUDENT;

                case 2 -> {
                    view.printMessage("Are you sure?");
                    view.printMessage("1 for Yes, 2 for No");
                    selection = pseudoScanner();
                    switch (selection) {
                        case 0 -> {
                            view.printMessage("Are you sure?");
                            view.printMessage("1 for Yes, 2 for No");
                        }
                        case 1 -> {
                            model.removeStudent(currentStudent);
                            view.printMessage("Student removed.");
                            model.saveList();
                            if (model.studentList.isEmpty()) {
                                currentState = state.MAIN_MENU;
                            }
                        }
                        case 2 -> {
                            view.printMessage("Exiting to main menu.");
                            currentState = state.MAIN_MENU;
                        }
                        default -> view.printMessage("Not a valid option");

                    }
                }
                case 3 -> {
                }


            }
        } else {
            view.printMessage("No students exist.");
            currentState = state.MAIN_MENU;
        }

    }

    public void createStudent() throws IOException {
        view.printMessage("Please enter the students first name: ");
        String fName = scannerString.nextLine().trim();
        view.printMessage("Please enter the students last name: ");
        String lName = scannerString.nextLine().trim();
        String email = fName + "." + lName + "@skola.se";
        int studentID = 10000 + (int) (Math.random() * 9000);
        view.printMessage("Student added.");
        currentStudent = Student.createStudent(fName, lName, email, studentID);
        model.addStudent(currentStudent);
        model.saveList();
        currentState = state.MAIN_MENU;
    }

    public void createTeacher() {
        view.printMessage("Please enter the teachers first name: ");
        String fName = scannerString.nextLine().trim();
        view.printMessage("Please enter the teachers last name: ");
        String lName = scannerString.nextLine().trim();
        String email = fName + "." + lName + "@skola.se";
        view.printMessage("Teacher added.");
        currentTeacher = Teacher.createTeacher(fName, lName, email);
        model.addTeacher(currentTeacher);
        model.saveList();
        currentState = state.MAIN_MENU;
    }

    public void selectTeacher() {
        if (!model.teacherList.isEmpty()) {
            view.printAllTeachers(model);
            view.printMessage("Input a number matching a teacher or " + (model.teacherList.size() + 1) + " to exit: ");
            int selection;

            while (true) {
                selection = pseudoScanner();
                if (selection <= model.teacherList.size() && selection > 0) {
                    break;
                } else if (selection == (model.teacherList.size() + 1)) {
                    currentState = state.MAIN_MENU;
                    return;
                }
                view.printMessage("Not a valid number, try again");
                view.printAllTeachers(model);
                view.printMessage("Input a number matching a teacher or " + (model.teacherList.size() + 1) + " to exit: ");
            }

            currentTeacher = model.teacherList.get(selection - 1);
            view.printTeacherInfo(currentTeacher);
            view.printMessage("1. Edit teacher info 2. Assign a course 3. Remove teacher 3. Back");
            selection = pseudoScanner();
            switch (selection) {
                case 0 -> {
                    view.printTeacherInfo(currentTeacher);
                    view.printMessage("1. Edit teacher info 2. Assign a course 3. Remove teacher 3. Back");
                }
                case 1 -> currentState = state.EDITING_TEACHER;

                case 2 -> currentState = state.ASSIGN_TEACHER_TO_COURSE;

                case 3 -> {
                    view.printMessage("Are you sure?");
                    view.printMessage("1 for Yes, 2 for No");
                    selection = pseudoScanner();
                    switch (selection) {
                        case 0 -> {
                            view.printMessage("Are you sure?");
                            view.printMessage("1 for Yes, 2 for No");
                        }
                        case 1 -> {
                            model.removeTeacher(currentTeacher);
                            view.printMessage("Teacher removed.");
                            model.saveList();
                            if (model.teacherList == null) {
                                currentState = state.MAIN_MENU;
                            }
                        }
                        case 2 -> currentState = state.MAIN_MENU;
                        default -> view.printMessage("Not a valid option");

                    }
                }
                case 4 -> {
                    view.printMessage("Exiting to main menu.");
                    currentState = state.MAIN_MENU;
                }


            }
        } else {
            view.printMessage("No teachers exist.");
        }

    }

    public void assignTeacherToCourse() {
        view.printMessage("Choose a course to assign " + currentTeacher.getFirstName() + " to.");
        view.printAllCourses(model);
        int selection;
        while (true) {
            selection = pseudoScanner();
            if (selection <= model.courses.size() && selection > 0) {
                currentCourse = model.courses.get(selection - 1);
                currentCourse.setTeacher(currentTeacher);
                view.printMessage(currentTeacher.firstName + " assigned to " + currentCourse.getCourseName() + ".");
                model.saveList();
                break;
            } else {
                view.printMessage("Not a valid number");
            }
        }
        currentState = state.MAIN_MENU;
    }

    public void assignCourseToTeacher() {
        if(!model.teacherList.isEmpty()) {
            view.printMessage("Choose a teacher to assign " + currentCourse.getCourseName() + " to.");
            view.printAllTeachers(model);
            int selection;
            while (true) {
                selection = pseudoScanner();
                if (selection <= model.teacherList.size() && selection > 0) {
                    currentTeacher = model.teacherList.get(selection - 1);
                    currentCourse.setTeacher(currentTeacher);
                    view.printMessage("Set " + currentTeacher.getFirstName() + " as the teacher of " + currentCourse.getCourseName() + ".");
                    model.saveList();
                    break;
                } else {
                    view.printMessage("Not a valid number");
                }
            }
            currentState = state.MAIN_MENU;
        } else {
            view.printMessage("No teachers exist.");
            currentState = state.TEACHERS;
        }
    }

    public void editTeacher(Teacher currentTeacher) {
        view.printMessage("1. Edit first name 2. Edit last name 3. Edit email adress 4. Back");
        int selection = pseudoScanner();
        switch (selection) {
            case 0 -> view.printMessage("1. Edit first name 2. Edit last name 3. Edit email adress 4. Back");
            case 1 -> {
                String previousName = currentTeacher.getFirstName();
                view.printMessage("Enter a new first name.");
                currentTeacher.setFirstName(scannerString.nextLine());
                view.printMessage(previousName + " " + currentTeacher.getLastName() + " changed name to: " + currentTeacher.getFirstName() + " " + currentTeacher.getLastName());
                model.saveList();
            }
            case 2 -> {
                String previousName = currentTeacher.getLastName();
                view.printMessage("Enter a new last name.");
                currentTeacher.setLastName(scannerString.nextLine());
                view.printMessage(currentTeacher.getFirstName() + " " + previousName + " changed name to: " + currentTeacher.getFirstName() + " " + currentTeacher.getLastName());
                model.saveList();
            }
            case 3 -> {
                String email = currentTeacher.getEmailAdress();
                view.printMessage("Enter a new email.");
                currentTeacher.setEmailAdress(scannerString.nextLine());
                view.printMessage(email + " changed their email to: " + currentTeacher.getEmailAdress());
                model.saveList();
            }
            case 4 -> {
                view.printMessage("Exiting to main menu.");
                currentState = state.MAIN_MENU;
            }
        }
    }
}
