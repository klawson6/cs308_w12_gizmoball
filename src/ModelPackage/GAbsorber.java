package ModelPackage;

import Physics.Circle;
import Physics.LineSegment;
import Physics.Vect;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class GAbsorber implements Gizmo {

    private int xStart;
    private int yStart;
    private int xEnd;
    private int yEnd;
    final private double coefficent = 1;
    private HashSet<KeyEvent> keyBindings = new HashSet<>();
    private String id;
    private HashSet<Gizmo> connections = new HashSet<>();
    private Set<LineSegment> composingLines = new HashSet<>();
    private Set<Circle> composingCircles = new HashSet<>();
    private Ball absorbedBall;

    public GAbsorber(int xStart, int yStart, int xEnd, int yEnd){

        this.xStart = xStart;
        this.yStart = yStart;
        this.xEnd = xEnd;
        this.yEnd = yEnd;
        id = "A" + xStart + yStart + xEnd + yEnd;

        absorbedBall = null;
        addLines();
        addCircles();
        //FIXME - adding a default key binding for the absorber to shoot the ball?
        addKeyBinding(new KeyEvent(KeyEvent.KEY_PRESSED,"c","c ", KeyCode.C,false,false,false,false));

    }

    public GAbsorber(int xStart, int yStart, int xEnd, int yEnd, String id){

        this.xStart = xStart;
        this.yStart = yStart;
        this.xEnd = xEnd;
        this.yEnd = yEnd;
        this.id = id;

        absorbedBall = null;
        //FIXME - adding a default key binding for the absorber to shoot the ball?
        addKeyBinding(new KeyEvent(KeyEvent.KEY_PRESSED,"c","c ", KeyCode.C,false,false,false,false));
        addLines();
        addCircles();

    }

    private void addCircles(){

        Circle topLeft = new Circle(xStart,yStart,0);
        Circle topRight = new Circle(xEnd,yStart,0);
        Circle bottomLeft = new Circle(xStart,yEnd,0);
        Circle bottomRight = new Circle(xEnd,yEnd,0);

        composingCircles.add(topLeft);
        composingCircles.add(topRight);
        composingCircles.add(bottomLeft);
        composingCircles.add(bottomRight);

    }

    private void addLines(){
        //Add one to end as we get top left co-ordinate which will be one short of draw
        //Add one to compensate for this

        LineSegment topLine = new LineSegment(xStart,yStart,xEnd,yStart);
        LineSegment leftLine = new LineSegment(xStart,yStart,xStart,yEnd);
        LineSegment rightLine = new LineSegment(xEnd,yStart,xEnd,yEnd);
        LineSegment bottomLine = new LineSegment(xStart,yEnd,xEnd,yEnd);

        composingLines.add(topLine);
        composingLines.add(leftLine);
        composingLines.add(rightLine);
        composingLines.add(bottomLine);
    }
    
    public String getGizmoType() {
        return "Absorber";
    }

    
    public int getStartXPosition() {
        return xStart;
    }

    
    public int getStartyPosition() {
        return yStart;
    }

    public int getEndxPosition() {
        return xEnd;
    }

    public int getEndyPosition() {
        return yEnd;
    }

    public Color getColour() {
        return null;
    }

    public int getRotation() {
        return 0;
    }

    public Set<LineSegment> getComposingLines() {
        return composingLines;
    }

    public double getReflectionCoef() {
        return coefficent;
    }

    public Set<Circle> getComposingCircles() {
        return composingCircles;
    }

    public void addKeyBinding(KeyEvent key) {
        keyBindings.add(key);
    }

    public HashSet<KeyEvent> getKeybindings() {return keyBindings;}

    public String getId() {
        return id;
    }

    public void addGizmoConnection(Gizmo gizmo) {
        connections.add(gizmo);
    }

    public void removeGizmoConnection(Gizmo gizmo) {
        connections.remove(gizmo);
    }

    public Set<String> getGizmoConnectionIds() {
        Set<String> ids = new HashSet<>();
        for(Gizmo gizmos: connections){
            ids.add(gizmos.getId());
        }
        return ids;
    }

    @Override
    public void rotate() {
        //Does nothing for absorber
    }

    public void setAbsorbedBall(Ball b){

        absorbedBall = b;
    }

    public void moveAbsorbedBall(){
        if(absorbedBall != null) {

               absorbedBall.setCircle(getEndxPosition(), getStartyPosition() );
          //  absorbedBall.modifyVelocity(new Vect(0, 0));
             absorbedBall.stopBall();
        }
    }
    @Override
    public void activate(){
        if(absorbedBall != null) {
            absorbedBall.setCircle(getEndxPosition()-0.5, getEndyPosition()-1);
            absorbedBall.modifyVelocity(new Vect(0, -50));;
            System.out.println( absorbedBall.getVelocity());
            absorbedBall.startBall();
            absorbedBall = null;
        }
    }
}
