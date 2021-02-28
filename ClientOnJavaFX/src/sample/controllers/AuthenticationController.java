package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import sample.network.ChatNetWorking;

import java.io.IOException;

public class AuthenticationController {
    private static boolean closeStage = true;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField loginField;

    @FXML
    private Button loginButton;

    @FXML
    private Button regButtonAuth;
    @FXML
    private Circle connectionInd;

    @FXML
    void initialize() {
        new Thread(this::isConnect).start();
        loginButton.setOnAction(actionEvent -> {
            ChatNetWorking.send(loginField.getText() + " " + passwordField.getText());

            try {
                if (ChatNetWorking.receive().equals("connected")) {
                    Stage close = (Stage) loginButton.getScene().getWindow();
                    close.close();
                    closeStage = false;
                    Parent root = FXMLLoader.load(getClass().getResource("/sample/view/Chat.fxml"));
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                }
            } catch (IOException exception) {
                ChatNetWorking.isLive = false;
            }
        });
        regButtonAuth.setOnAction(actionEvent -> {
            try {
                regButtonAuth.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("/sample/view/reg.fxml"));
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });
    }

    private void isConnect() {
        while (closeStage) {
            if (ChatNetWorking.isLive) connectionInd.setFill(Color.LIGHTGREEN);
            else connectionInd.setFill(Color.RED);
            try {
                Thread.sleep(5_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
