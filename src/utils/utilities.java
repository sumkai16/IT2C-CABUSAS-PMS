/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author axcee
 */
public class utilities {
    public static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void switchScene(Class<?> clazz, Event evt, String targetFXML) throws Exception {
        FXMLLoader contentLoader = new FXMLLoader(clazz.getResource(targetFXML));
        Parent content = contentLoader.load();

        FXMLLoader titleLoader = new FXMLLoader(clazz.getResource("/style/CustomTitle.fxml"));
        Parent titleBar = titleLoader.load();

        Stage stage = (Stage) ((Node) evt.getSource()).getScene().getWindow();
        VBox layout = new VBox(titleBar, content);
        Scene newScene = new Scene(layout);

        newScene.getStylesheets().add(clazz.getResource("/style/style.css").toExternalForm());

        stage.setScene(newScene);
        stage.centerOnScreen();
        stage.show();
    }

    public static void setCenterAlignment(Stage stage) {
        javafx.geometry.Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double centerX = (screenBounds.getWidth() - stage.getWidth()) / 2;
        double centerY = (screenBounds.getHeight() - stage.getHeight()) / 2;
        stage.setX(centerX);
        stage.setY(centerY);
    }
}

