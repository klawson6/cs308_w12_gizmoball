package ModelPackage;

import Physics.Circle;
import javafx.scene.paint.Color;

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
    public GizmoType getGizmoType() {
        return GizmoType.CIRCLE;
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
    public void move(int newxPos, int newyPos) {
        xPos = newxPos;
        yPos = newyPos;
        setxPosition(xPos);
        setyPosition(yPos);
        composingCircles.clear();
        addCircles();
    }

    @Override
    //Rotate gizmo 90 degrees
    public boolean rotate() {
        //Does Nothing for Circle
        return false;
    }
    @Override
    public void activate(){
        double red = Math.random();
        double green = Math.random();
        double blue = Math.random();
        Color newColor = new Color(red,green,blue,1.0);
        setColor(newColor);
    }

    @Override
    //Sto gizmo action
    public void deactivate() {

    }
}
