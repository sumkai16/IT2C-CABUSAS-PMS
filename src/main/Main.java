package main;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) {
        try {
            primaryStage.initStyle(StageStyle.DECORATED); // Window with default title bar
            
            // ✅ Set the window title
            primaryStage.setTitle("Syllabus Concordia");

            
            String iconPath = "/prospectus/images/Syllabus Concordia Icon.png";
            if (getClass().getResource(iconPath) != null) {
                primaryStage.getIcons().add(new Image(getClass().getResourceAsStream(iconPath)));
            } else {
                System.err.println("Warning: Icon file not found at " + iconPath);
            }

            Parent root = loadFXML("/prospectus/auth/fxml/LoginPage.fxml"); // Load login page

            if (root != null) {
                makeStageDraggable(root, primaryStage); // Enable dragging
                Scene scene = new Scene(root);

                // Load CSS if exists
                String cssPath = "/css/style.css";
                if (getClass().getResource(cssPath) != null) {
                    scene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());
                } else {
                    System.err.println("Warning: CSS file not found at " + cssPath);
                }

                primaryStage.setScene(scene);
                primaryStage.show();
            } else {
                System.err.println("Error: Unable to load LoginPage.fxml.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error initializing application: " + e.getMessage());
        }
    }

    private Parent loadFXML(String fxmlPath) {
        try {
            if (getClass().getResource(fxmlPath) == null) {
                throw new IOException("FXML file not found: " + fxmlPath);
            }
            return FXMLLoader.load(getClass().getResource(fxmlPath));
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load FXML: " + fxmlPath);
            return null;
        }
    }

    private void makeStageDraggable(Parent root, Stage stage) {
        root.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        root.setOnMouseDragged((MouseEvent event) -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
