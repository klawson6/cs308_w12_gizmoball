package controller;

import ModelPackage.Gizmo;
import ModelPackage.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.ResizableCanvas;

import java.net.URL;
import java.util.HashSet;
import java.util.Observable;
import java.util.ResourceBundle;

public class RunViewController implements Initializable {

    private Stage stage;
    private Scene buildScene;
    private BuildViewController buildController;

    @FXML private ResizableCanvas canvas;
    @FXML private VBox rootPane;

    @FXML private Button quitButton;
    @FXML private Button buildButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        canvas.widthProperty().bind(rootPane.widthProperty());
        canvas.heightProperty().bind(rootPane.heightProperty());

        quitButton.setOnAction(event -> System.exit(0));

        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent ->
                System.out.println(mouseEvent.getX() + " " + mouseEvent.getY()));

        buildButton.setOnAction(event -> stage.setScene(buildScene));

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
        Model model = (Model) o;
        HashSet<Gizmo> gizmos = model.getGizmoList();

        canvas.setGizmoList(gizmos);
        canvas.draw();

    }
}
