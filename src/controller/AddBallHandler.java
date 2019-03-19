package controller;

import ModelPackage.IModel;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import view.ResizableCanvas;

public class AddBallHandler implements EventHandler<MouseEvent> {

    private IModel model;
    private ResizableCanvas canvas;
    private Label info;

    public AddBallHandler(IModel model, ResizableCanvas canvas, Label infoLabel) {
        this.model = model;
        this.canvas = canvas;
        info = infoLabel;
    }

    @Override
    public void handle(MouseEvent event) {

        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {

            double width = canvas.getWidth();
            double height = canvas.getHeight();
            double wGridSquareSize = width / 20;
            double hGridSquareSize = height / 20;

            double x = (event.getX() / wGridSquareSize);
            double y = (event.getY() / hGridSquareSize);

            boolean status = model.createBall(x, y, 0, 0);

            if(status){
                info.setText("Ball successfully created at x : " + x + ", y : " + y + ". With Velocity x = (Variable)" + ", y = (Variable)");
            }else{
                info.setText("Failed to add ball");
            }

        }
    }
}
