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
import java.text.DecimalFormat;
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
  //  @FXML private Button buildButton;
   @FXML private Label speed;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       c canvas.widthProperty().bind(rootPane.widthProperty());
        canvas.heightProperty().bind(rootPane.heightProperty());

        quitButton.setOnAction(event -> System.exit(0));


      //  buildButton.setOnAction(event -> stage.setScene(buildScene));

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
        DecimalFormat df = new DecimalFormat("####0.0");

        speed.setText("Ball speed: " + df.format(ball.getSpeed()) + " L/s");
        canvas.setGizmoList(gizmos);
        canvas.setBall(ball);
        canvas.draw();

    }
}
