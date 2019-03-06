package controller;

import ModelPackage.GizmoType;
import ModelPackage.IGizmo;
import ModelPackage.IModel;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import view.ResizableCanvas;

public class RemoveKeyConnectionsHandler implements EventHandler<MouseEvent> {

     private IModel model;
     private ResizableCanvas canvas;
    private int startX;
    private int startY;

    public RemoveKeyConnectionsHandler(IModel model, ResizableCanvas canvas){
        this.model = model;
        this.canvas = canvas;
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

                if (g!= null && (g.getGizmoType().equals(GizmoType.ABSORBER) | g.getGizmoType().equals(GizmoType.LEFTFLIPPER) | g.getGizmoType().equals(GizmoType.RIGHTFLIPPER))) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Please press a button to remove a key binding!");
                    alert.setTitle("Remove a key binding");
                    ToggleGroup group = new ToggleGroup();

                //    alert.getDialogPane().lookupButton(ButtonType.OK).setVisible(false);

                    alert.getDialogPane().addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
                        @Override
                        public void handle(KeyEvent event) {


                            //If ESC button is pressed just close the dialog window
                            if (event.getCode().getCode() != 27) {
                                if (model.removeKeyConnection(startX,startY,event))
                                    System.out.println("Removed Key connections to key '" + event.getCode().getName() + "'");

                                else {
                                    Alert error = new Alert(Alert.AlertType.ERROR);
                                    error.setTitle("Invalid key");
                                    error.setHeaderText("There is no key binding on that key!");
                                    error.show();
                                }
                            }
                             event.consume();
                             alert.close();
                        }});
                    alert.show();

                }
            }



        }
    }
}


