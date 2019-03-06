package controller;

import ModelPackage.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.ResizableCanvas;

import java.net.URL;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class RunViewController implements Initializable {

    private Stage stage;
    private Scene buildScene;
    private BuildViewController buildController;
    private IModel model;



    @FXML private ResizableCanvas canvas;
    @FXML private VBox rootPane;
  //  @FXML private Label speed;
    @FXML private Button quitButton;
    @FXML private Button buildButton;
    @FXML private Button saveButton;
    @FXML private Button loadButton;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addButtonListeners();

        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent ->
                System.out.println(mouseEvent.getX() + " " + mouseEvent.getY()));

    }

    private void addButtonListeners(){
        quitButton.setOnAction(event -> System.exit(0));
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

    public void update(Observable o){

        Model model = (Model) o;
        HashSet<Gizmo> gizmos = model.getGizmoList();
        Ball ball = model.getBall();

        canvas.setGizmoList(gizmos);
        canvas.setBall(ball);
        canvas.draw();

        this.model = model;
    }
}
