package ModelPackage;

import Physics.Circle;
import Physics.Geometry;
import Physics.LineSegment;
import Physics.Vect;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;

public class Model extends Observable {

    private double gravity = 25;
    private double friction = 10;
    private final double mu = 0.025;
    private final double mu2 = 0.025;

    private LineSegment[] borders = new LineSegment[4];
    private HashSet<Gizmo> gizmoList = new HashSet<>();
    private Ball ball;
    private HashSet<Observer> observer;

    public Model(){
        observer = new HashSet<>();
    }

    public void addObserver(Observer o){
        observer.add(o);
    }


    public void addGizmo(Gizmo gizmo){

        gizmoList.add(gizmo);
        setChanged();
    }

    //TODO: should this be a return type of IGizmo?
    public HashSet<Gizmo> getGizmoList(){
        return gizmoList;
    }

    public void addKeyConnection(KeyEvent keyEvent, Gizmo gizmo){
        gizmo.addKeyBinding(keyEvent);
    }

    public void addGizmoConnection(Gizmo gizmoFrom, Gizmo gizmoTo){
        gizmoFrom.addGizmoConnection(gizmoTo);
    }

    public void RotateGizmo(Gizmo gizmo){
        gizmo.getRotation();
    }





    public void addBall(Ball ball){
        this.ball = ball;
    }

    public Ball getBall(){
        return ball;
    }

    /**
     * @modifies: ball
     * @effects: if timeUntilCollision is less than 0.05(the move time for 20 fps),
     *              ball moves for 0.05 seconds
     *          else if timeUntilCollision is greater than or equal to 0.05
     *              ball moves for 0.05 seconds and the ball velocity is modified by the collision
     */
    public void moveBall() {

        double moveTime = 0.05; //20fps
        CollisionDetails cd = timeUntilCollision();
        double tuc = cd.getTuc();

        if(tuc<moveTime){
            moveBallForTime(ball,moveTime);
        }

        else{
            moveBallForTime(ball,moveTime);
            ball.modifyVelocity(cd.getVelo());
        }

    }

    /**
     * @requires: time >= 0
     * @modifies: ball
     * @effects: Modifies the ball velocity by applying gravity for the specified duration 'time' in seconds
     * @param: time
     */
    public void applyGravity(double time){
        Vect newVelo = new Vect(ball.getVelocity().x(),(ball.getVelocity().y() + gravity*time));
        ball.modifyVelocity(newVelo);
    }

    /**
     * @requires: time >= 0
     * @modifies: ball
     * @effects: Modifies the ball velocity by applying friction for the specified duration 'time' in seconds
     * @param: time
     */
    public void applyFriction(double time){

         double fricX = ((1-mu*time) - (mu2*ball.getVelocity().x()*time));
         double fricY = ((1-mu*time) - (mu2*ball.getVelocity().y()*time));

         Vect newVelo = new Vect(ball.getVelocity().x()*fricX, ball.getVelocity().y()*fricY);
         ball.modifyVelocity(newVelo);
    }

    public void moveBallForTime(Ball b,double time){
        applyFriction(time);
        applyGravity(time);
        double xPos = b.getPos().x();
        double yPos = b.getPos().y();
        double xVelo = b.getVelocity().x();
        double yVelo = b.getVelocity().y();
      //  b.setxPosition(xPos + xVelo*time);
       // b.setyPosition(yPos + yVelo*time);

    }
    /**
     * @effects: Returns a CollisionDetails object which contains the minimum time until collision of the ball
     *               with any of the Gizmos on the board or any of the walls. The CollisionDetails object also contains
     *               information about the modified velocity of the ball due to that collision.
     */

    public CollisionDetails timeUntilCollision(){

        double minTime = Double.MAX_VALUE;
        Vect newVelocity = ball.getVelocity();

        for(int i = 0 ;i< borders.length ; i++){
            double current = Geometry.timeUntilWallCollision(borders[i],ball.getCircle(),ball.getVelocity());
            if(current<minTime){
                minTime = current;
                newVelocity = Geometry.reflectWall(borders[i] ,ball.getVelocity(),1.0);
            }
        }
        for(Gizmo g: gizmoList){

           // if(g.getClass().equals("GAbsorber")){
                //Check TUC for all composing line segments
                for(LineSegment l: g.getComposingLines()){
                    double current = Geometry.timeUntilWallCollision(l,ball.getCircle(), ball.getVelocity());
                    if(current<minTime){
                        minTime = current;
                        newVelocity = Geometry.reflectWall(l ,ball.getVelocity(),g.getReflectionCoef());
                    }

                    for(Circle c: g.getComposingCircles()){
                        current = Geometry.timeUntilCircleCollision(c, ball.getCircle(),ball.getVelocity());
                        if(current<minTime){
                            minTime = current;
                            newVelocity = Geometry.reflectCircle(c.getCenter(),ball.getPos(),ball.getVelocity(),g.getReflectionCoef());
                        }
                    }
                }

        }

        return new CollisionDetails(minTime,newVelocity);
    }
}
