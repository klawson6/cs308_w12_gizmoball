package controller;

import ModelPackage.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import view.ResizableCanvas;

import java.io.File;
import java.net.URL;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class Controller implements Initializable, Observer {

    private Stage stage;
    private IModel model;

    @FXML private ResizableCanvas canvas;
    @FXML private VBox rootPane;
  //  @FXML private Label speed;
    @FXML private Button startButton, stopButton, tickButton, buildButton, runButton, saveButton, loadButton, quitButton;
    @FXML private ToolBar commonToolBar, runToolBar, buildToolBar;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initialiseToolBars();

        initialiseCanvas();
    }

    private void initialiseToolBars(){
        buildToolBar.setManaged(false);
        buildToolBar.setVisible(false);
        addButtonListeners();
    }

    private void initialiseCanvas(){
        canvas.widthProperty().bind(rootPane.widthProperty());
        canvas.heightProperty().bind(rootPane.heightProperty().subtract(buildToolBar.heightProperty()).subtract(commonToolBar.heightProperty()));
        canvas.heightProperty().bind(rootPane.heightProperty().subtract(runToolBar.heightProperty()).subtract(commonToolBar.heightProperty()));
        canvas.widthProperty().addListener(observable -> canvas.draw());
        canvas.heightProperty().addListener(observable -> canvas.draw());

        //TODO Remove in final release
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent ->
                System.out.println(mouseEvent.getX() + " " + mouseEvent.getY()));
    }

    private void addButtonListeners(){
        startButton.setOnAction(event -> startTimeline());
        loadButton.setOnAction(event -> loadFile());
        runButton.setOnAction(event -> toggleModes());
        buildButton.setOnAction(event -> toggleModes());
        buildButton.setOnAction(event -> stage.setScene(buildScene));
        saveButton.setOnAction(event -> save());
        loadButton.setOnAction(event -> load());
    }

    private void save(){
        model.getBall().stopBall();
        new SaveFile(stage).save(model);
        model.getBall().startBall();
    }

    private void load(){
        model.getBall().stopBall();
        model.changeModel(new LoadFile(stage).run());
        model.getBall().startBall();
        quitButton.setOnAction(event -> System.exit(0));
    }

    private void toggleModes(){
        if(runToolBar.isManaged()){
            runToolBar.setManaged(false);
            runToolBar.setVisible(false);
            buildToolBar.setManaged(true);
            buildToolBar.setVisible(true);
        }else{
            runToolBar.setManaged(true);
            runToolBar.setVisible(true);
            buildToolBar.setManaged(false);
            buildToolBar.setVisible(false);
        }
    }

    public void setStage(Stage s){
        stage = s;
    }


    public void setModel(IModel m){
        model = m;
    }

    public void setHandlers(){
        rootPane.addEventHandler(KeyEvent.KEY_PRESSED, new KeyBindingHandler(model));
    }


    @Override
    public void update(Observable o, Object arg) {
        Model model = (Model) o;
        HashSet<Gizmo> gizmos = model.getGizmoList();
        Ball ball = model.getBall();

        canvas.setGizmoList(gizmos);
        canvas.setBall(ball);
        canvas.draw();
    }

    private void loadFile(){
        File file = new FileChooser().showOpenDialog(stage);
        if(file != null){
            LoadFile r = new LoadFile(file);
            model = r.run();
            model.addObserver(this);
            update((Observable) model, null);
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please pick a file.");
            alert.showAndWait();
        }

    }

    private void startTimeline(){
        setHandlers();
        Timeline redraw = new Timeline(new KeyFrame(Duration.millis(50), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                model.moveBall();
            }
        }));
        redraw.setCycleCount(Timeline.INDEFINITE);
        redraw.play();
    }
}
