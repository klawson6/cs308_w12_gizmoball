package controller;

import ModelPackage.GizmoType;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public interface MouseEventHandler extends EventHandler<MouseEvent> {

    void addGizmo(GizmoType gizmoType);

}
