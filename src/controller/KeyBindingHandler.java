package controller;

import ModelPackage.Gizmo;
import ModelPackage.IModel;
import ModelPackage.Model;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class KeyBindingHandler implements EventHandler<KeyEvent> {

    private IModel model;
    public KeyBindingHandler(IModel m){
        model = m;
    }
    @Override
    public void handle(KeyEvent event)
        {

            Set<Gizmo> gizmos = model.getGizmoList();
            for(Gizmo g:gizmos){

                Set<KeyEvent> bindings = g.getKeybindings();
                for(KeyEvent e: bindings){

                    if(event.getCode().equals(e.getCode())){
                        System.out.println("Keybind pressed!");
                        g.activate();
                    }
                }
            }
        }
}
