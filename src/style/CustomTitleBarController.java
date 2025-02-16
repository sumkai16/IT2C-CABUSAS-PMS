package style;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class CustomTitleBarController {
    @FXML
    private ImageView closeBtn, minimizeBtn, maximizeBtn;
    @FXML
    private HBox titleBar;

    private Stage stage;
    private double xOffset = 0;
    private double yOffset = 0;
    private boolean isMaximized = false; // Track window state

    @FXML
    public void initialize() {
        if (titleBar != null) {
            titleBar.setOnMousePressed(this::handleMousePressed);
            titleBar.setOnMouseDragged(this::handleMouseDragged);
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void MaximizeHandler(MouseEvent event) {
        if (stage != null) {
            if (isMaximized) {
                stage.setMaximized(false); // Exit maximized mode
                stage.setWidth(900);  // Restore original width
                stage.setHeight(600); // Restore original height
                stage.centerOnScreen(); // Center the window
            } else {
                stage.setMaximized(true);  // Maximize the window
            }
            isMaximized = !isMaximized;
        }
    }

    @FXML
    private void MinimizeHandler(MouseEvent event) {
        if (stage != null) {
            stage.setIconified(true);
        }
    }

    @FXML
    private void CloseHandler(MouseEvent event) {
        if (stage != null) {
            stage.close();
        }
    }

    private void handleMousePressed(MouseEvent event) {
        if (stage != null && !stage.isMaximized()) {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        }
    }

    private void handleMouseDragged(MouseEvent event) {
        if (stage != null && !stage.isMaximized()) {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        }
    }
}
