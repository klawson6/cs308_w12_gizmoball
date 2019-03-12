package ModelPackage;

import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.util.HashMap;

public interface IGizmo {

    GizmoType getGizmoType();

    int getStartxPosition();

    int getStartyPosition();

    int getEndxPosition();

    int getEndyPosition();

    int getRotation();

    Color getColour();

    void activate();

    void deactivate();

    HashMap<KeyEvent, String> getKeybindings();

//    boolean addKeyBinding(KeyEvent key, String action);
//    boolean removeKeyBinding(KeyEvent key);

}
