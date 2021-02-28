package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import sample.network.ChatNetWorking;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatController {
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
        new Thread(this::isConnect).start();
        new Thread(this::getChatArea).start();
        new Thread(() -> {
            try {
                upDateUserList();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        CmdButtonSend.setOnAction(actionEvent -> {
            try {
                if (CmdField.getText().isBlank())
                    return;
                ChatNetWorking.send(CmdField.getText());
                CmdField.setText("");
            } catch (RuntimeException e) {
//TODO
            }
        });
    }

    private void isConnect() {
        while (true) {
            if (ChatNetWorking.isLive) StatusIndicator.setFill(Color.LIGHTGREEN);
            else StatusIndicator.setFill(Color.RED);
            try {
                Thread.sleep(5_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void getChatArea() {
        while (true) {
            try {
                StringBuilder ss = new StringBuilder(CmdArea.getText());
                ss.append(ChatNetWorking.receive())
                        .append("\n");
                CmdArea.setText(ss.toString());
                ss.setLength(0);
            } catch (RuntimeException e) {
                //TODO
            }
        }
    }

    private void upDateUserList() throws InterruptedException {
        Socket userList;
        DataInputStream in;
        try {
            userList = new Socket("localhost", 2022);
            in = new DataInputStream(userList.getInputStream());
        } catch (IOException exception) {
            throw new RuntimeException("sww Error in updating user list...");
        }
        while (true) {
            try {
                UsersArea.setText(in.readUTF());
                Thread.sleep(1_000);
            } catch (IOException exception) {
                UsersArea.setText("Error in updating user list...");
                Thread.sleep(15_000);
            }
        }
    }
}
