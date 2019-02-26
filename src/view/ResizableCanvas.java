package view;

import ModelPackage.*;
import Physics.Vect;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;

import java.util.*;

public class ResizableCanvas extends Canvas implements Observer {

    private Set<IGizmo> gizmoList;
    private List<IBall> balls;

    @Override
    public boolean isResizable()
    {
        return true;
    }

    public void draw(boolean isBuilding) {
        double width = this.getWidth();
        double height = this.getHeight();

        GraphicsContext gc = getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width, height);

        if(isBuilding){
            drawGrid();
        }

        if (gizmoList != null) {
            double wGridSquareSize = width / 20;
            double hGridSquareSize = height / 20;


            for (IGizmo iGizmo : gizmoList) {
                String type = iGizmo.getGizmoType();
                switch (type) {
                    case "Circle":
                        gc.setFill(Color.GREEN);
                        gc.fillOval(iGizmo.getStartxPosition() * wGridSquareSize, iGizmo.getStartyPosition() * hGridSquareSize, wGridSquareSize, hGridSquareSize);
                        break;
                    case "Square":
                        gc.setFill(Color.RED);
                        gc.fillRect(iGizmo.getStartxPosition() * wGridSquareSize, iGizmo.getStartyPosition() * hGridSquareSize, wGridSquareSize, hGridSquareSize);
                        break;
                    case "Triangle":
                        gc.save();
                        gc.setFill(Color.BLUE);
                        double startX = (double) iGizmo.getStartxPosition() * wGridSquareSize;
                        double startY = (double) iGizmo.getStartyPosition() * hGridSquareSize;
                        double[] xPoints = {startX, startX, startX + wGridSquareSize};
                        double[] yPoints = {startY, startY + hGridSquareSize, startY};
                        gc.transform(new Affine(new Rotate(iGizmo.getRotation(), startX + (wGridSquareSize / 2), startY + (hGridSquareSize / 2))));
                        gc.fillPolygon(xPoints, yPoints, 3);
                        gc.restore();
                        break;
                    case "Absorber":
                        gc.setFill(Color.PURPLE);
                        double startXAbsorber = (double) iGizmo.getStartxPosition() * wGridSquareSize;
                        double startYAbsorber = (double) iGizmo.getStartyPosition() * hGridSquareSize;
                        double endXAbsorber = (double) iGizmo.getEndxPosition() * wGridSquareSize;
                        double endYAbsorber = (double) iGizmo.getEndyPosition() * hGridSquareSize;
                        double[] xPointsAbsorber = {startXAbsorber, startXAbsorber, endXAbsorber, endXAbsorber};
                        double[] yPointsAbsorber = {startYAbsorber, endYAbsorber, endYAbsorber, startYAbsorber};
                        gc.fillPolygon(xPointsAbsorber, yPointsAbsorber, 4);
                        break;
                    case "LeftFlipper":
                        gc.save();
                        gc.setFill(Color.YELLOW);
                        gc.transform(new Affine(new Rotate(-iGizmo.getRotation(), (iGizmo.getStartxPosition() + 0.5) * wGridSquareSize, (iGizmo.getStartyPosition() + 0.5) * hGridSquareSize)));
                        gc.fillRoundRect(iGizmo.getStartxPosition() * wGridSquareSize, iGizmo.getStartyPosition() * hGridSquareSize, wGridSquareSize, hGridSquareSize * 2, wGridSquareSize, hGridSquareSize);
                        gc.restore();
                        break;
                    case "RightFlipper":
                        gc.save();
                        gc.setFill(Color.YELLOW);
                        gc.transform(new Affine(new Rotate(iGizmo.getRotation(), (iGizmo.getStartxPosition() + 1.5) * wGridSquareSize, (iGizmo.getStartyPosition() + 0.5) * hGridSquareSize)));
                        gc.fillRoundRect((iGizmo.getStartxPosition() + 1) * wGridSquareSize, iGizmo.getStartyPosition() * hGridSquareSize, wGridSquareSize, hGridSquareSize * 2, wGridSquareSize, hGridSquareSize);
                        gc.restore();
                        break;

                }
            }

        }

        //TODO will need to change to use an Interface
        if(balls!=null) {
            for (IBall ball : balls) {
                double wGridSquareSize = width / 20;
                double hGridSquareSize = height / 20;
                gc.setFill(Color.YELLOW);
                // Ball is represented by a circle with a centered origin, but fillOval draws assuming it has an origin top-left.
                // Calibrate ball position on the board by shifting the origin by the ball radius
                gc.fillOval((ball.getXPosition() - 0.25) * wGridSquareSize, (ball.getYPosition() - 0.25) * hGridSquareSize, 0.5 * wGridSquareSize, 0.5 * hGridSquareSize);
            }
        }
    }

    private void drawGrid(){
        double width = this.getWidth();
        double height = this.getHeight();

        GraphicsContext gc = getGraphicsContext2D();

        double wGridSquareSize = width / 20;
        double hGridSquareSize = height / 20;

        gc.save();
        gc.setStroke(Color.WHITE);

        // Vertical lines
        for(int i = 0; i <= width; i += wGridSquareSize){
            gc.strokeLine(i, 0, i, height);
        }

        // Horizontal lines
        for(int i = 0; i <= height; i += hGridSquareSize){
            gc.strokeLine(0, i, width, i);
        }

        gc.restore();
    }

    public void setGizmoList(Set<IGizmo> gizmoList) {
        this.gizmoList = gizmoList;
    }

    public void setBalls(List<IBall> balls){
        this.balls = balls;
    }

    @Override
    public void update(Observable o, Object arg) {
        boolean isBuilding = (boolean) arg;
        draw(isBuilding);
    }
}