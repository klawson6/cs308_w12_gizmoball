package ModelPackage;

import Physics.Angle;
import Physics.Circle;
import Physics.Geometry;
import Physics.LineSegment;
import javafx.scene.paint.Color;

import java.util.ArrayList;
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
            this.xPosition = xPosition;
            this.yPosition = yPosition;
        setCoefficent(coefficent);
        setColor(defaultColor);


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

        setCoefficent(coefficent);
        setColor(defaultColor);
        setId(id);

        addCircles();
        addLines();


            setxPosition(xPosition);
            setyPosition(yPosition);
            this.xPosition = xPosition;
            this.yPosition = yPosition;
            this.id = id;
    }

    //TODO add flipper collision detection
    private void addCircles(){

       // if(isLeft) {

            composingCircles.clear();

            Circle circle = new Circle(xPosition + 0.5, yPosition + 0.5, 0.5);
            Circle circle1 = new Circle(xPosition + 0.5, yPosition + 1.5, 0.5);

            if(isLeft){
                circle1 = Geometry.rotateAround(circle1,circle.getCenter(),new Angle(-Math.toRadians(getRotation())));
            }else{
                circle1 = Geometry.rotateAround(circle1,circle.getCenter(),new Angle(Math.toRadians(getRotation())));
            }

            composingCircles.add(circle);
            composingCircles.add(circle1);
//        }else{
//
//            Circle circle = new Circle(xPosition + 0.5, yPosition + 0.5, 0.5);
//            Circle circle1 = new Circle(xPosition + 0.5, yPosition + 1.5, 0.5);
//
//            composingCircles.add(circle);
//            composingCircles.add(circle1);
//        }

    }

    private void addLines(){

     //   if(isLeft) {

            composingLines.clear();
            double topX = xPosition;
            double topY = yPosition + 0.5;
            double bottomX = xPosition;
            double bottomY = yPosition + 1.5;

            LineSegment lineSegment = new LineSegment(topX, topY, bottomX, bottomY);
            LineSegment lineSegment1 = new LineSegment(topX + 1, topY, bottomX + 1, bottomY);

            composingLines.add(lineSegment);
            composingLines.add(lineSegment1);

//        }
//        else{
//            double topX = xPosition ;
//            double topY = yPosition + 0.5;
//            double bottomX = xPosition;
//            double bottomY = yPosition + 1.5;
//
//            LineSegment lineSegment = new LineSegment(topX, topY, bottomX, bottomY);
//            LineSegment lineSegment1 = new LineSegment(topX + 1, topY, bottomX + 1, bottomY);
//
//            composingLines.add(lineSegment);
//            composingLines.add(lineSegment1);
//        }



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
        if(isLeft)
            return xPosition+1;
        else
            return xPosition-1;
    }


    public int getEndyPosition() {
       // if(isLeft)
            return yPosition+1;
//        else
//            return yPosition;
    }

    public int getRotation() {
        return angleDegrees;
    }

    @Override
    public void move(int newxPos, int newyPos) {
        xPosition = newxPos;
        yPosition = newyPos;
        setxPosition(xPosition);
        setyPosition(yPosition);
        composingLines.clear();
        composingCircles.clear();
        addCircles();
        addLines();
    }


    @Override
    public boolean rotate() {
        //Does nothing for flipper

        for(int i=0;i<90;i++){
            angleDegrees++;
            addCircles();
            addLines();
        }

        return false;
    }

    @Override
    public void activate(){
        //FIXME rotate flipper?
        rotate();
    }

    @Override
    public void deactivate() {

    }
}
