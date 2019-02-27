package controller;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public interface MouseEventHandler extends EventHandler<MouseEvent> {

    void addGizmo(String gizmoType);

}
