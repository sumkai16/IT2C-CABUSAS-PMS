package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Screen;
import javafx.stage.Stage;
import main.dbConnector;
import models.User;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import style.CustomTitleBarController;

public class AdminDashboardController implements Initializable {

    @FXML
    private TableView<User> tableView;
    @FXML
    private TableColumn<User, Integer> idColumn;
    @FXML
    private TableColumn<User, String> firstNameColumn;
    @FXML
    private TableColumn<User, String> middleNameColumn;
    @FXML
    private TableColumn<User, String> lastNameColumn;
    @FXML
    private TableColumn<User, String> emailColumn;
    @FXML
    private TableColumn<User, String> userNameColumn;
    @FXML
    private TableColumn<User, String> passwordColumn;
    @FXML
    private TableColumn<User, String> roleColumn;
    @FXML
    private TableColumn<User, String> statusColumn;

    private ObservableList<User> userList;
    private dbConnector db;
    @FXML
    private Label admindashboard;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = new dbConnector();  // Initialize the database connection
        userList = FXCollections.observableArrayList();

        // Configure table columns to match User model properties
        idColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        middleNameColumn.setCellValueFactory(new PropertyValueFactory<>("middleName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));  // Automatically set to "Registered"

        // Load data from database
        loadDataFromDatabase();
    }

    private void loadDataFromDatabase() {
    String query = "SELECT u_id, u_fname, u_mname, u_lname, u_email, u_username, u_password, u_role FROM user";
    try {
        ResultSet rs = db.getData(query);
        if (rs == null) {
            System.out.println("ResultSet is null");
            return;
        }

        while (rs.next()) {
            System.out.println("User found: " + rs.getString("u_username"));  // Debugging output
            int id = rs.getInt("u_id");
            String firstName = rs.getString("u_fname");
            String middleName = rs.getString("u_mname");
            String lastName = rs.getString("u_lname");
            String email = rs.getString("u_email");
            String userName = rs.getString("u_username");
            String password = rs.getString("u_password");
            String role = rs.getString("u_role");
            String status = "Registered";

            userList.add(new User(id, firstName, middleName, lastName, email, userName, password, role, status));
        }

        tableView.setItems(userList);

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

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



    // Method to center the stage on the screen
    public void setCenterAlignment(Stage stage) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double centerX = (screenBounds.getWidth() - stage.getWidth()) / 2;
        double centerY = (screenBounds.getHeight() - stage.getHeight()) / 2;
        stage.setX(centerX);
        stage.setY(centerY);
    }
}
