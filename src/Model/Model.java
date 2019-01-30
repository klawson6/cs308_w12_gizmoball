package Model;

import Physics.LineSegment;

import java.util.HashSet;
import java.util.Observer;

public class Model{

    private double gravity = 10;
    private double friction = 10;
    private LineSegment[] borders = new LineSegment[4];
    private HashSet<Gizmo> gizmoList = new HashSet<>();
    private Ball ball = new Ball();
    private HashSet<Observer> observer;

    public Model(){
        observer = new HashSet<>();


    }

    public void addObserver(Observer o){
        observer.add(o);
    }


    public void addGizmo(String Type, int x, int y){


    }

    public void moveBall() {

    }

}
