package controller;

import ModelPackage.GizmoType;
import ModelPackage.IModel;
import javafx.event.EventHandler;
import javafx.scene.control.ChoiceBox;
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

    PlaceGizmoHandler(IModel model, ResizableCanvas canvas, ChoiceBox<GizmoType> gizmoTypeChoiceBox){

        this.canvas = canvas;
        this.model = model;
        this.gizmoTypeChoiceBox = gizmoTypeChoiceBox;

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
                    model.createGizmo(gizmoTypeChoiceBox.getValue(), startX, startY, endX, endY);
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

                    //Keeps startX and startY consistent for the getGizmo method with checks boundaries
                    if(startX>endX)
                    {
                        int temp = endX;
                        endX= startX;
                        startX = temp;
                    }
                    if(startY>endY){
                        int temp = endY;
                        endY = startY;
                        startY = temp;
                    }
                    model.createGizmo(gizmoTypeChoiceBox.getValue(), startX, startY, endX, endY);
                }
                System.out.println("StartX : " + startX + ", Start Y : " + startY);
                System.out.println("EndX : " + endX + ", End Y : " + endY);
            }
        }
    }

}
