package prospectus.auth.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import main.dbConnector; // Ensure this class manages your database connection
import prospectus.models.UserSession;

public class SecretRecoveryPhraseController implements Initializable {

    @FXML
    private AnchorPane overlayPane;
    @FXML
    private Label first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth, eleventh, twelve;
    
    private dbConnector db = new dbConnector();  

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(this::loadRecoveryPhrase);  // Run on the UI thread
    }

    private void loadRecoveryPhrase() {
    String query = "SELECT recovery_phrase FROM user WHERE LOWER(u_username) = LOWER(?) AND recovery_phrase IS NOT NULL AND recovery_phrase != ''";

    try (Connection conn = db.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {

        String currentUser = UserSession.getUsername(); // Get username from session
        if (currentUser == null || currentUser.isEmpty()) {
            System.out.println("⚠ No user is logged in.");
            return;
        }

        pstmt.setString(1, currentUser);
        System.out.println("Fetching recovery phrase for user: " + currentUser);

        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            String recoveryPhrase = rs.getString("recovery_phrase");
            System.out.println("✅ Recovery Phrase Retrieved: " + recoveryPhrase);
            Platform.runLater(() -> setRecoveryPhrase(recoveryPhrase));  // Update UI safely
        } else {
            System.out.println("⚠ No recovery phrase found for user: " + currentUser);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    private void setRecoveryPhrase(String phrase) {
        String[] words = phrase.split(" ");

        if (words.length >= 12) { 
            first.setText("1. "+words[0]);
            second.setText("2. "+words[1]);
            third.setText("3. "+words[2]);
            fourth.setText("4. "+words[3]);
            fifth.setText("5. "+words[4]);
            sixth.setText("6. "+words[5]);
            seventh.setText("7. "+words[6]);
            eighth.setText("8. "+words[7]);
            ninth.setText("9. "+words[8]);
            tenth.setText("10. "+words[9]);
            eleventh.setText("11. "+words[10]);
            twelve.setText("12. "+words[11]);
        } else {
            System.out.println("Invalid recovery phrase format.");
        }
    }

    private void closeOverlay() {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), overlayPane);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished(event -> overlayPane.setVisible(false));
        fadeOut.play();
    }

    @FXML
    private void continueHandler(MouseEvent event) {
        closeOverlay();
    }

    
}
