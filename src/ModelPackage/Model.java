package ModelPackage;

import Physics.LineSegment;

import java.util.HashSet;
import java.util.Observer;

public class Model{

    private double gravity = 10;
    private double friction = 10;
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

    }

    public void addBall(Ball ball){

    }

    public void moveBall() {

    }

}
