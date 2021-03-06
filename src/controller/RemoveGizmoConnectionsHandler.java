package controller;

import ModelPackage.IGizmo;
import ModelPackage.IModel;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import view.ResizableCanvas;

public class RemoveGizmoConnectionsHandler implements EventHandler<MouseEvent> {
    private ResizableCanvas canvas;
    private IModel model;
    private int startX,startX2;
    private int startY,startY2;
    private boolean selectedGizmo = false;
    private Label infoLabel;

    public RemoveGizmoConnectionsHandler(ResizableCanvas canvas, IModel model, Label infoLabel) {
        this.model = model;
        this.canvas = canvas;
        this.infoLabel = infoLabel;
    }

    @Override
    public void handle(MouseEvent event) {


        if (event.getEventType().equals(MouseEvent.MOUSE_PRESSED) || event.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {


            if (event.isPrimaryButtonDown()) {
                //Check if the first gizmo has been selected
                if (selectedGizmo == false) {
//                    System.out.println("first gizmo");
                    infoLabel.setText("First Gizmo selected.");
                    double width = canvas.getWidth();
                    double height = canvas.getHeight();
                    double wGridSquareSize = width / 20;
                    double hGridSquareSize = height / 20;

                    startX = (int) (event.getX() / wGridSquareSize);
                    startY = (int) (event.getY() / hGridSquareSize);

                    selectedGizmo = true;
                    event.consume();

                } else {

                    double width = canvas.getWidth();
                    double height = canvas.getHeight();
                    double wGridSquareSize = width / 20;
                    double hGridSquareSize = height / 20;

                    startX2 = (int) (event.getX() / wGridSquareSize);
                    startY2 = (int) (event.getY() / hGridSquareSize);

                    IGizmo g = model.getGizmo(startX2, startY2);
                    if(g != null) {
                        if (model.removeGizmoConnection(startX, startY, startX2, startY2)) {
                            //System.out.println("Successfully removed gizmo connection");
                            infoLabel.setText("Connection removed.");
                        }
                        else {
                            Alert error = new Alert(Alert.AlertType.ERROR);
                            error.setTitle("Error");
                            error.setHeaderText("Gizmo doesn't already exists!");
                            error.show();
                        }
                        selectedGizmo = false;

                    }

                }
                event.consume();

            }
        }
    }
}