package prospectus.utilities;

import javafx.animation.TranslateTransition;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import controller.CustomTitleBarController;
import java.io.IOException;
import javafx.animation.FadeTransition;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;


/**
 * Utility class for JavaFX scene management and animations.
 */
public class utilities {

    // Show alert dialog and center it on screen
    public static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Load and apply CSS
        alert.getDialogPane().getStylesheets().add(utilities.class.getResource("/prospectus/utilities/alert.css").toExternalForm());
        alert.getDialogPane().getStyleClass().add("custom-alert");

        alert.show();

        // Center the alert on screen
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        alertStage.setX((screenBounds.getWidth() - alertStage.getWidth()) / 2);
        alertStage.setY((screenBounds.getHeight() - alertStage.getHeight()) / 2);
    }
    

    // Switch scene and add custom title bar
    public static void switchScene(Class clazz, Event evt, String targetFXML) {
        try {
            FXMLLoader contentLoader = new FXMLLoader(clazz.getResource(targetFXML));
            Parent content = contentLoader.load();

            FXMLLoader titleLoader = new FXMLLoader(clazz.getResource("/fxml/CustomTitle.fxml"));
            Parent titleBar = titleLoader.load();

            Stage stage = (Stage) ((Node) evt.getSource()).getScene().getWindow();

            CustomTitleBarController controller = titleLoader.getController();
            if (controller != null) {
                controller.setStage(stage);
            }

            VBox layout = new VBox(titleBar, content);
            Scene newScene = new Scene(layout);
            newScene.getStylesheets().add(clazz.getResource("/css/style.css").toExternalForm());

            stage.setScene(newScene);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException ex) {
            
             ex.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Scene Error", "Failed to load scene: " + ex.getMessage());
        }
    }
   
    

    // Center window on screen
    public static void setCenterAlignment(Stage stage) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
    }

    // Smoothly transition only the content pane, keeping the scene static
    public static void animatePaneTransition(Class<?> clazz, Event event, String fxmlPath, boolean leftToRight) {
        try {
            FXMLLoader contentLoader = new FXMLLoader(clazz.getResource(fxmlPath));
            Parent newContent = contentLoader.load();

            Node eventSource = (Node) event.getSource();
            Scene currentScene = eventSource.getScene();
            Parent root = currentScene.getRoot();

            if (!(root instanceof VBox)) {
                return;
            }

            VBox layout = (VBox) root;
            if (layout.getChildren().size() < 2) {
                return;
            }

            Node currentContent = layout.getChildren().remove(1);
            layout.getChildren().add(newContent);

            double sceneWidth = currentScene.getWidth();
            double startX = leftToRight ? -sceneWidth : sceneWidth;

            newContent.setTranslateX(startX);

            TranslateTransition slideOut = new TranslateTransition(Duration.millis(500), currentContent);
            slideOut.setToX(-startX);

            TranslateTransition slideIn = new TranslateTransition(Duration.millis(500), newContent);
            slideIn.setFromX(startX);
            slideIn.setToX(0);

            slideOut.setOnFinished(e -> layout.getChildren().remove(currentContent));

            slideOut.play();
            slideIn.play();
        } catch (IOException ex) {
             ex.printStackTrace();
             System.out.println("check ");
            showAlert(Alert.AlertType.ERROR, "Scene Error", "Failed to load content: " + ex.getMessage());
        }
    }

    // Wrapper method for Right-to-Left transition
    public static void animatePaneTransitionRightToLeft(Class<?> clazz, Event event, String fxmlPath) {
        animatePaneTransition(clazz, event, fxmlPath, false);
    }

    // Wrapper method for Left-to-Right transition
    public static void animatePaneTransitionLeftToRight(Class<?> clazz, Event event, String fxmlPath) {
        animatePaneTransition(clazz, event, fxmlPath, true);
    }
    public static void loadFXMLWithFade(Pane rootPane, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(utilities.class.getResource(fxmlPath));
            AnchorPane overlay = loader.load();  

            // Add overlay to the root pane
            rootPane.getChildren().add(overlay);            

            // Apply Fade-In Transition
            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), overlay);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.play();

        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }
}
