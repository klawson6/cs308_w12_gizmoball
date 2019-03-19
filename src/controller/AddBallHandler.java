package controller;

import ModelPackage.IBall;
import ModelPackage.IModel;
import Physics.Vect;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
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

            //Set Default velocity incase something happens

            double xVelocity = 0;
            double yVelocity = 0;

            //Create Window to allow user input
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("Set Ball Velocity");
            alert.setHeaderText("Please enter the x and y velocity components for the ball to start with");

            Label infomation = new Label("If no value is entered the components will be set at 0 by default");
            Label xTitle = new Label("Please Enter X Component : ");
            Label yTitle = new Label("Please Enter Y Component : ");
            TextField xValue = new TextField();
            TextField yValue = new TextField();
            Button button = new Button("Submit");


            GridPane gp = new GridPane();

            gp.alignmentProperty().setValue(Pos.CENTER);

            gp.setHgap(10);
            gp.setVgap(5);

            gp.add(xTitle,0,0);
            gp.add(yTitle,1,0);
            gp.add(xValue,0,1);
            gp.add(yValue,1,1);

            GridPane gridPane = new GridPane();
            gridPane.alignmentProperty().setValue(Pos.CENTER);
            gridPane.add(infomation,0,0);
            gridPane.add(gp,0,1);



            alert.getDialogPane().setContent(gridPane);
            //Need to make new window and wait for input
            alert.showAndWait();

            try{
                xVelocity = Double.parseDouble(xValue.getText());
            }catch (NumberFormatException e){
                xVelocity=0;
            }

            try {
                yVelocity = Double.parseDouble(yValue.getText());
            }catch (NumberFormatException e){
                yVelocity = 0;
            }


            boolean status = model.createBall(x, y, xVelocity, yVelocity);

            if(status){
                info.setText("Ball successfully created at x : " + x + ", y : " + y + ". With Velocity x = " + xVelocity + " +  y = " + yVelocity);
            }else{
                info.setText("Failed to add ball");
            }

        }
    }
}
