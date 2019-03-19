package controller;

import ModelPackage.GizmoType;
import ModelPackage.IGizmo;
import ModelPackage.IModel;
import javafx.event.EventHandler;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import view.ResizableCanvas;

public class PlaceGizmoHandler implements EventHandler<MouseEvent> {

    private ResizableCanvas canvas;
    private IModel model;
    private ChoiceBox<GizmoType> gizmoTypeChoiceBox;
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    Label info;

    PlaceGizmoHandler(IModel model, ResizableCanvas canvas, ChoiceBox<GizmoType> gizmoTypeChoiceBox, Label infoLabel){

        this.canvas = canvas;
        this.model = model;
        this.gizmoTypeChoiceBox = gizmoTypeChoiceBox;
        this.info = infoLabel;

    }

    @Override
    public void handle(MouseEvent event) {

        //Don't care about mouse move, only click/releases
        if(event.getEventType().equals(MouseEvent.MOUSE_PRESSED) || event.getEventType().equals(MouseEvent.MOUSE_CLICKED) || event.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {

            if (event.isPrimaryButtonDown()) {

                double width = canvas.getWidth();
                double height = canvas.getHeight();
                double wGridSquareSize = width / 20;
                double hGridSquareSize = height / 20;

                startX = (int) (event.getX() / wGridSquareSize);
                startY = (int) (event.getY() / hGridSquareSize);

                if (!gizmoTypeChoiceBox.getValue().equals(GizmoType.ABSORBER)) {
                   boolean status = model.createGizmo(gizmoTypeChoiceBox.getValue(), startX, startY, endX, endY);
                   if(status){
                       info.setText(gizmoTypeChoiceBox.getValue().toString() + " was created sucessfully");
                   }else{
                       IGizmo gizmo = model.getGizmo(startX,startY);
                       info.setText(gizmoTypeChoiceBox.getValue().toString() + " was not created successfully due to " + gizmo.getGizmoType() + " at x: " + gizmo.getStartxPosition() + ", y: " + gizmo.getStartyPosition());
                   }
                }
            }

            if (event.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
                //Drag Ended has ended (for absorber)

                double width = canvas.getWidth();
                double height = canvas.getHeight();
                double wGridSquareSize = width / 20;
                double hGridSquareSize = height / 20;

                endX = (int) (event.getX() / wGridSquareSize);
                endY = (int) (event.getY() / hGridSquareSize);

                if (gizmoTypeChoiceBox.getValue().equals(GizmoType.ABSORBER)) {
                    int maxX = Math.max(startX,endX);
                    int maxY = Math.max(startY,endY);
                    int minX = Math.min(startX,endX);
                    int minY = Math.min(startY,endY);
                    boolean status = model.createGizmo(gizmoTypeChoiceBox.getValue(), minX, minY, maxX, maxY);

                    if(status) {
                        info.setText("Create Absorber at x : " + minX + ", y : " + minY + ", to x : " + maxX + ", y : " + maxY);
                    }else{
                        info.setText("Failed to create Absorber");
                    }
                }
                System.out.println("StartX : " + startX + ", Start Y : " + startY);
                System.out.println("EndX : " + endX + ", End Y : " + endY);
            }
        }
    }

}
