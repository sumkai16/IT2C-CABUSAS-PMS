package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import style.CustomTitleBarController;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AdminDashboard.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Load custom title bar
            FXMLLoader titleLoader = new FXMLLoader(getClass().getResource("/style/CustomTitle.fxml"));
            Parent titleBar = titleLoader.load();
            CustomTitleBarController controller = titleLoader.getController();
            controller.setStage(primaryStage);

            // Add CSS for styling
            scene.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());

            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
