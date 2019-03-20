package controller;

import ModelPackage.*;
import com.sun.javafx.fxml.builder.JavaFXSceneBuilder;
import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import javafx.util.StringConverter;
import view.ResizableCanvas;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.function.Predicate;

public class Controller implements Initializable, Observer {

    @FXML public ToolBar editVariables;
    @FXML public Slider gravitySlider;
    @FXML public TextField gravityValue;
    @FXML public Slider muSlider;
    @FXML public TextField muValue;
    @FXML public Slider mu2Slider;
    @FXML public TextField mu2Value;
    private Stage stage;
    private IModel model;
    private Timeline timeline;
    private EventHandler<KeyEvent> keyBindHandler;
    private EventHandler<MouseEvent> mouseHandler;

    private boolean isBuilding = false;

    @FXML private ResizableCanvas canvas;
    @FXML private HBox rootPane;
    @FXML private ImageView saveImg, loadImg, exitImg, buildImg, startImg, pauseImg, tickImg, runImg, addBallImg, kConnectImg, kDisconnectImg, connectImg, disconnectImg, moveImg, rotateImg, deleteGizmoImg, deleteBallImg;
    @FXML private Button stopButton, tickButton, runButton, keyConnect,keyDisconnect,connect,disconnect;
    @FXML private ToolBar commonToolBar, runToolBar, buildToolBar1, buildToolBar2;
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

        gravityValue.setText(String.valueOf(gravitySlider.getValue()));
        muValue.setText(String.valueOf(muSlider.getValue()));
        mu2Value.setText(String.valueOf(mu2Slider.getValue()));

    }

    private void initialiseToolBars(){
        initialiseCommonToolBar();
        buildToolBar1.setManaged(false);
        buildToolBar1.setVisible(false);
        buildToolBar1.maxWidthProperty().bind(canvas.widthProperty());
        buildToolBar2.setManaged(false);
        buildToolBar2.setVisible(false);
        buildToolBar2.maxWidthProperty().bind(canvas.widthProperty());
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

        rotateImg.setOnMouseClicked(event -> {
            canvas.removeEventHandler(MouseEvent.ANY,mouseHandler);
            mouseHandler = new RotationHandler(model, canvas, infoLabel);
            canvas.addEventHandler(MouseEvent.ANY, mouseHandler);
            canvas.requestFocus();
            rotateImg.requestFocus();
            setInfoLabel("Click on a Gizmo to Rotate it.");
        }

        );

        //Add Handler for Deleting Gizmos

        deleteGizmoImg.setOnMouseClicked(event -> {
            canvas.removeEventHandler(MouseEvent.ANY, mouseHandler);
            mouseHandler = new DeleteGizmoHandler(model, canvas);
            canvas.addEventHandler(MouseEvent.ANY, mouseHandler);
            canvas.requestFocus();
            deleteGizmoImg.requestFocus();
            setInfoLabel("Click on a Gizmo to delete it.");
        });
        //Add handler for adding keybindings
        kConnectImg.setOnMouseClicked(event -> {
            canvas.removeEventHandler(MouseEvent.ANY, mouseHandler);
            mouseHandler = new AddKeyConnectionsHandler(canvas,model, infoLabel);
            canvas.addEventHandler(MouseEvent.ANY, mouseHandler);
            canvas.requestFocus();
            kConnectImg.requestFocus();
            setInfoLabel("Click on a Gizmo to add a key binding.");
        });
        //Add handler for adding keybindings
        kDisconnectImg.setOnMouseClicked(event -> {
            canvas.removeEventHandler(MouseEvent.ANY, mouseHandler);
            mouseHandler = new RemoveKeyConnectionsHandler(model,canvas, infoLabel);
            canvas.addEventHandler(MouseEvent.ANY, mouseHandler);
            canvas.requestFocus();
            kDisconnectImg.requestFocus();
            setInfoLabel("Click on a Gizmo to remove the key binding.");
        });

        //Add handler for adding gizmo connections
        connectImg.setOnMouseClicked(event -> {
            canvas.removeEventHandler(MouseEvent.ANY, mouseHandler);
            mouseHandler = new AddGizmoConnectionsHandler(canvas,model, infoLabel);
            canvas.addEventHandler(MouseEvent.ANY, mouseHandler);
            canvas.requestFocus();
            infoLabel.setText("Please select two gizmos to connect.");
            connectImg.requestFocus();
        });

        //Add handler for removing gizmo connections
        disconnectImg.setOnMouseClicked(event -> {
            canvas.removeEventHandler(MouseEvent.ANY, mouseHandler);
            mouseHandler = new RemoveGizmoConnectionsHandler(canvas,model, infoLabel);
            canvas.addEventHandler(MouseEvent.ANY, mouseHandler);
            canvas.requestFocus();
            setInfoLabel("Please select two gizmos to disconnect.");
            disconnectImg.requestFocus();
        });

        deleteBallImg.setOnMouseClicked(event -> {
            canvas.removeEventHandler(MouseEvent.ANY, mouseHandler);
            mouseHandler = new DeleteBallHandler(model,canvas);
            canvas.addEventHandler(MouseEvent.ANY, mouseHandler);
            canvas.requestFocus();
            deleteBallImg.requestFocus();
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
            addBallImg.requestFocus();
        });

        moveImg.setOnMouseClicked(event -> {
            canvas.removeEventHandler(MouseEvent.ANY, mouseHandler);
            mouseHandler = new MoveGizmoHandler(model,canvas,infoLabel);
            canvas.addEventHandler(MouseEvent.ANY, mouseHandler);
            setInfoLabel("Select Gizmo to Move");
            canvas.requestFocus();
            moveImg.requestFocus();
        });

       gravitySlider.valueProperty().addListener(new ChangeListener<Number>() {
           @Override
           public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
               gravityValue.setText(String.valueOf(newValue));
               model.setGravity(gravitySlider.getValue());
               canvas.requestFocus();
               gravitySlider.requestFocus();}
       });

        gravityValue.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.ENTER){
                    try {
                        gravitySlider.setValue(Double.parseDouble(gravityValue.getText()));
                        model.setGravity(gravitySlider.getValue());
                        canvas.requestFocus();
                        //gravityValue.requestFocus();
                    }catch (NumberFormatException e){
                        //Don't change gravity as something wasn't right
                    }

                }
            }
        });

        muSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                muValue.setText(String.valueOf(newValue));
                model.setMu(muSlider.getValue());
                canvas.requestFocus();
                muSlider.requestFocus();
            }
        });

        muValue.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.ENTER){
                    try {
                        muSlider.setValue(Double.parseDouble(muValue.getText()));
                        model.setMu(muSlider.getValue());
                        canvas.requestFocus();
                        //muValue.requestFocus();
                    }catch (NumberFormatException e){
                        //Don't change gravity as something wasn't right
                    }

                }
            }
        });

        mu2Slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                mu2Value.setText(String.valueOf(newValue));
                model.setMu2(mu2Slider.getValue());
                canvas.requestFocus();
                mu2Slider.requestFocus();
            }
        });

        mu2Value.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.ENTER){
                    try {
                        mu2Slider.setValue(Double.parseDouble(muValue.getText()));
                        model.setMu2(mu2Slider.getValue());
                        canvas.requestFocus();
                        //mu2Value.requestFocus();
                    }catch (NumberFormatException e){
                        //Don't change gravity as something wasn't right
                    }

                }
            }
        });

    }


    public void setInfoLabel(String text){
       infoLabel.setText(text);
    }

    private void toggleModes(){
        if(runToolBar.isManaged()){ // From run to build mode

            //Stop if running
            model.reset();
            stopTimeline();

            isBuilding = true;
            runToolBar.setManaged(false);
            runToolBar.setVisible(false);
            buildToolBar1.setManaged(true);
            buildToolBar1.setVisible(true);
            buildToolBar2.setManaged(true);
            buildToolBar2.setVisible(true);
            //FIXME handler on root pane?
            rootPane.removeEventHandler(KeyEvent.ANY,keyBindHandler);
            canvas.removeEventHandler(MouseEvent.ANY,mouseHandler);

            //Default Handler will be place Gizmo's from selection
            mouseHandler = new PlaceGizmoHandler(model, canvas,gizmoChoiceBox, infoLabel);

            canvas.addEventHandler(MouseEvent.ANY, mouseHandler);
            //canvas.removeEventHandler(KeyEvent.ANY,keyBindHandler);
            canvas.draw(isBuilding);
            setCanvasSize(Integer.valueOf(canvasSizeTextField.getText()));
        }else{ // From build to run mode
            isBuilding = false;
            runToolBar.setManaged(true);
            runToolBar.setVisible(true);
            buildToolBar1.setManaged(false);
            buildToolBar1.setVisible(false);
            buildToolBar2.setManaged(false);
            buildToolBar2.setVisible(false);
            editVariables.setManaged(true);
            editVariables.setVisible(true);


            canvas.removeEventHandler(MouseEvent.ANY, mouseHandler);

            mouseHandler = new RunMouseEventHandler(model);
            //keyBindHandler = new KeyBindingHandler(model);
            keyBindHandler = new MagicKeyHandler(new KeyBindingHandler(model));
            rootPane.addEventHandler(KeyEvent.ANY,keyBindHandler);
            rootPane.requestFocus();
            canvas.addEventHandler(MouseEvent.ANY, mouseHandler);

            canvas.draw(isBuilding);
            stage.sizeToScene();
            setCanvasSize(Integer.valueOf(canvasSizeTextField.getText()));
        }

//        addBallImg.setOnMouseClicked(event -> {
//            canvas.removeEventHandler(MouseEvent.ANY, mouseHandler);
//            mouseHandler = new AddBallHandler(model,canvas, infoLabel);
//            canvas.addEventHandler(MouseEvent.ANY, mouseHandler);
//            setInfoLabel("Click on the grid to add a Ball.");
//        });

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
            System.out.println(model.getGravity());
            gravitySlider.setValue(model.getGravity());
            muSlider.setValue(model.getMu());
            mu2Slider.setValue(model.getMu2());
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please pick a file.");
            alert.showAndWait();
        }

    }

    private boolean saveFile(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text doc(*.txt)", "*.txt"));
        fileChooser.setInitialFileName("Gizmoball.txt");
        fileChooser.setTitle("Save Game");
        File file = fileChooser.showSaveDialog(stage);
        if(file != null) {
            if(model != null){
                model.save(file);
                return true;
            }else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Empty game! Nothing to save!");
                alert.showAndWait();
                return false;
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please pick a place to save the game at.");
            alert.showAndWait();
            return false;
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

    public void onClose(WindowEvent event) {

        //What to do when user press X button to close

        Alert closeWindowAlert = new Alert(Alert.AlertType.WARNING);
        closeWindowAlert.setTitle("Do you want to save before exiting");
        closeWindowAlert.setHeaderText("Do you want to save current game");
        Label label = new Label("This action can not be undone");
        closeWindowAlert.getDialogPane().setContent(label);
        //closeWindowAlert.setContentText("This action can not be undone");
        GridPane gridPane = new GridPane();
        ButtonType yes = new ButtonType("Yes");
        ButtonType no = new ButtonType("No");
        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        closeWindowAlert.getButtonTypes().setAll(yes,no,cancel);

        //Don't need the included OK button

        //closeWindowAlert.getDialogPane().setContent(gridPane);

        Optional<ButtonType> buttonPressed = closeWindowAlert.showAndWait();

        if(buttonPressed.get().equals(yes)){
            if(saveFile()){
                System.exit(0);
            }else{
                //Prevent close is something went wrong
                event.consume();
            }
        }
        if(buttonPressed.get().equals(no)){
            System.exit(0);
        }
        if(buttonPressed.get().equals(cancel)){
            //Do nothing
            event.consume();
        }


    }
}