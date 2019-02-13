package ModelPackage;

import Physics.Angle;
import Physics.Circle;
import Physics.Vect;

public class Ball implements IBall{

    // Composing circle that contains the position of the ball.
    private Circle circle;
    // The velocity of the ball, has speed and direction.
    private Vect velocity;

    private Boolean stopped;

    private static final double ballRadius = 0.25;

    public Ball(double xPosition, double yPosition, double xVelocity, double yVelocity) {
        // Build the composing circle of the playing ball with initial position and required diameter of 0.5L.
        circle = new Circle(xPosition, yPosition, ballRadius);
        // Initialise the velocity field.
        // Pass in the x and y components of the velocity.
        // With this information it will calculate the resultant speed(length in the Vect class) and direction once relevant getters are called.
        velocity = new Vect(xVelocity, yVelocity);
        stopped = false;
    }

    // Return the speed component of the velocity of this ball.
    public double getSpeed() {
        return velocity.length();
    }

    // Return the angle component of the velocity of this ball.
    public Angle getAngle() {
        return velocity.angle();
    }

    // Return the current position of this ball as a Vect.
    public Vect getPos() {
        return circle.getCenter();
    }
    /**
     * @effects Returns the corresponding Velocity object of the ball
     */
    public Vect getVelocity(){
        return velocity;
    }

    /**
     * @effects Returns the corresponding Circle object of the ball
     */
    public Circle getCircle(){
        return circle;
    }

    public void setCircle(double x, double y){
        circle = new Circle(x,y,ballRadius);
        //System.out.println("x=" + x + "y=" + y);

    }
    /**
     * @requires velocity is not null
     * @modifies this
     * @effects Sets the current Vect velocity of this ball to the new Vect velocity given.
     *          Returns true if this changed as a result, and returns false if it did not
     * @param velocity
     */
    public boolean modifyVelocity(Vect velocity) {
        if (velocity != null) {
            this.velocity = velocity;
            return true;
        } else
            return false;
    }

    /**
     * @requires 0 < moveTime < 0.05
     * @modifies this
     * @effects Moves this ball by replacing its composing circle object with a new one.
     *          The position of this new composing circle is computed using the velocity of this ball and the time for which is should move linearly.
     *          Returns the Vect position of the new composing circle if this changed as a result, and returns false if it did not.
     * @param moveTime
     */
    public Vect moveBall (double moveTime){
        if (0 < moveTime && moveTime < 0.05){
            Vect distanceVector = new Vect(velocity.angle(), velocity.length()*moveTime);
            //circle = new Circle(circle.getCenter().x()+distanceVector.x(),circle.getCenter().y()+distanceVector.y(), 0.25);
            circle = new Circle(circle.getCenter().plus(distanceVector), 0.25);
            return circle.getCenter();
        } else {
            return null;
        }
    }
    public void stopBall(){
        stopped = true;
    }
    public void startBall(){
        stopped = false;
    }

    public boolean isStopped(){
        return stopped;
    }
    @Override
    public double getXPosition() {
       return circle.getCenter().x();
    }

    @Override
    public double getYPosition() {
        return circle.getCenter().y();
    }
}
