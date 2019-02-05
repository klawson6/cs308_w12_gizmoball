package view;

import controller.Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    final static int WINDOW_SIZE = 700;

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("runView.fxml"));
        Parent root = loader.load();

        Controller controller = loader.getController();

        primaryStage.setTitle("Gizmoball");
        primaryStage.setScene(new Scene(root, WINDOW_SIZE, WINDOW_SIZE));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
