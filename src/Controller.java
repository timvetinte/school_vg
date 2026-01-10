import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Controller {

    private Student currentStudent;
    private Teacher currentTeacher;
    private Teacher currentLogin;
    private Course currentCourse;
    private Model model;
    private View view;
    private boolean emailFound;

    private enum state {
        LOGIN,
        MAIN_MENU,
        STUDENTS,
        SEARCHING_STUDENT,
        STUDENT_VIEW,
        EDITING_STUDENT,
        ADDING_STUDENT,
        ///
        TEACHERS,
        ADDING_TEACHER,
        SELECT_TEACHER,
        TEACHER_VIEW,
        REMOVE_TEACHER,
        EDITING_TEACHER,
        ASSIGN_COURSE_TO_TEACHER,
        ///
        COURSES,
        COURSE_VIEW,
        ADD_STUDENT_TO_COURSE,
        GRADE_STUDENT,
        ASSIGNING_GRADE,
        REMOVE_STUDENT_FROM_COURSE,
        ASSIGN_TEACHER_TO_COURSE
    }

    private state currentState = state.LOGIN;

    Scanner scanner = new Scanner(System.in);
    Scanner scannerString = new Scanner(System.in);

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    public void run() throws IOException, InterruptedException {
        while (true) {
            switch (currentState) {


                case LOGIN -> login();
                case MAIN_MENU -> mainMenu();
                case STUDENTS -> students();
                case SEARCHING_STUDENT -> searchStudent();
                case STUDENT_VIEW -> studentView();
                case EDITING_STUDENT -> editStudent(currentStudent);
                case ADD_STUDENT_TO_COURSE -> addStudentToCourse();
                case REMOVE_STUDENT_FROM_COURSE -> removeStudentFromCourse();
                case GRADE_STUDENT -> gradeStudent();
                case ADDING_STUDENT -> createStudent();
                ///
                case TEACHERS -> teachers();
                case TEACHER_VIEW -> teacherView();
                case ADDING_TEACHER -> createTeacher();
                case SELECT_TEACHER -> selectTeacher();
                case EDITING_TEACHER -> editTeacher(currentTeacher);
                ///
                case COURSES -> courses();
                case COURSE_VIEW -> courseView();
                case ASSIGN_TEACHER_TO_COURSE -> assignTeacherToCourse();
                case ASSIGN_COURSE_TO_TEACHER -> assignCourseToTeacher();
                case REMOVE_TEACHER -> removeTeacher();
                case ASSIGNING_GRADE -> assigningGrade();

            }
        }
    }

    public int pseudoScanner() {
        int selection;
        try {
            selection = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            scanner.nextLine();
            return 0;
        }
        return selection;
    }

    public void login() {
        view.printMessage("ADMINISTRATIVE SCHOOL SYSTEM");
        view.printOnOneLine("Enter email address: ");
        String loginEmail = scanner.nextLine();
        for (Teacher t : model.teacherList) {
            if (loginEmail.equalsIgnoreCase(t.getEmailAddress())) {
                currentLogin = t;
                while (true) {
                    view.printOnOneLine("Enter password or 1 to exit: ");
                    emailFound = true;
                    String loginPass = scanner.nextLine();
                    if (currentLogin.getPassword().equals(loginPass)) {
                        view.printMessage("\n\nSuccessful login!");
                        view.printMessage("Welcome " + currentLogin.getFirstName() + " " + currentLogin.getLastName() + "!");
                        emailFound = false;
                        currentState = state.MAIN_MENU;
                        return;
                    } else if (loginPass.equals("1")) {
                        emailFound = true;
                        break;
                    } else {
                        view.printMessage("Wrong password, try again.");
                    }
                }

            }
        }
        if (!emailFound) {
            view.printMessage("Email not found.");
        }
        emailFound = false;
    }

    public void mainMenu() throws InterruptedException {
        view.printIntro();
        while (true) {
            view.printMessage("Select one of the numbers below:");
            view.printMessage("1. Students 2. Teachers 3. Courses 4. Logout");

            int selection = pseudoScanner();

            switch (selection) {
                case 1 -> {
                    currentState = state.STUDENTS;
                    return;
                }
                case 2 -> {
                    currentState = state.TEACHERS;
                    return;
                }
                case 3 -> {
                    currentState = state.COURSES;
                    return;
                }
                case 4 -> {
                    currentLogin = null;
                    currentState = state.LOGIN;
                    return;
                }
                default -> view.printMessage("Not a valid option");
            }
        }

    }


    public void courses() throws InterruptedException {
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
            Thread.sleep(1000);
            view.printAllCourses(model);
            view.printMessage("Input a number matching a course or " + (model.courses.size() + 1) + " to exit: ");
        }

        currentCourse = model.courses.get(selection - 1);
        currentState = state.COURSE_VIEW;
    }

    public void courseView() throws InterruptedException {
        view.printCourseInfo(currentCourse);
        view.printMessage("1. Add student to course 2. Remove student from course 3. Assign teacher 4. Grade student 5. Back");
        while (true) {
            int selection = pseudoScanner();
            switch (selection) {
                case 0 -> {
                    view.printCourseInfo(currentCourse);
                    view.printMessage("1. Add student to course 2. Remove student from course 3. Assign teacher 4. Grade student 5. Back");
                }
                case 1 -> {
                    currentState = state.ADD_STUDENT_TO_COURSE;
                    return;
                }
                case 2 -> {
                    currentState = state.REMOVE_STUDENT_FROM_COURSE;
                    return;
                }
                case 3 -> {
                    currentState = state.ASSIGN_COURSE_TO_TEACHER;
                    return;
                }

                case 4 -> {
                    currentState = state.GRADE_STUDENT;
                    return;
                }

                case 5 -> {
                    System.out.println("Exiting...");
                    Thread.sleep(500);
                    currentState = state.COURSES;
                    return;
                }
                default -> {
                }


            }
        }
    }

    public void removeStudentFromCourse() throws InterruptedException {
        if (!currentCourse.getClassList().isEmpty()) {
            while (true) {
                view.printCourseStudents(currentCourse);
                view.printMessage("Enter the number of the student to be removed or enter " + (currentCourse.getClassList().size() + 1) + " to exit");
                int selection = pseudoScanner();
                if (selection <= currentCourse.getClassList().size() && selection > 0) {
                    currentStudent = currentCourse.getClassList().get(selection - 1);
                    view.printMessage("Are you sure?");
                    view.printMessage("1 for yes 2 for no.");
                    int selection2 = pseudoScanner();
                    switch (selection2) {
                        case 1 -> {
                            currentCourse.removeStudent(currentStudent);
                            view.printMessage("Removed " + currentStudent.getFirstName() + " from course.");
                            Thread.sleep(1000);
                            model.saveList();
                            currentState = state.COURSE_VIEW;
                            return;
                        }
                        case 2 -> {
                            view.printMessage("Did not remove student");
                            Thread.sleep(1000);
                            currentState = state.COURSE_VIEW;
                            return;
                        }

                    }
                } else if (selection == (currentCourse.getClassList().size() + 1)) {
                    currentState = state.COURSE_VIEW;
                    return;
                }
            }
        } else {
            view.printMessage("No student are available to be removed.");
            Thread.sleep(1700);
            currentState = state.COURSE_VIEW;
        }
    }

    public void addStudentToCourse() throws InterruptedException {
        if (currentCourse.getClassList().size() < 20) {
            view.printAllStudents(model);
            view.printMessage("Input a number matching a student or " + (model.studentList.size() + 1) + " to exit: ");
            int selection;
            while (true) {
                selection = pseudoScanner();
                if (selection <= model.studentList.size() && selection > 0) {
                    break;
                } else if (selection == (model.studentList.size() + 1)) {
                    view.printMessage("Exiting...");
                    currentState = state.COURSE_VIEW;
                    return;
                }
                view.printMessage("Not a valid number, try again");
                Thread.sleep(1500);

            }
            currentStudent = model.studentList.get(selection - 1);
            if (!currentCourse.getClassList().contains(currentStudent)) {
                if (!currentCourse.addStudentToCourse(currentStudent)) {
                    view.printMessage("Class is full.");
                    Thread.sleep(1700);
                    return;
                }
            } else {
                view.printMessage("Student already enrolled in course.");
                Thread.sleep(1500);
                return;
            }
            model.saveList();
            view.printMessage(currentStudent.getFirstName() + " was added to the " + currentCourse.getCourseName() + " course.");
            currentState = state.COURSE_VIEW;
        } else {
            view.printMessage("Course is full, remove student or enroll in a different course.");
            Thread.sleep(2200);
            currentState = state.COURSE_VIEW;
        }
    }

    public void gradeStudent() throws InterruptedException {
        if (currentCourse.getClassList().isEmpty()) {
            view.printMessage("No students available to grade.");
            Thread.sleep(1700);
            currentState = state.COURSE_VIEW;

        } else {
            view.printCourseStudents(currentCourse);
            while (true) {
                view.printMessage("Input a number matching a student or type " + (currentCourse.getClassList().size() + 1) + " to exit.");
                int selection3 = pseudoScanner();

                if (selection3 <= currentCourse.getClassList().size() && selection3 > 0) {
                    currentStudent = currentCourse.getClassList().get(selection3 - 1);

                    currentState = state.ASSIGNING_GRADE;
                    return;

                } else if (selection3 == (currentCourse.getClassList().size() + 1)) {
                    view.printMessage("Exiting...");
                    currentState = state.COURSE_VIEW;
                    return;

                } else {
                    view.printMessage("Not a valid option.");
                }
            }
        }
    }

    public void assigningGrade() throws InterruptedException {
        view.printMessage("Enter " + currentStudent.getFirstName() + "'s grade.");
        String grade = scanner.nextLine();
        currentStudent.addGrade(currentCourse, grade);
        view.printMessage(currentStudent.getFirstName() + "'s grade in " +
                currentCourse.getCourseName() + " was set to " + grade + ".");
        model.saveList();
        Thread.sleep(1500);

        if (currentCourse.getClassList().size() != 1) {
            currentState = state.GRADE_STUDENT;
        } else {
            currentState = state.COURSE_VIEW;
        }
    }

    public void students() {
        view.printMessage("Select one of the numbers below:");
        view.printMessage("1. Search for student 2. Add a new student 3. Back");

        int selection = pseudoScanner();

        switch (selection) {
            case 0 -> {
                view.printMessage("Select one of the numbers below:");
                view.printMessage("1. Search for student 2. Add a new student 3. Back");
            }
            case 1 -> {
                if (!model.studentList.isEmpty()) {
                    currentState = state.SEARCHING_STUDENT;
                } else {
                    view.printMessage("No students exist.");
                    currentState = state.STUDENTS;
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
        view.printMessage("Select one of the numbers below:");
        view.printMessage("1. Select teacher 2. Add a new teacher 3. Back");

        int selection = pseudoScanner();

        switch (selection) {
            case 1 -> {
                if (!model.teacherList.isEmpty()) {
                    currentState = state.SELECT_TEACHER;
                } else {
                    view.printMessage("No teachers exist.");
                }
            }
            case 2 -> currentState = state.ADDING_TEACHER;
            case 3 -> {
                view.printMessage("Exiting to main menu.");
                currentState = state.MAIN_MENU;
            }
            default -> view.printMessage("Not a valid option");
        }
    }

    public void editStudent(Student currentStudent) {
        view.printMessage("1. Edit first name 2. Edit last name 3. Edit email address 4. Back");
        int selection = pseudoScanner();
        switch (selection) {
            case 0 -> view.printMessage("1. Edit first name 2. Edit last name 3. Edit email address 4. Back");
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
                String email = currentStudent.getEmailAddress();
                view.printMessage("Enter a new email.");
                currentStudent.setEmailAddress(scannerString.nextLine());
                view.printMessage(email + " changed their email to: " + currentStudent.getEmailAddress());
                model.saveList();
            }
            case 4 -> currentState = state.STUDENT_VIEW;
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
                    currentState = state.STUDENTS;
                    return;
                }
                view.printMessage("Not a valid number, try again");
                view.printAllStudents(model);
                view.printMessage("Input a number matching a student or " + (model.studentList.size() + 1) + " to exit: ");
            }

            currentStudent = model.studentList.get(selection - 1);
            currentState = state.STUDENT_VIEW;

        } else {
            view.printMessage("No students exist.");
            currentState = state.MAIN_MENU;
        }

    }

    public void studentView() {
        view.printStudentInfo(currentStudent);
        view.printMessage("1. Edit student info 2. Show students courses 3. Remove student 4. Back");
        int selection = pseudoScanner();
        switch (selection) {
            case 1 -> currentState = state.EDITING_STUDENT;

            case 2 -> {
                view.studentFindCourses(currentStudent, model);
                view.printMessage("Press enter to exit.");
                scanner.nextLine();
            }
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
                        model.removeStudentFromAllCourses(currentStudent);
                        model.removeStudent(currentStudent);
                        view.printMessage("Student removed.");
                        model.saveList();
                        if (model.studentList.isEmpty()) {
                            currentState = state.STUDENTS;
                        } else {
                            currentState = state.SEARCHING_STUDENT;
                        }
                    }
                    case 2 -> currentState = state.SEARCHING_STUDENT;

                    default -> view.printMessage("Not a valid option");

                }
            }
            case 4 ->
                currentState = state.SEARCHING_STUDENT;



        }
    }

    public void createStudent() throws IOException {
        view.printMessage("Please enter the students first name: ");
        view.printMessage("Type 1 to exit. ");
        String fName = scannerString.nextLine().trim();
        if (fName.equalsIgnoreCase("1")) {
            view.printMessage("Exiting...");
            currentState = state.STUDENTS;
            return;
        }
        view.printMessage("Please enter the students last name: ");
        view.printMessage("Type 1 to exit. ");
        String lName = scannerString.nextLine().trim();
        if (lName.equalsIgnoreCase("1")) {
            view.printMessage("Exiting...");
            currentState = state.STUDENTS;
            return;
        }
        String email = fName + "." + lName + "@skola.se";
        int studentID = 10000 + (int) (Math.random() * 9000);
        view.printMessage("Student created.");
        currentStudent = Student.createStudent(fName, lName, email, studentID);
        model.addStudent(currentStudent);
        model.saveList();
        currentState = state.STUDENTS;
    }

    public void createTeacher() {
        view.printMessage("Please enter the teachers first name: ");
        view.printMessage("Type 1 to exit. ");
        String fName = scannerString.nextLine().trim();
        if (fName.equalsIgnoreCase("1")) {
            view.printMessage("Exiting...");
            currentState = state.TEACHERS;
            return;
        }
        view.printMessage("Please enter the teachers last name: ");
        String lName = scannerString.nextLine().trim();
        if (lName.equalsIgnoreCase("1")) {
            view.printMessage("Exiting...");
            currentState = state.TEACHERS;
            return;
        }
        String email = fName + "." + lName + "@skola.se";
        view.printMessage("Teacher added.");
        currentTeacher = Teacher.createTeacher(fName, lName, email, false);
        model.addTeacher(currentTeacher);
        model.saveList();
        currentState = state.TEACHER_VIEW;
    }

    public void teacherView() {
        view.printTeacherInfo(currentTeacher);
        view.printMessage("1. Edit teacher info 2. Assign a course 3. Remove teacher 4. Back");
        int selection = pseudoScanner();
        switch (selection) {
            case 0 -> {
                view.printTeacherInfo(currentTeacher);
                view.printMessage("1. Edit teacher info 2. Assign a course 3. Remove teacher 4. Back");
            }
            case 1 -> currentState = state.EDITING_TEACHER;

            case 2 -> currentState = state.ASSIGN_TEACHER_TO_COURSE;

            case 3 -> currentState = state.REMOVE_TEACHER;

            case 4 -> {
                view.printMessage("Exiting...");
                currentState = state.SELECT_TEACHER;
            }


        }
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
                    currentState = state.TEACHERS;
                    return;
                }
                view.printMessage("Not a valid number, try again");
                view.printAllTeachers(model);
                view.printMessage("Input a number matching a teacher or " + (model.teacherList.size() + 1) + " to exit: ");
            }

            currentTeacher = model.teacherList.get(selection - 1);
            currentState = state.TEACHER_VIEW;

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
                view.printMessage(currentTeacher.firstName + " was assigned to " + currentCourse.getCourseName() + ".");
                model.saveList();
                break;
            } else {
                view.printMessage("Not a valid number");
            }
        }
        currentState = state.TEACHER_VIEW;
    }

    public void removeTeacher() {
        if (!currentTeacher.isAdmin()) {
            view.printMessage("Are you sure?");
            view.printMessage("1 for Yes, 2 for No");
            int selection = pseudoScanner();
            switch (selection) {
                case 0 -> {
                    view.printMessage("Are you sure?");
                    view.printMessage("1 for Yes, 2 for No");
                }
                case 1 -> {
                    view.printMessage("Teacher " + currentTeacher.getFirstName() + " removed.");
                    model.removeTeacher(currentTeacher);
                    model.saveList();
                    if (model.teacherList == null) {
                        currentState = state.MAIN_MENU;
                    } else {
                        currentState = state.SELECT_TEACHER;
                    }
                }
                case 2 -> {
                    view.printMessage("Did not remove teacher");
                    currentState = state.TEACHER_VIEW;
                }
                default -> view.printMessage("Not a valid option");

            }
        } else {
            view.printMessage("Cannot remove admin.");
            currentState = state.TEACHER_VIEW;
        }
    }

    public void assignCourseToTeacher() throws InterruptedException {
        if (!model.teacherList.isEmpty()) {
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
                    Thread.sleep(1000);
                    break;
                } else if (selection == model.teacherList.size() + 1) {
                    currentState = state.COURSE_VIEW;
                    return;
                } else {
                    view.printMessage("Not a valid number");
                }
            }
            currentState = state.COURSE_VIEW;
        } else {
            view.printMessage("No teachers exist.");
            Thread.sleep(500);
            currentState = state.COURSE_VIEW;
        }
    }

    public void editTeacher(Teacher currentTeacher) {
        view.printMessage("1. Edit first name 2. Edit last name 3. Edit email address 4. Edit password 5. Back");
        int selection = pseudoScanner();
        switch (selection) {
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
                if (!currentTeacher.isAdmin()) {
                    String email = currentTeacher.getEmailAddress();
                    view.printMessage("Enter a new email.");
                    currentTeacher.setEmailAddress(scannerString.nextLine());
                    view.printMessage(email + " changed their email to: " + currentTeacher.getEmailAddress());
                    model.saveList();
                } else {
                    view.printMessage("Cant change the admins email address.");
                }
            }
            case 4 -> {
                if (!currentTeacher.isAdmin()) {
                    String email = currentTeacher.getEmailAddress();
                    String password;
                    while (true) {
                        view.printMessage("Enter a new password.");
                        password = scannerString.nextLine();
                        if (password.equalsIgnoreCase("1")) {
                            currentState = state.TEACHER_VIEW;
                            return;
                        } else if (password.length() < 4) {
                            view.printMessage("Password too short, minium 4 characters. Try again");
                        } else if (password.length() > 15) {
                            view.printMessage("Password too long, maximum 15 characters. Try again");
                        } else {
                            break;
                        }
                    }
                    currentTeacher.setPassword(password);
                    view.printMessage(email + " changed their password to: " + ("*".repeat(password.length())));
                    model.saveList();
                } else {
                    view.printMessage("Cant change the admins password.");
                }
            }
            case 5 -> currentState = state.SELECT_TEACHER;

        }
    }
}
