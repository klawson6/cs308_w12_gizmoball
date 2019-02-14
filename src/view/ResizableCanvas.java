package view;

import ModelPackage.*;
import Physics.Vect;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;

public class ResizableCanvas extends Canvas implements Observer {

    private HashSet<Gizmo> gizmoList;
    private IBall ball;

    public void draw() {
        double width = this.getWidth();
        double height = this.getHeight();

        GraphicsContext gc = getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width, height);

        if(gizmoList != null) {
            double wGridSquareSize = width / 20;
            double hGridSquareSize = height / 20;


            for (Gizmo gizmo : gizmoList) {
                IGizmo iGizmo = (IGizmo) gizmo;
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
                        gc.setFill(Color.BLUE);
                        // Get the origin of the triangle in the top-left corner of the containing grid square
                        double startX = (double) iGizmo.getStartxPosition() * wGridSquareSize;
                        double startY = (double) iGizmo.getStartyPosition() * hGridSquareSize;
                        // The coordinates of the 3 corners of the triangle, used for drawing the polygon to look like a triangle.
                        double[] xPoints = new double[3];
                        double[] yPoints = new double[3];
                        switch (iGizmo.getRotation()){
                            // Corner opposite hypotenuse is in the NW corner of the containing grid square
                            case 0:
                                xPoints[0] = startX; xPoints[1] = startX; xPoints[2] = startX + wGridSquareSize;
                                yPoints[0] = startY; yPoints[1] = startY+hGridSquareSize; yPoints[2] = startY;
                                gc.fillPolygon(xPoints, yPoints, 3);
                                break;
                            // Corner opposite hypotenuse is in the NE corner of the containing grid square
                            case 90:
                                xPoints[0] = startX; xPoints[1] = startX+wGridSquareSize; xPoints[2] = startX + wGridSquareSize;
                                yPoints[0] = startY; yPoints[1] = startY; yPoints[2] = startY+hGridSquareSize;
                                gc.fillPolygon(xPoints, yPoints, 3);
                                break;
                            // Corner opposite hypotenuse is in the SE corner of the containing grid square
                            case 180:
                                xPoints[0] = startX+wGridSquareSize; xPoints[1] = startX+wGridSquareSize; xPoints[2] = startX;
                                yPoints[0] = startY; yPoints[1] = startY+hGridSquareSize; yPoints[2] = startY+hGridSquareSize;
                                gc.fillPolygon(xPoints, yPoints, 3);
                                break;
                            // Corner opposite hypotenuse is in the SW corner of the containing grid square
                            case 270:
                                xPoints[0] = startX; xPoints[1] = startX; xPoints[2] = startX + wGridSquareSize;
                                yPoints[0] = startY; yPoints[1] = startY+hGridSquareSize; yPoints[2] = startY+hGridSquareSize;
                                gc.fillPolygon(xPoints, yPoints, 3);
                                break;
                        }
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
                }
            }

        }

        //TODO will need to change to use an Interface
        if(ball != null){
            double wGridSquareSize =  width / 20;
            double hGridSquareSize = height / 20;
            gc.setFill(Color.YELLOW);
            // Ball is represented by a circle with a centered origin, but fillOval draws assuming it has an origin top-left.
            // Calibrate ball position on the board by shifting the origin by the ball radius
            gc.fillOval((ball.getXPosition() - 0.25) * wGridSquareSize, (ball.getYPosition() - 0.25) * hGridSquareSize, 0.5*wGridSquareSize, 0.5*hGridSquareSize);
        }

    }

    public void setGizmoList(HashSet<Gizmo> gizmoList) {
        this.gizmoList = gizmoList;
    }

    public void setBall(IBall ball){
        this.ball = ball;
    }

    @Override
    public void update(Observable o, Object arg) { draw(); }
}