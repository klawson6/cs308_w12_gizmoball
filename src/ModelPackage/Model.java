package ModelPackage;

import Physics.Circle;
import Physics.Geometry;
import Physics.LineSegment;
import Physics.Vect;
import javafx.scene.input.KeyEvent;

import java.io.File;
import java.util.*;

import static java.lang.Math.abs;

public class Model extends Observable implements IModel {

    private double gravity = 25;
    private double friction = 10;
    private final double mu = 0.025;
    private final double mu2 = 0.025;

    private LineSegment[] borders = new LineSegment[4];
    private Set<Gizmo> gizmoList = new HashSet<>();
    private ArrayList<Ball> balls = new ArrayList<>();


    public Model() {

        //TopLine
        borders[0] = new LineSegment(0, 0, 20, 0);
        //LeftSide
        borders[1] = new LineSegment(0, 0, 0, 20);
        //RightSide
        borders[2] = new LineSegment(20, 0, 20, 20);
        //BottomLine
        borders[3] = new LineSegment(0, 20, 20, 20);

        //   ball = new Ball(15,15,45,50);
        //   addGizmo(new GAbsorber(0,0,20,4));

        //addGizmo(new GAbsorber(1,18,18,18));
    }

    public boolean createGizmo(GizmoType type, int xStart, int yStart, int xEnd, int yEnd, String id) {
        Gizmo gizmo;
        Gizmo location = (Gizmo) getGizmo(xStart, yStart);
        if (location != null) {
            return false;
        }
        switch (type) {
            case SQUARE:
                gizmo = new GSquare(xStart, yStart, id);
                break;
            case CIRCLE:
                gizmo = new GCircle(xStart, yStart, id);
                break;
            case TRIANGLE:
                gizmo = new GTriangle(xStart, yStart, id);
                break;
            case LEFTFLIPPER:
                gizmo = new GFlipper(xStart, yStart, true, id);
                break;
            case RIGHTFLIPPER:
                gizmo = new GFlipper(xStart, yStart, false, id);
                break;
            case ABSORBER:
                gizmo = new GAbsorber(xStart, yStart, xEnd, yEnd, id);
                break;
            default:
                return false;
        }

        gizmoList.add(gizmo);
        return true;
    }

    public boolean createGizmo(GizmoType type, int xStart, int yStart, int xEnd, int yEnd) {
        Gizmo gizmo;
        Gizmo location = (Gizmo) getGizmo(xStart, yStart);
        if (location != null) {
            return false;
        }
        switch (type) {
            case SQUARE:
                gizmo = new GSquare(xStart, yStart);
                break;
            case CIRCLE:
                gizmo = new GCircle(xStart, yStart);
                break;
            case TRIANGLE:
                gizmo = new GTriangle(xStart, yStart);
                break;
            case LEFTFLIPPER:
                gizmo = new GFlipper(xStart, yStart, true);
                break;
            case RIGHTFLIPPER:
                gizmo = new GFlipper(xStart, yStart, false);
                break;
            case ABSORBER:
                gizmo = new GAbsorber(xStart, yStart, xEnd, yEnd);
                break;
            default:
                return false;
        }

        gizmoList.add(gizmo);

        setChanged();
        notifyObservers();

        return true;
    }

    public Set<Gizmo> getModelGizmoList() {
        return gizmoList;
    }

    public Set<IGizmo> getGizmoList() {

        return (Set) gizmoList;
    }

    public Gizmo getGizmo(String id) {

        //Find the gizmo with the correct id
        for (Gizmo g : gizmoList) {
            if (g.getId().equals(id))
                return g;
        }


        return null;
    }

    public Ball getBall(double xPos, double yPos){

        for(Ball ball : balls){

            double ballx = ball.getXPosition();
            double bally = ball.getYPosition();

            double xlimit = ballx + ball.getCircle().getRadius();
            double xlimit2 = ballx - ball.getCircle().getRadius();

            double ylimit = bally + ball.getCircle().getRadius();
            double ylimit2 = bally - ball.getCircle().getRadius();

            //XPos greater than lower limit, lower than higher limit
            if((xPos<xlimit && xPos>xlimit2) && (yPos<ylimit && yPos>ylimit2) ){
                return ball;
            }

        }

        return null;

    }

    @Override
    public Gizmo getGizmo(int xPos, int yPos) {

        for (Gizmo g : gizmoList) {

            if ((xPos >= g.getStartxPosition() && xPos <= g.getEndxPosition()) && (yPos >= g.getStartyPosition() && yPos <= g.getEndyPosition())) {
                return g;
            }

        }

        return null;
    }

    @Override
    public boolean moveGizmo(int xPos, int yPos, int newxPos, int newyPos) {
        Gizmo gizmo = getGizmo(xPos, yPos);
        Gizmo location = getGizmo(newxPos, newyPos);
        if (location == null && gizmo != null) {
            gizmo.setxPosition(newxPos);
            gizmo.setyPosition(newyPos);
            setChanged();
            notifyObservers();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void deleteGizmo(int xPos, int yPos) {
        Gizmo gizmo = getGizmo(xPos, yPos);
        if (gizmo != null) {
            gizmoList.remove(gizmo);
            setChanged();
            notifyObservers();
        }
    }

    @Override
    public boolean rotate(int xPos, int yPos) {
        Gizmo gizmo = getGizmo(xPos, yPos);
        if(gizmo != null) {
            boolean change = gizmo.rotate();
            setChanged();
            notifyObservers();
            return change;
        }
        return false;
    }

    @Override
    public void activateGizmo(IGizmo g) {
        if(g != null){
            g.activate();
            setChanged();
            notifyObservers();
        }
    }

    @Override
    public boolean createBall(double xPos, double yPos, double xVelocity, double yVelocity) {
        //Get top left of where ball if placed
        //int removes all numbers after decimal, effectively rounding down

        //TODO need to add placement detection to ball

        int x = (int) xPos;
        int y = (int) yPos;

        Gizmo location = getGizmo(x, y);
        Ball ballocation = getBall(xPos,yPos);

        if (location == null && ballocation == null) {
            Ball ball = new Ball(xPos, yPos, xVelocity, yVelocity);
            balls.add(ball);
            setChanged();
            notifyObservers();
            return true;
        }
        return false;
    }

    // TODO Remove?
    @Override
    public IBall getBall() {
        return null;
    }

    public void addKeyConnection(KeyEvent keyEvent, Gizmo gizmo) {
        gizmo.addKeyBinding(keyEvent, "");
    }

    public void addGizmoConnection(Gizmo gizmoFrom, Gizmo gizmoTo) {
        gizmoFrom.addGizmoConnection(gizmoTo);
    }

    public void rotateGizmo(Gizmo gizmo) {
        gizmo.rotate();
    }


    public void addBall(Ball ball) {
        balls.add(ball);
        setChanged();
        notifyObservers();
    }

    public List<IBall> getBalls() {
        return (List) balls;
    }

    public List<Ball> getModelBalls() {
        return balls;
    }

    /**
     * @modifies: ball
     * @effects: if timeUntilCollision is less than 0.05(the move time for 20 fps),
     * ball moves for 0.05 seconds
     * else if timeUntilCollision is greater than or equal to 0.05
     * ball moves for 0.05 seconds and the ball velocity is modified by the collision
     */

    public void moveBall() {
        double moveTime = 0.05; //20fps

        for (Ball ball : balls) {

            CollisionDetails cd = timeUntilCollision(ball);
            double tuc = cd.getTuc();
            // System.out.println("Tuc" + tuc);

            if (tuc > moveTime) {
                moveBallForTime(moveTime, ball);


            } else {

                moveBallForTime(tuc, ball);
                ball.modifyVelocity(cd.getVelo());
                if (cd.getCollisionGizmo() != null) {
                    if (cd.getCollisionGizmo().getGizmoType().equals(GizmoType.ABSORBER)) {

                        GAbsorber absorber = (GAbsorber) cd.getCollisionGizmo();
//                        ball = new Ball(absorber.getEndxPosition() - 0.75, absorber.getStartyPosition() + 0.75, 0, 0);
//                        int counter = 0;
//                        System.out.println("colided with absorber");
//                        ball.setCircle(absorber.getEndxPosition()-0.75,absorber.getStartyPosition()+0.75);
//                        ball.setVelocity(0,0);
//                        ball.stopBall();

                        absorber.addAbsorbedBall(ball);

                        LinkedList<Ball> listofballs = absorber.getAbsorberBalls();

//                      for(int i =0;i<listofballs.size();i++){
//                          listofballs.get(i).setCircle(absorber.getEndxPosition()-0.75,absorber.getStartyPosition()+0.75);
//                          listofballs.get(i).setVelocity(0,0);
//                      }
                      //TODO add absorber to absorber connection activation
                    }

                    if(cd.getCollisionGizmo().getGizmoConnectionIds().size()>0){
                        for(String id: cd.getCollisionGizmo().getGizmoConnectionIds()){
                            getGizmo(id).activate();
                        }
                    }
                }



            }

            setChanged();
            notifyObservers();
        }
    }

    @Override
    public boolean moveBallPostion(IBall ball, double newxPos, double newyPos) {
        Ball ballToMove = (Ball) ball;

        int x = (int) newxPos;
        int y = (int) newyPos;

        Gizmo location = getGizmo(x, y);

        //If nothing there move ball
        if (location == null) {
            ballToMove.setCircle(newxPos,newyPos);
            return true;
        }
        return false;


    }

    @Override
    public boolean addKeyConnection(int xPos, int yPos, KeyEvent key) {
        Gizmo gizmo = getGizmo(xPos,yPos);
        if(gizmo == null){
            return false;
        }
        return gizmo.addKeyBinding(key,"");
    }

    @Override
    public boolean addGizmoConnection(int xPosofSelectedGizmo, int yPosfSelectedGizmo, int xPosofTargetGizmo, int yPosofTargetGizmo) {
        if(getGizmo(xPosofSelectedGizmo,yPosfSelectedGizmo) != null & getGizmo(xPosofTargetGizmo,yPosofTargetGizmo) != null)
       return getGizmo(xPosofSelectedGizmo,yPosfSelectedGizmo).addGizmoConnection(getGizmo(xPosofTargetGizmo,yPosofTargetGizmo));
        else
            return false;
    }

    @Override
    public boolean removeKeyConnection(int xPos, int yPos, KeyEvent key) {
        Gizmo gizmo = getGizmo(xPos,yPos);
        if(gizmo == null){
            return false;
        }
        return gizmo.removeKeyBinding(key);
    }

    @Override
    public boolean removeGizmoConnection(int xPosofSelectedGizmo, int yPosfSelectedGizmo, int xPosofTargetGizmo, int yPosofTargetGizmo) {
        Gizmo gizmo = getGizmo(xPosofSelectedGizmo,yPosfSelectedGizmo);
        if(gizmo == null){
            return false;
        }
        return gizmo.removeGizmoConnection(getGizmo(xPosofTargetGizmo,yPosofTargetGizmo));
    }

    @Override
    public void save(File path) {
        SaveFile saveFile = new SaveFile(path);
        saveFile.save(this);

    }

    @Override
    public Model load(File path) {
        LoadFile loadFile = new LoadFile(path);
        return loadFile.run();

    }

    @Override
    public void play() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void tick() {

    }

    @Override
    public boolean deleteBall(double x, double y) {
        Ball ball = getBall(x,y);
        System.out.println(ball);
        boolean outcome = balls.remove(ball);

        setChanged();
        notifyObservers();

        return outcome;
    }

    /**
     * @requires: time >= 0
     * @modifies: ball
     * @effects: Modifies the ball velocity by applying gravity for the specified duration 'time' in seconds
     * @param: time
     */
    public void applyGravity(double time, Ball ball) {
        if (!ball.isStopped()) {
            Vect newVelo = new Vect(ball.getVelocity().x(), (ball.getVelocity().y() + gravity * time));
            ball.modifyVelocity(newVelo);
        }
    }

    /**
     * @requires: time >= 0
     * @modifies: ball
     * @effects: Modifies the ball velocity by applying friction for the specified duration 'time' in seconds
     * @param: time
     */
    public void applyFriction(double time, Ball ball) {
        if (!ball.isStopped()) {
            double fricX = 1 - (mu * time) - (mu2 * abs(ball.getVelocity().x())) * time;
            double fricY = 1 - (mu * time) - (mu2 * abs(ball.getVelocity().y())) * time;

            Vect newVelo = new Vect(ball.getVelocity().x() * fricX, ball.getVelocity().y() * fricY);
            ball.modifyVelocity(newVelo);
        }
    }

    public void moveBallForTime(double time, Ball ball) {
        if (!ball.isStopped()) {
            applyFriction(time, ball);
            applyGravity(time, ball);

            double xPos = ball.getPos().x();
            double yPos = ball.getPos().y();
            double xVelo = ball.getVelocity().x();
            double yVelo = ball.getVelocity().y();
            ball.setCircle(xPos + xVelo * time, yPos + yVelo * time);
        }
    }

    /**
     * @effects: Returns a CollisionDetails object which contains the minimum time until collision of the ball
     * with any of the Gizmos on the board or any of the walls. The CollisionDetails object also contains
     * information about the modified velocity of the ball due to that collision.
     */

    public CollisionDetails timeUntilCollision(Ball ball) {

            double minTime = Double.MAX_VALUE;
            Vect newVelocity = ball.getVelocity();
            Gizmo collisionGizmo = null;


            for(int j = 0; j<balls.size();j++){
                if(!ball.equals(balls.get(j))) {
                    double current = Geometry.timeUntilBallBallCollision(ball.getCircle(),ball.getVelocity(),balls.get(j).getCircle(),
                            balls.get(j).getVelocity());
                    if(current< minTime){
                        minTime = current;
                        newVelocity = Geometry.reflectCircle(balls.get(j).getPos(),ball.getPos(),ball.getVelocity(),1.0);
                    }
                }


            }

            for (int i = 0; i < borders.length; i++) {
                double current = Geometry.timeUntilWallCollision(borders[i], ball.getCircle(), ball.getVelocity());
                if (current < minTime) {
                    minTime = current;
                    newVelocity = Geometry.reflectWall(borders[i], ball.getVelocity(), 1.0);
                }
            }
            for (Gizmo g : gizmoList) {

                //Check TUC for all composing line segments
                for (LineSegment l : g.getComposingLines()) {
                    double current = Geometry.timeUntilWallCollision(l, ball.getCircle(), ball.getVelocity());
                    if (current < minTime) {
                        minTime = current;
                        newVelocity = Geometry.reflectWall(l, ball.getVelocity(), g.getReflectionCoef());
                        collisionGizmo = g;
                    }
                }
                for (Circle c : g.getComposingCircles()) {
                    double current = Geometry.timeUntilCircleCollision(c, ball.getCircle(), ball.getVelocity());
                    if (current < minTime) {
                        minTime = current;
                        newVelocity = Geometry.reflectCircle(c.getCenter(), ball.getPos(), ball.getVelocity(), g.getReflectionCoef());
                        collisionGizmo = g;
                    }
                }

            }
            return new CollisionDetails(minTime, newVelocity, collisionGizmo);

    }
}
