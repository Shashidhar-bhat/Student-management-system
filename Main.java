import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Main extends Application {
    private TableView<Student> table = new TableView<>();
    private TextField nameField = new TextField();
    private TextField ageField = new TextField();
    private TextField gradeField = new TextField();
    private Database database = new Database();

    @Override
    public void start(Stage primaryStage) {
        // Table columns
        TableColumn<Student, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> cellData.getValue().getId());
        
        TableColumn<Student, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getName());

        TableColumn<Student, Integer> ageColumn = new TableColumn<>("Age");
        ageColumn.setCellValueFactory(cellData -> cellData.getValue().getAge());

        TableColumn<Student, String> gradeColumn = new TableColumn<>("Grade");
        gradeColumn.setCellValueFactory(cellData -> cellData.getValue().getGrade());

        table.getColumns().add(idColumn);
        table.getColumns().add(nameColumn);
        table.getColumns().add(ageColumn);
        table.getColumns().add(gradeColumn);

        // Data binding
        nameField.setPromptText("Name");
        ageField.setPromptText("Age");
        gradeField.setPromptText("Grade");

        Button addButton = new Button("Add Student");
        addButton.setOnAction(e -> addStudent());

        Button updateButton = new Button("Update Student");
        updateButton.setOnAction(e -> updateStudent());

        Button deleteButton = new Button("Delete Student");
        deleteButton.setOnAction(e -> deleteStudent());

        // Layout
        VBox vbox = new VBox();
        vbox.getChildren().addAll(nameField, ageField, gradeField, addButton, updateButton, deleteButton, table);
        Scene scene = new Scene(vbox, 600, 400);

        primaryStage.setTitle("Student Management System");
        primaryStage.setScene(scene);
        primaryStage.show();

        loadStudents();
    }

    private void addStudent() {
        String name = nameField.getText();
        int age = Integer.parseInt(ageField.getText());
        String grade = gradeField.getText();
        database.insertStudent(name, age, grade);
        loadStudents();
    }

    private void updateStudent() {
        Student selectedStudent = table.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            String grade = gradeField.getText();
            database.updateStudent(selectedStudent.getId(), name, age, grade);
            loadStudents();
        }
    }

    private void deleteStudent() {
        Student selectedStudent = table.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            database.deleteStudent(selectedStudent.getId());
            loadStudents();
        }
    }

    private void loadStudents() {
        ResultSet resultSet = database.getStudents();
        table.getItems().clear();

        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String grade = resultSet.getString("grade");
                table.getItems().add(new Student(id, name, age, grade));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
