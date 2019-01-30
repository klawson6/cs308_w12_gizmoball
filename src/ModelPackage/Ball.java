package ModelPackage;

import Physics.Circle;
import Physics.Vect;

public class Ball {

    private Circle circle;
    private Vect v;
    private double xPosition;
    private double yPosition;

    public Ball(double xPosition, double yPosition,double radius){

        circle = new Circle(xPosition,yPosition,radius);

    }


}
