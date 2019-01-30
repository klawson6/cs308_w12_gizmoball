package ModelPackage;

public class GTriangle extends Gizmo{

    private double rotationDegrees = 0;

    public GTriangle(int xPosition, int yPosition){
        setPosition(xPosition,yPosition);
    }

    public void setPosition(int xPosition, int yPosition){
        super.setxPosition(xPosition);
        super.setyPosition(yPosition);
    }

    public void setRotation(double angle){

        rotationDegrees = angle;

    }

    public void rotateRight(){

        rotationDegrees = rotationDegrees - 90;

    }

    public void rotateLeft(){

        rotationDegrees = rotationDegrees + 90;

    }

    public double getRotationDegrees(){
        return rotationDegrees;
    }


}
