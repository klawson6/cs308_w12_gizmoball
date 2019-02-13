package ModelPackage;

import Physics.Vect;

public class CollisionDetails {

    private double tuc;
    private Vect velo;

    public CollisionDetails(double tuc, Vect velo){

        this.tuc = tuc;
        this.velo = velo;
    }

    public Vect getVelo(){
        return velo;
    }

    public double getTuc(){
        return tuc;
    }
}
