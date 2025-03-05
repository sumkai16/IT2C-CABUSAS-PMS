package main;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import controller.CustomTitleBarController;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            primaryStage.initStyle(StageStyle.UNDECORATED);
            loadScene(primaryStage, "/fxml/LoginPage.fxml"); // Load login page on startup
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error initializing application: " + e.getMessage());
        }
    }
 
    public void loadScene(Stage stage, String fxmlPath) {
        try {
            // Validate main content path
            if (getClass().getResource(fxmlPath) == null) {
                throw new IOException("FXML file not found: " + fxmlPath);
            }

            FXMLLoader contentLoader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent content = contentLoader.load();

            // Validate custom title bar path
            String titleBarPath = "/fxml/CustomTitle.fxml";
            if (getClass().getResource(titleBarPath) == null) {
                throw new IOException("FXML file not found: " + titleBarPath);
            }

            FXMLLoader titleLoader = new FXMLLoader(getClass().getResource(titleBarPath));
            Parent titleBar = titleLoader.load();

            // Set stage in CustomTitleBarController
            CustomTitleBarController controller = titleLoader.getController();
            if (controller != null) {
                controller.setStage(stage);
                System.out.println("CustomTitleBarController successfully received the stage.");
            } else {
                System.err.println("Error: CustomTitleBarController is null.");
            }

            VBox layout = new VBox(titleBar, content);
            Scene scene = new Scene(layout);

            // Load CSS if exists
            String cssPath = "/css/style.css";
            if (getClass().getResource(cssPath) != null) {
                scene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());
            } else {
                System.err.println("Warning: CSS file not found at " + cssPath);
            }

            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load FXML: " + fxmlPath);
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
