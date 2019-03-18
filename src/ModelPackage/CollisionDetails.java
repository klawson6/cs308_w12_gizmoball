package ModelPackage;

import Physics.Vect;

public class CollisionDetails {

    private double tuc;
    private Vect velo;
    private Gizmo gizmo;
    private Ball ball;

    public CollisionDetails(double tuc, Vect velo,Gizmo g, Ball b){

        this.tuc = tuc;
        gizmo = g;
        this.velo = velo;
        this.ball = b;
    }

    public Vect getVelo(){
        return velo;
    }

    public double getTuc(){
        return tuc;
    }

    public Gizmo getCollisionGizmo(){
        return gizmo;
    }

    public Ball getCollisionBall(){
        return ball;
    }
}
