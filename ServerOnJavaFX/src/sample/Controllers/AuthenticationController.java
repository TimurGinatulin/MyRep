package sample.Controllers;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.database.DataBaseHandler;
import sample.database.Admin;

public class AuthenticationController {

    @FXML
    private Button loginButton;
    @FXML
    private TextField loginField;
    @FXML
    private TextField passwordField;

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        passwordField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().toString().equals("ENTER")) sendToLogin();
        });
        loginButton.setOnAction(actionEvent -> sendToLogin());
    }

    private void sendToLogin() {
        String loginText = loginField.getText().trim();
        String password = passwordField.getText().trim();
        if (!loginText.equals("") && !password.equals("")) {
            loginAdmin(loginText, password);
        }
    }

    private void loginAdmin(String loginText, String password) {
        DataBaseHandler dbHandler = new DataBaseHandler();
        Admin admin = new Admin(loginText, password);
        ResultSet res = dbHandler.getAdmin(admin);
        int count = 0;
        try {
            while (res.next())
                count++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (count != 0) {
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/sample/View/MainSceneServer.fxml"));
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            Stage stage = new Stage();
            if (root != null) {
                stage.setScene(new Scene(root));
                stage.setTitle("MyChat Server Edition. v0.1");
                stage.setResizable(false);
                loginButton.getScene().getWindow().hide();
                stage.showAndWait();
            }
        } else {
            Shake userLog = new Shake(loginField);
            Shake userPas = new Shake(passwordField);
            userLog.playAnimation();
            userPas.playAnimation();
        }
    }
}