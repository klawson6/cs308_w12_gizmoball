package ModelPackage;

import Physics.Circle;
import Physics.LineSegment;

import java.awt.*;
import java.util.Set;

public class GCircle implements Gizmo {

    private final int radius = 1;
    final private double coefficent = 1;
    private int xPos;
    private int yPos;

    public GCircle(int xPos, int yPos){
        this.xPos = xPos;
        this.yPos = yPos;
    }

    @Override
    public String getGizmoType() {
        return "Circle";
    }

    @Override
    public int getStartxPosition() {
        return xPos;
    }

    @Override
    public int getStartyPosition() {
        return yPos;
    }

    @Override
    public int getEndxPosition() {
        return xPos;
    }

    @Override
    public int getEndyPosition() {
        return yPos;
    }

    @Override
    public Color getColour() {
        return null;
    }

    @Override
    public double getRotation() {
        return 0;
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
