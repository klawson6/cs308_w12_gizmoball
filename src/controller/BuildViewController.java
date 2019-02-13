package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.ResizableCanvas;

import java.net.URL;
import java.util.ResourceBundle;

public class BuildViewController implements Initializable {

    private Stage stage;
    private Scene runScene;
    private RunViewController runController;

    @FXML
    private ResizableCanvas canvas;
    @FXML private VBox rootPane;

    @FXML private Button quitButton;
    @FXML private Button runButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        canvas.widthProperty().bind(rootPane.widthProperty());
        canvas.heightProperty().bind(rootPane.heightProperty());

        quitButton.setOnAction(event -> System.exit(0));

        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent ->
                System.out.println(mouseEvent.getX() + " " + mouseEvent.getY()));

        runButton.setOnAction(event -> stage.setScene(runScene));

    }

    public void setStage(Stage s){
        stage = s;
    }

    public void setRunScene(Scene runScene){
        this.runScene = runScene;
    }

    public void setRunController(RunViewController runController) {
        this.runController = runController;
    }
}
