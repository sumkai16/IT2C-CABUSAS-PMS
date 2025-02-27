package controller;

import java.io.IOException;
import main.dbConnector;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import style.CustomTitleBarController;
import utils.hoveer;

public class RegisterPageController implements Initializable {

    @FXML
    private TextField firstnameF;
    @FXML
    private TextField lastnameF;
    @FXML
    private TextField emailF;
    @FXML
    private TextField contactF;
    @FXML
    private TextField userFF;
    @FXML
    private PasswordField pwF;
    @FXML
    private TextField middleF;

    private dbConnector db;
    @FXML
    private Label loginlbl;
    @FXML
    private Label registerBtn11;
    @FXML
    private Label registerBtn1;
    @FXML
    private Button register;
    @FXML
    private Button login;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = new dbConnector();  // Initialize the database connection
        
        hoveer hv = new hoveer();
        hv.btnAuth(register);
        hv.btnSwitch(login);
    }

    @FXML
    private void RegisterOnClickHandler(MouseEvent event) {
        String firstname = firstnameF.getText().trim();
        String lastname = lastnameF.getText().trim();
        String email = emailF.getText().trim();
        String contact = contactF.getText().trim();
        String username = userFF.getText().trim();
        String password = pwF.getText().trim();
        String middle = middleF.getText().trim();

        if (!validateInputs(firstname, lastname, email, contact, username, password)) {
            return;
        }

        try {
            if (isDuplicate("u_email", email)) {
                showAlert(Alert.AlertType.ERROR, "Duplicate Email", "This email is already registered.");
                return;
            }

            if (isDuplicate("u_username", username)) {
                showAlert(Alert.AlertType.ERROR, "Duplicate Username", "This username is already taken.");
                return;
            }

            String sql = "INSERT INTO user (u_fname, u_mname, u_lname, u_email, u_contact_number, u_username, u_password, u_role) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pst = db.getConnection().prepareStatement(sql)) {
                pst.setString(1, firstname);
                pst.setString(2, middle);
                pst.setString(3, lastname);
                pst.setString(4, email);
                pst.setString(5, contact);
                pst.setString(6, username);
                pst.setString(7, password);
                pst.setString(8, "user");  // Default role set as "user". Change this as needed.
                pst.executeUpdate();
                showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "You have successfully registered!");
                clearFields();
            }
        } catch (SQLException ex) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred: " + ex.getMessage());
        }
    }

    
   


    private boolean validateInputs(String firstname, String lastname, String email, String contact, String username, String password) {
        if (firstname.isEmpty() || lastname.isEmpty() || email.isEmpty() || contact.isEmpty() || username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "All fields except middle name are required.");
            return false;
        }

        if (password.length() < 8) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Password must be at least 8 characters long.");
            return false;
        }

        if (!contact.matches("\\d{10,12}")) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Contact number must be 10 to 12 digits long.");
            return false;
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Please enter a valid email address.");
            return false;
        }

        return true;
    }

    private boolean isDuplicate(String column, String value) throws SQLException {
        String sql = "SELECT 1 FROM user WHERE " + column + " = ?";
        try (PreparedStatement pst = db.getConnection().prepareStatement(sql)) {
            pst.setString(1, value);
            ResultSet rs = pst.executeQuery();
            return rs.next();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        firstnameF.clear();
        lastnameF.clear();
        emailF.clear();
        contactF.clear();
        userFF.clear();
        pwF.clear();
        middleF.clear();
    }
    // Method to switch scenes while keeping the custom title bar
    public void switchScene(Class<?> clazz, Event evt, String targetFXML) throws Exception {
        FXMLLoader contentLoader = new FXMLLoader(clazz.getResource(targetFXML));
        Parent content = contentLoader.load();

        FXMLLoader titleLoader = new FXMLLoader(clazz.getResource("/style/CustomTitle.fxml"));
        Parent titleBar = titleLoader.load();
        CustomTitleBarController controller = titleLoader.getController();

        Stage stage = (Stage) ((Node) evt.getSource()).getScene().getWindow();
        controller.setStage(stage);  // Pass the stage to title bar

        VBox layout = new VBox(titleBar, content);  // Combine title bar + content
        Scene newScene = new Scene(layout, 900, 600);  // Set default size

        // Apply styling
        newScene.getStylesheets().add(clazz.getResource("/style/style.css").toExternalForm());

        stage.setScene(newScene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    private void LoginHandler(ActionEvent event) {
        try {
            switchScene(getClass(), event, "/fxml/LoginPage.fxml");  // Ensure title bar remains
        } catch (Exception ex) {
            showAlert(Alert.AlertType.ERROR, "Scene Error", "Failed to load login page: " + ex.getMessage());
        }
    }

}
