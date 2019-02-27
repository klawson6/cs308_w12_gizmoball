package ModelPackage;

import Physics.Circle;
import Physics.LineSegment;
import Physics.Vect;
import javafx.scene.input.KeyEvent;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class GTriangle extends Gizmo {

    private rotations orientation;
    final private double coefficent = 1;
    private int xPosition;
    private int yPosition;
    private Set<LineSegment> composingLines = super.composingLines;
    private Set<Circle> composingCircles = super.composingCircles;
    private Color defaultColor = Color.BLUE;
    private String id;

    // The enums to represent the fixed 90 degree rotations
    // Specified by the corner that is opposite the triangle hypotenuse.
    private enum rotations {
        NWcorner, NEcorner, SEcorner, SWcorner
    }

    public GTriangle(int xPosition, int yPosition) {
        setxPosition(xPosition);
        setyPosition(yPosition);
        setCoefficent(coefficent);
        setColor(defaultColor);
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        id = "T" + xPosition + yPosition;
        setId(id);
        orientation = rotations.NWcorner;
        addLines();
        addCircles();
    }

    public GTriangle(int xPosition, int yPosition, String id) {

        setxPosition(xPosition);
        setyPosition(yPosition);
        setCoefficent(coefficent);
        setColor(defaultColor);
        setId(id);

        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.id = id;

        orientation = rotations.NWcorner; // Default orientation should be corner opposite hypotenuse is in the NW corner.
        addLines(); // Build composing lines
        addCircles(); // Build composing circles
    }

    public GizmoType getGizmoType() {
        return GizmoType.TRIANGLE;
    }

    public int getEndxPosition() {
        return xPosition;
    }

    public int getEndyPosition() {
        return yPosition;
    }

    // Cycle through the enum values for the orientation of this triangle.
    // A rotation represents a 90 degree clockwise change.
    // Calls methods to construct new composing shapes.
    private void rotateRight() {
        switch (orientation) {
            case NWcorner:
                orientation = rotations.NEcorner;
                addCircles();
                addLines();
                break;
            case NEcorner:
                orientation = rotations.SEcorner;
                addCircles();
                addLines();
                break;
            case SEcorner:
                orientation = rotations.SWcorner;
                addCircles();
                addLines();
                break;
            case SWcorner:
                orientation = rotations.NWcorner;
                addCircles();
                addLines();
                break;
        }
    }

    // Will likely never be used, but it is here for use if needed
    public void rotateLeft() {
        switch (orientation) {
            case NWcorner:
                orientation = rotations.SWcorner;
                addCircles();
                addLines();
                break;
            case SWcorner:
                orientation = rotations.SEcorner;
                addCircles();
                addLines();
                break;
            case SEcorner:
                orientation = rotations.NEcorner;
                addCircles();
                addLines();
                break;
            case NEcorner:
                orientation = rotations.NWcorner;
                addCircles();
                addLines();
                break;
        }
    }

    // Based on the orientation enum, construct new composing circles for this triangle to be represented by.
    private void addCircles() {
        composingCircles.clear(); // Remove old composing circles
        switch (orientation) {
            case NWcorner:
                composingCircles.add(new Circle(xPosition, yPosition, 0));
                composingCircles.add(new Circle(xPosition + 1, yPosition, 0));
                composingCircles.add(new Circle(xPosition, yPosition + 1, 0));
                break;
            case NEcorner:
                composingCircles.add(new Circle(xPosition + 1, yPosition, 0));
                composingCircles.add(new Circle(xPosition, yPosition, 0));
                composingCircles.add(new Circle(xPosition + 1, yPosition + 1, 0));
                break;
            case SEcorner:
                composingCircles.add(new Circle(xPosition + 1, yPosition, 0));
                composingCircles.add(new Circle(xPosition, yPosition + 1, 0));
                composingCircles.add(new Circle(xPosition + 1, yPosition + 1, 0));
                break;
            case SWcorner:
                composingCircles.add(new Circle(xPosition, yPosition, 0));
                composingCircles.add(new Circle(xPosition, yPosition + 1, 0));
                composingCircles.add(new Circle(xPosition + 1, yPosition + 1, 0));
                break;
            default:
                //TODO add an exception?
                System.out.println("ENUM WAS WRONG");
                break;
        }
    }

    // Based on the orientation enum, construct new composing lines for this triangle to be represented by.
    private void addLines() {
        composingLines.clear(); // Remove old composing lines
        switch (orientation) {
            case NWcorner:
                composingLines.add(new LineSegment(new Vect(xPosition, yPosition), new Vect(xPosition + 1, yPosition)));
                composingLines.add(new LineSegment(new Vect(xPosition + 1, yPosition), new Vect(xPosition, yPosition + 1)));
                composingLines.add(new LineSegment(new Vect(xPosition, yPosition), new Vect(xPosition, yPosition + 1)));
                break;
            case NEcorner:
                composingLines.add(new LineSegment(new Vect(xPosition, yPosition), new Vect(xPosition + 1, yPosition)));
                composingLines.add(new LineSegment(new Vect(xPosition + 1, yPosition), new Vect(xPosition + 1, yPosition + 1)));
                composingLines.add(new LineSegment(new Vect(xPosition + 1, yPosition + 1), new Vect(xPosition, yPosition)));
                break;
            case SEcorner:
                composingLines.add(new LineSegment(new Vect(xPosition + 1, yPosition), new Vect(xPosition + 1, yPosition + 1)));
                composingLines.add(new LineSegment(new Vect(xPosition + 1, yPosition + 1), new Vect(xPosition, yPosition + 1)));
                composingLines.add(new LineSegment(new Vect(xPosition, yPosition + 1), new Vect(xPosition + 1, yPosition)));
                break;
            case SWcorner:
                composingLines.add(new LineSegment(new Vect(xPosition, yPosition), new Vect(xPosition + 1, yPosition + 1)));
                composingLines.add(new LineSegment(new Vect(xPosition + 1, yPosition + 1), new Vect(xPosition, yPosition + 1)));
                composingLines.add(new LineSegment(new Vect(xPosition, yPosition + 1), new Vect(xPosition, yPosition)));
                break;
            default:
                //TODO add an exception?
                System.out.println("ENUM WAS WRONG");
                break;
        }
    }
    // Returns the relevant integer to represent the angle of rotation.
    // Used for drawing the triangles on the GUI
    public int getRotation() {
        switch (orientation) {
            case NWcorner:
                return 0;
            case NEcorner:
                return 90;
            case SEcorner:
                return 180;
            case SWcorner:
                return 270;
            default:
                return 0;
        }
    }

    // Default rotation action is to rotate the object 90 degrees clockwise.
    @Override
    public boolean rotate() {
        rotateRight();
        return true;
    }

    @Override
    public void activate(){
        //Does nothing for Triangle
    }

    @Override
    public void deactivate() {

    }
}
