package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import view.ResizableCanvas;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML private ResizableCanvas canvas;
    @FXML private BorderPane rootBorderPane;

    @FXML private Button quitButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        canvas.widthProperty().bind(rootBorderPane.widthProperty());
        canvas.heightProperty().bind(rootBorderPane.heightProperty());

        quitButton.setOnAction(event -> System.exit(0));

    }
}
