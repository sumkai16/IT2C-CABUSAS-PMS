/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prospectus.auth.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import main.dbConnector;
import prospectus.models.UserSession;
import prospectus.utilities.logger;
import prospectus.utilities.utilities;

/**
 * FXML Controller class
 *
 * @author axcee
 */
public class ForgotPasswordController implements Initializable {

    @FXML
    private TextField emailField;
    @FXML
    private TextField recoveryPhraseField;
    @FXML
    private AnchorPane overlayPane;
    private dbConnector db = new dbConnector();  
    @FXML
    private Button btnVerify;
    @FXML
    private Button btnBackToLogin;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
     private void closeOverlay() {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), overlayPane);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished(event -> overlayPane.setVisible(false));
        fadeOut.play();
    }
     private void proceedToPasswordReset() {
          utilities.loadFXMLWithFade(overlayPane, "/prospectus/auth/fxml/PasswordReset.fxml");
    }
    @FXML
    private void verifyPhraseHandler(MouseEvent event) {
         String email = emailField.getText().trim();
    String enteredPhrase = recoveryPhraseField.getText().trim();

    if (email.isEmpty() || enteredPhrase.isEmpty()) {
          utilities.showAlert(Alert.AlertType.ERROR, "Verify Failed", "Fields are empty, Please fill!");
        return;
    }

    String query = "SELECT u_username FROM user WHERE LOWER(u_email) = LOWER(?) AND recovery_phrase = ?";

    try (Connection conn = db.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {

        pstmt.setString(1, email);
        pstmt.setString(2, enteredPhrase);

        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            String username = rs.getString("u_username");
            System.out.println("✅ Verification successful for user: " + username);
            proceedToPasswordReset();
            logger.addLog(username, "Forgot Password", "Verification successful for user: .: " + username);
        } else {
             utilities.showAlert(Alert.AlertType.ERROR, "Verify Failed", "Recovery phrase or email doesn't exist.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    }

    @FXML
    private void backToLoginHandler(MouseEvent event) {
        closeOverlay();    
    }

    
}
