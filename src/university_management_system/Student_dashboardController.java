package university_management_system;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class Student_dashboardController implements Initializable {

    @FXML private TableView<Student> st_list;
    @FXML private TextField st_name, st_id, st_dept, st_sec, st_phn, st_advisor;
    @FXML private Button btn5, btn6, btn7, btn8;

    ObservableList<Student> studentList = FXCollections.observableArrayList();

    Connection conn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TableColumn<Student, String> colName = new TableColumn<>("Name");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Student, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Student, String> colDept = new TableColumn<>("Dept");
        colDept.setCellValueFactory(new PropertyValueFactory<>("dept"));

        TableColumn<Student, String> colSec = new TableColumn<>("Section");
        colSec.setCellValueFactory(new PropertyValueFactory<>("sec"));

        TableColumn<Student, String> colPhone = new TableColumn<>("Phone");
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));

        TableColumn<Student, String> colAdvisor = new TableColumn<>("Advisor");
        colAdvisor.setCellValueFactory(new PropertyValueFactory<>("advisor"));

        st_list.getColumns().addAll(colName, colId, colDept, colSec, colPhone, colAdvisor);

        conn = DatabaseConnection.getConnection();
        loadStudents();

        st_list.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                st_name.setText(newSel.getName());
                st_id.setText(newSel.getId());
                st_dept.setText(newSel.getDept());
                st_sec.setText(newSel.getSec());
                st_phn.setText(newSel.getPhone());
                st_advisor.setText(newSel.getAdvisor());
            }
        });
    }

    private void loadStudents() {
        studentList.clear();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM students");
            while (rs.next()) {
                studentList.add(new Student(
                    rs.getString("name"),
                    rs.getString("id"),
                    rs.getString("dept"),
                    rs.getString("sec"),
                    rs.getString("phone"),
                    rs.getString("advisor")
                ));
            }
            st_list.setItems(studentList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void add_student(ActionEvent event) {
        String name = st_name.getText();
        String id = st_id.getText();
        String dept = st_dept.getText();
        String sec = st_sec.getText();
        String phone = st_phn.getText();
        String advisor = st_advisor.getText();

        if (name.isEmpty() || id.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Missing Data", "Name and ID are required.");
            return;
        }

        try {
            PreparedStatement pst = conn.prepareStatement(
                "INSERT INTO students (name, id, dept, sec, phone, advisor) VALUES (?, ?, ?, ?, ?, ?)"
            );
            pst.setString(1, name);
            pst.setString(2, id);
            pst.setString(3, dept);
            pst.setString(4, sec);
            pst.setString(5, phone);
            pst.setString(6, advisor);
            pst.executeUpdate();
            loadStudents();
            clearFields();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Student added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Could not add student.");
        }
    }

    @FXML
    private void update_student(ActionEvent event) {
        Student selected = st_list.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a student to update.");
            return;
        }

        try {
            PreparedStatement pst = conn.prepareStatement(
                "UPDATE students SET name=?, dept=?, sec=?, phone=?, advisor=? WHERE id=?"
            );
            pst.setString(1, st_name.getText());
            pst.setString(2, st_dept.getText());
            pst.setString(3, st_sec.getText());
            pst.setString(4, st_phn.getText());
            pst.setString(5, st_advisor.getText());
            pst.setString(6, st_id.getText());
            pst.executeUpdate();
            loadStudents();
            clearFields();
            showAlert(Alert.AlertType.INFORMATION, "Updated", "Student information updated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Could not update student.");
        }
    }

    @FXML
    private void delete_student(ActionEvent event) {
        Student selected = st_list.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a student to delete.");
            return;
        }

        try {
            PreparedStatement pst = conn.prepareStatement("DELETE FROM students WHERE id=?");
            pst.setString(1, selected.getId());
            pst.executeUpdate();
            loadStudents();
            clearFields();
            showAlert(Alert.AlertType.INFORMATION, "Deleted", "Student deleted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Could not delete student.");
        }
    }

    @FXML
    private void logout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load login page.");
        }
    }

    private void clearFields() {
        st_name.clear();
        st_id.clear();
        st_dept.clear();
        st_sec.clear();
        st_phn.clear();
        st_advisor.clear();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
