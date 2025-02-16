/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import style.CustomTitleBarController;

/**
 * FXML Controller class
 *
 * @author axcee
 */
public class UserDashboardController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    // Method to switch scenes while keeping the custom title bar
    public void switchScene(Class<?> clazz, Event evt, String targetFXML) throws Exception {
        FXMLLoader contentLoader = new FXMLLoader(clazz.getResource(targetFXML));
        Parent content = contentLoader.load();

        FXMLLoader titleLoader = new FXMLLoader(clazz.getResource("/style/CustomTitle.fxml"));
        Parent titleBar = titleLoader.load();
        CustomTitleBarController controller = titleLoader.getController();

        Stage stage = (Stage) ((Node) evt.getSource()).getScene().getWindow();
        controller.setStage(stage);  // Pass the stage to title bar

        VBox layout = new VBox(titleBar, content);  // Combine title bar + content
        Scene newScene = new Scene(layout, 900, 600);  // Set default size

        // Apply styling
        newScene.getStylesheets().add(clazz.getResource("/style/style.css").toExternalForm());

        stage.setScene(newScene);
        stage.centerOnScreen();
        stage.show();
    }

    
}
