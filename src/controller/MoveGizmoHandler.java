package controller;

import ModelPackage.Gizmo;
import ModelPackage.IGizmo;
import ModelPackage.IModel;
import com.sun.javafx.cursor.CursorType;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import view.ResizableCanvas;

public class MoveGizmoHandler implements EventHandler<MouseEvent> {

    private IModel model;
    private ResizableCanvas canvas;
    boolean secondpress = false;
    int firstx;
    int firsty;
    Label info;

    public MoveGizmoHandler(IModel model, ResizableCanvas canvas, Label infolabel) {

        this.model = model;
        this.canvas = canvas;
        this.info = infolabel;
    }

    @Override
    public void handle(MouseEvent event) {

        if(event.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {

            if (secondpress) {

                double width = canvas.getWidth();
                double height = canvas.getHeight();
                double wGridSquareSize = width / 20;
                double hGridSquareSize = height / 20;

                int x = (int) (event.getX() / wGridSquareSize);
                int y = (int) (event.getY() / hGridSquareSize);

                IGizmo gizmo = model.getGizmo(firstx, firsty);

                boolean status = model.moveGizmo(firstx, firsty, x, y);

                if (status) {
                    info.setText(gizmo.getGizmoType().toString() + " Moved Successfully from " + firstx + " , " + firsty + ", to : " + x + " , " + y);
                } else {
                    if (gizmo != null) {
                        info.setText("Failed to move " + gizmo.getGizmoType().toString());
                    }
                }

                secondpress = false;
            } else {

                double width = canvas.getWidth();
                double height = canvas.getHeight();
                double wGridSquareSize = width / 20;
                double hGridSquareSize = height / 20;

                firstx = (int) (event.getX() / wGridSquareSize);
                firsty = (int) (event.getY() / hGridSquareSize);

                IGizmo gizmo = model.getGizmo(firstx, firsty);

                if (gizmo != null) {
                    info.setText("Select Location to move " + gizmo.getGizmoType().toString() + " to");
                    secondpress = true;
                }

            }
        }
    }
}
