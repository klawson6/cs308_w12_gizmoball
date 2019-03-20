package view;

import com.sun.javafx.stage.WindowCloseRequestHandler;
import controller.Controller;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class GUIDriver extends Application{

    final static int CANVAS_SIZE = 700;

    private Controller controller;
    private Scene runScene;
    private Stage primaryStage;


    @Override
    public void start(Stage pStage){
        this.primaryStage = pStage;
        setUpScenes();
        primaryStage.setTitle("Gizmoball");
        primaryStage.setScene(runScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void setUpScenes(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("View.fxml"));
            Parent root = loader.load();
            runScene = new Scene(root);
            runScene.getStylesheets().add(getClass().getResource("viewCSS.css").toExternalForm());
            controller = loader.getController();
            controller.setStage(primaryStage);
            //controller.setCanvasSize(CANVAS_SIZE);
            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    controller.onClose(event);
                }
            });

        }catch (IOException e){
            e.printStackTrace();

            System.err.println("Error when loading the view! Please check the FXML file doesn't have any errors!");
            System.exit(-1);
        }


    }




    public static void main(String[] args) {

        launch(args);


    }
}
