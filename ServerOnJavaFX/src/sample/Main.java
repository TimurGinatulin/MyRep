package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Parent root = FXMLLoader.load(getClass().getResource("View/Authentication.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("View/MainSceneServer.fxml"));
        primaryStage.setTitle("MyChat Server Edition. v0.1");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    //TODO Writing in log txt crashing when all users off
    public static void main(String[] args) {
        launch(args);

    }
}
