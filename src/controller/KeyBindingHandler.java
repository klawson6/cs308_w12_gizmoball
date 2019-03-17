package controller;

import ModelPackage.IGizmo;
import ModelPackage.IModel;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

import java.util.HashMap;
import java.util.Set;

public class KeyBindingHandler implements EventHandler<KeyEvent> {

    private IModel model;

    public KeyBindingHandler(IModel m) {
        model = m;
    }



    @Override
    public void handle(KeyEvent event) {


        Set<IGizmo> gizmos = model.getGizmoList();

            for (IGizmo g : gizmos) {

                HashMap<KeyEvent, String> allbindings = g.getKeybindings();
                Set<KeyEvent> bindings = allbindings.keySet();

                for (KeyEvent e : bindings) {

                    if (event.getCode().equals(e.getCode()) && event.getEventType().getName().equals(e.getEventType().getName())   ) {
                        model.activate(g);
                        //g.activate();
                        System.out.println("Keybind Pressed/Released");

                    }
                }

        }



    }
}
