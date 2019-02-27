package ModelPackage;

import Physics.Vect;

public class CollisionDetails {

    private double tuc;
    private Vect velo;
    private Gizmo gizmo;

    public CollisionDetails(double tuc, Vect velo,Gizmo g){

        this.tuc = tuc;
        gizmo = g;
        this.velo = velo;
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
}
