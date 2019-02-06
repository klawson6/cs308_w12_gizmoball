package ModelPackage;

import Physics.Circle;
import Physics.LineSegment;

import java.awt.*;
import java.util.Set;

public class GAbsorber implements Gizmo {

    private int xStart;
    private int yStart;
    private int xEnd;
    private int yEnd;
    final private double coefficent = 1;

    public GAbsorber(int xStart, int yStart, int xEnd, int yEnd){

        this.xStart = xStart;
        this.yStart = yStart;
        this.xEnd = xEnd;
        this.yEnd = yEnd;

    }

    @Override
    public String getGizmoType() {
        return "Absorber";
    }

    @Override
    public int getStartxPosition() {
        return xStart;
    }

    @Override
    public int getStartyPosition() {
        return yStart;
    }

    @Override
    public int getEndxPosition() {
        return xEnd;
    }

    @Override
    public int getEndyPosition() {
        return yEnd;
    }

    @Override
    public Color getColour() {
        return null;
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
