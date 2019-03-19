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
    private double mu = 0.025;
    private double mu2 = 0.025;

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

        Ball balllocation = getBallCheck(xStart,yStart);

        if(balllocation!=null){
            return false;
        }

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
                if(xStart == 19 || yStart == 19)
                    return false;
                //Check all surrounding squares for gizmos
                Gizmo location2 = (Gizmo) getGizmo(xStart+1,yStart);
                Gizmo location3 = (Gizmo) getGizmo(xStart,yStart+1);
                Gizmo location4 = (Gizmo) getGizmo(xStart+1,yStart+1);
                if(location2 != null || location3 != null || location4 != null)
                    return false;

                Ball location5 = getBallCheck(xStart,yStart+1);

                if (location5 != null)
                    return false;

                gizmo = new GFlipper(xStart, yStart, true, id);
                break;

            case RIGHTFLIPPER:
                //Checks the boundary conditions for the board and flipper taking into account possible rotation
                if(xStart == 0 || yStart == 19)
                    return false;
                //Check all  surrounding squares for gizmos
                location2 = (Gizmo) getGizmo(xStart-1,yStart);
                location3 = (Gizmo) getGizmo(xStart,yStart+1);
                location4 = (Gizmo) getGizmo(xStart-1,yStart+1);

                if(location2 != null || location3 != null || location4 != null)
                    return false;

                //Only need to make sure its not placed directly on a ball
                location5 = getBallCheck(xStart, yStart + 1);
                if (location5 != null)
                    return false;

                System.out.println("Created a right flipper at " + "X = " + xStart + " Y = " + yStart);
                gizmo = new GFlipper(xStart, yStart, false, id);
                break;

            case ABSORBER:
                for(int l = xStart;l<=xEnd;l++){
                    for(int k= yStart;k<=yEnd;k++){
                        location = (Gizmo) getGizmo(l,k);
                        location5 = getBallCheck(l,k);
//                        System.out.println("Checking X = "+ l + " Y= " + k);
                        if(location != null || location5 != null)
                            return false;
                    }
                }
                gizmo = new GAbsorber(xStart, yStart, xEnd, yEnd,id);
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

        Ball balllocation = getBallCheck(xStart,yStart);

        if(balllocation!=null){
            return false;
        }

        if (location != null) {
            System.out.println(location);
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
                //Checks the boundary conditions for the board and flipper taking into account possible rotation
                if (xStart == 19 || yStart == 19)
                    return false;
                //Check all surrounding squares for gizmos
                Gizmo location2 = (Gizmo) getGizmo(xStart + 1, yStart);
                Gizmo location3 = (Gizmo) getGizmo(xStart, yStart + 1);
                Gizmo location4 = (Gizmo) getGizmo(xStart + 1, yStart + 1);
                if (location2 != null || location3 != null || location4 != null)
                    return false;

                Ball location5 = getBallCheck(xStart,yStart+1);
                if (location5 != null)
                    return false;

                gizmo = new GFlipper(xStart, yStart, true);
                break;
            case RIGHTFLIPPER:

                //Checks the boundary conditions for the board and flipper taking into account possible rotation
                if (xStart == 0 || yStart == 19)
                    return false;
                //Check all  surrounding squares for gizmos
                location2 = (Gizmo) getGizmo(xStart - 1, yStart);
                location3 = (Gizmo) getGizmo(xStart, yStart + 1);
                location4 = (Gizmo) getGizmo(xStart - 1, yStart + 1);

                if (location2 != null || location3 != null || location4 != null)
                    return false;

                location5 = getBallCheck(xStart, yStart+1);
                if (location5 != null)
                    return false;

                System.out.println("Created a right flipper at " + "X = " + xStart + " Y = " + yStart);
                gizmo = new GFlipper(xStart, yStart, false);
                break;
            case ABSORBER:

                for(int l = xStart;l<=xEnd;l++){
                    for(int k= yStart;k<=yEnd;k++){
                        location = (Gizmo) getGizmo(l,k);
                        location5 = getBallCheck(l,k);
                        System.out.println("Checking X = "+ l + " Y= " + k);
                        if(location != null || location5 != null)
                            return false;
                    }
                }
                System.out.println("Start x : " + xStart);
                System.out.println("Start y : " + yStart);
                System.out.println("End x : " + yEnd);
                System.out.println("End y : " + xEnd);
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

    private Ball getBallCheck(int xPos, int yPos) {

        //Need to check square for an balls inside + radius being inside

        // xPos, yPos is top left corner need to do XPos to XPos + 1 and yPos to yPos + 1

        // minus ball raduis to detect overlap
        // add ball radius as well

        for (Ball ball : balls) {

            double xLowerLimit = xPos - ball.getCircle().getRadius();
            double xUpperLimit = xPos + 1 + ball.getCircle().getRadius();
            double yLowerLimit = yPos - ball.getCircle().getRadius();
            double yUpperLimit = yPos + 1 + ball.getCircle().getRadius();

            double ballx = ball.getXPosition();
            double bally = ball.getYPosition();

            if((ballx>xLowerLimit && ballx<xUpperLimit) && (bally>yLowerLimit && bally<yUpperLimit)){
                return ball;
            }

        }

        return null;

    }

    public Ball getBall(double xPos, double yPos) {

        for (Ball ball : balls) {

            double ballx = ball.getXPosition();
            double bally = ball.getYPosition();

            double xlimit = ballx + ball.getCircle().getRadius();
            double xlimit2 = ballx - ball.getCircle().getRadius();

            double ylimit = bally + ball.getCircle().getRadius();
            double ylimit2 = bally - ball.getCircle().getRadius();

            //XPos greater than lower limit, lower than higher limit
            if ((xPos < xlimit && xPos > xlimit2) && (yPos < ylimit && yPos > ylimit2)) {
                return ball;
            }

        }

        return null;

    }

    @Override
    public Gizmo getGizmo(int xPos, int yPos) {

        for (Gizmo g : gizmoList) {
            if (!g.getGizmoType().equals(GizmoType.RIGHTFLIPPER)) {
                if ((xPos >= g.getStartxPosition() && xPos <= g.getEndxPosition()) && (yPos >= g.getStartyPosition() && yPos <= g.getEndyPosition())) {
                    return g;
                }
            } else {
                if ((xPos <= g.getStartxPosition() && xPos >= g.getEndxPosition()) && (yPos >= g.getStartyPosition() && yPos <= g.getEndyPosition())) {
                    return g;
                }
            }


        }

        return null;
    }

    @Override
    public boolean moveGizmo(int xPos, int yPos, int newxPos, int newyPos) {
        Gizmo gizmo = getGizmo(xPos, yPos);
        Gizmo location = getGizmo(newxPos, newyPos);

        int Startx = gizmo.getStartxPosition();
        int Starty = gizmo.getStartyPosition();
        int Endx = gizmo.getEndxPosition();
        int Endy = gizmo.getEndyPosition();

        int xDifference = Math.max(Endx, Startx) - Math.min(Endx, Startx);
        int yDifference = Math.max(Endy, Starty) - Math.min(Endy, Starty);

        boolean clearArea = true;


        //Check area to move can fit gizmo

        HashSet<Gizmo> gizmos = new HashSet<>();

        for (int i = newxPos; i < newxPos + xDifference; i++) {
            for (int j = newyPos; j < newyPos + yDifference; j++) {
                if (getGizmo(i, j) != null) {
                    if (getGizmo(i, j) != gizmo) {
                        clearArea = false;
                    }
                }
                ;
            }

        }

        if ((location == null && gizmo != null || location == gizmo) && clearArea) {
            gizmo.move(newxPos, newyPos);
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
        if (gizmo != null) {
            boolean change = gizmo.rotate();
            setChanged();
            notifyObservers();
            return change;
        }
        return false;
    }

    @Override
    public void activateGizmo(IGizmo g) {
        if (g != null) {
            g.activate();
            setChanged();
            notifyObservers();
        }
    }

    @Override
    public void deactivateGizmo(IGizmo g) {
        if (g != null) {
            g.deactivate();
            setChanged();
            notifyObservers();
        }
    }

    @Override
    public boolean createBall(double xPos, double yPos, double xVelocity, double yVelocity) {
        //Get top left of where ball if placed
        //int removes all numbers after decimal, effectively rounding down

        //TODO need to add placement detection to ball

        double XUpperLimit = xPos+0.25;
        double YUpperLimit = yPos+0.25;
        double XLowerLimit = xPos-0.25;
        double YLowerLimit = yPos-0.25;

        int x = (int) xPos;
        int y = (int) yPos;

        if(XUpperLimit>x){
            if(YUpperLimit>y){
                if(getGizmo((int)XUpperLimit,(int)YUpperLimit) != null || getGizmo(x,y) != null){
                    return false;
                };
            }
            if(YLowerLimit<y){
                if(getGizmo((int)XUpperLimit,(int)YLowerLimit) != null || getGizmo(x,y) != null){
                    return false;
                }
            }
        }

        if(XLowerLimit<x){
            if(YUpperLimit>y){
                if(getGizmo((int)XLowerLimit,(int)YUpperLimit) != null || getGizmo(x,y) != null){
                    return false;
                };
            }
            if(YLowerLimit<y) {
                if (getGizmo((int) XLowerLimit, (int) YLowerLimit) != null || getGizmo(x, y) != null) {
                    return false;
                }
            }
        }

        Gizmo location;

        location = getGizmo(x, y);
        Ball ballocation = getBall(xPos, yPos);

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
    public IBall getBall(int startX, int startY) {

        return getBallCheck(startX,startY);

    }

    public void addKeyConnection(KeyEvent keyEvent, Gizmo gizmo) {

        if(gizmo!=null)
            gizmo.addKeyBinding(keyEvent, "");

    }

    public void addGizmoConnection(Gizmo gizmoFrom, Gizmo gizmoTo) {
        if(gizmoFrom!=null && gizmoTo!= null)
            gizmoFrom.addGizmoConnection(gizmoTo);
    }

    public void rotateGizmo(Gizmo gizmo) {
        if(gizmo!=null)
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
        double moveTime = 0.01; //20fps

        for (Ball ball : balls) {

            CollisionDetails cd = timeUntilCollision(ball);
            double tuc = cd.getTuc();
            // System.out.println("Tuc" + tuc);

            if (tuc > moveTime) {
                moveBallForTime(moveTime, ball);
                applyFriction(moveTime, ball);
                applyGravity(moveTime, ball);

            } else {

                moveBallForTime(tuc, ball);
                ball.modifyVelocity(cd.getVelo());
                applyFriction(tuc, ball);
                applyGravity(tuc, ball);
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

                    if (cd.getCollisionGizmo().getGizmoConnectionIds().size() > 0) {
                        for (String id : cd.getCollisionGizmo().getGizmoConnectionIds()) {
                            getGizmo(id).activate();
                        }
                    }
                }
                else if (cd.getCollisionBall() != null){
                    cd.getCollisionBall().modifyVelocity(Geometry.reflectCircle(cd.getCollisionBall().getCircle().getCenter(), ball.getCircle().getCenter(), ball.getVelocity(), 1));
                }
            }

            setChanged();
            notifyObservers();
        }
    }

    public void activate(IGizmo gizmo) {
        gizmo.activate();
        //Allows for animation of all actions even if no ball is in play
        setChanged();
        notifyObservers();
    }

    public void checkFlippers() {
        for (Gizmo g : gizmoList) {
            if (g.getGizmoType().equals(GizmoType.RIGHTFLIPPER)) {
                GFlipper flipper = ((GFlipper) g);
                if (flipper.isActivated()) {
                    flipper.rotate();
                } else {
                    flipper.antirotate();
                }
            } else if (g.getGizmoType().equals(GizmoType.LEFTFLIPPER)) {
                GFlipper flipper = ((GFlipper) g);
                if (flipper.isActivated()) {
                    flipper.rotate();
                } else {
                    flipper.antirotate();
                }
            }
        }
        setChanged();
        notifyObservers();
    }

    @Override
    public boolean moveBallPostion(IBall ball, double newxPos, double newyPos) {
        Ball ballToMove = (Ball) ball;

        int x = (int) newxPos;
        int y = (int) newyPos;

        Gizmo location = getGizmo(x, y);

        //If nothing there move ball
        if (location == null) {
            ballToMove.setCircle(newxPos, newyPos);
            return true;
        }
        return false;


    }

    @Override
    public boolean addKeyConnection(int xPos, int yPos, KeyEvent key) {
        Gizmo gizmo = getGizmo(xPos, yPos);
        if (gizmo == null) {
            return false;
        }
        return gizmo.addKeyBinding(key, "");
    }

    @Override
    public boolean addGizmoConnection(int xPosofSelectedGizmo, int yPosfSelectedGizmo, int xPosofTargetGizmo, int yPosofTargetGizmo) {
        if (getGizmo(xPosofSelectedGizmo, yPosfSelectedGizmo) != null & getGizmo(xPosofTargetGizmo, yPosofTargetGizmo) != null)
            return getGizmo(xPosofSelectedGizmo, yPosfSelectedGizmo).addGizmoConnection(getGizmo(xPosofTargetGizmo, yPosofTargetGizmo));
        else
            return false;
    }

    @Override
    public boolean removeKeyConnection(int xPos, int yPos, KeyEvent key) {
        Gizmo gizmo = getGizmo(xPos, yPos);
        if (gizmo == null) {
            return false;
        }
        return gizmo.removeKeyBinding(key);
    }

    @Override
    public boolean removeGizmoConnection(int xPosofSelectedGizmo, int yPosfSelectedGizmo, int xPosofTargetGizmo, int yPosofTargetGizmo) {
        Gizmo gizmo = getGizmo(xPosofSelectedGizmo, yPosfSelectedGizmo);
        if (gizmo == null) {
            return false;
        }
        return gizmo.removeGizmoConnection(getGizmo(xPosofTargetGizmo, yPosofTargetGizmo));
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

    public void reset() {
        for (Ball ball : balls) {
            ball.setCircle(ball.getStartingX(), ball.getStartingY());
            ball.startBall();
            ball.setVelocity(ball.getStartingVelocity().x(), ball.getStartingVelocity().y());
        }
    }

    @Override
    public boolean deleteBall(double x, double y) {
        Ball ball = getBall(x, y);
        System.out.println(ball);
        boolean outcome = balls.remove(ball);

        setChanged();
        notifyObservers();

        return outcome;
    }

    @Override
    public void setGravity(double value) {
        gravity = value;
    }

    public double getGravity(){
        return gravity;
    }

    @Override
    public void setMu(double value) {
        mu = value;
    }

    public double getMu(){
        return mu;
    }

    @Override
    public void setMu2(double value) {
        mu2 = value;
    }

    public double getMu2(){
        return mu2;
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
        Ball collisionBall = null;

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
        for (Ball b : balls) {
            double current = Geometry.timeUntilCircleCollision(b.getCircle(), ball.getCircle(), ball.getVelocity());
            if (current < minTime) {
                minTime = current;
                newVelocity = Geometry.reflectCircle(b.getCircle().getCenter(), ball.getPos(), ball.getVelocity(), 1);
                // b.modifyVelocity(Geometry.reflectCircle(ball.getCircle().getCenter(), b.getPos(), newVelocity, 1));
                collisionGizmo = null;
                collisionBall = b;
            }
        }
        return new CollisionDetails(minTime, newVelocity, collisionGizmo, collisionBall);

    }
}
