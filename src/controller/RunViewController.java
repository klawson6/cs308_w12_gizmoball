package controller;

import ModelPackage.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import view.ResizableCanvas;

import java.net.URL;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class RunViewController implements Initializable, Observer {

    private Stage stage;
    private Scene buildScene;
    private BuildViewController buildController;
    private IModel model;

    @FXML private ResizableCanvas canvas;
    @FXML private VBox rootPane;
  //  @FXML private Label speed;
    @FXML private Button startButton, stopButton, tickButton, buildButton, saveButton, loadButton, quitButton ;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addButtonListeners();

        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent ->
                System.out.println(mouseEvent.getX() + " " + mouseEvent.getY()));

    }

    private void addButtonListeners(){
        startButton.setOnAction(event -> startTimeline());
        loadButton.setOnAction(event -> loadFile());
        buildButton.setOnAction(event -> stage.setScene(buildScene));
        quitButton.setOnAction(event -> System.exit(0));
    }

    public void setStage(Stage s){
        stage = s;
    }

    public void setBuildScene(Scene buildScene) {
        this.buildScene = buildScene;
    }

    public void setBuildController(BuildViewController buildController) {
        this.buildController = buildController;
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
        LoadFile r = new LoadFile();
        model = r.run();
        model.addObserver(this);
        update((Observable) model, null);
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
