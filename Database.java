import java.sql.*;

public class Database {
    private Connection connection;

    // Establish the connection to the database
    public Connection connect() {
        try {
            connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/student_management", "root", "password");  // Change the password if needed
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Insert a student record
    public void insertStudent(String name, int age, String grade) {
        try {
            String query = "INSERT INTO students (name, age, grade) VALUES (?, ?, ?)";
            PreparedStatement statement = connect().prepareStatement(query);
            statement.setString(1, name);
            statement.setInt(2, age);
            statement.setString(3, grade);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update a student record
    public void updateStudent(int id, String name, int age, String grade) {
        try {
            String query = "UPDATE students SET name = ?, age = ?, grade = ? WHERE id = ?";
            PreparedStatement statement = connect().prepareStatement(query);
            statement.setString(1, name);
            statement.setInt(2, age);
            statement.setString(3, grade);
            statement.setInt(4, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete a student record
    public void deleteStudent(int id) {
        try {
            String query = "DELETE FROM students WHERE id = ?";
            PreparedStatement statement = connect().prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get all students
    public ResultSet getStudents() {
        try {
            String query = "SELECT * FROM students";
            Statement statement = connect().createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
