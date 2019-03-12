package controller;

import ModelPackage.IModel;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import view.ResizableCanvas;

public class DeleteBallHandler implements EventHandler<MouseEvent> {

    private IModel model;
    private ResizableCanvas canvas;

    public DeleteBallHandler(IModel model, ResizableCanvas canvas) {

        this.model = model;
        this.canvas = canvas;


    }

    @Override
    public void handle(MouseEvent event) {

        if (event.isPrimaryButtonDown()) {

            double width = canvas.getWidth();
            double height = canvas.getHeight();
            double wGridSquareSize = width / 20;
            double hGridSquareSize = height / 20;

            double x =  (event.getX() / wGridSquareSize);
            double y =  (event.getY() / hGridSquareSize);

            model.deleteBall(x, y);

        }

    }
}
