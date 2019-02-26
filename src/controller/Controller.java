package controller;

import ModelPackage.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import view.ResizableCanvas;

import javax.swing.*;
import java.io.File;
import java.net.URL;
import java.sql.Time;
import java.util.*;

public class Controller implements Initializable, Observer {

    private Stage stage;
    private IModel model;
    private Timeline timeline;
    private KeyBindingHandler keyBindHandler;

    private boolean isBuilding = false;

    @FXML private ResizableCanvas canvas;
    @FXML private VBox rootPane;
  //  @FXML private Label speed;
    @FXML private Button startButton, stopButton, tickButton, buildButton, runButton, saveButton, loadButton, quitButton;
    @FXML private ToolBar commonToolBar, runToolBar, buildToolBar;
    @FXML ChoiceBox<String> gizmoChoiceBox;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initialiseToolBars();
        initialiseCanvas();
        initialiseTimeline();
    }

    private void initialiseToolBars(){
        buildToolBar.setManaged(false);
        buildToolBar.setVisible(false);
        populateChoiceBox();
        addButtonListeners();
    }

    private void populateChoiceBox(){
        ObservableList<String> gizmoTypes = FXCollections.observableArrayList("Absorber", "Circle", "Square","Triangle","Left Flipper", "Right Flipper");
        gizmoChoiceBox.setItems(gizmoTypes);
        gizmoChoiceBox.setValue(gizmoTypes.get(0));
    }

    private void initialiseCanvas(){
        canvas.widthProperty().bind(rootPane.widthProperty());
        canvas.heightProperty().bind(rootPane.heightProperty().subtract(buildToolBar.heightProperty()).subtract(commonToolBar.heightProperty()));
        canvas.heightProperty().bind(rootPane.heightProperty().subtract(runToolBar.heightProperty()).subtract(commonToolBar.heightProperty()));
        canvas.widthProperty().addListener(observable -> canvas.draw(isBuilding));
        canvas.heightProperty().addListener(observable -> canvas.draw(isBuilding));

        //TODO Remove in final release
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent ->
                System.out.println(mouseEvent.getX() + " " + mouseEvent.getY()));
    }

    private void initialiseTimeline(){
        timeline = new Timeline(new KeyFrame(Duration.millis(50), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                model.moveBall();
            }
        }));
    }

    private void addButtonListeners(){
        startButton.setOnAction(event -> startTimeline());
        stopButton.setOnAction(event -> stopTimeline());
        tickButton.setOnAction(event -> tick());
        saveButton.setOnAction(event -> saveFile());
        loadButton.setOnAction(event -> loadFile());
        runButton.setOnAction(event -> toggleModes());
        buildButton.setOnAction(event -> toggleModes());
        quitButton.setOnAction(event -> System.exit(0));
    }

    private void toggleModes(){
        if(runToolBar.isManaged()){ // From run to build mode
            isBuilding = true;
            runToolBar.setManaged(false);
            runToolBar.setVisible(false);
            buildToolBar.setManaged(true);
            buildToolBar.setVisible(true);
            canvas.draw(isBuilding);
        }else{ // From build to run mode
            isBuilding = false;
            runToolBar.setManaged(true);
            runToolBar.setVisible(true);
            buildToolBar.setManaged(false);
            buildToolBar.setVisible(false);
            canvas.draw(isBuilding);
        }
    }

    public void setStage(Stage s){
        stage = s;
    }


    public void setModel(IModel m){
        model = m;
    }

    public void setHandlers(){
        keyBindHandler = new KeyBindingHandler(model);
        //rootPane.addEventHandler(KeyEvent.ANY,keyBindHandler);
        rootPane.setOnKeyReleased(keyBindHandler);
    }


    @Override
    public void update(Observable o, Object arg) {
        Model model = (Model) o;
        Set<IGizmo> gizmos = model.getGizmoList();
        List<IBall> balls = model.getBalls();

        setHandlers();

        canvas.setGizmoList(gizmos);

        canvas.setBalls(balls);

        canvas.draw(isBuilding);
    }

    private void loadFile(){
        File file = new FileChooser().showOpenDialog(stage);
        if(file != null){
            LoadFile r = new LoadFile(file);
            model = r.run();
            model.addObserver(this);
            update((Observable) model, isBuilding);
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please pick a file.");
            alert.showAndWait();
        }

    }

    private void saveFile(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text doc(*.txt)", "*.txt"));
        fileChooser.setInitialFileName("Gizmoball.txt");
        fileChooser.setTitle("Save Game");
        File file = fileChooser.showSaveDialog(stage);
        if(file != null) {
            if(model != null){
                model.save(file);
            }else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Empty game! Nothing to save!");
                alert.showAndWait();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please pick a place to save the game at.");
            alert.showAndWait();
        }
    }

    private void startTimeline(){
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void stopTimeline(){
        timeline.stop();
    }

    private void tick(){
        timeline.stop();
        timeline.setCycleCount(1);
        timeline.play();
    }
}
