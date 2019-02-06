package ModelPackage;

import Physics.Circle;
import Physics.LineSegment;

import java.awt.*;
import java.util.Set;

public class GTriangle implements Gizmo{

    private double rotationDegrees = 0;
    final private double coefficent = 1;
    private int xPosition;
    private int yPosition;

    public GTriangle(int xPosition, int yPosition){
        this.xPosition = xPosition;
        this.yPosition = yPosition;

    }

    public void setRotation(double angle){

        rotationDegrees = angle;

    }

    public void rotateRight(){

        rotationDegrees = rotationDegrees - 90;

    }

    public void rotateLeft(){

        rotationDegrees = rotationDegrees + 90;

    }


    @Override
    public String getGizmoType() {
        return "Triangle";
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
        return rotationDegrees;
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
