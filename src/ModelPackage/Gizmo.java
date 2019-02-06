package ModelPackage;

import Physics.Circle;
import Physics.LineSegment;

import java.awt.*;
import java.util.Set;

public interface Gizmo{

    String getGizmoType();

    int getStartxPosition();

    int getStartyPosition();

    int getEndxPosition();

    int getEndyPosition();

    Color getColour();

    double getRotation();

    Set<LineSegment> getComposingLines();

    double getReflectionCoef();

    Set<Circle> getComposingCircles();

}
