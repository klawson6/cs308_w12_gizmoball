package controller;

import ModelPackage.Gizmo;
import ModelPackage.IGizmo;
import ModelPackage.IModel;
import ModelPackage.Model;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.awt.event.KeyListener;
import java.security.Key;
import java.util.*;

public class KeyBindingHandler implements EventHandler<KeyEvent> {

    private IModel model;
    public KeyBindingHandler(IModel m){
        model = m;
    }
    boolean releaseyet = false;

    @Override
    public void handle(KeyEvent event)
        {

            Set<IGizmo> gizmos = model.getGizmoList();
            for(IGizmo g:gizmos) {

                HashMap<KeyEvent, String> allbindings = g.getKeybindings();
                Set<KeyEvent> bindings = allbindings.keySet();

                for (KeyEvent e : bindings) {
                    if (event.getCode().equals(e.getCode())) {
                        System.out.println("Keybind released!");
                        g.activate();
                    }
                }
            }
                event.consume();


        }


}
