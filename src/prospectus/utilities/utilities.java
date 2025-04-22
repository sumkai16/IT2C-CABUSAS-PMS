package prospectus.utilities;

import java.io.IOException;
import java.util.function.Consumer;
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
import javafx.animation.FadeTransition;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javax.swing.text.Utilities;

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
    
    // Switch scene without custom title bar
    public static void switchScene(Class clazz, Event evt, String targetFXML) {
        try {
            FXMLLoader contentLoader = new FXMLLoader(clazz.getResource(targetFXML));
            Parent content = contentLoader.load();

            Stage stage = (Stage) ((Node) evt.getSource()).getScene().getWindow();
            Scene newScene = new Scene(content);
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
    public static void animatePaneTransition(Class<?> clazz, Event event, String fxmlPath) {
        try {
            FXMLLoader contentLoader = new FXMLLoader(clazz.getResource(fxmlPath));
            Parent newContent = contentLoader.load();

            Node eventSource = (Node) event.getSource();
            if (eventSource == null) {
                System.out.println("Error: Event source is null!");
                return;
            }

            Scene currentScene = eventSource.getScene();
            if (currentScene == null) {
                System.out.println("Error: Current scene is null!");
                return;
            }

            Parent root = currentScene.getRoot();
            if (!(root instanceof Pane)) {  
                System.out.println("Error: Root is not a Pane!");
                return;
            }

            Pane layout = (Pane) root;
            if (layout.getChildren().isEmpty()) {
                System.out.println("Error: Pane does not have any children!");
                return;
            }

            Node currentContent = layout.getChildren().get(0); 
            layout.getChildren().add(newContent); 

            // Set initial opacity to 0 (hidden)
            newContent.setOpacity(0);

            // Fade Out for current content
            FadeTransition fadeOut = new FadeTransition(Duration.millis(500), currentContent);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);

            // Fade In for new content
            FadeTransition fadeIn = new FadeTransition(Duration.millis(500), newContent);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);

            fadeOut.setOnFinished(e -> layout.getChildren().remove(currentContent)); 

            fadeOut.play();
            fadeIn.play();

        } catch (IOException ex) {
            ex.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Scene Error", "Failed to load content: " + ex.getMessage());
        }
    }



    public static void animatePaneFadeTransition(Class<?> clazz, Event event, String fxmlPath) {
        animatePaneTransition(clazz, event, fxmlPath); 
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
    public static void loadFXMLWithFadeEdit(Pane rootPane, String fxmlPath, Consumer<Object> controllerInitializer) {
        try {
            FXMLLoader loader = new FXMLLoader(utilities.class.getResource(fxmlPath));
            AnchorPane overlay = loader.load(); 
            
            // Initialize controller if needed
            if (controllerInitializer != null) {
                controllerInitializer.accept(loader.getController());
            }

            // Set initial opacity before adding to rootPane
            overlay.setOpacity(0);
            rootPane.getChildren().add(overlay); 

            // Apply fade-in transition
            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), overlay);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.play();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Scene Error", "Failed to load page: " + e.getMessage());
        }
    }
    @FunctionalInterface
    public interface FXMLControllerConsumer{
        void accept(Object controller);
    }
    public static void loadFXMLWithFadeView(Pane overlayPane, String fxmlPath, FXMLControllerConsumer controllerConsumer) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(Utilities.class.getResource(fxmlPath));
            Parent newRoot = loader.load();

            // Get the controller
            Object controller = loader.getController();

            // Apply the controller consumer if provided
            if (controllerConsumer != null) {
                controllerConsumer.accept(controller);
            }

            // Create a fade transition for the overlayPane
            FadeTransition fadeOut = new FadeTransition(Duration.millis(300), overlayPane);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setOnFinished(event -> {
                // Set the new scene after fade out
                overlayPane.getChildren().setAll(newRoot);
                
                // Create a fade-in transition for the overlayPane
                FadeTransition fadeIn = new FadeTransition(Duration.millis(300), overlayPane);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeIn.play();
            });

            // Start the fade-out transition
            fadeOut.play();
        } catch (Exception ex) {
            ex.printStackTrace(); // Log the exception
            // Optionally, show an alert for the error
        }
    }
    public static void loadPageWithFade(BorderPane rootPane, String fxmlPath, Consumer<Object> controllerInitializer) {
        try {
            FXMLLoader loader = new FXMLLoader(utilities.class.getResource(fxmlPath));
            Parent root = loader.load();

            // Initialize controller if needed
            if (controllerInitializer != null) {
                controllerInitializer.accept(loader.getController());
            }

            // Apply fade transition
            root.setOpacity(0);
            rootPane.setCenter(root); 

            FadeTransition fade = new FadeTransition(Duration.millis(300), root);
            fade.setFromValue(0);
            fade.setToValue(1);
            fade.play();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Scene Error", "Failed to load page: " + e.getMessage());
        }
    }
    public static void closeOverlay(Pane overlay) {
        if (overlay == null) return; 

        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), overlay);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished(event -> overlay.setVisible(false));
        fadeOut.play();
    }
    //enter feature
    public static void setupEnterKeyHandler(TextField textField, Runnable action) {
        textField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                action.run(); 
            }
        });
    }


}
