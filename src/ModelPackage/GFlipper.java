package ModelPackage;

import Physics.Circle;
import Physics.LineSegment;
import javafx.scene.paint.Color;

import java.util.Set;

public class GFlipper extends Gizmo{

    private int angleDegrees;
    private boolean isLeft;
    private boolean isActivated;
    private int xPosition;
    private int yPosition;
    final private double coefficent = 1;
    private String id;

    private Set<LineSegment> composingLines = super.composingLines;
    private Set<Circle> composingCircles = super.composingCircles;

    private Color defaultColor = Color.YELLOW;

    //Flippers are split into left or right flippers through boolean parameter

    //TODO Still to figure out how flipper line and circles will work
    //Constructor that gives id, if none given
    public GFlipper(int xPosition, int yPosition, boolean isLeft){

        setxPosition(xPosition);
        setyPosition(yPosition);
        setCoefficent(coefficent);
        setColor(defaultColor);

        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.isLeft = isLeft;
        if(isLeft){
            id = "LF" + xPosition + yPosition;
        }else{
            id = "RF" + xPosition + yPosition;
        }

        setId(id);

    }
    //Constructor that sets all fields
    public GFlipper(int xPosition, int yPosition, boolean isLeft, String id){

        setxPosition(xPosition);
        setyPosition(yPosition);
        setCoefficent(coefficent);
        setColor(defaultColor);
        setId(id);

        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.isLeft = isLeft;
        this.id = id;
    }

    //TODO add flipper collision detection
    private void addCircles(){

    }

    private void addLines(){

    }

    public void setAngleDegrees(int angleDegrees) {
        this.angleDegrees = angleDegrees;
    }


    public GizmoType getGizmoType() {
        if(isLeft) {
            return GizmoType.LEFTFLIPPER;
        }else{
            return GizmoType.RIGHTFLIPPER;
        }
    }


    public int getEndxPosition() {
        return xPosition;
    }


    public int getEndyPosition() {
        return yPosition;
    }

    public int getRotation() {
        return angleDegrees;
    }


    @Override
    public boolean rotate() {
        //Does nothing for flipper
        return false;
    }

    @Override
    public void activate(){
        //FIXME rotate flipper?

    }

    @Override
    public void deactivate() {

    }
}
