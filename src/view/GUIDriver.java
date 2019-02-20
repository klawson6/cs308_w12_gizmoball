package view;

import controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIDriver extends Application implements Observer {

    final static int WINDOW_SIZE = 700;

    private Controller controller;
    private Scene runScene;
    private Stage primaryStage;

    private static IModel model;


    @Override
    public void start(Stage pStage){
        this.primaryStage = pStage;

        LoadFile r = new LoadFile(primaryStage);
        model = r.run();

        setUpScenes();
        model.addObserver(this);
        Timeline redraw = new Timeline(new KeyFrame(Duration.millis(50), new EventHandler<ActionEvent>() {

         @Override
            public void handle(ActionEvent event) {
                model.moveBall();
            }
        }));
        redraw.setCycleCount(Timeline.INDEFINITE);
        redraw.play();

        primaryStage.setTitle("Gizmoball");
        primaryStage.setScene(runScene);
        primaryStage.show();


    }

    private void setUpScenes(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("View.fxml"));
            Parent root = loader.load();
            runScene = new Scene(root, WINDOW_SIZE, WINDOW_SIZE);
            controller = loader.getController();
            controller.setStage(primaryStage);

        }catch (IOException e){
            System.err.println("Error when loading the view! Please check the FXML file doesn't have any errors!");
            System.exit(-1);
        }
    }


    public static void main(String[] args) {

        launch(args);


    }


}
