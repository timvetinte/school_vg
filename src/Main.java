
void main() throws IOException {
Model model = new Model();
View view = new View(model);
Controller controller = new Controller(model, view);
controller.run();
}
