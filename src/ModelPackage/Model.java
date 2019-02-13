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

import static java.lang.Math.abs;

public class Model extends Observable implements IModel {

    private double gravity = 25;
    private double friction = 10;
    private final double mu = 0.025;
    private final double mu2 = 0.025;

    private LineSegment[] borders = new LineSegment[4];
    private HashSet<Gizmo> gizmoList = new HashSet<>();
    private Ball ball;


    public Model(){

        //TopLine
        borders[0] = new LineSegment(0,0,20,0);
        //LeftSide
        borders[1] = new LineSegment(0,0,0,20);
        //RightSide
        borders[2] = new LineSegment(20,0,20,20);
        //BottomLine
        borders[3] = new LineSegment(0,20,20,20);

     //   ball = new Ball(15,15,45,50);
     //   addGizmo(new GAbsorber(0,0,20,4));

        //addGizmo(new GAbsorber(1,18,18,18));
    }


    public void addGizmo(Gizmo gizmo){

        gizmoList.add(gizmo);
        setChanged();
        notifyObservers();
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
        setChanged();
        notifyObservers();
        System.out.println("Notified:" + countObservers());
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
        if(!ball.isStopped()) {
            CollisionDetails cd = timeUntilCollision();
            double tuc = cd.getTuc();
            // System.out.println("Tuc" + tuc);

            if (tuc > moveTime) {
                moveBallForTime(moveTime);


            } else {

                moveBallForTime(tuc);
                ball.modifyVelocity(cd.getVelo());
            if (cd.getCollisionGizmo() != null) {
                if (cd.getCollisionGizmo().getGizmoType().equals("Absorber")) {
                    //((GAbsorber) cd.getCollisionGizmo()).moveAbsorbedBall();
                    GAbsorber absorber =  (GAbsorber) cd.getCollisionGizmo();
                    //   ball.setCircle(absorber.getEndxPosition()-0.5,absorber.getStartyPosition()-0.25);
                    ball = new Ball(absorber.getEndxPosition()-0.5,absorber.getStartyPosition()-0.25,0,0);
                    ball.stopBall();
                    absorber.setAbsorbedBall(ball);
                }
            }


            }
        }
        setChanged();
        notifyObservers();
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

         double fricX =1 - (mu * time) - (mu2 *abs( ball.getVelocity().x())) * time;
         double fricY = 1 - (mu * time) - (mu2 *abs(ball.getVelocity().y())) * time;

         Vect newVelo = new Vect(ball.getVelocity().x()*fricX, ball.getVelocity().y()*fricY);
         ball.modifyVelocity(newVelo);
    }

    public void moveBallForTime(double time){

        applyGravity(time);
        applyFriction(time);
        double xPos = ball.getPos().x();
        double yPos = ball.getPos().y();
        double xVelo = ball.getVelocity().x();
        double yVelo = ball.getVelocity().y();


        ball.setCircle(xPos+ xVelo*time,yPos + yVelo*time);

    }
    /**
     * @effects: Returns a CollisionDetails object which contains the minimum time until collision of the ball
     *               with any of the Gizmos on the board or any of the walls. The CollisionDetails object also contains
     *               information about the modified velocity of the ball due to that collision.
     */

    public CollisionDetails timeUntilCollision(){

        double minTime = Double.MAX_VALUE;
        Vect newVelocity = ball.getVelocity();
        Gizmo collisionGizmo = null;

        for(int i = 0 ;i< borders.length ; i++){
            double current = Geometry.timeUntilWallCollision(borders[i],ball.getCircle(),ball.getVelocity());
            if(current<minTime){
                minTime = current;
                newVelocity = Geometry.reflectWall(borders[i] ,ball.getVelocity(),1.0);
            }
        }
        for(Gizmo g: gizmoList){

                //Check TUC for all composing line segments
                for(LineSegment l: g.getComposingLines()) {
                    double current = Geometry.timeUntilWallCollision(l, ball.getCircle(), ball.getVelocity());
                    if (current < minTime) {
                        minTime = current;
                        newVelocity = Geometry.reflectWall(l, ball.getVelocity(), g.getReflectionCoef());
                        collisionGizmo = g;
                    }
                }
                for(Circle c: g.getComposingCircles()){
                        double current = Geometry.timeUntilCircleCollision(c, ball.getCircle(),ball.getVelocity());
                        if(current<minTime){
                            minTime = current;
                            newVelocity = Geometry.reflectCircle(c.getCenter(),ball.getPos(),ball.getVelocity(),g.getReflectionCoef());
                            collisionGizmo = g;
                        }
                    }

        }

  //  if(collisionGizmo != null) {
       // if (collisionGizmo.getGizmoType().equals("Absorber")) {
         //   System.out.println("CollidedAbsorber");
          //GAbsorber absorber =  (GAbsorber) collisionGizmo;
       //   ball.setCircle(absorber.getEndxPosition()-0.5,absorber.getStartyPosition()-0.25);
//            ball = new Ball(absorber.getEndxPosition()-0.5,absorber.getStartyPosition()-0.25,0,0);
//            ball.stopBall();
//                    absorber.setAbsorbedBall(ball);
           // System.out.println("Absorbed ball");
         //   collisionGizmo = null;
       // }
   // }
        return new CollisionDetails(minTime,newVelocity,collisionGizmo);
    }


}
