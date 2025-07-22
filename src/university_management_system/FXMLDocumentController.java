package university_management_system;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class FXMLDocumentController implements Initializable {

    @FXML
    private TextField studentid;
    @FXML
    private TextField password;
    @FXML
    private Button btn1; // Login button
    @FXML
    private Button btn2; // Create Account button

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialization if needed
    }

    @FXML
    private void login(ActionEvent event) {
        String id = studentid.getText();
        String pass = password.getText();

        if (id.equals("user") && pass.equals("22222222")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login Successful");
            alert.setHeaderText(null);
            alert.setContentText("Your login is successful.");
            alert.showAndWait();
            studentid.clear();
            password.clear();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("Invalid ID or Password!");
            alert.showAndWait();
        }
    }

    @FXML
    private void createaccount(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Create Account");
        alert.setHeaderText(null);
        alert.setContentText("Registration page not available right now.");
        alert.showAndWait();
    }
}
