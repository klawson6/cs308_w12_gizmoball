package controller;

import ModelPackage.IModel;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import view.ResizableCanvas;


public class RotationHandler implements EventHandler<MouseEvent> {

    private IModel model;
    private ResizableCanvas canvas;

    RotationHandler(IModel model, ResizableCanvas canvas){
        this.model = model;
        this.canvas = canvas;
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

            model.rotate(x, y);
        }
    }
}
