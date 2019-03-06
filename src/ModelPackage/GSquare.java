package ModelPackage;

import Physics.Circle;
import Physics.LineSegment;
import javafx.scene.input.KeyEvent;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class GSquare implements Gizmo {

    final private double coefficent = 1;
    private int xPos;
    private int yPos;
    private HashSet<KeyEvent> keyBindings = new HashSet<>();
    private String id;
    private HashSet<Gizmo> connections = new HashSet<>();
    private Set<LineSegment> composingLines = new HashSet<>();
    private Set<Circle> composingCircles = new HashSet<>();

    public GSquare(int xPos, int yPos){
        this.xPos = xPos;
        this.yPos = yPos;
        id = "S" + xPos + yPos;
        addLines();
        addCircles();
    }

    public GSquare(int xPos, int yPos, String id){
        this.xPos = xPos;
        this.yPos = yPos;
        this.id = id;
        addLines();
        addCircles();
    }

    private void addLines(){
        //Add one to end as we get top left co-ordinate which will be one short of draw
        //Add one to compensate for this

        LineSegment topLine = new LineSegment(xPos,yPos,xPos+1,yPos);
        LineSegment leftLine = new LineSegment(xPos,yPos,xPos,yPos+1);
        LineSegment rightLine = new LineSegment(xPos+1,yPos,xPos+1,yPos+1);
        LineSegment bottomLine = new LineSegment(xPos,yPos+1,xPos+1,yPos+1);

        composingLines.add(topLine);
        composingLines.add(leftLine);
        composingLines.add(rightLine);
        composingLines.add(bottomLine);
    }

    private void addCircles(){

        Circle topLeft = new Circle(xPos,yPos,0);
        Circle topRight = new Circle(xPos+1,yPos,0);
        Circle bottomLeft = new Circle(xPos,yPos+1,0);
        Circle bottomRight = new Circle(xPos+1,yPos+1,0);

        composingCircles.add(topLeft);
        composingCircles.add(topRight);
        composingCircles.add(bottomLeft);
        composingCircles.add(bottomRight);

    }

    public String getGizmoType() {
        return "Square";
    }

    public int getStartXPosition() {
        return xPos;
    }

    public int getStartyPosition() {
        return yPos;
    }

    public int getEndxPosition() {
        return xPos;
    }

    public int getEndyPosition() {
        return yPos;
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

    public void addKeyBinding(KeyEvent key) {keyBindings.add(key);}

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
        //Does nothing for square
    }

    @Override
    public void activate(){
        //Does nothing for Square
    }
}
