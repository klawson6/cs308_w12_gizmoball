package view;

import ModelPackage.*;
import Physics.Vect;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;

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
            int wGridSquareSize = (int) width / 20;
            int hGridSquareSize = (int) height / 20;


            for (Gizmo gizmo : gizmoList) {
                IGizmo iGizmo = (IGizmo) gizmo;
                String type = iGizmo.getGizmoType();
                switch (type) {
                    case "Circle":
                        gc.save();
                        gc.setFill(Color.GREEN);
                        gc.fillOval(iGizmo.getStartxPosition() * wGridSquareSize, iGizmo.getStartyPosition() * hGridSquareSize, wGridSquareSize, hGridSquareSize);
                        gc.restore();
                        break;
                    case "Square":
                        gc.save();
                        gc.setFill(Color.RED);
                        gc.fillRect(iGizmo.getStartxPosition() * wGridSquareSize, iGizmo.getStartyPosition() * hGridSquareSize, wGridSquareSize, hGridSquareSize);
                        gc.restore();
                        break;
                    case "Triangle":
                        gc.save();
                        gc.setFill(Color.BLUE);
                        double startX = (double) iGizmo.getStartxPosition() * wGridSquareSize;
                        double startY = (double) iGizmo.getStartyPosition() * hGridSquareSize;
                        double[] xPoints = {startX, startX, startX + (double) wGridSquareSize};
                        double[] yPoints = {startY, startY + (double) hGridSquareSize, startY};
                        gc.transform(new Affine(new Rotate(iGizmo.getRotation(), startX+(wGridSquareSize/2), startY+(hGridSquareSize/2))));
                        gc.fillPolygon(xPoints, yPoints, 3);
                        gc.restore();
                        break;
                    case "Absorber":
                        gc.save();
                        gc.setFill(Color.PURPLE);
                        double startXAbsorber = (double) iGizmo.getStartxPosition() * wGridSquareSize;
                        double startYAbsorber = (double) iGizmo.getStartyPosition() * hGridSquareSize;
                        double endXAbsorber = (double) iGizmo.getEndxPosition() * wGridSquareSize;
                        double endYAbsorber = (double) iGizmo.getEndyPosition() * hGridSquareSize;
                        double[] xPointsAbsorber = {startXAbsorber, startXAbsorber, endXAbsorber, endXAbsorber};
                        double[] yPointsAbsorber = {startYAbsorber, endYAbsorber, endYAbsorber, startYAbsorber};
                        gc.fillPolygon(xPointsAbsorber, yPointsAbsorber, 4);
                        gc.restore();
                        break;
                    case "LeftFlipper":
                        gc.save();
                        GFlipper leftFlipper = (GFlipper) iGizmo;
                        gc.setFill(Color.YELLOW);
                        gc.transform(new Affine(new Rotate(-iGizmo.getRotation(), (iGizmo.getStartxPosition()+0.5)*wGridSquareSize, (iGizmo.getStartyPosition()+0.5)*hGridSquareSize)));
                        /*if(leftFlipper.isActivated()){
                            gc.fillRoundRect(iGizmo.getStartxPosition() * wGridSquareSize, iGizmo.getStartyPosition() * hGridSquareSize, hGridSquareSize*2, wGridSquareSize, wGridSquareSize, hGridSquareSize);
                        }else {
                        */
                            gc.fillRoundRect(iGizmo.getStartxPosition() * wGridSquareSize, iGizmo.getStartyPosition() * hGridSquareSize, wGridSquareSize, hGridSquareSize*2, wGridSquareSize, hGridSquareSize);
                        //}
                        gc.restore();
                        break;
                    case "RightFlipper":
                        gc.save();
                        GFlipper rightFlipper = (GFlipper) iGizmo;
                        gc.setFill(Color.YELLOW);
                        gc.transform(new Affine(new Rotate(iGizmo.getRotation(), (iGizmo.getStartxPosition()+1.5)*wGridSquareSize, (iGizmo.getStartyPosition()+0.5)*hGridSquareSize)));
//                        if(rightFlipper.isActivated()){
//                            gc.fillRoundRect(iGizmo.getStartxPosition() * wGridSquareSize, iGizmo.getStartyPosition() * hGridSquareSize, wGridSquareSize*2, hGridSquareSize, wGridSquareSize, hGridSquareSize);
//                        }else {
                            gc.fillRoundRect((iGizmo.getStartxPosition()+1) * wGridSquareSize, iGizmo.getStartyPosition() * hGridSquareSize, wGridSquareSize, hGridSquareSize*2, wGridSquareSize, hGridSquareSize);
//                        }
                        gc.restore();
                        break;
                }
            }

        }

        //TODO will need to change to use an Interface
        if(ball != null){
            int wGridSquareSize = (int) width / 20;
            int hGridSquareSize = (int) height / 20;
            gc.setFill(Color.YELLOW);
            gc.fillOval(ball.getXPosition() * wGridSquareSize, ball.getYPosition() * hGridSquareSize, wGridSquareSize, hGridSquareSize);
        }

    }

    public void setGizmoList(HashSet<Gizmo> gizmoList) {
        this.gizmoList = gizmoList;
    }

    public void setBall(IBall ball){
        this.ball = ball;
    }

    @Override
    public void update(Observable o, Object arg) {

        draw();
    }
}