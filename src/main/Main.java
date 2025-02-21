package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import style.CustomTitleBarController;

public class Main extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.initStyle(StageStyle.UNDECORATED);
        loadScene("/fxml/LoginPage.fxml"); // Load login page on startup
        
        primaryStage.show();
    }

    public void loadScene(String fxmlPath) {
        try {
            // Load main content
            FXMLLoader contentLoader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent content = contentLoader.load();

            // Load custom title bar
            FXMLLoader titleLoader = new FXMLLoader(getClass().getResource("/style/CustomTitle.fxml"));
            Parent titleBar = titleLoader.load();
            CustomTitleBarController controller = titleLoader.getController();
            controller.setStage(primaryStage);

            // Combine title bar and content
            VBox layout = new VBox(titleBar, content);
            Scene scene = new Scene(layout);

            // Add CSS for styling
            scene.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());

            primaryStage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
