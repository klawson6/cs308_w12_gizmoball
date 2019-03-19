package controller;

import ModelPackage.*;
import com.sun.javafx.fxml.builder.JavaFXSceneBuilder;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import view.ResizableCanvas;

import java.io.File;
import java.net.URL;
import java.util.*;

public class Controller implements Initializable, Observer {

    private Stage stage;
    private IModel model;
    private Timeline timeline;
    private EventHandler<KeyEvent> keyBindHandler;
    private EventHandler<MouseEvent> mouseHandler;

    private boolean isBuilding = false;

    @FXML private ResizableCanvas canvas;
    @FXML private VBox rootPane;
    @FXML private ImageView saveImg, loadImg, exitImg, buildImg, startImg, pauseImg, tickImg, runImg, addBallImg;
    @FXML private Button stopButton, tickButton, runButton, keyConnect,keyDisconnect,connect,disconnect;
    @FXML private ToolBar commonToolBar, runToolBar, buildToolBar;
    @FXML private Button rotateButton;
    @FXML private Button deleteButton;
    @FXML private Button addBallButton;
    @FXML public Button deleteBall;
    @FXML public Button moveGizmo;
    @FXML private ChoiceBox<GizmoType> gizmoChoiceBox;
    @FXML private Label infoLabel;
    @FXML private TextField canvasSizeTextField;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        model = new Model();
        model.addObserver(this);

        mouseHandler = new RunMouseEventHandler(model);
        keyBindHandler = new MagicKeyHandler(new KeyBindingHandler(model));
        rootPane.addEventHandler(KeyEvent.ANY,keyBindHandler);
        initialiseCanvas();
        initialiseToolBars();
        initialiseTimeline();
    }

    private void initialiseToolBars(){
        initialiseCommonToolBar();
        buildToolBar.setManaged(false);
        buildToolBar.setVisible(false);
        buildToolBar.maxWidthProperty().bind(canvas.widthProperty());
        runToolBar.maxWidthProperty().bind(canvas.widthProperty());
        commonToolBar.maxWidthProperty().bind(canvas.widthProperty());
        populateChoiceBox();
        addButtonListeners();
    }

    private void initialiseCommonToolBar(){
        canvasSizeTextField.setMinWidth(Region.USE_PREF_SIZE);
        canvasSizeTextField.setMaxWidth(Region.USE_PREF_SIZE);
        canvasSizeTextField.textProperty().addListener((ov, prevText, currText) -> {
            // Do this in a Platform.runLater because of Textfield has no padding at first time and so on
            Platform.runLater(() -> {
                Text text = new Text(currText);
                text.setFont(canvasSizeTextField.getFont()); // Set the same font, so the size is the same
                double width = text.getLayoutBounds().getWidth() // This big is the Text in the TextField
                        + canvasSizeTextField.getPadding().getLeft() + canvasSizeTextField.getPadding().getRight() // Add the padding of the TextField
                        + 2d; // Add some spacing
                canvasSizeTextField.setPrefWidth(width); // Set the width
                canvasSizeTextField.positionCaret(canvasSizeTextField.getCaretPosition()); // If you remove this line, it flashes a little bit
            });
        });
    }

    private void populateChoiceBox(){
        //ObservableList<String> gizmoTypes = FXCollections.observableArrayList(getGizmoTypeStringArray());
        ObservableList<GizmoType> gizmoTypes = FXCollections.observableArrayList(GizmoType.values());
        gizmoChoiceBox.setItems(gizmoTypes);
        gizmoChoiceBox.setValue(gizmoTypes.get(0));
    }

    private static String[] getGizmoTypeStringArray() {
        GizmoType[] array = GizmoType.values();
        String[] stringArray = new String[array.length];
        for(int i = 0; i < array.length; i++){
            stringArray[i] = array[i].toString();
        }
        return stringArray;
    }

    private void initialiseCanvas(){
        canvas.addEventHandler(MouseEvent.ANY, mouseHandler);
        canvas.addEventHandler(KeyEvent.ANY,keyBindHandler);
    }

    private void initialiseTimeline(){
        timeline = new Timeline(new KeyFrame(Duration.millis(10), event -> {
            model.moveBall();
            model.checkFlippers();
        }));
    }

    private void addButtonListeners(){
        //startButton.setOnAction(event -> startTimeline());
        //stopButton.setOnAction(event -> stopTimeline());
        //tickButton.setOnAction(event -> tick());
       // saveButton.setOnAction(event -> saveFile());
        //loadButton.setOnAction(event -> loadFile());
       // runButton.setOnAction(event -> toggleModes());
       // buildButton.setOnAction(event -> toggleModes());
        //quitButton.setOnAction(event -> System.exit(0));
        saveImg.setOnMouseClicked(event -> saveFile());
        loadImg.setOnMouseClicked(event -> loadFile());
        exitImg.setOnMouseClicked(event -> System.exit(0));
        buildImg.setOnMouseClicked(event -> toggleModes());
        startImg.setOnMouseClicked(event -> startTimeline());
        pauseImg.setOnMouseClicked(event -> stopTimeline());
        tickImg.setOnMouseClicked(event -> tick());
        runImg.setOnMouseClicked(event -> toggleModes());
        // Resize Canvas
        canvasSizeTextField.setOnKeyPressed((event) -> {
            if(event.getCode() == KeyCode.ENTER) {
                setCanvasSize(Integer.valueOf(canvasSizeTextField.getText()));
            }
        });

        //Add Handler for Rotation

        rotateButton.setOnAction(event -> {
            canvas.removeEventHandler(MouseEvent.ANY,mouseHandler);
            mouseHandler = new RotationHandler(model, canvas, infoLabel);
            canvas.addEventHandler(MouseEvent.ANY, mouseHandler);
            canvas.requestFocus();
            rotateButton.requestFocus();
            setInfoLabel("Click on a Gizmo to Rotate it.");

        }

        );

        //Add Handler for Deleting Gizmos

        deleteButton.setOnAction(event -> {

            canvas.removeEventHandler(MouseEvent.ANY, mouseHandler);
            mouseHandler = new DeleteGizmoHandler(model, canvas);
            canvas.addEventHandler(MouseEvent.ANY, mouseHandler);
            canvas.requestFocus();
            deleteButton.requestFocus();
            setInfoLabel("Click on a Gizmo to delete it.");

        });
        //Add handler for adding keybindings
        keyConnect.setOnAction(event -> {

            canvas.removeEventHandler(MouseEvent.ANY, mouseHandler);
            mouseHandler = new AddKeyConnectionsHandler(canvas,model, infoLabel);
            canvas.addEventHandler(MouseEvent.ANY, mouseHandler);
            canvas.requestFocus();
            keyConnect.requestFocus();
            setInfoLabel("Click on a Gizmo to add a key binding.");

        });
        //Add handler for adding keybindings
        keyDisconnect.setOnAction(event -> {

            canvas.removeEventHandler(MouseEvent.ANY, mouseHandler);
            mouseHandler = new RemoveKeyConnectionsHandler(model,canvas, infoLabel);
            canvas.addEventHandler(MouseEvent.ANY, mouseHandler);
            canvas.requestFocus();
            keyDisconnect.requestFocus();
            setInfoLabel("Click on a Gizmo to remove the key binding.");

        });

        //Add handler for adding gizmo connections
        connect.setOnAction(event -> {

            canvas.removeEventHandler(MouseEvent.ANY, mouseHandler);
            mouseHandler = new AddGizmoConnectionsHandler(canvas,model, infoLabel);
            canvas.addEventHandler(MouseEvent.ANY, mouseHandler);
            canvas.requestFocus();
            infoLabel.setText("Please select two gizmos to connect.");
            connect.requestFocus();



        });

        //Add handler for removing gizmo connections
        disconnect.setOnAction(event -> {

            canvas.removeEventHandler(MouseEvent.ANY, mouseHandler);
            mouseHandler = new RemoveGizmoConnectionsHandler(canvas,model, infoLabel);
            canvas.addEventHandler(MouseEvent.ANY, mouseHandler);
            canvas.requestFocus();
            setInfoLabel("Please select two gizmos to disconnect.");
            disconnect.requestFocus();

        });

        deleteBall.setOnAction(event -> {

            canvas.removeEventHandler(MouseEvent.ANY, mouseHandler);
            mouseHandler = new DeleteBallHandler(model,canvas);
            canvas.addEventHandler(MouseEvent.ANY, mouseHandler);
            canvas.requestFocus();
            keyDisconnect.requestFocus();
            setInfoLabel("Click on the grid to delete a Ball.");

        });

        //Add Handler for Gizmos to be added

        gizmoChoiceBox.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                if(gizmoChoiceBox.isFocused()){
                    //Focused so use Handler
                        canvas.removeEventHandler(MouseEvent.ANY, mouseHandler);
                        mouseHandler = new PlaceGizmoHandler(model, canvas, gizmoChoiceBox,infoLabel);
                        canvas.addEventHandler(MouseEvent.ANY, mouseHandler);
                        setInfoLabel("Click on the grid to place a Gizmo.");
                    }
            }
        });

        addBallImg.setOnMouseClicked(event -> {
            canvas.removeEventHandler(MouseEvent.ANY, mouseHandler);
            mouseHandler = new AddBallHandler(model,canvas,infoLabel);
            canvas.addEventHandler(MouseEvent.ANY, mouseHandler);
            canvas.requestFocus();
            addBallButton.requestFocus();

        });

        moveGizmo.setOnAction(event -> {
            canvas.removeEventHandler(MouseEvent.ANY, mouseHandler);
            mouseHandler = new MoveGizmoHandler(model,canvas,infoLabel);
            canvas.addEventHandler(MouseEvent.ANY, mouseHandler);
            setInfoLabel("Select Gizmo to Move");
            canvas.requestFocus();
            addBallButton.requestFocus();
        });

    }


    public void setInfoLabel(String text){
       infoLabel.setText(text);
    }

    private void toggleModes(){
        if(runToolBar.isManaged()){ // From run to build mode

            //Stop if running
            // TODO Reset ball position
            model.reset();
            stopTimeline();

            isBuilding = true;
            runToolBar.setManaged(false);
            runToolBar.setVisible(false);
            buildToolBar.setManaged(true);
            buildToolBar.setVisible(true);
            //FIXME handler on root pane?
            rootPane.removeEventHandler(KeyEvent.ANY,keyBindHandler);
            canvas.removeEventHandler(MouseEvent.ANY,mouseHandler);

            //Default Handler will be place Gizmo's from selection
            mouseHandler = new PlaceGizmoHandler(model, canvas,gizmoChoiceBox, infoLabel);

            canvas.addEventHandler(MouseEvent.ANY, mouseHandler);
            //canvas.removeEventHandler(KeyEvent.ANY,keyBindHandler);
            canvas.draw(isBuilding);

        }else{ // From build to run mode
            isBuilding = false;
            runToolBar.setManaged(true);
            runToolBar.setVisible(true);
            buildToolBar.setManaged(false);
            buildToolBar.setVisible(false);


            canvas.removeEventHandler(MouseEvent.ANY, mouseHandler);

            mouseHandler = new RunMouseEventHandler(model);
            //keyBindHandler = new KeyBindingHandler(model);
            keyBindHandler = new MagicKeyHandler(new KeyBindingHandler(model));
            rootPane.addEventHandler(KeyEvent.ANY,keyBindHandler);
            rootPane.requestFocus();
            canvas.addEventHandler(MouseEvent.ANY, mouseHandler);

            canvas.draw(isBuilding);
            stage.sizeToScene();
        }

        addBallButton.setOnAction(event -> {
            canvas.removeEventHandler(MouseEvent.ANY, mouseHandler);
            mouseHandler = new AddBallHandler(model,canvas, infoLabel);
            canvas.addEventHandler(MouseEvent.ANY, mouseHandler);
            setInfoLabel("Click on the grid to add a Ball.");
        });

    }



    public void setStage(Stage s){
        stage = s;
    }


    public void setModel(IModel m){
        model = m;
    }

    public void setCanvasSize(int canvasSize) {
        if(canvasSize % 20 != 0){
            canvasSize = 20*(Math.round(canvasSize/20));
        }
        if(canvasSize < 200){
            canvasSize = 200;
            canvasSizeTextField.setText(String.valueOf(canvasSize));
        }
        canvas.setWidth(canvasSize);
        canvas.setHeight(canvasSize);
        canvas.draw(isBuilding);
        canvasSizeTextField.setText(String.valueOf(canvasSize));
        stage.setMaxWidth(canvasSize);
        stage.sizeToScene();
    }


    @Override
    public void update(Observable o, Object arg) {
        Model model = (Model) o;
        Set<IGizmo> gizmos = model.getGizmoList();
        List<IBall> balls = model.getBalls();

        //setHandlers();

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

            //Update key listener
            keyBindHandler = new MagicKeyHandler(new KeyBindingHandler(model));
            rootPane.addEventHandler(KeyEvent.ANY,keyBindHandler);
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