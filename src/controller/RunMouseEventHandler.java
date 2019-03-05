package controller;

import ModelPackage.GizmoType;
import ModelPackage.IModel;
import javafx.scene.input.MouseEvent;

public class RunMouseEventHandler implements MouseEventHandler{

    private IModel model;
    public RunMouseEventHandler(IModel m){
        model = m;
    }

    @Override
    public void handle(MouseEvent event) {

    }

    @Override
    public void addGizmo(GizmoType gizmoType) {

    }
}