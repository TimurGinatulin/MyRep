package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import sample.network.ChatNetWorking;
import java.io.IOException;

public class RegController {

    private static boolean closeStage = true;

    @FXML
    private TextField LNField;

    @FXML
    private TextField FNField;

    @FXML
    private Button regButton;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField countryField;

    @FXML
    private RadioButton RadioMale;

    @FXML
    private Circle connectionInd;

    @FXML
    void initialize() {
        new Thread(this::isConnect).start();
        regButton.setOnAction(actionEvent -> {
            if (RadioMale.isSelected())
                ChatNetWorking.send("/Reg " + FNField.getText().trim() + " " + LNField.getText().trim() + " "
                        + loginField.getText().trim() + " " + passwordField.getText().trim() + " "
                        + countryField.getText().trim() + " male");
            else
                ChatNetWorking.send("/Reg " + FNField.getText().trim() + " " + LNField.getText().trim() + " "
                        + loginField.getText().trim() + " " + passwordField.getText().trim() + " "
                        + countryField.getText().trim() + " female");
            try {
                Stage close = (Stage) regButton.getScene().getWindow();
                close.close();
                closeStage = false;
                Parent root = FXMLLoader.load(getClass().getResource("/sample/view/Auth.fxml"));
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
