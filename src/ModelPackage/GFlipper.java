package ModelPackage;

import Physics.Circle;
import Physics.LineSegment;
import javafx.scene.input.KeyEvent;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class GFlipper implements Gizmo{

    private int angleDegrees;
    private boolean isLeft;
    private boolean isActivated;
    private int xPosition;
    private int yPosition;
    final private double coefficent = 1;
    private HashSet<KeyEvent> keyBindings = new HashSet<>();
    private String id;
    private HashSet<Gizmo> connections = new HashSet<>();

    private HashSet<LineSegment> composingLines = new HashSet<>();
    private HashSet<Circle> composingCircles = new HashSet<>();

    //TODO Still to figure out how flipper line and circles will work

    public GFlipper(int xPosition, int yPosition, boolean isLeft){
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.isLeft = isLeft;
        if(isLeft){
            id = "LF" + xPosition + yPosition;
        }else{
            id = "RF" + xPosition + yPosition;
        }
    }

    public GFlipper(int xPosition, int yPosition, boolean isLeft, String id){
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.isLeft = isLeft;
        this.id = id;
    }

    private void addCircles(){

    }

    private void addLines(){

    }

    public void setAngleDegrees(int angleDegrees) {
        this.angleDegrees = angleDegrees;
    }

    
    public String getGizmoType() {
        if(isLeft) {
            return "LeftFlipper";
        }else{
            return "RightFlipper";
        }
    }

    
    public int getStartxPosition() {
        return xPosition;
    }

    
    public int getStartyPosition() {
        return yPosition;
    }

    
    public int getEndxPosition() {
        return xPosition;
    }

    
    public int getEndyPosition() {
        return yPosition;
    }

    
    public Color getColour() {
        return null;
    }

    
    public int getRotation() {
        return angleDegrees;
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
    public void Rotate() {
        //Does nothing for flipper
    }
}
