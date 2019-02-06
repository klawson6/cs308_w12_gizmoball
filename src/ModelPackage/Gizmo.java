package ModelPackage;

import Physics.Circle;
import Physics.LineSegment;

import java.awt.*;
import java.util.Set;

public interface Gizmo{

    public String getGizmoType();

    public int getStartxPosition();

    public int getStartyPosition();

    public int getEndxPosition();

    public int getEndyPosition();

    public Color getColour();

    public Set<LineSegment> getComposingLines();

    public double getReflectionCoef();

    public Set<Circle> getComposingCircles();
}
