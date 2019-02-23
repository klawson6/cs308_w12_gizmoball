package ModelPackage;

import Physics.Circle;
import Physics.LineSegment;
import javafx.scene.input.KeyEvent;
import sun.plugin2.util.ParameterNames;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class GSquare extends Gizmo {

    private final double coefficent = 1;
    private int xPos;
    private int yPos;
    private Set<LineSegment> composingLines = super.composingLines;
    private Set<Circle> composingCircles = super.composingCircles;
    private Color defaultColor = Color.RED;

    //Constructor that gives id, if none given
    public GSquare(int xPos, int yPos){
        setxPosition(xPos);
        setyPosition(yPos);
        setCoefficent(coefficent);
        setColor(defaultColor);
        this.xPos = xPos;
        this.yPos = yPos;
        id = "S" + xPos + yPos;
        setId(id);
        addLines();
        addCircles();
    }

    //Constructor that sets all fields
    public GSquare(int xPos, int yPos, String id){
        setxPosition(xPos);
        setyPosition(yPos);
        setCoefficent(coefficent);
        setColor(defaultColor);
        setId(id);
        this.xPos = xPos;
        this.yPos = yPos;
        addLines();
        addCircles();
    }

    //Returns type of gizmo
    public String getGizmoType() {
        return "Square";
    }

    //Returns end positions of current gizmo
    public int getEndxPosition() {
        return xPos;
    }
    public int getEndyPosition() {
        return yPos;
    }

    //TODO carry out actions
    //Will carry out gizmo's standard action
    public void activate() {}

    //TODO stop actions
    //Will stop gizmo's action
    public void deactivate() {}

    @Override
    //gets current rotation of gizmo
    public int getRotation() {
        return 0;
    }

    //Does nothing for square
    public boolean rotate() {return false;}

    private void addLines(){
        //Add one to end as we get top left co-ordinate which will be one short of draw
        //Add one to compensate for this

        LineSegment topLine = new LineSegment(xPos,yPos,xPos+1,yPos);
        LineSegment leftLine = new LineSegment(xPos,yPos,xPos,yPos+1);
        LineSegment rightLine = new LineSegment(xPos+1,yPos,xPos+1,yPos+1);
        LineSegment bottomLine = new LineSegment(xPos,yPos+1,xPos+1,yPos+1);

        composingLines.add(topLine);
        composingLines.add(leftLine);
        composingLines.add(rightLine);
        composingLines.add(bottomLine);
    }

    private void addCircles(){

        Circle topLeft = new Circle(xPos,yPos,0);
        Circle topRight = new Circle(xPos+1,yPos,0);
        Circle bottomLeft = new Circle(xPos,yPos+1,0);
        Circle bottomRight = new Circle(xPos+1,yPos+1,0);

        composingCircles.add(topLeft);
        composingCircles.add(topRight);
        composingCircles.add(bottomLeft);
        composingCircles.add(bottomRight);

    }
}
