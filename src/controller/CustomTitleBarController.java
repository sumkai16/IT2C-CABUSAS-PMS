package controller;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

public class CustomTitleBarController {

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private ImageView maximizeBtn, minimizeBtn, closeBtn;

    @FXML
    private void MaximizeHandler(MouseEvent event) {
        if (stage == null) {
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        }
        stage.setMaximized(!stage.isMaximized());
    }

    @FXML
    private void MinimizeHandler(MouseEvent event) {
        if (stage == null) {
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        }
        stage.setIconified(true);
    }

    @FXML
    private void CloseHandler(MouseEvent event) {
        if (stage == null) {
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        }
        stage.close();
    }
}
