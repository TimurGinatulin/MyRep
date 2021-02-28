package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.network.ChatNetWorking;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/Auth.fxml"));
        primaryStage.setTitle("MyChat Client v. 0.1");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        new Thread(()-> new ChatNetWorking("localhost",2021)).start();
    }

    public static void main(String[] args) {
        launch(args);

//TODO List active user, second connection in server,menu
    }
}
