package ModelPackage;

import Physics.Circle;
import Physics.LineSegment;
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

    public GAbsorber(int xStart, int yStart, int xEnd, int yEnd){

        this.xStart = xStart;
        this.yStart = yStart;
        this.xEnd = xEnd;
        this.yEnd = yEnd;
        id = "A" + xStart + yStart + xEnd + yEnd;

    }

    public GAbsorber(int xStart, int yStart, int xEnd, int yEnd, String id){

        this.xStart = xStart;
        this.yStart = yStart;
        this.xEnd = xEnd;
        this.yEnd = yEnd;
        this.id = id;

    }

    
    public String getGizmoType() {
        return "Absorber";
    }

    
    public int getStartxPosition() {
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

    public double getRotation() {
        return 0;
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
    public void Rotate(double degrees) {
        //Does nothing for absorber
    }
}
