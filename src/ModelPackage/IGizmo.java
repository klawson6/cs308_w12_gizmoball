package ModelPackage;

import javafx.scene.input.KeyEvent;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;

public interface IGizmo {

    String getGizmoType();

    int getStartXPosition();

    int getStartyPosition();

    int getEndxPosition();

    int getEndyPosition();

    int getRotation();

    Color getColour();

    void activate();

    void deactivate();

    HashMap<KeyEvent, String> getKeybindings();

}
