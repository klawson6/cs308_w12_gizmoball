package ModelPackage;

import java.awt.*;
import java.util.HashSet;

public interface IGizmo {

    String getGizmoType();

    int getStartXPosition();

    int getStartyPosition();

    int getEndxPosition();

    int getEndyPosition();

    int getRotation();

    Color getColour();

}
