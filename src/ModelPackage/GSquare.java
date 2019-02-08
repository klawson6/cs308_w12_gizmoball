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

    public GSquare(int xPos, int yPos){
        this.xPos = xPos;
        this.yPos = yPos;
        id = "S" + xPos + yPos;
    }

    public GSquare(int xPos, int yPos, String id){
        this.xPos = xPos;
        this.yPos = yPos;
        this.id = id;
    }

    public String getGizmoType() {
        return null;
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
        return null;
    }

    public double getReflectionCoef() {
        return coefficent;
    }

    public Set<Circle> getComposingCircles() {
        return null;
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
    public void Rotate(double degrees) {
        //Does nothing for square
    }
}
