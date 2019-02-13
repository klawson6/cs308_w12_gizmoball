package ModelPackage;

import Physics.Circle;
import Physics.LineSegment;
import javafx.scene.input.KeyEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public interface Gizmo extends IGizmo{

    String getGizmoType();

    int getStartxPosition();

    public int getEndxPosition();

    public int getEndyPosition();

    public int getRotation();

    public Color getColour();

    Set<LineSegment> getComposingLines();

    double getReflectionCoef();

    Set<Circle> getComposingCircles();

    void addKeyBinding(KeyEvent key);

    HashSet<KeyEvent> getKeybindings();

    String getId();

    void addGizmoConnection(Gizmo gizmo);

    void removeGizmoConnection(Gizmo gizmo);

    Set<String> getGizmoConnectionIds();

    void Rotate();

}
