package controller;

import ModelPackage.GizmoType;
import ModelPackage.IGizmo;
import ModelPackage.IModel;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import view.ResizableCanvas;

public class AddKeyConnectionsHandler implements EventHandler<MouseEvent> {

    private ResizableCanvas canvas;
    private IModel model;
    private int startX;
    private int startY;
    private Label infoLabel;

    public AddKeyConnectionsHandler(ResizableCanvas canvas, IModel model, Label infoLabel) {
        this.model = model;
        this.canvas = canvas;
        this.infoLabel = infoLabel;
    }

    @Override
    public void handle(MouseEvent event) {

        if (event.getEventType().equals(MouseEvent.MOUSE_PRESSED) || event.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {


            if (event.isPrimaryButtonDown()) {

                double width = canvas.getWidth();
                double height = canvas.getHeight();
                double wGridSquareSize = width / 20;
                double hGridSquareSize = height / 20;

                startX = (int) (event.getX() / wGridSquareSize);
                startY = (int) (event.getY() / hGridSquareSize);

                IGizmo g = model.getGizmo(startX, startY);

                if (g != null) {
                    if (g.getGizmoType().equals(GizmoType.ABSORBER) || g.getGizmoType().equals(GizmoType.LEFTFLIPPER) || g.getGizmoType().equals(GizmoType.RIGHTFLIPPER)) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Add a key binding");
                        alert.setHeaderText("Please press a key to add a connection!");

                        ToggleGroup group = new ToggleGroup();
                        Label label = new Label("Please press a key to add a connection");
                        RadioButton rb1 = new RadioButton("On key press");
                        rb1.setUserData("press");
                        rb1.setToggleGroup(group);
                        RadioButton rb2 = new RadioButton("On key release");
                        rb2.setUserData("release");
                        rb2.setToggleGroup(group);
                        rb1.setSelected(true);

                        //Add radio buttons to a pane
                        GridPane gp = new GridPane();
                        gp.add(rb1, 0, 0);
                        gp.add(rb2, 1, 0);
                        gp.setHgap(10);
                        ColumnConstraints c = new ColumnConstraints();
                        alert.getDialogPane().setContent(gp);
                        alert.getDialogPane().lookupButton(ButtonType.OK).setVisible(false);
                        //Add an key event handler to pick up the key pressed
                        alert.getDialogPane().getContent().addEventHandler(KeyEvent.ANY, new EventHandler<KeyEvent>() {
                            @Override
                            public void handle(KeyEvent event) {

                                //If ESC button is pressed just close the dialog window
                                if (!event.getCode().getName().equals("Esc")) {
                                    if (group.getSelectedToggle().getUserData().toString().equals("press")) {
                                        addOnKeyPressed(event);
                                    } else {
                                        addOnKeyReleased(event);
                                    }


                                }
                                event.consume();
                                alert.close();
                            }
                        });
                        alert.show();
                    } else {
                        Alert error = new Alert(Alert.AlertType.ERROR);
                        error.setTitle("Keybinding Cannot Be Applied");
                        error.setHeaderText("Key connection cannot be applied to " + g.getGizmoType() + "s!");
                        error.show();
                    }
                }
            }
        }
    }

    private void addOnKeyPressed(KeyEvent event) {
        KeyEvent binding = new KeyEvent(KeyEvent.KEY_PRESSED, event.getCharacter(), event.getText(), event.getCode(), false, false, false, false);
        if (model.addKeyConnection(startX, startY, binding)) {
            //System.out.println("Added Key connection to key '" + event.getCode().getName() + "' on key press!");
            infoLabel.setText("Added Key connection to key '" + event.getCode().getName() + "' on key press!");
        } else {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Keybinding exists");
            error.setHeaderText("Key connection already exists on gizmo!");
            error.show();
        }
    }

    private void addOnKeyReleased(KeyEvent event) {
        KeyEvent binding = new KeyEvent(KeyEvent.KEY_RELEASED, event.getCharacter(), event.getText(), event.getCode(), false, false, false, false);
        if (model.addKeyConnection(startX, startY, binding)) {
            //System.out.println("Added Key connection to key '" + event.getCode().getName() + "' on key release!");
            infoLabel.setText("Added Key connection to key '" + event.getCode().getName() + "' on key release!");
        } else {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Key binding exists");
            error.setHeaderText("Key connection already exists on gizmo!");
            error.show();
        }
    }
}
