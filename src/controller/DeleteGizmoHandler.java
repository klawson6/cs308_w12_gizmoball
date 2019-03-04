package controller;

import ModelPackage.GizmoType;
import ModelPackage.IModel;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import view.ResizableCanvas;

public class DeleteGizmoHandler implements EventHandler<MouseEvent> {

    private IModel model;
    private ResizableCanvas canvas;

    DeleteGizmoHandler(IModel model, ResizableCanvas canvas) {

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

            int x = (int) (event.getX() / wGridSquareSize);
            int y = (int) (event.getY() / hGridSquareSize);

            model.deleteGizmo(x, y);

        }
    }
}
