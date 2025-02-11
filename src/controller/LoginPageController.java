package controller;

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
import javafx.stage.Screen;
import javafx.stage.Stage;

public class LoginPageController implements Initializable {

    @FXML
    private TextField userF;
    @FXML
    private PasswordField passF;
    @FXML
    private Button loginBtn;
    @FXML
    private Label registerBtn;

    private dbConnector db;
    @FXML
    private Label registerBtn11;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = new dbConnector();  // Initialize the database connection
    }

    @FXML
    private void LoginOnClickHandler(ActionEvent event) {
        String username = userF.getText().trim();
        String password = passF.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Username and password cannot be empty.");
            return;
        }

        try {
            String role = authenticateUser(username, password);
            if (role != null) {
                showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome back!");

                // Switch scenes based on the user's role
                if (role.equalsIgnoreCase("admin") || role.equalsIgnoreCase("Admin")) {
                    switchScene(getClass(), event, "/fxml/AdminDashboard.fxml");
                }else if ((role.equalsIgnoreCase("user")) || role.equalsIgnoreCase("User")) {
                    switchScene(getClass(), event, "/fxml/UserDashboard.fxml");
                }else {
                    showAlert(Alert.AlertType.WARNING, "Role Error", "Unrecognized role: " + role);
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password.");
            }
        } catch (SQLException ex) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred: " + ex.getMessage());
        } catch (Exception ex) {
            showAlert(Alert.AlertType.ERROR, "Scene Error", "Failed to load dashboard: " + ex.getMessage());
        }
    }

    // Method to authenticate user and retrieve role
    private String authenticateUser(String username, String password) throws SQLException {
        String sql = "SELECT u_role FROM user WHERE u_username = ? AND u_password = ?";
        try (PreparedStatement pst = db.getConnection().prepareStatement(sql)) {
            pst.setString(1, username);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getString("u_role");  // Return user role if authentication is successful
            }
        }
        return null;
    }

    // Utility method to show alert messages
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Method to switch scenes
    public void switchScene(Class<?> clazz, Event evt, String targetFXML) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(targetFXML));  // Use getClass() to load resource
        Stage stage = (Stage) ((Node) evt.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.show();
        setCenterAlignment(stage);
    }


    // Method to center the stage on the screen
    public void setCenterAlignment(Stage stage) {
        javafx.geometry.Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double centerX = (screenBounds.getWidth() - stage.getWidth()) / 2;
        double centerY = (screenBounds.getHeight() - stage.getHeight()) / 2;
        stage.setX(centerX);
        stage.setY(centerY);
    }

    @FXML
    private void RegisterBtnOnClickHandler(MouseEvent event) {
    }
}
