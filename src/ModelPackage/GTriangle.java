package ModelPackage;

import Physics.Circle;
import Physics.LineSegment;
import javafx.scene.input.KeyEvent;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class GTriangle implements Gizmo{

    private double rotationDegrees = 0;
    final private double coefficent = 1;
    private int xPosition;
    private int yPosition;
    private HashSet<KeyEvent> keyBindings = new HashSet<>();
    private String id;
    private HashSet<Gizmo> connections = new HashSet<>();

    public GTriangle(int xPosition, int yPosition){
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        id = "T" + xPosition + yPosition;
    }

    public GTriangle(int xPosition, int yPosition, String id){
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.id = id;
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


    
    public String getGizmoType() {
        return "Triangle";
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

    
    public double getRotation() {
        return rotationDegrees;
    }

    
    public Set<LineSegment> getComposingLines() {
        return null;
    }

    
    public double getReflectionCoef() {
        return coefficent;
    }

    
    public Set<Circle> getComposingCircles() {
        return null;
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
    public void Rotate(double degrees) {
        rotationDegrees = rotationDegrees + degrees;
    }
}
