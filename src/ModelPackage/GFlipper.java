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
        addCircles();
        addLines();

        setId(id);

    }
    //Constructor that sets all fields
    public GFlipper(int xPosition, int yPosition, boolean isLeft, String id){

        setxPosition(xPosition);
        setyPosition(yPosition);
        setCoefficent(coefficent);
        setColor(defaultColor);
        setId(id);

        addCircles();
        addLines();

        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.isLeft = isLeft;
        this.id = id;
    }

    //TODO add flipper collision detection
    private void addCircles(){

        if(isLeft) {

            Circle circle = new Circle(xPosition + 0.5, yPosition + 0.5, 0.5);
            Circle circle1 = new Circle(xPosition + 0.5, yPosition + 1.5, 0.5);

            composingCircles.add(circle);
            composingCircles.add(circle1);
        }else{

            Circle circle = new Circle(xPosition + 1.5, yPosition + 0.5, 0.5);
            Circle circle1 = new Circle(xPosition + 1.5, yPosition + 1.5, 0.5);

            composingCircles.add(circle);
            composingCircles.add(circle1);
        }

    }

    private void addLines(){

        if(isLeft) {

            double topX = xPosition;
            double topY = yPosition + 0.5;
            double bottomX = xPosition;
            double bottomY = yPosition + 1.5;

            LineSegment lineSegment = new LineSegment(topX, topY, bottomX, bottomY);
            LineSegment lineSegment1 = new LineSegment(topX + 1, topY, bottomX + 1, bottomY);

            composingLines.add(lineSegment);
            composingLines.add(lineSegment1);
        }
        else{
            double topX = xPosition + 1;
            double topY = yPosition + 0.5;
            double bottomX = xPosition + 1;
            double bottomY = yPosition + 1.5;

            LineSegment lineSegment = new LineSegment(topX, topY, bottomX, bottomY);
            LineSegment lineSegment1 = new LineSegment(topX + 1, topY, bottomX + 1, bottomY);

            composingLines.add(lineSegment);
            composingLines.add(lineSegment1);
        }



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

    public boolean isActivated() {
        return isActivated;
    }

    @Override
    public boolean rotate() {
        //Does nothing for flipper

        int rotation = angleDegrees + 5;
        if(rotation > 90){
            angleDegrees = 90;
        }else {
            angleDegrees = rotation;
        }

        return false;
    }

    public void antirotate(){
        int rotation = angleDegrees - 5;
        if(rotation < 0){
            angleDegrees = 0;
        }else {
            angleDegrees = rotation;
        }
    }


    @Override
    public void activate(){
        //FIXME rotate flipper?
            isActivated = true;
    }

    @Override
    public void deactivate() {
        isActivated = false;
    }
}
