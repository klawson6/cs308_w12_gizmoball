package ModelPackage;

import Physics.Circle;
import Physics.LineSegment;

import java.awt.*;
import java.util.HashSet;

public abstract class Gizmo{

    private int xPosition;
    private int yPosition;
    private Color colour;
    private HashSet<Circle> composingCircles;
    private HashSet<LineSegment> composingLines;

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public void setyPosition(int yPosition){
        this.yPosition = yPosition;
    }

    public void setColour(Color colour) {
        this.colour = colour;
    }

    public int getxPosition() {
        return xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public Color getColour() {
        return colour;
    }

    public HashSet<Circle> getComposingCircles() {
        return composingCircles;
    }

    public HashSet<LineSegment> getComposingLines() {
        return composingLines;
    }
}
