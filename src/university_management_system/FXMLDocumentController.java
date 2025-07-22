package university_management_system;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLDocumentController implements Initializable {

    @FXML
    private TextField studentid;
    @FXML
    private TextField password;
    @FXML
    private Button btn1; // Login button

    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
    }

    @FXML
    private void login(ActionEvent event) {
        String id = studentid.getText();
        String pass = password.getText();

        if (id.equals("admin") && pass.equals("22222222")) {
            try {
              
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Login Successful");
                alert.setHeaderText(null);
                alert.setContentText("Your login is successful.");
                alert.showAndWait();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("Student_dashboard.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

                studentid.clear();
                password.clear();

            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Load Error");
                alert.setHeaderText(null);
                alert.setContentText("Unable to load the Student Dashboard.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("Invalid ID or Password!");
            alert.showAndWait();
        }
    }

    private void createaccount(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Create Account");
        alert.setHeaderText(null);
        alert.setContentText("Registration page not available right now.");
        alert.showAndWait();
    }
}
