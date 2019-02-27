package ModelPackage;

import Physics.Circle;
import Physics.LineSegment;
import Physics.Vect;
import javafx.scene.input.KeyEvent;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class GCircle extends Gizmo {

    private final int radius = 1;
    final private double coefficent = 1;
    private int xPos;
    private int yPos;
    private String id;
    private Set<Circle> composingCircles = super.composingCircles;
    private Color defaultColor = Color.GREEN;

    //Constructor that gives id, if none given
    public GCircle(int xPos, int yPos){
        setxPosition(xPos);
        setyPosition(yPos);
        setCoefficent(coefficent);
        setColor(defaultColor);
        this.xPos = xPos;
        this.yPos = yPos;
        id = "C" + xPos + yPos;
        setId(id);
        addCircles();
    }

    //Constructor that sets all fields
    public GCircle(int xPos, int yPos, String id){
        setxPosition(xPos);
        setyPosition(yPos);
        setCoefficent(coefficent);
        setColor(defaultColor);
        setId(id);
        this.xPos = xPos;
        this.yPos = yPos;
        this.id = id;
        addCircles();
    }

    // Create the composing circle for the circle Gizmo to be placed on screen and drawn.
    private void addCircles(){
        Circle circle = new Circle(xPos + 0.5,yPos + 0.5,0.5); // For collision calculations in Physics, move the Circle xy position to the center.
        composingCircles.add(circle);
    }

    //Returns Gizmo Type
    public String getGizmoType() {
        return "Circle";
    }

    //Getters
    
    public int getEndxPosition() {
        return xPos;
    }

    
    public int getEndyPosition() {
        return yPos;
    }

    
    public int getRotation() {
        return 0;
    }

    @Override
    //Rotate gizmo 90 degrees
    public boolean rotate() {
        //Does Nothing for Circle
        return false;
    }
    @Override
    //Do gizmo default action
    public void activate(){
        //Does nothing for Circle
    }

    @Override
    //Sto gizmo action
    public void deactivate() {

    }
}
