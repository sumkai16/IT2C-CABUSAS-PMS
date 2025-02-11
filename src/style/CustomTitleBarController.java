/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package style;

import javafx.fxml.*;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 *
 * @author axcee
 */
public class CustomTitleBarController {
     @FXML
    private Button closeBtn, minimizeBtn, maximizeBtn;

    private Stage stage;

    @FXML
    public void initialize() {
        closeBtn.setOnAction(e -> stage.close());
        minimizeBtn.setOnAction(e -> stage.setIconified(true));
        maximizeBtn.setOnAction(e -> {
            if (stage.isFullScreen()) {
                stage.setFullScreen(false);
            } else {
                stage.setFullScreen(true);
            }
        });
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
