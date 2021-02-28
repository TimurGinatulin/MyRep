package sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import sample.server.Server;
import java.io.IOException;

public class MainSceneServerController {
    @FXML
    private TextArea CmdArea;

    @FXML
    private TextArea UsersArea;

    @FXML
    private TextField CmdField;

    @FXML
    private Button CmdButtonSend;

    @FXML
    private Circle StatusIndicator;

    @FXML
    void initialize() {
        new Thread(() -> {
            try {
                new Server(this);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }).start();
        CmdField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().toString().equals("ENTER")) sendInCMDArea();
        });
        CmdButtonSend.setOnAction(actionEvent -> sendInCMDArea());
    }

    public void sendInCMDArea() {
        CmdField.clear();
        CmdArea.setText(CmdArea.getText() + CmdField.getText() + "\n");
    }

    public void sendInCMDArea(String s) {
        CmdArea.setText(CmdArea.getText() + s + "\n");
    }

    public void changeStatusIndicator(boolean isAreLife) {
        if (isAreLife) StatusIndicator.setFill(Color.LIGHTGREEN);
        else StatusIndicator.setFill(Color.LIGHTPINK);
    }

    public void setUsersArea(String s) {
        UsersArea.setText(s);
    }
}
