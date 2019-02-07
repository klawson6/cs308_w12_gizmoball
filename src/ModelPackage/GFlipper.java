package ModelPackage;

import Physics.Circle;
import Physics.LineSegment;

import java.awt.*;
import java.util.Set;

public class GFlipper implements Gizmo{

    private double angleDegrees;
    private boolean isLeft;
    private boolean isActivated;
    private int xPosition;
    private int yPosition;
    final private double coefficent = 1;

    public GFlipper(int xPosition, int yPosition, boolean isLeft){
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.isLeft = isLeft;
    }

    public void setAngleDegrees(double angleDegrees) {
        this.angleDegrees = angleDegrees;
    }

    @Override
    public String getGizmoType() {
        if(isLeft) {
            return "Left Flipper";
        }else{
            return "Right Flipper";
        }
    }

    @Override
    public int getStartxPosition() {
        return xPosition;
    }

    @Override
    public int getStartyPosition() {
        return yPosition;
    }

    @Override
    public int getEndxPosition() {
        return xPosition;
    }

    @Override
    public int getEndyPosition() {
        return yPosition;
    }

    @Override
    public Color getColour() {
        return null;
    }

    @Override
    public double getRotation() {
        return angleDegrees;
    }

    @Override
    public Set<LineSegment> getComposingLines() {
        return null;
    }

    @Override
    public double getReflectionCoef() {
        return coefficent;
    }

    @Override
    public Set<Circle> getComposingCircles() {
        return null;
    }
}
