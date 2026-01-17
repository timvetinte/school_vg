
void main() throws IOException, InterruptedException {
Model model = new Model();
View view = new View(model);
Controller controller = new Controller(model, view);
controller.run();


/*
DEFAULT LOGIN
admin.login@skola.se
"Password"

Default password for new Person
"Password"

write "createstudents" at login to create 20 students that will be
assigned to the java course
 */
}
