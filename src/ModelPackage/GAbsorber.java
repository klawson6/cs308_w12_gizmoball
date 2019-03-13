package ModelPackage;

import Physics.Circle;
import Physics.LineSegment;
import Physics.Vect;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.Set;

public class GAbsorber extends Gizmo {

    private int xStart;
    private int yStart;
    private int xEnd;
    private int yEnd;
    final private double coefficent = 0;
    private Set<LineSegment> composingLines = super.composingLines;
    private Set<Circle> composingCircles = super.composingCircles;
    private LinkedList<Ball> absorbedBall = new LinkedList<>();
    private Color defaultColor = Color.MAGENTA;
    private String id;

    //Constructor that gives id, if none given
    public GAbsorber(int xStart, int yStart, int xEnd, int yEnd){

        setxPosition(xStart);
        setyPosition(yStart);
        setCoefficent(coefficent);
        setColor(defaultColor);

        this.xStart = xStart;
        this.yStart = yStart;
        this.xEnd = xEnd;
        this.yEnd = yEnd;
        id = "A" + xStart + yStart + xEnd + yEnd;
        setId(id);

        addLines();
        addCircles();

    }

    //Constructor that sets all fields
    public GAbsorber(int xStart, int yStart, int xEnd, int yEnd, String id){

        setxPosition(xStart);
        setyPosition(yStart);
        setCoefficent(coefficent);
        setColor(defaultColor);
        setId(id);

        this.xStart = xStart;
        this.yStart = yStart;
        this.xEnd = xEnd;
        this.yEnd = yEnd;
        this.id = id;


        addLines();
        addCircles();

    }


    //Getters

    public GizmoType getGizmoType() {
        return GizmoType.ABSORBER;
    }

    public int getEndxPosition() {
        return xEnd;
    }

    public int getEndyPosition() {
        return yEnd;
    }

    @Override
    public int getRotation() {
        return 0;
    }

    @Override
    //Rotates gizmo 90 degrees
    public boolean rotate() {
        //Does nothing for absorber
        return false;
    }



    private void addCircles(){

        Circle topLeft = new Circle(xStart,yStart,0);
        Circle topRight = new Circle(xEnd,yStart,0);
        Circle bottomLeft = new Circle(xStart,yEnd,0);
        Circle bottomRight = new Circle(xEnd,yEnd,0);

        composingCircles.add(topLeft);
        composingCircles.add(topRight);
        composingCircles.add(bottomLeft);
        composingCircles.add(bottomRight);

    }

    private void addLines(){
        //Add one to end as we get top left co-ordinate which will be one short of draw
        //Add one to compensate for this

        LineSegment topLine = new LineSegment(xStart,yStart,xEnd,yStart);
        LineSegment leftLine = new LineSegment(xStart,yStart,xStart,yEnd);
        LineSegment rightLine = new LineSegment(xEnd,yStart,xEnd,yEnd);
        LineSegment bottomLine = new LineSegment(xStart,yEnd,xEnd,yEnd);

        composingLines.add(topLine);
        composingLines.add(leftLine);
        composingLines.add(rightLine);
        composingLines.add(bottomLine);
    }
    

    //Sets ball currently in the absorber
    public void addAbsorbedBall(Ball b){

        absorbedBall.addLast(b);
        moveAbsorbedBall(b);

    }

    private void moveAbsorbedBall(Ball ball){


        ball.setCircle(getEndxPosition() - 0.75, getEndyPosition() -0.75);
        ball.setVelocity(0,0);

        ball.stopBall();

    }
    @Override
    //Launches ball on absorber
    public void activate() {
        if (!absorbedBall.isEmpty()) {
            Ball ball = absorbedBall.removeFirst();
            double newX = getEndxPosition()- 0.5;
            double newY = getStartyPosition() + -0.5;
              ball.setCircle(newX, newY);
              ball.modifyVelocity(new Vect(0, -50));
                ball.startBall();

        }
    }

    public LinkedList<Ball> getAbsorberBalls(){
        return absorbedBall;
    }

    @Override
    //Stops action
    public void deactivate() {
//        if (!absorbedBall.isEmpty()) {
//            Ball ball = absorbedBall.removeFirst();
//            ball.setCircle(getEndxPosition() - 0.5, getEndyPosition() - 1);
//            ball.modifyVelocity(new Vect(0, -50));
//            System.out.println(ball.getVelocity());
//            ball.startBall();
//
//        }

    }
}
