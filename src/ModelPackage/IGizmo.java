package ModelPackage;

import java.awt.*;
import java.util.HashSet;

public interface IGizmo {

    String getGizmoType();

    int getStartxPosition();

    int getStartyPosition();

    int getEndxPosition();

    int getEndyPosition();

    double getRotation();

    Color getColour();

}
