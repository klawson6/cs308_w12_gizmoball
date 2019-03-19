package controller;

import ModelPackage.IGizmo;
import ModelPackage.IModel;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import view.ResizableCanvas;



public class RotationHandler implements EventHandler<MouseEvent> {

    private IModel model;
    private ResizableCanvas canvas;
    private Label info;

    RotationHandler(IModel model, ResizableCanvas canvas, Label infoLabel){
        this.model = model;
        this.canvas = canvas;
        this.info = infoLabel;
    }

    @Override
    public void handle(MouseEvent event) {

        if(event.isPrimaryButtonDown()) {

            System.out.println("Rotate Attempted");

            double width = canvas.getWidth();
            double height = canvas.getHeight();
            double wGridSquareSize = width / 20;
            double hGridSquareSize = height / 20;

            int x = (int) (event.getX() / wGridSquareSize);
            int y = (int) (event.getY() / hGridSquareSize);

            System.out.println("X : " + x + " , Y : " + y);

            IGizmo g = model.getGizmo(x, y);
            if (model.rotate(x, y)){
                info.setText(g.getGizmoType() + " at x: " + g.getStartxPosition() + ", y: " + g.getStartyPosition() + " successfully rotated 90 degrees.");
            } else if (g != null){
                info.setText(g.getGizmoType() + "s cannot be rotated.");
            }
        }
    }
}
