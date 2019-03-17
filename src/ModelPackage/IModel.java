package ModelPackage;

import javafx.scene.input.KeyEvent;

import java.io.File;
import java.util.Observer;
import java.util.Set;

public interface IModel  {

    boolean createGizmo(GizmoType type, int xStart, int yStart, int xEnd, int yEnd,String id);
    boolean createGizmo(GizmoType type, int xStart, int yStart, int xEnd, int yEnd);
    Set<IGizmo> getGizmoList();
    IGizmo getGizmo(int xPos, int yPos);
    boolean moveGizmo(int xPos, int yPos, int newxPos, int newyPos);
    void deleteGizmo(int xPos, int yPos);
    boolean rotate(int xPos, int yPos);
    void activate(IGizmo gizmo);

    boolean createBall(double xPos, double yPos,double xVelocity, double yVelocity);
    IBall getBall();
    void moveBall();
    boolean moveBallPostion(IBall ball, double newxPos, double newyPos);

    boolean addKeyConnection(int xPos, int Pos, KeyEvent key);
    boolean addGizmoConnection(int xPosofSelectedGizmo, int yPosfSelectedGizmo, int xPosofTargetGizmo, int yPosofTargetGizmo);
    boolean removeKeyConnection(int xPos,int yPos, KeyEvent key);
    boolean removeGizmoConnection(int xPosofSelectedGizmo, int yPosfSelectedGizmo, int xPosofTargetGizmo, int yPosofTargetGizmo);

    void addObserver(Observer observer);

    void save(File path);
    Model load(File path);

    void play();
    void stop();
    void tick();
    void reset();

    boolean deleteBall(double x, double y);
}
