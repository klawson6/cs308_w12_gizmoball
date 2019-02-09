package view;

import ModelPackage.LoadFile;
import ModelPackage.Model;
import controller.BuildViewController;
import controller.RunViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.RunnableScheduledFuture;

public class GUIDriver extends Application implements Observer {

    final static int WINDOW_SIZE = 700;

    private RunViewController runViewController;
    private BuildViewController buildViewController;
    private Scene runScene, buildScene;
    private Stage primaryStage;

    private Observable model;

    @Override
    public void start(Stage pStage){
        this.primaryStage = pStage;

        LoadFile r = new LoadFile();
        Model model = r.run();
        model.addObserver(this);

        setUpScenes();

        primaryStage.setTitle("Gizmoball");
        primaryStage.setScene(runScene);
        primaryStage.show();

        update(model, null);

    }

    private void setUpScenes(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("runView.fxml"));
            Parent root = loader.load();
            runScene = new Scene(root, WINDOW_SIZE, WINDOW_SIZE);
            runViewController = loader.getController();
            runViewController.setStage(primaryStage);

            loader = new FXMLLoader(getClass().getResource("buildView.fxml"));
            root = loader.load();
            buildScene = new Scene(root, WINDOW_SIZE, WINDOW_SIZE);
            buildViewController = loader.getController();
            buildViewController.setStage(primaryStage);

            runViewController.setBuildScene(buildScene);
            buildViewController.setRunScene(runScene);

        }catch (IOException e){
            System.err.println("Error when loading the views! Please check the FXML files doesn't have any errors!");
            System.exit(-1);
        }
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void update(Observable o, Object arg) {
        runViewController.update(o);
    }
}
