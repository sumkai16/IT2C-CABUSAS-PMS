package prospectus.utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.TextArea;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import java.util.function.Consumer;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import main.dbConnector;
import prospectus.models.Course;

public class adminUtilities {

    private dbConnector db; 
    private TextArea logTextArea; 
    private AnchorPane rootPane;
    public adminUtilities(dbConnector db, TextArea logTextArea) {
        this.db = db;
        this.logTextArea = logTextArea;
    }

    public <T> void setupDoubleClickListener(TableView<T> tableView, Consumer<T> action) {
        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Check for double-click
                T selectedItem = tableView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    action.accept(selectedItem); // Call the action with the selected item
                }
            }
        });
    }

    
    
}