package controller;

import ModelPackage.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.ResizableCanvas;

import java.net.URL;
import java.util.HashSet;
import java.util.Observable;
import java.util.ResourceBundle;

public class RunViewController implements Initializable {

    private IModel model;

    private Stage stage;
    private Scene buildScene, runScene;
    private BuildViewController buildController;

    @FXML private ResizableCanvas canvas;
    @FXML private VBox rootPane;
  //  @FXML private Label speed;
    @FXML private Button quitButton;
    @FXML private Button buildButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addButtonListeners();
        addKeyListeners();

    }

    private void addButtonListeners(){
        quitButton.setOnAction(event -> System.exit(0));
        buildButton.setOnAction(event -> stage.setScene(buildScene));
    }

    @FXML
    void keyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case A:
                model.toggleLeftFlippers();
                break;
            case D:
                model.toggleRightFlippers();
                break;
            default:
                break;
        }
    }

    @FXML
    void keyReleased(KeyEvent event) {
        switch (event.getCode()) {
            case A:
                model.toggleLeftFlippers();
                break;
            case D:
                model.toggleRightFlippers();
                break;
            default:
                break;
        }
    }

    private void addKeyListeners(){
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent ->
                System.out.println(mouseEvent.getX() + " " + mouseEvent.getY()));
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


    public void update(Observable o){

        this.model = (Model) o;
        HashSet<Gizmo> gizmos = model.getGizmoList();
        Ball ball = model.getBall();

        canvas.setGizmoList(gizmos);
        canvas.setBall(ball);
        canvas.draw();

    }

    public void setRunScene(Scene runScene) {
        this.runScene = runScene;
    }
}
