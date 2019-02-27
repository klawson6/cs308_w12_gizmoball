package controller;

import ModelPackage.IModel;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;

public class BuildMouseEventHandler implements MouseEventHandler{

    private IModel model;
    private Canvas canvas;
    private String actionType, gizmoType;
    public BuildMouseEventHandler(IModel m, Canvas c){
        model = m;
        canvas = c;
    }

    @Override
    public void handle(MouseEvent event) {
        switch(event.getEventType().getName()){
            case "MOUSE_CLICKED":
                if(actionType.equals("AddGizmo")){
                    addGizmoInternal(event, gizmoType);
                }
                break;
        }
    }

    private void addGizmoInternal(MouseEvent e, String gizmoType){

        double width = canvas.getWidth();
        double height = canvas.getHeight();
        double wGridSquareSize = width / 20;
        double hGridSquareSize = height / 20;

        int x = (int) (e.getX() / wGridSquareSize);
        int y = (int) (e.getY() / hGridSquareSize);

        model.createGizmo(gizmoType, x, y, x, y);
    }

    @Override
    public void addGizmo(String gizmoType) {
        this.actionType = "AddGizmo";
        this.gizmoType = gizmoType;
    }
}
