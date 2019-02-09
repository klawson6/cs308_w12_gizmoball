package view;

import ModelPackage.Gizmo;
import ModelPackage.IGizmo;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.HashSet;

public class ResizableCanvas extends Canvas {

    private HashSet<Gizmo> gizmoList;

    public ResizableCanvas() {
        // Redraw canvas when size changes.
        widthProperty().addListener(evt -> draw());
        heightProperty().addListener(evt -> draw());
    }

    public void draw() {
        double width = getWidth();
        double height = getHeight();

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
                        gc.setFill(Color.GREEN);
                        gc.fillOval(iGizmo.getStartxPosition() * wGridSquareSize, iGizmo.getStartyPosition() * hGridSquareSize, wGridSquareSize, hGridSquareSize);
                        break;
                    case "Square":
                        gc.setFill(Color.RED);
                        gc.fillRect(iGizmo.getStartxPosition() * wGridSquareSize, iGizmo.getStartyPosition() * hGridSquareSize, wGridSquareSize, hGridSquareSize);
                        break;
                    case "Triangle":
                        gc.setFill(Color.BLUE);
                        double startX = (double) iGizmo.getStartxPosition() * wGridSquareSize;
                        double startY = (double) iGizmo.getStartyPosition() * hGridSquareSize;
                        double[] xPoints = {startX, startX, startX + (double) wGridSquareSize};
                        double[] yPoints = {startY, startY + (double) hGridSquareSize, startY + (double) hGridSquareSize};
                        gc.fillPolygon(xPoints, yPoints, 3);
                        break;
                    case "Absorber":
                        gc.setFill(Color.PURPLE);
                        double startXAbsorber = (double) iGizmo.getStartxPosition() * wGridSquareSize;
                        double startYAbsorber = (double) iGizmo.getStartyPosition() * hGridSquareSize - hGridSquareSize;
                        double endXAbsorber = (double) iGizmo.getEndxPosition() * wGridSquareSize;
                        double endYAbsorber = (double) iGizmo.getEndyPosition() * hGridSquareSize - hGridSquareSize;
                        double[] xPointsAbsorber = {startXAbsorber, startXAbsorber, endXAbsorber, endXAbsorber};
                        double[] yPointsAbsorber = {startYAbsorber, endYAbsorber, endYAbsorber, startYAbsorber};
                        gc.fillPolygon(xPointsAbsorber, yPointsAbsorber, 4);
                        break;
                }
            }

        }

    }

    public void setGizmoList(HashSet<Gizmo> gizmoList) {
        this.gizmoList = gizmoList;
    }

    @Override
    public boolean isResizable() {
        return true;
    }

    @Override
    public double prefWidth(double height) {
        return getWidth();
    }

    @Override
    public double prefHeight(double width) {
        return getHeight();
    }
}