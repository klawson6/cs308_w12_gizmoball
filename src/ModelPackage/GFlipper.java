package ModelPackage;

public class GFlipper extends Gizmo{

    private double angleDegrees;
    private boolean isLeft;
    private boolean isActivated;

    public GFlipper(int xPosition, int yPosition, boolean isLeft){
        super.setxPosition(xPosition);
        super.setyPosition(yPosition);
        this.isLeft = isLeft;
    }

    public void setAngleDegrees(double angleDegrees) {
        this.angleDegrees = angleDegrees;
    }

    public double getAngleDegrees() {
        return angleDegrees;
    }

}
