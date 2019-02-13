package ModelPackage;

import Physics.Circle;
import Physics.LineSegment;
import javafx.scene.input.KeyEvent;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class GCircle implements Gizmo {

    private final int radius = 1;
    final private double coefficent = 1;
    private int xPos;
    private int yPos;
    private HashSet<KeyEvent> keyBindings = new HashSet<>();
    private String id;
    private HashSet<Gizmo> connections = new HashSet<>();
    private HashSet<Circle> composingCircles = new HashSet<>();

    public GCircle(int xPos, int yPos){
        this.xPos = xPos;
        this.yPos = yPos;
        id = "C" + xPos + yPos;
        addCircles();
    }

    public GCircle(int xPos, int yPos, String id){
        this.xPos = xPos;
        this.yPos = yPos;
        this.id = id;
        addCircles();
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
        return "Circle";
    }

    
    public int getStartxPosition() {
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

    
    public double getRotation() {
        return 0;
    }

    
    public Set<LineSegment> getComposingLines() {
        //Circles doesn't need any lines so empty list
        return new HashSet<>();
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

    public HashSet<KeyEvent> getKeybindings() {
        return keyBindings;
    }

    public String getId() {
        return id;
    }

    @Override
    public void addGizmoConnection(Gizmo gizmo) {
        connections.add(gizmo);
    }

    @Override
    public void removeGizmoConnection(Gizmo gizmo) {
        connections.remove(gizmo);
    }

    @Override
    public Set<String> getGizmoConnectionIds() {
        Set<String> ids = new HashSet<>();
        for(Gizmo gizmos: connections){
            ids.add(gizmos.getId());
        }
        return ids;
    }

    @Override
    public void rotate() {
    }
}
