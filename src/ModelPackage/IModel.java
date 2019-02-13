package ModelPackage;

import javafx.beans.Observable;

import java.util.HashSet;
import java.util.List;
import java.util.Observer;
import java.util.Set;

public interface IModel  {

    HashSet<Gizmo> getGizmoList();

    Ball getBall();

    void addObserver(Observer observer);
   void  deleteObserver(Observer o);

    void addBall(Ball ball);

    void addGizmo(Gizmo g);

    void moveBall();

    void activateLeftFlippers();

    void activateRightFlippers();

    void deactivateLeftFlippers();

    void deactivateRightFlippers();

    void checkFlippers();

}
